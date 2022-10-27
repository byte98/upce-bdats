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
package cz.upce.fei.skodaj.bdats.semestralprojecta.data;

import static cz.upce.fei.skodaj.bdats.semestralprojecta.data.enumReorg.AGREGACE;
import static cz.upce.fei.skodaj.bdats.semestralprojecta.data.enumReorg.DEKOMPOZICE;
import cz.upce.fei.skodaj.bdats.semestralprojecta.structs.AbstrDoubleList;
import cz.upce.fei.skodaj.bdats.semestralprojecta.structs.AbstrLifo;
import cz.upce.fei.skodaj.bdats.semestralprojecta.structs.IAbstrDoubleList;
import cz.upce.fei.skodaj.bdats.semestralprojecta.structs.IAbstrLifo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class which manages all processes
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class VyrobniProces implements IVyrobniProces
{
    /**
     * Character used to separate data in CSV
     */
    private static final char FILE_SEPARATOR = ';';
    
    /**
     * Header of CSV file defining identifier of process
     */
    private static final String CSV_ID = "IdProc";
    
    /**
     * Header of CSV file defining number of persons needed to complete process
     */
    private static final String CSV_PERSONS = "persons";
    
    /**
     * Header of CSV file defining time needed to complete process
     */
    private static final String CSV_TIME = "time";
    
    /**
     * List of all managed processes
     */
    private IAbstrDoubleList<Proces> data;
    
    /**
     * Creates new manager of all processes
     */
    public VyrobniProces()
    {
        this.data = new AbstrDoubleList<>();
    }
    
    /**
     * Imports data from CSV file
     * @param soubor Path to file
     * @return Number of imported processes
     */
    @Override
    public int importDat(String soubor)
    {
        int reti = 0;
        List<Map<String, String>> content = this.loadFile(soubor);
        if (!content.isEmpty())
        {
            for(Map<String, String> item: content)
            {
                String id = item.get(VyrobniProces.CSV_ID);
                String persons = item.get(VyrobniProces.CSV_PERSONS);
                String time = item.get(VyrobniProces.CSV_TIME);
                if (Objects.nonNull(id) &&
                    Objects.nonNull(persons) &&
                    Objects.nonNull(time)
                )
                {
                    if (ProcesRoboticky.isRobotic(id))
                    {
                        this.data.vlozPosledni(
                                new ProcesRoboticky(Integer.parseInt(time), id)
                        );
                        reti++;
                    }
                    else if (ProcesManualni.isManual(id))
                    {
                        this.data.vlozPosledni(
                                new ProcesManualni(Integer.parseInt(time),
                                        Integer.parseInt(persons),
                                        id
                                )
                        );
                        reti++;
                    }
                }
            }
        }
        return reti;
    }
    
    /**
     * Cleans string from any unexpected characters
     * @param input Input which will be checked
     * @return String cleaned from any unexpected characters
     */
    private static String cleanString(String input)
    {
        StringBuilder reti = new StringBuilder();
        for(char c: input.toCharArray())
        {
            if (Charset.forName("US-ASCII").newEncoder().canEncode(c) && c >= '!' && c <= '}')
            {
                reti.append(c);
            }
        }
        return reti.toString();
    }
    
    /**
     * Loads data from file into map
     * @param path Path to file
     * @return Map with data loaded from file
     */
    private List<Map<String, String>> loadFile(String path)
    {
        List<Map<String, String>> reti = new ArrayList<>();
        List<String> headers = new ArrayList<>();
        
        File f = new File(path);
        try
        {
            FileReader fr = new FileReader(f, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(fr);
            String line;
            int counter = 0;
            while ((line = br.readLine()) != null)
            {
                String[] parts = VyrobniProces.cleanString(line).trim().split(String.valueOf(VyrobniProces.FILE_SEPARATOR));
                if (parts.length > 0)
                {
                    if (counter == 0)
                    {
                        for (String part: parts)
                        {
                            headers.add(part.trim());
                        }
                    }
                    else
                    {
                        Map<String, String> row = new HashMap<>();
                        int idx = 0;
                        for (String part: parts)
                        {
                            row.put(headers.get(idx), part);
                            idx++;
                        }
                        reti.add(row);
                    }
                    counter++;
                }
            }
            br.close();
            fr.close();
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(VyrobniProces.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(VyrobniProces.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return reti;
    }

    @Override
    public void vlozProces(Proces proces, enumPozice pozice)
    {
        switch(pozice)
        {
            case PRVNI:      this.data.vlozPrvni(proces);      break;
            case POSLEDNI:   this.data.vlozPosledni(proces);   break;
            case PREDCHUDCE: this.data.vlozPredchudce(proces); break;
            case NASLEDNIK:  this.data.vlozNaslednika(proces); break;
        }
    }

    @Override
    public Proces zpristupniProces(enumPozice pozice)
    {
        Proces reti = null;
        switch(pozice)
        {
            case PRVNI:      reti = this.data.zpristupniPrvni();      break;
            case POSLEDNI:   reti = this.data.zpristupniPosledni();   break;
            case PREDCHUDCE: reti = this.data.zpristupniPredchudce(); break;
            case NASLEDNIK:  reti = this.data.zpristupniNaslednika(); break;
            case AKTUALNI:   reti = this.data.zpristupniAktualni();   break;
        }
        return reti;
    }

    @Override
    public Proces odeberProces(enumPozice pozice)
    {
        Proces reti = null;
        switch(pozice)
        {
            case PRVNI:      reti = this.data.odeberPrvni();      break;
            case POSLEDNI:   reti = this.data.odeberPosledni();   break;
            case PREDCHUDCE: reti = this.data.odeberPredchudce(); break;
            case NASLEDNIK:  reti = this.data.odeberNaslednika(); break;
            case AKTUALNI:   reti = this.data.odeberAktualni();   break;
        }
        return reti;
    }

    @Override
    public IAbstrLifo<Proces> vytipujKandidatiReorg(int cas, enumReorg reorgan)
    {
        IAbstrLifo<Proces> reti = new AbstrLifo<>();
        if (reorgan == enumReorg.AGREGACE)
        {
            reti = this.selectAggregatable(cas);
        }
        else if (reorgan == enumReorg.DEKOMPOZICE)
        {
            reti = this.selectDecomposable(cas);
        }
        return reti;
    }
    
    /**
     * Selects decomposable items
     * @param time Time criteria for decomposition
     * @return Processes allowed for decomposition
     */
    private IAbstrLifo<Proces> selectDecomposable(int time)
    {
        IAbstrLifo<Proces> reti = new AbstrLifo<>();
        for (Proces p: this.data)
        {
            if (p instanceof ProcesManualni && p.getCas() >= time)
            {
                reti.vloz(p);
            }
        }
        return reti;
    }
    
    /**
     * Selects aggregatable processes
     * @param time Time criteria for aggregation
     * @return Processes allowed for aggregation
     */
    private IAbstrLifo<Proces> selectAggregatable(int time)
    {
        IAbstrLifo<Proces> reti = new AbstrLifo<>();
        Proces previous = null;
        boolean previousAggregatable = false;
        for (Proces p: this.data)
        {
            if (p instanceof ProcesManualni && p.getCas() <= time && previousAggregatable)
            {
                reti.vloz(previous);
                reti.vloz(p);
                p = null;
            }
            previous = p;
            if (Objects.nonNull(previous) && previous instanceof ProcesManualni && p.getCas() <= time)
            {
                previousAggregatable = true;
            }
            else
            {
                previousAggregatable = false;
            }
        }
        return reti;
    }

    @Override
    public void reorganizace(enumReorg reorgan, IAbstrLifo<Proces> zasobnik)
    {
        /*
         Pro dekompozici vytvorit novy proces (jako naslednika vybraneho)
         a u obou nastavit polovicni cas.
        Agregace:
        Pokud dva po sobe jdouci procesy splnuji casovou podminku, tak
        je spojit v jeden proces se souctem casu puvodnich procesu,
        ktere budou samozrejme odstraneny.
        */
        if (reorgan == DEKOMPOZICE && zasobnik.jePrazdny() == false)
        {
            this.decompose(zasobnik);
        }
        else if (reorgan == AGREGACE && zasobnik.jePrazdny() == false)
        {
            this.aggregate(zasobnik);
        }
    }
    
    /**
     * Sets actual item in list to defined one
     * @param process Process which will be selected as actual one
     */
    private void setActual(Proces process)
    {
        Proces set = this.data.zpristupniPrvni();
        for(Proces p: this.data)
        {
            if (set != process)
            {
                set = this.data.zpristupniNaslednika();
            }
        }
        if (set != process)
        {
            this.data.zpristupniNaslednika();
        }
    }
   
    /**
     * Perform decomposition of processes
     * @param processes Processes which will be decomposed
     */
    private void decompose(IAbstrLifo<Proces> processes)
    {
        Proces actual = this.data.zpristupniAktualni();
        while (processes.jePrazdny() == false)
        {
            ProcesManualni p = (ProcesManualni) processes.odeber();
            Proces selected = this.data.zpristupniPrvni();
            while (p != selected)
            {
                selected = this.data.zpristupniNaslednika();
            }
            ProcesManualni p1 = new ProcesManualni(Integer.max(p.getCas() / 2, 1) , Integer.max(p.getPocetLidi() / 2, 1));
            ProcesManualni p2 = new ProcesManualni(Integer.max(p.getCas() - (Integer.max(p.getCas() / 2, 1)), 1), Integer.max(p.getPocetLidi() - (Integer.max(p.getPocetLidi() / 2, 1)),1));
            this.data.vlozPredchudce(p1);
            this.data.vlozPredchudce(p2);
            this.data.odeberAktualni();
        }
        if (Objects.nonNull(actual))
        {
            this.setActual(actual);
        }
        else
        {
            this.data.zpristupniPrvni();
        }
    }
    
    /**
     * Performs aggregation of processes
     * @param processes Processes which will be aggregated
     */
    private void aggregate(IAbstrLifo<Proces> processes)
    {
        Proces actual = this.data.zpristupniAktualni();
        Iterator<Proces> it = this.data.iterator();
        ProcesManualni aggr1 = (ProcesManualni) processes.odeber();
        ProcesManualni aggr2 = (ProcesManualni) processes.odeber();
        this.data.zpristupniPosledni();
        while (it.hasNext())
        {
            Proces actl = this.data.zpristupniAktualni();
            Proces pred = this.data.zpristupniPredchudce();
            if (actl.equals(aggr1) && pred.equals(aggr2))
            {
                ProcesManualni newProcess = new ProcesManualni(
                        aggr1.getCas() + aggr2.getCas(),
                        aggr1.getPocetLidi() + aggr2.getPocetLidi()
                );
                this.data.zpristupniPredchudce();
                this.data.odeberNaslednika();
                this.data.odeberNaslednika();
                this.data.vlozNaslednika(newProcess);
                aggr1 = (ProcesManualni) processes.odeber();
                aggr2 = (ProcesManualni) processes.odeber();
            }
            it.next();
        }
        if (Objects.nonNull(actual))
        {
            this.setActual(actual);
        }
        else
        {
            this.data.zpristupniPrvni();
        }
    }
    
    @Override
    public void zrus()
    {
        this.data.zrus();
    }

    @Override
    public Iterator iterator()
    {
        return this.data.iterator();
    }
    
}
