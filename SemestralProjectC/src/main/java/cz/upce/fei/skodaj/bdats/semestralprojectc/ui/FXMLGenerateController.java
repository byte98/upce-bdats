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

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Controller of generate dialog
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class FXMLGenerateController implements Initializable
{

    /**
     * Flag, whether OK button has been clicked
     */
    private boolean okClicked = false;
    
    /**
     * Flag, whether known castles should be used for generating
     */
    private boolean useKnown = true;
    
    /**
     * Stage for dialog
     */
    private Stage dialogStage;
    
    /**
     * Entered number of items to generate
     */
    private int result = 1;
    
    @FXML
    private Spinner<Integer> spinner;
    
    @FXML
    private CheckBox checkBoxKnown;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        this.spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE));
        this.spinner.valueProperty().addListener(new ChangeListener<Integer>(){
            @Override
            public void changed(ObservableValue<? extends Integer> ov, Integer t, Integer t1)
            {
                if (spinner.getValue() < 1)
                {
                    spinner.getValueFactory().setValue(1);
                }
            }
        });
        this.checkBoxKnown.selectedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1)
            {
                useKnown = checkBoxKnown.isSelected();
            }
        
        });
    }
    
    /**
     * Sets stage for dialog
     * @param stage New stage for dialog
     */
    public void setDialogStage(Stage stage)
    {
        this.dialogStage = stage;
        this.dialogStage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("gen.png")));
    }
    
    @FXML
    private void buttonOk_onAction(ActionEvent e)
    {
        this.okClicked = true;
        this.result = this.spinner.getValue();
        this.dialogStage.close();
    }
    
    @FXML
    private void buttonCancel_onAction(ActionEvent e)
    {
        this.okClicked = false;
        this.result = Integer.MIN_VALUE;
        this.dialogStage.close();
    }
    
    
    /**
     * Checks, whether OK button has been clicked
     * @return TRUE if OK button has been clicked, FALSE otherwise
     */
    public boolean getOkClicked()
    {
        return this.okClicked;
    }
    
    /**
     * Gets result of dialog
     * @return Number entered into dialog
     */
    public int getResult()
    {
        return this.result;
    }
    
    /**
     * Gets flag, whether known castles should be used for generating
     * @return TRUE if known castles should be preferred, FALSE otherwise
     */
    public boolean useKnown()
    {
        return this.useKnown;
    }
}

