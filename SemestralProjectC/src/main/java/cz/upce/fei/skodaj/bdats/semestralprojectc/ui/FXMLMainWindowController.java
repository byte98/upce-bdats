/*
 * Copyright (C) 2022 Jiri Skoda <jiri.skoda@student.upce.cz>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package cz.upce.fei.skodaj.bdats.semestralprojectc.ui;

import cz.upce.fei.skodaj.bdats.semestralprojectc.data.Generator;
import cz.upce.fei.skodaj.bdats.semestralprojectc.data.IPamatky;
import cz.upce.fei.skodaj.bdats.semestralprojectc.data.Location;
import cz.upce.fei.skodaj.bdats.semestralprojectc.data.Pamatky;
import cz.upce.fei.skodaj.bdats.semestralprojectc.data.Zamek;
import cz.upce.fei.skodaj.bdats.semestralprojectc.files.IStateLoader;
import cz.upce.fei.skodaj.bdats.semestralprojectc.files.IStateSaver;
import cz.upce.fei.skodaj.bdats.semestralprojectc.files.XMLLoader;
import cz.upce.fei.skodaj.bdats.semestralprojectc.files.XMLSaver;
import cz.upce.fei.skodaj.bdats.semestralprojectc.structs.eTypProhl;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller of main window
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class FXMLMainWindowController implements Initializable
{        
    /**
     * Saver of current state of program
     */
    private IStateSaver stateSaver;
    
    /**
     * Loader of current state of program
     */
    private IStateLoader stateLoader;
    
    /**
     * Manager of data
     */
    private final IPamatky data = new Pamatky();
    
    /**
     * Actual location
     */
    private Location actualLocation;
    
    /**
     * Actually used order for browsing data
     */
    private eTypProhl order = eTypProhl.DO_SIRKY;
    
    /**
     * Searched castle
     */
    private Zamek searched;
    
    //<editor-fold defaultstate="collapsed" desc="FXML">
    @FXML
    private Button buttonNewCastle;
    
    @FXML
    private Button buttonGenerate;
    
    @FXML
    private ListView<Zamek> listViewContent;
    
    @FXML
    private Button buttonSave;
    
    @FXML
    private Button buttonSaveToFile;
    
    @FXML
    private Button buttonLoad;
    
    @FXML
    private Button buttonLoadFromFile;

    @FXML
    private Button buttonRemove;
    
    @FXML
    private Label labelCounter;
    
    @FXML
    private Button buttonOrder;
    
    @FXML
    private HBox hBoxResults;
        
    //</editor-fold>
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        this.stateSaver = new XMLSaver();
        this.stateLoader = new XMLLoader();
        this.stateSaver.setManager(this.data);
        this.stateSaver.setOrder(this.order);
        this.listViewContent.setItems(FXCollections.observableArrayList());
        this.buttonOrder.setText("Prohlídka: " + (this.order == eTypProhl.DO_SIRKY ? "do šířky": "do hloubky"));
        this.displayData();
        this.hBoxResults.setVisible(false);
        this.listViewContent.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.listViewContent.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Zamek>(){
            @Override
            public void changed(ObservableValue<? extends Zamek> ov, Zamek t, Zamek t1)
            {
                if (listViewContent.getSelectionModel().isEmpty())
                {
                    stateSaver.unsetSelected();
                }
                else
                {
                    stateSaver.setSelected(t1);
                }
                buttonRemove.setDisable(listViewContent.getSelectionModel().getSelectedIndex() != 0);
            }
        
        });
        this.showPositionDialog(false);
    }
    
    /**
     * Displays data
     */
    private void displayData()
    {
        int counter = 0;
        Zamek selected = this.listViewContent.getSelectionModel().getSelectedItem();
        this.listViewContent.getItems().clear();
        Iterator<Zamek> it = this.data.vytvorIterator(this.order);
        while (it.hasNext())
        {
            Zamek z = it.next();
            this.listViewContent.getItems().add(z);
            if (Objects.nonNull(selected) && Objects.nonNull(z) && selected.equals(z))
            {
                this.listViewContent.getSelectionModel().select(z);
            }
            counter++;
        }
        this.labelCounter.setText(String.valueOf(counter));
    }
    
    /**
     * Shows add castle dialog
     */
    private void showAddCastleDialog()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("FXMLAddCastleDialog.fxml"));
            Parent dialog = loader.load();
            FXMLAddCastleDialogController controller = loader.getController();
            Scene scene = new Scene(dialog);
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(ProgPamatky.primaryStage);
            dialogStage.setResizable(false);
            dialogStage.setScene(scene);
            dialogStage.setTitle("Vložit nový zámek");
            controller.init();
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();
            if (controller.getOkClicked() == true)
            {
                Zamek z = new Zamek(controller.getName(), controller.getLocation());
                if (Objects.nonNull(z))
                {
                    this.data.vlozZamek(z);
                }
                this.displayData();
            }
        }
        catch (IOException ex)
        {
            Logger.getLogger(FXMLMainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void buttonNewCastle_onAction(ActionEvent event)
    {
        this.showAddCastleDialog();
    }
    
    @FXML
    private void buttonGenerate_onAction(ActionEvent event)
    {
        this.showGenerateDialog();
    }
    
    @FXML
    private void buttonSave_onAction(ActionEvent event)
    {
        this.stateSaver.save();
    }
    
    @FXML
    private void buttonSaveToFile_onAction(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(ProgPamatky.FILE_SAVE).getParentFile());
        fileChooser.setInitialFileName(new File(ProgPamatky.FILE_SAVE).getName());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Soubor XML", "*.xml"));
        File selectedFile = fileChooser.showSaveDialog(ProgPamatky.primaryStage);
        if (Objects.nonNull(selectedFile))
        {
            this.stateSaver.save(selectedFile.getAbsolutePath());
        }
    }
    
    @FXML
    private void buttonLoad_onAction(ActionEvent event)
    {
        this.stateLoader.load();
        this.loadState();
    }
    
    @FXML
    private void buttonLoadFromFile_onAction(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(ProgPamatky.FILE_SAVE).getParentFile());
        fileChooser.setInitialFileName(new File(ProgPamatky.FILE_SAVE).getName());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Soubor XML", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(ProgPamatky.primaryStage);
        if (Objects.nonNull(selectedFile))
        {
            this.stateLoader.load(selectedFile.getAbsolutePath());
            this.loadState();
        }
    }
     
    /**
     * Shows generate dialog
     */
    private void showGenerateDialog()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("FXMLGenerateDialog.fxml"));
            Parent dialog = loader.load();
            FXMLGenerateController controller = loader.getController();
            Scene scene = new Scene(dialog);
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(ProgPamatky.primaryStage);
            dialogStage.setResizable(false);
            dialogStage.setScene(scene);
            dialogStage.setTitle("Generovat zámky");
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();
            if (controller.getOkClicked() == true)
            {
                Zamek[] generated = new Zamek[0];
                if (controller.useKnown())
                {
                    generated = Generator.getInstance().generateKnown(controller.getResult());
                }
                else
                {
                    generated = Generator.getInstance().generateRandom(controller.getResult());
                }
                
                for (Zamek z: generated)
                {
                    this.data.vlozZamek(z);
                }
                this.displayData();
            }
        }
        catch (IOException ex)
        {
            Logger.getLogger(FXMLMainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void buttonDetail_onAction(ActionEvent e)
    {
        this.showDetailDialog();
    }
    
    private void showDetailDialog()
    {
        if (this.listViewContent.getSelectionModel().isEmpty() == false)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getClassLoader().getResource("FXMLDetailDialog.fxml"));
                Parent dialog = loader.load();
                FXMLDetailDialogController controller = loader.getController();
                Scene scene = new Scene(dialog);
                Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(ProgPamatky.primaryStage);
                dialogStage.setResizable(false);
                dialogStage.setScene(scene);
                dialogStage.setTitle("Detail zámku");
                controller.setDialogStage(dialogStage);
                controller.init();
                controller.setCastle(this.listViewContent.getSelectionModel().getSelectedItem());
                dialogStage.showAndWait();
            }
            catch (IOException ex)
            {
                Logger.getLogger(FXMLMainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML
    private void buttonOrder_onAction(ActionEvent e)
    {
        if (this.order == eTypProhl.DO_SIRKY)
        {
            this.order = eTypProhl.DO_HLOUBKY;
        }
        else
        {
            this.order = eTypProhl.DO_SIRKY;
        }
        this.buttonOrder.setText("Prohlídka: " + (this.order == eTypProhl.DO_SIRKY ? "do šířky": "do hloubky"));
        this.displayData();
        this.stateSaver.setOrder(this.order);
    }
           
    /**
     * Shows position dialog
     * @param cancel Flag, whether cancel option should be active
     */
    private void showPositionDialog(boolean cancel)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("FXMLPositionDialog.fxml"));
            Parent dialog = loader.load();
            FXMLPositionDialogController controller = loader.getController();
            Scene scene = new Scene(dialog);
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(ProgPamatky.primaryStage);
            dialogStage.setResizable(false);
            dialogStage.setScene(scene);
            dialogStage.setTitle("Aktuální pozice");
            controller.setDialogStage(dialogStage);
            controller.init();
            controller.setCancelEnabled(cancel);
            dialogStage.showAndWait();
            if (controller.getOkClicked() == true)
            {
                this.actualLocation = controller.getLocation();
                this.data.nastavPozici(this.actualLocation);
                this.stateSaver.setActualLocation(this.actualLocation);
                this.displayData();
            }
        }
        catch (IOException ex)
        {
            Logger.getLogger(FXMLMainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void buttonNearest_onAction(ActionEvent e)
    {
        this.showPositionDialog(true);
    }
    
    @FXML
    private void buttonRemove_onAction(ActionEvent e)
    {
        if (this.listViewContent.getSelectionModel().isEmpty() == false)
        {
            if (this.listViewContent.getSelectionModel().getSelectedItem().equals(this.data.zpristupniZamek()))
            // As defined in interface IPamatky, only the nearest castle can be removed
            {
                this.data.odeberZamek();
            }
            this.displayData();
        }
    }
    
    @FXML
    private void buttonDelete_onAction(ActionEvent e)
    {
        this.data.zrus();
        this.displayData();
    }
    
    @FXML
    private void buttonImport_onAction(ActionEvent e)
    {
        this.data.importDatZTXT();
        this.displayData();
    }
    
    
    /**
     * Loads state of program 
     */
    private void loadState()
    {
        this.data.zrus();
        this.displayData();
        if (this.stateLoader.loaded())
        {
            this.order = this.stateLoader.getOrder();
            this.buttonOrder.setText("Prohlídka: " + (this.order == eTypProhl.DO_SIRKY ? "do šířky": "do hloubky"));
            this.actualLocation = this.stateLoader.getActualLocation();
            this.stateLoader.getData(this.data);
            this.displayData();
            if (this.stateLoader.hasSelected())
            {
                this.listViewContent.getSelectionModel().select(this.stateLoader.getSelected());
            this.listViewContent.getFocusModel().focus(this.listViewContent.getSelectionModel().getSelectedIndex());
            this.listViewContent.scrollTo(this.stateLoader.getSelected());
            }
            
            
        }
    }
}
