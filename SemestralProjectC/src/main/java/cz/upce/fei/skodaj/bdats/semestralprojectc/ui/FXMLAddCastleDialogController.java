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

import com.sothawo.mapjfx.Configuration;
import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.MapView;
import com.sothawo.mapjfx.Marker;
import com.sothawo.mapjfx.Projection;
import com.sothawo.mapjfx.event.MapViewEvent;
import cz.upce.fei.skodaj.bdats.semestralprojectc.data.Dataset;
import cz.upce.fei.skodaj.bdats.semestralprojectc.data.Location;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Class controlling behavior of add castle dialog
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class FXMLAddCastleDialogController implements Initializable
{
    /**
     * Default location of castle
     */
    private static final Location DEFAULT_LOCATION = new Location(50.0334956, 15.7675053);
    
    /**
     * Actually selected location
     */
    private Location location;
    
    /**
     * Marker of actually selected location
     */
    private Marker locationMarker;
    
    /**
     * Flag, whether OK button has been clicked
     */
    private boolean okClicked;
    
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
    @FXML private ComboBox<String> comboBoxName;
    @FXML private Button buttonOk;
    //</editor-fold>

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        this.location = new Location(FXMLAddCastleDialogController.DEFAULT_LOCATION.getLatitude(),
            FXMLAddCastleDialogController.DEFAULT_LOCATION.getLongitude());
        this.textFieldLatitude.setText(
                String.valueOf(FXMLAddCastleDialogController.DEFAULT_LOCATION.getLatitude())
        );
        this.textFieldLongitude.setText(
                String.valueOf(FXMLAddCastleDialogController.DEFAULT_LOCATION.getLongitude())
        );
        this.textFieldLatitude.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1)
            {
                handleLatitude();
            }
        
        });
        this.textFieldLongitude.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1)
            {
                handleLongitude();
            }
        
        });
        this.locationMarker = Marker.createProvided(Marker.Provided.BLUE).setPosition(
            new Coordinate(
                FXMLAddCastleDialogController.DEFAULT_LOCATION.getLatitude(),
                FXMLAddCastleDialogController.DEFAULT_LOCATION.getLongitude()
            )
        ).setVisible(true);
        this.comboBoxName.setItems(FXCollections.observableArrayList(Dataset.getInstance().getNames()));
        this.autoComplete = new AutoCompleteComboBoxListener<>(this.comboBoxName);
        this.updateLocation();
    }
    
    /**
     * Sets stage for dialog
     * @param stage 
     */
    public void setDialogStage(Stage stage)
    {
        this.dialogStage = stage;
        this.dialogStage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("add.png")));
    }
    
    /**
     * Initializes whole dialog
     */
    public void init()
    {
        this.mapView.initialize(Configuration.builder()
            .projection(Projection.WEB_MERCATOR)
            .showZoomControls(true)
            .build());
        this.mapView.setCenter(new Coordinate(
            FXMLAddCastleDialogController.DEFAULT_LOCATION.getLatitude(),
            FXMLAddCastleDialogController.DEFAULT_LOCATION.getLongitude()
        ));
        this.mapView.addMarker(this.locationMarker);
        this.mapView.addEventHandler(MapViewEvent.MAP_CLICKED, event -> {
            event.consume();
            Coordinate newPosition = event.getCoordinate().normalize();
            this.location = new Location(newPosition.getLatitude(), newPosition.getLongitude());
            this.updateLocation();
        });        
        this.updateLocation();
    }
    
    /**
     * Updates actual location
     */
    private void updateLocation()
    {
        this.textFieldLatitude.setText(String.valueOf(this.location.getLatitude()));
        this.textFieldLongitude.setText(String.valueOf(this.location.getLongitude()));
        this.mapView.setCenter(new Coordinate(this.location.getLatitude(), this.location.getLongitude()));
        this.locationMarker.setPosition(new Coordinate(this.location.getLatitude(), this.location.getLongitude()));
        this.mapView.removeMarker(this.locationMarker);
        this.mapView.addMarker(this.locationMarker);
    }
    
    /**
     * Handles change of longitude
     */
    private void handleLongitude()
    {
        try
        {
            double lon = Double.parseDouble(this.textFieldLongitude.getText());
            this.location = new Location(this.location.getLatitude(), lon);
        }
        catch (NumberFormatException ex)
        {
            this.textFieldLongitude.setText(String.valueOf(this.location.getLongitude()));
        }   
    }
    
    /**
     * Handles change of latitude
     */
    private void handleLatitude()
    {
        try
        {
            double lat = Double.parseDouble(this.textFieldLatitude.getText());
            this.location = new Location(lat, this.location.getLongitude());
        }
        catch (NumberFormatException ex)
        {
            this.textFieldLatitude.setText(String.valueOf(this.location.getLatitude ()));
        }   
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
     * Gets actually set location
     * @return Actually set location
     */
    public Location getLocation()
    {
        return this.location;
    }
    
    /**
     * Gets name of new castle
     * @return Name of new castle entered by user
     */
    public String getName()
    {
        return this.comboBoxName.getValue();
    }
    
    @FXML
    public void buttonCancel_onAction(ActionEvent e)
    {
        this.okClicked = false;
        this.dialogStage.close();
    }
    
    @FXML
    public void buttonOk_onAction(ActionEvent e)
    {
        this.okClicked = true;
        this.dialogStage.close();
    }
    
    @FXML
    public void comboBoxName_onAction(ActionEvent e)
    {
        Location loc = Dataset.getInstance().getByName(this.comboBoxName.getValue());
        if (Objects.nonNull(loc))
        {
            this.location = loc;
        }
        this.buttonOk.setDisable((Objects.nonNull(this.comboBoxName.getValue()) && this.comboBoxName.getValue().length() > 0) == false);
        this.updateLocation(); 
    }
    
    @FXML
    public void buttonLocate_onAction(ActionEvent e)
    {
        this.updateLocation();
    }
}
