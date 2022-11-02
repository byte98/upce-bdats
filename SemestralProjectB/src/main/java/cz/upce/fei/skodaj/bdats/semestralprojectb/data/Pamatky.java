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

import cz.upce.fei.skodaj.bdats.semestralprojectb.Main;
import cz.upce.fei.skodaj.bdats.semestralprojectb.structs.AbstrFifo;
import cz.upce.fei.skodaj.bdats.semestralprojectb.structs.AbstrTable;
import cz.upce.fei.skodaj.bdats.semestralprojectb.structs.IAbstrLifoFifo;
import cz.upce.fei.skodaj.bdats.semestralprojectb.structs.IAbstrTable;
import cz.upce.fei.skodaj.bdats.semestralprojectb.structs.eTypKey;
import cz.upce.fei.skodaj.bdats.semestralprojectb.structs.eTypProhl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
     * Default key
     */
    public static final eTypKey DEFAULT_KEY = eTypKey.NAME;
    
    
    
    /**
     * Actually used key
     */
    private eTypKey key = Pamatky.DEFAULT_KEY;
    
    /**
     * Table which stores castles 
     */
    private IAbstrTable<String, Zamek> data;
    
    /**
     * Creates new manager for castles
     */
    public Pamatky()
    {
        this.data = new AbstrTable<>();
    }
    
    /**
     * Prepares data for binary search tree rebuilding
     * @return Structure with castles in right order
     */
    private IAbstrLifoFifo<Zamek> prepareForRebuilding()
    {
        IAbstrLifoFifo<Zamek> reti = new AbstrFifo<>();
        Zamek[] array = this.getAsArray();
        if (array.length > 0)
        {
            this.insertCastle(array, 0, array.length, reti);
        }        
        return reti;
    }
    
    
    /**
     * Recursive function to add middle castles from array
     * @param array Array with castles
     * @param start Start index for insertion
     * @param end  End index for insertion
     * @param resutls Structure with results
     */
    private void insertCastle(Zamek[] array, int start, int end, IAbstrLifoFifo<Zamek> results)
    {
        if (start <= end)
        {
            int idx = (start + end) / 2; // Average of start and end
            if (idx >= 0  && idx < array.length) // Index is in array
            {
                Zamek z = array[idx];
                results.vloz(z);
                this.insertCastle(array, start, idx - 1, results); // Continue from start to average (excl.)
                this.insertCastle(array, idx + 1, end, results); // Continue from average (excl.) to end
            }            
        }
    }
    
    /**
     * Gets all stored castles as sorted array
     * @return Sorted array with all castles
     */
    private Zamek[] getAsArray()
    {
        List<Zamek> list = new ArrayList<>();
        Iterator<Zamek> it = this.vytvorIterator(eTypProhl.DO_HLOUBKY);
        while(it.hasNext())
        {
            list.add(it.next());
        }
        Zamek[] reti = new Zamek[list.size()];
        reti = list.toArray(reti);
        return reti;
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
    public Zamek najdiZamek(String klic)
    {
        return this.data.najdi(klic);
    }

    @Override
    public Zamek odeberZamek(String klic)
    {
        return this.data.odeber(klic);
    }

    @Override
    public void zrus()
    {
        this.data.zrus();
    }

    @Override
    public void prebuduj()
    {
        IAbstrLifoFifo data = this.prepareForRebuilding();
        Iterator<Zamek> it = data.iterator();
        this.zrus();
        while (it.hasNext())
        {
            this.vlozZamek(it.next());
        }
    }

    @Override
    public Zamek najdiNejbliz(String key)
    {
        Zamek reti = null;
        if (this.key == eTypKey.GPS)
        {
            Iterator<Zamek> it = this.data.vytvorIterator(eTypProhl.DO_SIRKY);
            double distance = Double.MAX_VALUE;
            Location myLocation = Location.fromString(key);
            while (it.hasNext())
            {
                Zamek z = it.next();
                if (myLocation.distanceTo(z.getLocation()) < distance)
                {
                    distance = myLocation.distanceTo(z.getLocation());
                    reti = z;
                }
            }
        }
        return reti;
    }

    @Override
    public Iterator<Zamek> vytvorIterator(eTypProhl typ)
    {
        return this.data.vytvorIterator(typ);
    }

    @Override
    public int vlozZamek(Zamek zamek)
    {
        int reti = IPamatky.ERR;
        if ((this.key == eTypKey.GPS && Objects.isNull(this.najdiZamek(zamek.getLocation().toString()))) ||
            (this.key == eTypKey.NAME && Objects.isNull(this.najdiZamek(zamek.getName()))))
        {
            reti = IPamatky.OK;
            this.data.vloz((
                    this.key == eTypKey.GPS
                    ? zamek.getLocation().toString()
                    : zamek.getName()
            ), zamek);
        }
        return reti;
    }

    @Override
    public void nastavKlic(eTypKey typ)
    {
        this.key = typ;
        this.prebuduj();
    }
}
