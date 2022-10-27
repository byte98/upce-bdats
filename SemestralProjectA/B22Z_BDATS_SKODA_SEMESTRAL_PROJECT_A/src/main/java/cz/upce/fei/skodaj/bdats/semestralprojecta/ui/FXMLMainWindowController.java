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
package cz.upce.fei.skodaj.bdats.semestralprojecta.ui;

import cz.upce.fei.skodaj.bdats.semestralprojecta.data.Generator;
import cz.upce.fei.skodaj.bdats.semestralprojecta.data.IVyrobniProces;
import cz.upce.fei.skodaj.bdats.semestralprojecta.data.Proces;
import cz.upce.fei.skodaj.bdats.semestralprojecta.data.VyrobniProces;
import cz.upce.fei.skodaj.bdats.semestralprojecta.data.enumPozice;
import cz.upce.fei.skodaj.bdats.semestralprojecta.data.enumReorg;
import cz.upce.fei.skodaj.bdats.semestralprojecta.files.IStateLoader;
import cz.upce.fei.skodaj.bdats.semestralprojecta.files.IStateSaver;
import cz.upce.fei.skodaj.bdats.semestralprojecta.files.XMLLoader;
import cz.upce.fei.skodaj.bdats.semestralprojecta.files.XMLSaver;
import cz.upce.fei.skodaj.bdats.semestralprojecta.structs.AbstrLifo;
import cz.upce.fei.skodaj.bdats.semestralprojecta.structs.IAbstrLifo;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
     * Base value for height of rows in list view
     */
    private static final double BASEHEIGHT = 32;
    
    /**
     * Background for odd rows in list
     */
    private static final Color NORMAL_ODD = Color.rgb(215, 215, 215);
    
    /**
     * Background for even rows in list
     */
    private static final Color NORMAL_EVEN = Color.rgb(250, 250, 250);
    
    /**
     * Background of row with actual process
     */
    private static final Color ACTUAL = Color.rgb(16, 16, 128);
    
    /**
     * Background of row with candidate for reorganization
     */
    private static final Color CANDIDATE = Color.rgb(16, 128, 16);
    
    /**
     * Manager of all processes
     */
    private IVyrobniProces manager;
    
    /**
     * Structure with candidates for reorganization
     */
    private IAbstrLifo<Proces> reorganize;
    
    /**
     * List of all processes
     */
    private ObservableList<Proces> processes;
    
    /**
     * Saver of current state of program
     */
    private IStateSaver stateSaver;
    
    /**
     * Loader of current state of program
     */
    private IStateLoader stateLoader;
    
    //<editor-fold defaultstate="collapsed" desc="FXML">
    @FXML
    private Button buttonNewProcess;
    
    @FXML
    private Button buttonGenerate;
    
    @FXML
    private ListView<Proces> listViewContent;
    
    @FXML
    private Label labelProcesses;
    
    @FXML
    private Label labelTime;
    
    @FXML
    private Button buttonFirst;
    
    @FXML
    private Button buttonPrevious;
    
    @FXML
    private Button buttonNext;
    
    @FXML
    private Button buttonLast;
    
    @FXML
    private Button buttonAggregate;
    
    @FXML
    private Button buttonDecompose;
    
    @FXML
    private Button buttonReorganize;
    
    @FXML
    private Label labelReorganize;
    
    @FXML
    private Label labelReorganizeType;
    
    @FXML
    private Button buttonImport;
    
    @FXML
    private Button buttonImportFromFile;
    
    @FXML
    private Button buttonRemoveFirst;
    
    @FXML
    private Button buttonRemovePrevious;
    
    @FXML
    private Button buttonRemoveActual;
    
    @FXML
    private Button buttonRemoveNext;
    
    @FXML
    private Button buttonRemoveLast;
    
    @FXML
    private Button buttonRemoveAll;
    
    @FXML
    private Button buttonSave;
    
    @FXML
    private Button buttonSaveToFile;
    
    @FXML
    private Button buttonLoad;
    
    @FXML
    private Button buttonLoadFromFile;
    //</editor-fold>
    
    /**
     * Index of selected item
     */
    private int selectedIdx = 0;
    
    /**
     * Type of reorganization
     */
    private enumReorg reorgType = enumReorg.AGREGACE;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        this.stateSaver = new XMLSaver();
        this.stateLoader = new XMLLoader();
        this.selectedIdx = 0;
        this.manager = new VyrobniProces();
        this.processes = FXCollections.observableArrayList();
        this.listViewContent.setItems(this.processes);
        this.listViewContent.setCellFactory(lst -> new ListCell<Proces>(){
            @Override
            protected void updateItem(Proces item, boolean empty)
            {
                super.updateItem(item, empty);
                
                this.setFont(new Font("Tahoma", 14));
                if (empty)
                {
                    this.setPrefHeight(FXMLMainWindowController.BASEHEIGHT);
                    this.setText(null);
                }
                else
                {
                    this.setPrefHeight(FXMLMainWindowController.BASEHEIGHT);
                    this.setText(item.toString());
                }
                this.setTextFill(Color.BLACK);
                if (Objects.nonNull(manager.zpristupniProces(enumPozice.AKTUALNI)) &&
                    Objects.nonNull(item) &&
                    item.equals(manager.zpristupniProces(enumPozice.AKTUALNI)))
                {
                    this.setBackground(
                            new Background(
                                new BackgroundFill(
                                        FXMLMainWindowController.ACTUAL,
                                        CornerRadii.EMPTY,
                                        Insets.EMPTY
                                )
                            )
                    );
                    this.setTextFill(Color.WHITE);
                    selectedIdx = this.getIndex();
                }
                else if (isStructEmpty(reorganize) == false && isCandidate(item))
                {
                    this.setBackground(
                            new Background(
                                new BackgroundFill(
                                        FXMLMainWindowController.CANDIDATE,
                                        CornerRadii.EMPTY,
                                        Insets.EMPTY
                                )
                            )
                    );
                    this.setTextFill(Color.WHITE);
                }
                else if (this.getIndex() % 2 == 1 && empty == false)
                {
                    this.setBackground(
                            new Background(
                                new BackgroundFill(
                                        FXMLMainWindowController.NORMAL_ODD,
                                        CornerRadii.EMPTY,
                                        Insets.EMPTY
                                )
                            )
                    );
                }
                else
                {
                    this.setBackground(
                            new Background(
                                new BackgroundFill(
                                        FXMLMainWindowController.NORMAL_EVEN,
                                        CornerRadii.EMPTY,
                                        Insets.EMPTY
                                )
                            )
                    );
                }
            }
        });
        //this.listViewContent.setMouseTransparent( true );
        this.listViewContent.setFocusTraversable( false );
        this.reorganize = new AbstrLifo<>();
        
        
    }
    
    @FXML
    private void buttonNewProcess_onAction(ActionEvent event)
    {
        this.showAddProcessDialog();
    }
    
    
    @FXML
    private void buttonGenerate_onAction(ActionEvent event)
    {
        this.showGenerateDialog();
    }
    
    /**
     * Shows add process dialog
     */
    private void showAddProcessDialog()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("FXMLAddProcessDialog.fxml"));
            Parent dialog = loader.load();
            Scene scene = new Scene(dialog);
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(ProgVyrobniProces.primaryStage);
            dialogStage.setResizable(false);
            dialogStage.setScene(scene);
            FXMLAddProcessDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();
            if (controller.okClicked() && Objects.nonNull(controller.getResult()))
            {
                this.manager.vlozProces(controller.getResult(), controller.getPosition());
                this.displayProcesses();
            }
        }
        catch (IOException ex)
        {
            Logger.getLogger(FXMLMainWindowController.class.getName()).log(Level.SEVERE, null, ex);
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
            Scene scene = new Scene(dialog);
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(ProgVyrobniProces.primaryStage);
            dialogStage.setResizable(false);
            dialogStage.setScene(scene);
            FXMLGenerateDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();
            if (controller.okClicked() && controller.getCount() > 0)
            {
                Generator.addToStructure(this.manager, controller.getPosition(), controller.getCount());
                this.displayProcesses();
            }
        }
        catch (IOException ex)
        {
            Logger.getLogger(FXMLMainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Shows reorganize dialog
     * @param type Type of reorganization
     */
    private void showReorganizeDialog(enumReorg type)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("FXMLReorganizeDialog.fxml"));
            Parent dialog = loader.load();
            Scene scene = new Scene(dialog);
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(ProgVyrobniProces.primaryStage);
            dialogStage.setResizable(false);
            dialogStage.setScene(scene);
            FXMLReorganizeDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setType(type);
            dialogStage.showAndWait();
            if (controller.okClicked())
            {
                this.setReorganization(controller.getType(), manager.vytipujKandidatiReorg(controller.getTime(), controller.getType()));
                this.showReorganization();                
            }
        }
        catch (IOException ex)
        {
            Logger.getLogger(FXMLMainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Displays all processes
     */
    private void displayProcesses()
    {
        int counter = 0;
        int timer = 0;
        int candidates = 0;
        this.processes.clear();
        Iterator<Proces> it = this.manager.iterator();
        while(it.hasNext())
        {
            Proces p = it.next();
            counter++;
            timer += p.getCas();
            this.processes.add(p);
            if (this.isCandidate(p))
            {
                candidates++;
            }
        }
        this.labelProcesses.setText(String.valueOf(counter));
        this.labelTime.setText(String.valueOf(timer));
        this.labelReorganize.setText(String.valueOf(candidates));
    }
    
    @FXML
    private void buttonFirst_onAction(ActionEvent event)
    {
        this.navigate(enumPozice.PRVNI);
    }
    
    @FXML
    private void buttonPrevious_onAction(ActionEvent event)
    {
        this.navigate(enumPozice.PREDCHUDCE);
    }
    
    @FXML
    private void buttonNext_onAction(ActionEvent event)
    {
        this.navigate(enumPozice.NASLEDNIK);
    }
    
    @FXML
    private void buttonLast_onAction(ActionEvent event)
    {
        this.navigate(enumPozice.POSLEDNI);
    }
    
    /**
     * Performs navigation between processes
     * @param position Position of destination of navigation
     */
    private void navigate(enumPozice position)
    {
        if (this.isStructEmpty(this.manager) == false)
        {
            this.manager.zpristupniProces(position);
            this.saveActual();
            this.displayProcesses();
        }
    }
    
    /**
     * Saves actual process
     */
    private void saveActual()
    {
        if (this.isStructEmpty(this.manager) == false)
        {
            this.stateSaver.setActual(this.manager.zpristupniProces(enumPozice.AKTUALNI));
        }
        else
        {
            this.stateSaver.unsetActual();
        }
    }
    
    /**
     * Checks, whether process is candidate for reorganization
     * @param p Process which will be checked
     * @return TRUE if process is candidate for reorganization, FALSE otherwise
     */
    private boolean isCandidate(Proces p)
    {
        boolean reti = false;
        Iterator<Proces> it = this.reorganize.iterator();
        while (it.hasNext())
        {
            if (it.next().equals(p))
            {
                reti = true;
                break;
            }
        }
        return reti;
    }
    
    /**
     * Checks, whether structure is empty
     * @param structure Structure which will be checked
     * @returns TRUE structure is empty, FALSE otherwise
     */
    private boolean isStructEmpty(Iterable structure)
    {
        int counter = 0;
        Iterator it = structure.iterator();
        while (it.hasNext())
        {
            if (counter > 0)
            {
                break;
            }
            it.next();
            counter++;
        }
        return counter == 0;
    }
    
    @FXML
    private void buttonAggregate_onAction(ActionEvent event)
    {
        this.reorgType = enumReorg.AGREGACE;
        this.showReorganizeDialog(this.reorgType);
    }
    
    @FXML
    private void buttonDecompose_onAction(ActionEvent event)
    {
        this.reorgType = enumReorg.DEKOMPOZICE;
        this.showReorganizeDialog(this.reorgType);
    }
    
    @FXML
    private void buttonReorganize_onAction(ActionEvent event)
    {
        manager.reorganizace(reorgType, reorganize);
        this.cancelReorganization();
        this.displayProcesses();
    }
    
    @FXML
    private void buttonImport_onAction(ActionEvent event)
    {
        this.manager.importDat(new File(ProgVyrobniProces.FILE_IMPORT).getAbsolutePath());
        this.cancelReorganization();
        this.displayProcesses();
    }
    
    @FXML
    private void buttonImportFromFile_onAction(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(ProgVyrobniProces.FILE_IMPORT).getParentFile());
        fileChooser.setInitialFileName(new File(ProgVyrobniProces.FILE_IMPORT).getName());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Soubor CSV", "*.csv"));
        File selectedFile = fileChooser.showOpenDialog(ProgVyrobniProces.primaryStage);
        if (Objects.nonNull(selectedFile))
        {
            this.manager.importDat(selectedFile.getAbsolutePath());
            this.cancelReorganization();
            this.displayProcesses();
        }
    }
    
    @FXML
    private void buttonRemoveFirst_onAction(ActionEvent event)
    {
        this.removeProcess(enumPozice.PRVNI);
    }
    
    @FXML
    private void buttonRemovePrevious_onAction(ActionEvent event)
    {
        this.removeProcess(enumPozice.PREDCHUDCE);
    }
    
    @FXML
    private void buttonRemoveActual_onAction(ActionEvent event)
    {
        this.removeProcess(enumPozice.AKTUALNI);
    }
    
    @FXML
    private void buttonRemoveNext_onAction(ActionEvent event)
    {
        this.removeProcess(enumPozice.NASLEDNIK);
    }
    
    @FXML
    private void buttonRemoveLast_onAction(ActionEvent event)
    {
        this.removeProcess(enumPozice.POSLEDNI);
    }
    
    @FXML
    private void buttonRemoveAll_onAction(ActionEvent event)
    {
        this.manager.zrus();
        this.cancelReorganization();
        this.saveActual();
        this.displayProcesses();
    }
    
    /**
     * Cancels reorganization
     */
    private void cancelReorganization()
    {
        this.reorganize.zrus();
        this.buttonReorganize.setDisable(true);
        this.stateSaver.unsetReorganization();
        this.labelReorganizeType.setText("(žádná)");
        this.labelReorganize.setText(String.valueOf(0));
    }
    
    /**
     * Sets reorganization
     * @param type Type of reorganization
     * @param candidates Candidates for reorganization
     */
    private void setReorganization(enumReorg type, IAbstrLifo<Proces> candidates)
    {
        this.reorganize.zrus();
        this.reorganize = candidates;
        this.reorgType = type;
        this.stateSaver.setReorganization(this.reorgType, this.reorganize);
        
    }
    
    /**
     * Shows actual reorganization
     */
    private void showReorganization()
    {
        this.buttonReorganize.setDisable(this.isStructEmpty(this.reorganize));
        this.labelReorganizeType.setText(
                this.reorgType == enumReorg.AGREGACE
                ? "agregace"
                : "dekompozice"
        );
        this.labelReorganize.setText(String.valueOf(this.countStructure(this.reorganize)));
        this.displayProcesses();
    }
    
    /**
     * Counts items in structure
     * @param struct Structure which items will be count
     * @return Number of items stored in structure
     */
    private int countStructure(Iterable struct)
    {
        int reti = 0;
        Iterator it = struct.iterator();
        while (it.hasNext())
        {
            reti++;
            it.next();
        }
        return reti;
    }
    
    /**
     * Removes process
     * @param position Position of process 
     */
    private void removeProcess(enumPozice position)
    {
        this.manager.odeberProces(position);
        this.cancelReorganization();
        this.displayProcesses();
    }
    
    @FXML
    private void buttonSave_onAction(ActionEvent event)
    {
        this.stateSaver.save(this.manager, ProgVyrobniProces.FILE_SAVE);
    }
    
    @FXML
    private void buttonSaveToFile_onAction(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(ProgVyrobniProces.FILE_SAVE).getParentFile());
        fileChooser.setInitialFileName(new File(ProgVyrobniProces.FILE_SAVE).getName());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Soubor XML", "*.xml"));
        File selectedFile = fileChooser.showSaveDialog(ProgVyrobniProces.primaryStage);
        if (Objects.nonNull(selectedFile))
        {
            this.stateSaver.save(this.manager, ProgVyrobniProces.FILE_SAVE);
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
        fileChooser.setInitialDirectory(new File(ProgVyrobniProces.FILE_SAVE).getParentFile());
        fileChooser.setInitialFileName(new File(ProgVyrobniProces.FILE_SAVE).getName());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Soubor XML", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(ProgVyrobniProces.primaryStage);
        if (Objects.nonNull(selectedFile))
        {
            this.stateLoader.load(selectedFile.getAbsolutePath());
            this.loadState();
        }
    }
    
    /**
     * Loads state of program
     */
    private void loadState()
    {
        if (this.stateLoader.loaded() == false)
        {
            this.stateLoader.load();
        }
        if (this.stateLoader.loaded())
        {
            this.cancelReorganization();
            this.manager.zrus();
            
            // Load all processes
            for (Proces p: this.stateLoader.getProcesses())
            {
                this.manager.vlozProces(p, enumPozice.POSLEDNI);
            }
            
            // Select actual process
            if (Objects.nonNull(this.stateLoader.getActual()))
            {
                Iterator<Proces> it = this.manager.iterator();
                this.manager.zpristupniProces(enumPozice.PRVNI);
                while (it.hasNext())
                {
                    if (this.manager.zpristupniProces(enumPozice.AKTUALNI).equals(this.stateLoader.getActual()))
                    {
                        break;
                    }
                    else
                    {
                        this.manager.zpristupniProces(enumPozice.NASLEDNIK);
                        it.next();
                    }
                    
                }
            }
            
            // Load reorganization
            if (this.stateLoader.getReorgActive())
            {
                this.setReorganization(this.stateLoader.getReorgType(), this.stateLoader.getReorgList());
                this.showReorganization();
            }          
            
            // Set counter of processes
            for (int i = 0; i <= this.countStructure(this.manager); i++)
            {
                Proces.incrementCounter();
            }
        }
        this.displayProcesses();  
    }
    
    
}
