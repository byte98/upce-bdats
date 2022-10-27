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
package cz.upce.fei.skodaj.bdats.semestralprojectb.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller of search dialog
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class FXMLSearchDialogController
{
    /**
     * Result of dialog
     */
    private String result;
    
    /**
     * Flag, whether OK button has been clicked
     */
    private boolean okClicked = false;
    
    /**
     * Stage for dialog
     */
    private Stage dialogStage;
    
    @FXML
    private TextField textFieldKey;
    
    /**
     * Sets stage for dialog
     * @param stage New stage for dialog
     */
    public void setDialogStage(Stage stage)
    {
        this.dialogStage = stage;
    }
    
    @FXML
    private void buttonOk_onAction(ActionEvent e)
    {
        this.okClicked = true;
        this.result = this.textFieldKey.getText();
        this.dialogStage.close();
    }
    
    @FXML
    private void buttonCancel_onAction(ActionEvent e)
    {
        this.okClicked = false;
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
     * @return Value of key entered by user
     */
    public String getResult()
    {
        return this.result;
    }
    
}
