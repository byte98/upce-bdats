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
package cz.upce.fei.skodaj.bdats.semestralprojectb.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class representing set of all available castles
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Dataset
{
    /**
     * File from which data will be read
     */
    private static final String FILE = "dataset.csv";
        
    /**
     * Data stored in data set
     */
    private Map<String, Location> data;
    
    /**
     * Source of data
     */
    private URL source;
    
    /**
     * Flag, whether data has been loaded
     */
    private boolean loaded = false;
    
    /**
     * Reference to instance of data set
     */
    private static Dataset instance;
    
    /**
     * Creates new data set
     */
    public Dataset()
    {
        this.data = new HashMap<>();
        this.source = this.getClass().getClassLoader().getResource(Dataset.FILE);
    }
    
    /**
     * Creates new data set
     * @param source Source of data
     */
    private Dataset(URL source)
    {
        this();
        this.source = source;
    }
    
    /**
     * Gets instance of data set
     * @return Instance of data set
     */
    public static Dataset getInstance()
    {
        Dataset reti = Dataset.instance;
        if (Objects.isNull(Dataset.instance))
        {
            Dataset.instance = new Dataset();
            Dataset.instance.checkLoaded();
            reti = Dataset.instance;
        }
        return reti;
    }
    
    /**
     * Gets instance of data set
     * @param source Source of data
     * @return Instance of data set
     */
    public static Dataset getInstance(URL source)
    {
        Dataset reti = Dataset.instance;
        if (Objects.isNull(Dataset.instance))
        {
            Dataset.instance = new Dataset(source);
            Dataset.instance.checkLoaded();
            reti = Dataset.instance;
        }
        return reti;
    }
    
    /**
     * Checks, whether data has been loaded and if not
     * loads data from file
     */
    private void checkLoaded()
    {
        if (this.loaded == false)
        {
            this.load();
        }
    }
    
    /**
     * Loads data from file
     */
    private void load()
    {
        try
        {
            File f = new File(this.source.toURI());
            FileInputStream fis = new FileInputStream(f);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);            
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();
            while (line != null)
            {
                String parts[] = line.split(",");
                if (parts.length == 3)
                {
                    String name = parts[0];
                    double lat = Double.parseDouble(parts[1]);
                    double lon = Double.parseDouble(parts[2]);
                    Location loc = new Location(lat, lon);
                    this.data.put(name, loc);
                }
                line = br.readLine();
            }
            this.loaded = true;
        }
        catch (URISyntaxException | FileNotFoundException ex)
        {
            Logger.getLogger(Dataset.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(Dataset.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    /**
     * Gets location of castle by its name
     * @param name Name of castle
     * @return Location of castle with defined name
     *         or NULL, if there is no such a castle
     */
    public Location getByName(String name)
    {
        this.checkLoaded();
        Location reti = null;
        if (this.data.containsKey(name))
        {
            reti = this.data.get(name);
        }
        return reti;
    }
    
    /**
     * Gets name of all castles
     * @return List of names of all available castles
     */
    public List<String> getNames()
    {
        List<String> reti = new ArrayList<>();
        this.checkLoaded();
        reti.addAll(this.data.keySet());
        Collections.sort(reti);
        return reti;
    }
    
    /**
     * Gets number of stored castles
     * @return Number of stored castles
     */
    public int count()
    {
        this.checkLoaded();
        return this.data.size();
    }
}
