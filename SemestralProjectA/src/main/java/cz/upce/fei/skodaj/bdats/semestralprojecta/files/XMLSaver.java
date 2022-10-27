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
package cz.upce.fei.skodaj.bdats.semestralprojecta.files;

import cz.upce.fei.skodaj.bdats.semestralprojecta.data.IVyrobniProces;
import cz.upce.fei.skodaj.bdats.semestralprojecta.data.Proces;
import cz.upce.fei.skodaj.bdats.semestralprojecta.data.enumReorg;
import cz.upce.fei.skodaj.bdats.semestralprojecta.structs.IAbstrLifo;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class which saves actual state of program into XML file
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class XMLSaver implements IStateSaver
{
    /**
     * Type of reorganization which has been set
     */
    private enumReorg reorganization;
        
    /**
     * Processes accepted for reorganization
     */
    private IAbstrLifo<Proces> processes;
    
    /**
     * Flag, whether reorganization has been set
     */
    private boolean reorgSet = false;
    
    /**
     * Flag, whether actual process has been set
     */
    private boolean actualSet = false;
    
    /**
     * Reference to actual process
     */
    private Proces actual;
    
    @Override
    public void setReorganization(enumReorg reorg, IAbstrLifo<Proces> processes)
    {
        this.reorgSet = true;
        this.reorganization = reorg;
        this.processes = processes;
    }

    @Override
    public void unsetReorganization()
    {
        this.reorgSet = false;
    }

    @Override
    public void save(IVyrobniProces processes, String file)
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(this.generateXML(processes));
            writer.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(XMLSaver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void setActual(Proces process)
    {
        this.actual = process;
        this.actualSet = true;
    }
    
    @Override
    public void unsetActual()
    {
        this.actualSet = false;
    }
    
    /**
     * Generates XML string containing actual state
     * @param processes Processes which will be saved
     * @param reorganization Processes accepted to reorganization
     * @return XML string containing actual state
     */
    private String generateXML(IVyrobniProces processes)
    {
        StringBuilder reti = new StringBuilder();
        reti.append(XMLFormat.XML_HEADER).append(System.lineSeparator());
        reti.append("<").append(XMLFormat.XML_ROOT).append(">").append(System.lineSeparator());
        reti.append(XMLFormatter.indent(1));
        reti.append("<").append(XMLFormat.XML_LIST).append(">").append(System.lineSeparator());
        Iterator<Proces> it = processes.iterator();
        while(it.hasNext())
        {
            reti.append(XMLFormatter.encodeProcess(it.next(), 2));
        }
        reti.append(XMLFormatter.indent(1));
        reti.append("</").append(XMLFormat.XML_LIST).append(">").append(System.lineSeparator());
        if (this.reorgSet)
        {
            reti.append(XMLFormatter.encodeReorganization(this.reorganization, this.processes, 1));
        }
        if (this.actualSet && Objects.nonNull(this.actual))
        {
            reti.append(XMLFormatter.indent(1));
            reti.append("<").append(XMLFormat.XML_ACTUAL).append(">").append(System.lineSeparator());
            reti.append(XMLFormatter.encodeProcess(this.actual, 2));
            reti.append(XMLFormatter.indent(1));
            reti.append("</").append(XMLFormat.XML_ACTUAL).append(">").append(System.lineSeparator());
        }
        reti.append("</").append(XMLFormat.XML_ROOT).append(">").append(System.lineSeparator());
        return reti.toString();
    }
    
}
