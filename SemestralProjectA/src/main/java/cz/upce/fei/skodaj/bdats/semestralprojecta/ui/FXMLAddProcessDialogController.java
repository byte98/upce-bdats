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

import cz.upce.fei.skodaj.bdats.semestralprojecta.data.Proces;
import cz.upce.fei.skodaj.bdats.semestralprojecta.data.ProcesManualni;
import cz.upce.fei.skodaj.bdats.semestralprojecta.data.ProcesRoboticky;
import cz.upce.fei.skodaj.bdats.semestralprojecta.data.enumPozice;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class FXMLAddProcessDialogController implements Initializable
{
    
    @FXML
    private Spinner<Integer> spinnerTime;
    
    @FXML
    private ChoiceBox<String> choiceBoxType;
    
    @FXML
    private Spinner<Integer> spinnerPeople;
    
    @FXML
    private Button buttonOK;
    
    @FXML
    private Button buttonCancel;
    
    @FXML
    private Label labelPosition;
    
    @FXML
    private Slider sliderPosition;
    
    /**
     * Selected position of new process
     */
    private enumPozice position = enumPozice.PRVNI;
    
    /**
     * List of all available types of processes
     */
    private ObservableList<String> availableTypes;
    
    /**
     * Flag, whether OK button has been clicked
     */
    private boolean okClicked = false;
    
    /**
     * Result of dialog
     */
    private Proces result;
    
    /**
     * Stage for dialog
     */
    private Stage dialogStage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        
        this.spinnerTime.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE));
        this.choiceBoxType.getItems().add("ROBOTICKÝ");
        this.choiceBoxType.getItems().add("MANUÁLNÍ");
        this.choiceBoxType.getSelectionModel().selectFirst();
        this.spinnerPeople.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE));
        this.okClicked = false;
        this.sliderPosition.valueProperty().addListener((o) -> {
            this.sliderPosition_onAction();
        });
    }
    
    /**
     * Checks, whether OK button has been clicked
     * @return TRUE if OK button has been clicked, FALSE otherwise
     */
    public boolean okClicked()
    {
        return this.okClicked;
    }
    
    /**
     * Gets result of dialog
     * @return Process which has been defined by dialog
     */
    public Proces getResult()
    {
        return this.result;   
    }
    
    /**
     * Gets new position of process
     * @return Selected position of process
     */
    public enumPozice getPosition()
    {
        return this.position;
    }
    
    /**
     * Sets stage for dialog
     * @param dialogStage Stage which will be used for dialog
     */
    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
        this.dialogStage.setTitle("Přidat nový proces");
    }
    
    @FXML
    private void buttonOK_onAction(ActionEvent event)
    {
        this.okClicked = true;
        if (this.choiceBoxType.getSelectionModel().getSelectedItem() == "ROBOTICKÝ")
        {
            this.result = new ProcesRoboticky(this.spinnerTime.getValue());
        }
        else if (this.choiceBoxType.getSelectionModel().getSelectedItem() == "MANUÁLNÍ")
        {
            this.result = new ProcesManualni(this.spinnerTime.getValue(), this.spinnerPeople.getValue());
        }
        this.dialogStage.close();
    }
    
    @FXML
    private void buttonCancel_onAction(ActionEvent event)
    {
        this.okClicked = false;
        this.dialogStage.close();
    }
    
    @FXML
    private void choiceBoxType_onAction(ActionEvent event)
    {
        this.spinnerPeople.setDisable(this.choiceBoxType.getSelectionModel().getSelectedItem() == "ROBOTICKÝ");
    }
    
    private void sliderPosition_onAction()
    {
        if (this.sliderPosition.getValue() < 0.5)
        {
            this.labelPosition.setText("První");
            this.position = enumPozice.PRVNI;
        }
        else if (this.sliderPosition.getValue() >= 0.5 &&
                 this.sliderPosition.getValue() < 1.5)
        {
            this.labelPosition.setText("Předchozí");
            this.position = enumPozice.PREDCHUDCE;
        }
        else if (this.sliderPosition.getValue() >= 1.5 &&
                 this.sliderPosition.getValue() < 2.5)
        {
            this.labelPosition.setText("Následující");
            this.position = enumPozice.NASLEDNIK;
        }
        else if (this.sliderPosition.getValue() >= 2.5)
        {
            this.labelPosition.setText("Poslední");
            this.position = enumPozice.POSLEDNI;
        }
    }
    
}
