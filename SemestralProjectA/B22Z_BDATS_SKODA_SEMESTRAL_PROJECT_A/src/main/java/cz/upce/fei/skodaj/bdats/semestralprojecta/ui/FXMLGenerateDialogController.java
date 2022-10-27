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

import cz.upce.fei.skodaj.bdats.semestralprojecta.data.enumPozice;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

/**
 * Controller of generate dialog
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class FXMLGenerateDialogController  implements Initializable
{
    @FXML
    private Spinner<Integer> spinnerCount;
    
    @FXML
    private Label labelPosition;
    
    @FXML
    private Slider sliderPosition;
    
    @FXML
    private Button buttonOK;
    
    @FXML
    private Button buttonCancel;
    
    /**
     * Stage for dialog
     */
    private Stage dialogStage;
    
    /**
     * Selected position
     */
    private enumPozice position = enumPozice.PRVNI;
    
    /**
     * Flag, whether OK button has been clicked
     */
    private boolean okClicked = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        this.spinnerCount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE));
        this.sliderPosition.valueProperty().addListener((o) -> {
            this.sliderPosition_onAction();
        });
        this.okClicked = false;
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
    
    @FXML
    private void buttonOK_onAction(ActionEvent event)
    {
        this.okClicked = true;
        this.dialogStage.close();
    }
    
    @FXML
    private void buttonCancel_onAction(ActionEvent event)
    {
        this.okClicked = false;
        this.dialogStage.close();
    }
    
    /**
     * Sets stage for dialog
     * @param dialogStage New stage for dialog
     */
    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
        this.dialogStage.setTitle("Generovat procesy");
    }
    
    /**
     * Checks, whether OK button has been clicked
     * @return TRUE, if OK button has been clicked, FALSE otherwise
     */
    public boolean okClicked()
    {
        return this.okClicked;
    }
    
    /**
     * Gets selected position
     * @return Selected position
     */
    public enumPozice getPosition()
    {
        return this.position;
    }
    
    /**
     * Gets count of processes which should be generated
     * @return Count of processes which should be generated
     */
    public int getCount()
    {
        return this.spinnerCount.getValue();
    }
}
