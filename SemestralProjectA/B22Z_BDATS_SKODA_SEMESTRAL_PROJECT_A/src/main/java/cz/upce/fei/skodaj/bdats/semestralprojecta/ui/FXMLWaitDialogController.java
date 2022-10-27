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

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * Controller of waiting dialog
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class FXMLWaitDialogController implements Initializable
{
    @FXML
    private Label labelHeader;
    
    @FXML
    private Label labelBody;
    
    /**
     * Text of header of the dialog
     */
    private String header;
    
    /**
     * Text of body of the dialog
     */
    private String body;
    

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        this.labelHeader.setText(this.header);
        this.labelBody.setText(this.body);
    }
    
    /**
     * Sets header text of dialog
     * @param header New header text of dialog
     */
    public void setHeader(String header)
    {
        this.header = header;
        if (Objects.nonNull(this.labelHeader))
        {
            this.labelHeader.setText(this.header);
        }
    }
    
    /**
     * Sets body text of dialog
     * @param body New body text of dialog
     */
    public void setBody(String body)
    {
        this.body = body;
        if (Objects.nonNull(this.labelBody))
        {
            this.labelBody.setText(this.body);
        }
    }
    
    
}
