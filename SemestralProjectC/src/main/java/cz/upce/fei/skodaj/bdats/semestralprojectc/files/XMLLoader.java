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
package cz.upce.fei.skodaj.bdats.semestralprojectc.files;

import cz.upce.fei.skodaj.bdats.semestralprojectc.data.IPamatky;
import cz.upce.fei.skodaj.bdats.semestralprojectc.data.Location;
import cz.upce.fei.skodaj.bdats.semestralprojectc.data.Zamek;
import cz.upce.fei.skodaj.bdats.semestralprojectc.structs.eTypProhl;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
     * Data loaded from file
     */
    private List<Zamek> data;
    
    /**
     * Flag, whether selected item is set
     */
    private boolean hasSelected = false;
    
    /**
     * Selected item
     */
    private Zamek selected = null;
    
    /**
     * Actual location
     */
    private Location actualLocation = null;
    
    /**
     * Order used to browse data
     */
    private eTypProhl order = eTypProhl.DO_SIRKY;
    
    
    /**
     * Creates new loader of state from XML files
     */
    public XMLLoader()
    {
        this.loaded = false;
        this.data = new ArrayList<>();
    }
    
    /**
     * Initializes loader
     */
    private void init()
    {
        this.loaded = false;
        this.data.clear();
        this.hasSelected = false;
        this.selected = null;
        this.order = eTypProhl.DO_SIRKY;
    }

    @Override
    public void load(String file)
    {
        this.init();
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
                    if(Objects.nonNull(n.getNodeName()))
                    {
                        switch(n.getNodeName())
                        {
                            case XMLFormat.XML_DATA: this.parseData(n); break;
                            case XMLFormat.XML_ORDER:
                                eTypProhl o = XMLParser.getInstance().parseOrder(n);
                                if (Objects.nonNull(o))
                                {
                                    this.order = o;
                                }
                                break;
                            case XMLFormat.XML_SELECTED:
                            {
                                NodeList nl = n.getChildNodes();
                                Node castleNode = null;
                                for (int c = 0; c < nl.getLength(); c++)
                                {
                                    Node nli = nl.item(c);
                                    if (nli.getNodeName().equals(XMLFormat.XML_CASTLE))
                                    {
                                        castleNode = nl.item(c);
                                        break;
                                    }
                                }
                                if (Objects.nonNull(castleNode))
                                {
                                    Zamek sel = XMLParser.getInstance().parseCastle(castleNode);
                                    if (Objects.nonNull(sel))
                                    {
                                        this.hasSelected = true;
                                        this.selected = sel;
                                    }
                                }                                
                                break;
                            }
                            case XMLFormat.XML_ACTUAL_LOCATION:
                            {
                                this.actualLocation = XMLParser.getInstance().parseActualLocation(n);
                                break;
                            }
                        }
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
     * Parses data from XML file
     * @param node Node containing data
     */
    private void parseData(Node node)
    {
        NodeList nodes = node.getChildNodes();
        for(int i = 0; i < nodes.getLength(); i++)
        {
            if (Objects.nonNull(nodes.item(i).getNodeName())  && nodes.item(i).getNodeName().equals(XMLFormat.XML_CASTLE))
            {
                Zamek z = XMLParser.getInstance().parseCastle(nodes.item(i));
                if (Objects.nonNull(z))
                {
                    this.data.add(z);
                }
            }
        }
    }
    
    @Override
    public boolean loaded()
    {
        return this.loaded;
    }

    @Override
    public void getData(IPamatky manager)
    {
        for (Zamek z: this.data)
        {
            manager.vlozZamek(z);
        }
    }

    @Override
    public eTypProhl getOrder()
    {
        return this.order;
    }

    @Override
    public boolean hasSelected()
    {
        return this.hasSelected;
    }

    @Override
    public Zamek getSelected()
    {
        return this.selected;
    }

    @Override
    public Location getActualLocation()
    {
        return this.actualLocation;
    }

}
