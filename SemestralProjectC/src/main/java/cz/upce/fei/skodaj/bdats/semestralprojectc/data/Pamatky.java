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
package cz.upce.fei.skodaj.bdats.semestralprojectc.data;

import cz.upce.fei.skodaj.bdats.semestralprojectc.Main;
import cz.upce.fei.skodaj.bdats.semestralprojectc.structs.AbstrHeap;
import cz.upce.fei.skodaj.bdats.semestralprojectc.structs.IAbstrHeap;
import cz.upce.fei.skodaj.bdats.semestralprojectc.structs.eTypProhl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class managing all castles
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Pamatky implements IPamatky
{    
    /**
     * Structure which stores castles
     */
    private IAbstrHeap<Distance, Zamek> data;
    
    /**
     * Creates new manager for castles
     */
    public Pamatky()
    {
        this.data = new AbstrHeap<>(AbstrHeap.ASC);
    }    
    
    @Override
    public int importDatZTXT()
    {
        int reti = 0;
        BufferedReader br = null;
        try
        {            
            br = new BufferedReader(new FileReader(new File(Main.class .getClassLoader().getResource("data.txt").toURI())));
            String line = br.readLine();
            while (Objects.nonNull(line))
            {
                try
                {
                    String latPart = line.substring(20);
                    latPart = latPart.substring(0, 10);
                    String latParts[] = latPart.split(" ");

                    String lonPart = line.substring(32);
                    lonPart = lonPart.substring(0, 10);

                    String namePart = line.substring(69);
                    namePart = namePart.substring(0, 20);
                    if(latParts.length == 2)
                    {
                        int latDeg        = Integer.parseInt(latParts[0]);
                        double latMin     = Double.parseDouble(latParts[1]);
                        String lonParts[] =  lonPart.split(" ");
                        if(lonParts.length == 2)
                        {
                            int lonDeg     = Integer.parseInt(lonParts[0]);
                            double lonMin  = Double.parseDouble(lonParts[1]);
                            Location loc = new Location(latDeg, latMin, lonDeg, lonMin);
                            Zamek z = new Zamek(namePart.trim(), loc);
                            this.vlozZamek(z);
                            reti++;
                        }
                    }                
                }
                catch(NumberFormatException ex)
                {
                    Logger.getLogger(Pamatky.class.getName()).log(Level.WARNING, null, ex);
                }                
                line = br.readLine();
            }
        } 
        catch (FileNotFoundException | URISyntaxException ex)
        {
            Logger.getLogger(Pamatky.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(Pamatky.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally 
        {
            try 
            {
                br.close();
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(Pamatky.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return reti;
    }

    @Override
    public int vlozZamek(Zamek zamek)
    {
        this.data.vloz(zamek, new Distance(zamek.getLocation()));
        return IPamatky.OK;
    }

    @Override
    public void odeberZamek()
    {
        this.data.odeberMax();
    }

    @Override
    public void zrus()
    {
        this.data.zrus();
    }

    @Override
    public Iterator<Zamek> vytvorIterator(eTypProhl typ)
    {
        return this.data.iterator(typ);
    }

    @Override
    public void nastavPozici(Location pozice)
    {
        Distance.setOrigin(pozice);
        this.data.prebuduj();
    }

    @Override
    public Zamek zpristupniZamek()
    {
        return this.data.zpristupniMax();
    }
}
