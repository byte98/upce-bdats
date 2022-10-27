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

import cz.upce.fei.skodaj.bdats.semestralprojecta.data.enumReorg;
import static cz.upce.fei.skodaj.bdats.semestralprojecta.data.enumReorg.AGREGACE;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

/**
 * Controller of reorganize dialog
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class FXMLReorganizeDialogController implements Initializable
{
    @FXML
    private Spinner<Integer> spinnerTime;
    
    @FXML
    private Label labelHeader;
    
    @FXML
    private Button buttonOK;
    
    @FXML
    private Button buttonCancel;
    
    /**
     * Flag, whether OK button has been clicked
     */
    private boolean okClicked = false;
    
    /**
     * Stage for dialog
     */
    private Stage dialogStage;
    
    /**
     * Type of reorganization
     */
    private enumReorg type = enumReorg.AGREGACE;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        this.spinnerTime.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE)
        );
        this.setType(this.type);
    }
    
    /**
     * Sets type of reorganization
     * @param type New type of reorganization
     */
    public void setType(enumReorg type)
    {
        this.type = type;
        this.labelHeader.setText((
                type == AGREGACE 
                ? "Agregace procesů"
                : "Dekompozice procesů"
        ));
        if (Objects.nonNull(this.dialogStage))
        {
            this.dialogStage.setTitle((
                type == AGREGACE 
                ? "Agregace procesů"
                : "Dekompozice procesů"
            ));
        }
    }
    
    /**
     * Gets type of reorganization
     * @return Type of reorganization
     */
    public enumReorg getType()
    {
        return this.type;
    }
    
    /**
     * Sets stage for dialog
     * @param dialogStage New stage for dialog
     */
    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
        this.dialogStage.setTitle("Generovat procesy");
        this.setType(this.type);
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
     * Gets entered time to dialog
     * @return Time entered by user
     */
    public int getTime()
    {
        return this.spinnerTime.getValue();
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
}
