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

import cz.upce.fei.skodaj.bdats.semestralprojecta.data.Proces;
import cz.upce.fei.skodaj.bdats.semestralprojecta.data.enumReorg;
import cz.upce.fei.skodaj.bdats.semestralprojecta.structs.AbstrDoubleList;
import cz.upce.fei.skodaj.bdats.semestralprojecta.structs.AbstrLifo;
import cz.upce.fei.skodaj.bdats.semestralprojecta.structs.IAbstrDoubleList;
import cz.upce.fei.skodaj.bdats.semestralprojecta.structs.IAbstrLifo;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class which loads state of program from XML file
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class XMLLoader implements IStateLoader
{
    
    /**
     * Flag, whether data has been loaded
     */
    private boolean loaded = false;
    
    /**
     * Loaded processes from file
     */
    private IAbstrDoubleList<Proces> processes;
    
    /**
     * Flag, whether reorganization is in progress
     */
    private boolean reorgActive = false;
    
    /**
     * Loaded type of reorganization
     */
    private enumReorg reorgType;
    
    /**
     * Processes accepted for reorganization
     */
    private IAbstrLifo<Proces> reorgList;
    
    /**
     * Flag, whether active process is set
     */
    private boolean actualSet = false;
    
    /**
     * Actual process
     */
    private Proces actual = null;
    
    /**
     * Creates new loader of state from XML files
     */
    public XMLLoader()
    {
        this.processes = new AbstrDoubleList<>();
        this.reorgList = new AbstrLifo<>();
    }

    @Override
    public void load(String file)
    {
        this.loaded = false;
        this.reorgActive = false;
        this.actualSet = false;
        this.processes = new AbstrDoubleList<>();
        this.reorgList = new AbstrLifo<>();
        try
        {
            File f = new File(file);
            if (f.exists())
            {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(f);
                doc.getDocumentElement().normalize();
                Element root = doc.getDocumentElement();
                NodeList nodes = root.getChildNodes();
                for (int i = 0; i < nodes.getLength(); i++)
                {
                    Node n = nodes.item(i);
                    switch (n.getNodeName())
                    {
                        case XMLFormat.XML_LIST: this.parseList(nodes.item(i)); break;
                        case XMLFormat.XML_REORGANIZATION: this.parseReorganization(nodes.item(i)); break;
                        case XMLFormat.XML_ACTUAL: this.parseActual(nodes.item(i)); break;
                    }
                }
                this.loaded = true;
            }
        }
        catch (ParserConfigurationException | SAXException | IOException ex)
        {
            Logger.getLogger(XMLLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.loaded = true;
    }
    
    /**
     * Parses list with processes from file
     * @param node XML node containing list of processes
     */
    private void parseList(Node node)
    {
        NodeList nodes = node.getChildNodes();
        for(int i = 0; i < nodes.getLength(); i++)
        {
            Node n = nodes.item(i);
            if (n.getNodeName().equals(XMLFormat.XML_PROCESS))
            {
                Proces p = XMLParser.parseProcess(n);
                if (Objects.nonNull(p))
                {
                    this.processes.vlozPosledni(p);
                }
            }
            
        }
    }
    
    /**
     * Parses reorganization from XML node
     * @param node Node containing information about reorganization
     */
    private void parseReorganization(Node node)
    {
        boolean typeSet = false;
        enumReorg type = enumReorg.AGREGACE;
        NodeList nodes = node.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++)
        {
            switch(nodes.item(i).getNodeName())
            {
                case XMLFormat.XML_REORGANIZATION_TYPE:
                    type = (nodes.item(i).getTextContent().equals(XMLFormat.XML_REORGANIZATION_TYPE_AGGREGATION) ? enumReorg.AGREGACE : enumReorg.DEKOMPOZICE);
                    typeSet = (nodes.item(i).getTextContent().equals(XMLFormat.XML_REORGANIZATION_TYPE_AGGREGATION) || nodes.item(i).getTextContent().equals(XMLFormat.XML_REORGANIZATION_TYPE_DECOMPOSITION));
                    break;
                case XMLFormat.XML_REORGANIZATION_LIST:
                    NodeList list = nodes.item(i).getChildNodes();
                    for (int c = 0; c < list.getLength(); c++)
                    {
                        Proces p = XMLParser.parseProcess(list.item(c));
                        if (Objects.nonNull(p))
                        {
                            this.reorgList.vloz(p);
                        }
                    }
                    break;
            }
        }
        if (typeSet)
        {
            this.reorgActive = true;
            this.reorgType = type;
        }
    }

    /**
     * Parses actual item from XML node
     * @param node Node containing information about actual process
     */
    private void parseActual(Node node)
    {
        NodeList nodes = node.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++)
        {
            if (nodes.item(i).getNodeName().equals(XMLFormat.XML_PROCESS))
            {
                this.actualSet = true;
                this.actual = XMLParser.parseProcess(nodes.item(i));
                break;
            }
        }
    }
    
    @Override
    public boolean loaded()
    {
        return this.loaded;
    }

    @Override
    public IAbstrDoubleList<Proces> getProcesses() 
    {
        return this.processes;
    }

    @Override
    public boolean getReorgActive()
    {
        return this.reorgActive;
    }

    @Override
    public enumReorg getReorgType()
    {
        return this.reorgType;
    }

    @Override
    public IAbstrLifo<Proces> getReorgList()
    {
        return this.reorgList;
    }

    @Override
    public Proces getActual()
    {
        Proces reti = null;
        if (this.actualSet)
        {
            reti = this.actual;
        }
        return reti;
    }
    
}
