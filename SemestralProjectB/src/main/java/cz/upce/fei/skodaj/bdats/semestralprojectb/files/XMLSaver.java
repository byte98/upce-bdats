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
package cz.upce.fei.skodaj.bdats.semestralprojectb.files;

import cz.upce.fei.skodaj.bdats.semestralprojectb.data.IPamatky;
import cz.upce.fei.skodaj.bdats.semestralprojectb.data.Zamek;
import cz.upce.fei.skodaj.bdats.semestralprojectb.structs.eTypKey;
import cz.upce.fei.skodaj.bdats.semestralprojectb.structs.eTypProhl;
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
     * Manager of all castles
     */
    private IPamatky data;
    
    /**
     * Selected castle
     */
    private Zamek selected;
    
    /**
     * Searched castle
     */
    private Zamek searched;
    
    /**
     * Nearest castle
     */
    private Zamek nearest;
    
    /**
     * Key type used to store castles
     */
    private eTypKey key;
    
    /**
     * Order of browsing castles
     */
    private eTypProhl order;
    
    @Override
    public void save(String file)
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(this.generateXML());
            writer.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(XMLSaver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setManager(IPamatky manager)
    {
        this.data = manager;
    }

    @Override
    public void setKey(eTypKey type)
    {
        this.key = type;
    }

    @Override
    public void setOrder(eTypProhl type)
    {
        this.order = type;
    }

    @Override
    public void setSelected(Zamek castle)
    {
        this.selected = castle;
    }

    @Override
    public void unsetSelected()
    {
        this.selected = null;
    }

    @Override
    public void setNearest(Zamek nearest)
    {
        this.nearest = nearest;
    }

    @Override
    public void unsetNearest()
    {
        this.nearest = null;
    }

    @Override
    public void setSearched(Zamek searched)
    {
        this.searched = searched;
    }

    @Override
    public void unsetSearched()
    {
        this.searched = null;
    }
    
    
    
    /**
     * Generates XML string containing actual state
     * @return XML string containing actual state
     */
    private String generateXML()
    {
        StringBuilder reti = new StringBuilder();
        reti.append(XMLFormat.XML_HEADER).append(System.lineSeparator());
        //<xml>
        reti.append("<").append(XMLFormat.XML_ROOT).append(">").append(System.lineSeparator());
        if (Objects.nonNull(this.data))
        {
            //<data>
            reti.append(XMLFormatter.getInstance().indent(1)).append("<").append(XMLFormat.XML_DATA).append(">").append(System.lineSeparator());
            Iterator<Zamek> it = this.data.vytvorIterator(eTypProhl.DO_SIRKY);
            while(it.hasNext())
            {
                reti.append(XMLFormatter.getInstance().formatCastle(it.next(), 2));
            }
            reti.append(XMLFormatter.getInstance().indent(1)).append("</").append(XMLFormat.XML_DATA).append(">").append(System.lineSeparator());
            //</data>
        }
        if (Objects.nonNull(this.selected))
        {
            //<selected>
            reti.append(XMLFormatter.getInstance().indent(1)).append("<").append(XMLFormat.XML_SELECTED).append(">").append(System.lineSeparator());
            reti.append(XMLFormatter.getInstance().formatCastle(this.selected, 2));
            reti.append(XMLFormatter.getInstance().indent(1)).append("</").append(XMLFormat.XML_SELECTED).append(">").append(System.lineSeparator());
            //</selected>
        }
        if(Objects.nonNull(this.searched))
        {
            //<searched>
            reti.append(XMLFormatter.getInstance().formatSearched(searched, 1));
            //</searched>
        }
        else if (Objects.nonNull(this.nearest))
        {
            //<nearest>
            reti.append(XMLFormatter.getInstance().formatNearest(this.nearest, 1));
            //</nearest>
        }
        if (Objects.nonNull(this.key))
        {
            //<key>
            reti.append(XMLFormatter.getInstance().formatKey(this.key, 1));
            //</key>
        }
        if (Objects.nonNull(this.order))
        {
            //<order>
            reti.append(XMLFormatter.getInstance().formatOrder(this.order, 1));
            //</order>
        }
        reti.append("</").append(XMLFormat.XML_ROOT).append(">").append(System.lineSeparator());
        //</xml>
        return reti.toString();
    }
    
}
