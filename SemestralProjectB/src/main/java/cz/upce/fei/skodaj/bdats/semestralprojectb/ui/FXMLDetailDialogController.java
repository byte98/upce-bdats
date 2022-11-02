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

import com.sothawo.mapjfx.Configuration;
import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.MapView;
import com.sothawo.mapjfx.Marker;
import com.sothawo.mapjfx.Projection;
import cz.upce.fei.skodaj.bdats.semestralprojectb.data.Zamek;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Class controlling behavior of detail dialog
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class FXMLDetailDialogController implements Initializable
{
    /**
     * Castle which details will be shown
     */
    private Zamek castle;
    
    /**
     * Marker of actually selected location
     */
    private Marker locationMarker;
    
    /**
     * Stage for dialog
     */
    private Stage dialogStage;
    
    /**
     * Handler of auto completion mechanism for combo box 
     */
    private AutoCompleteComboBoxListener autoComplete;
    
    //<editor-fold defaultstate="collapsed" desc="FXML">
    @FXML private MapView mapView;
    @FXML private TextField textFieldLongitude;
    @FXML private TextField textFieldLatitude;
    @FXML private TextField textFieldName;
    //</editor-fold>
    
    /**
     * Sets stage for dialog
     * @param stage 
     */
    public void setDialogStage(Stage stage)
    {
        this.dialogStage = stage;
    }
    
    
    /**
     * Updates actual location
     */
    private void updateLocation()
    {
        this.textFieldLatitude.setText(String.valueOf(this.castle.getLocation().getLatitude()));
        this.textFieldLongitude.setText(String.valueOf(this.castle.getLocation().getLongitude()));
        this.mapView.setCenter(new Coordinate(this.castle.getLocation().getLatitude(), this.castle.getLocation().getLongitude()));
        this.locationMarker.setPosition(new Coordinate(this.castle.getLocation().getLatitude(), this.castle.getLocation().getLongitude()));
        this.mapView.removeMarker(this.locationMarker);
        this.mapView.addMarker(this.locationMarker);
        this.locationMarker.setVisible(true);
    }
    
    @FXML
    public void buttonOk_onAction(ActionEvent e)
    {
        this.dialogStage.close();
    }
    
    @FXML
    public void buttonLocate_onAction(ActionEvent e)
    {
        this.updateLocation();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        this.locationMarker = Marker.createProvided(Marker.Provided.BLUE);
    }
    
    /**
     * Sets castle which will be displayed
     * @param z Castle which details will be displayed
     */
    public void setCastle(Zamek z)
    {
        this.castle = z;
        this.updateLocation();
        this.textFieldName.setText(this.castle.getName());
    }
    
    /**
     * Initializes map view
     */
    public void init()
    {
        this.mapView.initialize(Configuration.builder()
            .projection(Projection.WEB_MERCATOR)
            .showZoomControls(true)
            .build());
    }
}
