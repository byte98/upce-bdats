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

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Entry class for GUI of program
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class ProgVyrobniProces extends Application
{
    /**
     * Default location of file for importing processes
     */
    public static final String FILE_IMPORT = "./import.csv";
    
    /**
     * Default location of file for saving and loading state of program
     */
    public static final String FILE_SAVE = "./state.xml";
    
    /**
     * Primary stage of application
     */
    public static Stage primaryStage;
    
    /**
     * Runs program
     * @param args Arguments of program
     */
    public void run(String[] args)
    {
        ProgVyrobniProces.launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("FXMLMainWindow.fxml"));
        Scene scene = new Scene(root);        
        stage.setScene(scene);
        stage.setTitle("Semestrální práce A - Jiří Škoda");
        stage.show();
        ProgVyrobniProces.primaryStage = stage;
    }
}
