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

import cz.upce.fei.skodaj.bdats.semestralprojectc.data.Location;
import cz.upce.fei.skodaj.bdats.semestralprojectc.data.Zamek;
import cz.upce.fei.skodaj.bdats.semestralprojectc.structs.eTypProhl;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Class which parses XML to state of program
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class XMLParser
{
    /**
     * Reference to instance of this class
     */
    private static XMLParser instance;
    
    /**
     * Private constructor
     */
    private XMLParser(){}
    
    /**
     * Gets instance of XML parser
     * @return Instance of XML parser
     */
    public static XMLParser getInstance()
    {
        if (Objects.isNull(XMLParser.instance))
        {
            XMLParser.instance = new XMLParser();
        }
        return XMLParser.instance;
    }
    
    /**
     * Parses castle from XML node
     * @param node XML node containing castle
     * @return Castle parsed from XML node or NULL, if castle cannot be parsed
     */
    public Zamek parseCastle(Node node)
    {
        Zamek reti = null;
        if (node.getNodeName().equals(XMLFormat.XML_CASTLE))
        {
            long id = Long.MIN_VALUE;
            String name = null;
            Location loc = null;
            try
            {
                NodeList nodes = node.getChildNodes();
                for (int i = 0; i < nodes.getLength(); i++)
                {
                    switch (nodes.item(i).getNodeName())
                    {
                        case XMLFormat.XML_LOCATION:
                            loc = this.parseLocation(nodes.item(i));
                            break;
                        case XMLFormat.XML_CASTLE_ID:
                            {
                                Node val = nodes.item(i).getFirstChild();
                                id = Long.parseLong(val.getNodeValue());
                                break;
                            }
                        case XMLFormat.XML_CASTLE_NAME:
                            {
                                Node val = nodes.item(i).getFirstChild();
                                name = val.getNodeValue();
                                break;
                            }
                        default:
                            break;
                    }
                }
            }
            catch (NumberFormatException ex)
            {
                Logger.getLogger(XMLParser.class.getName()).log(Level.WARNING, null, ex);
            }
            if (id != Long.MIN_VALUE && Objects.nonNull(loc) && Objects.nonNull(name))
            {
                reti = new Zamek(name, loc, id);
            }
        }
        
        return reti;
    }
    
    /**
     * Parses location from XML node
     * @param node XML node containing location
     * @return Location parsed from XML node or NULL, if location cannot be parsed
     */
    public Location parseLocation(Node node)
    {
        Location reti = null;
        if (node.getNodeName().equals(XMLFormat.XML_LOCATION))
        {
            double lat = Double.NaN;
            double lon = Double.NaN;
            NodeList nodes = node.getChildNodes();
            try
            {
                for (int i = 0; i < nodes.getLength(); i++)
                {
                    if (nodes.item(i).getNodeName().equals(XMLFormat.XML_LOCATION_LAT))
                    {
                        Node val = nodes.item(i).getFirstChild();
                        lat = Double.parseDouble(val.getNodeValue());
                    }
                    else if (nodes.item(i).getNodeName().equals(XMLFormat.XML_LOCATION_LON))
                    {
                        Node val = nodes.item(i).getFirstChild();
                        lon = Double.parseDouble(val.getNodeValue());
                    }
                }
            }
            catch(NumberFormatException ex)
            {
                Logger.getLogger(XMLParser.class.getName()).log(Level.WARNING, null, ex);
            }
            if (lat != Double.NaN && lon != Double.NaN)
            {
                reti = new Location(lat, lon);
            }
        }
        return reti;
    }
    
    /**
     * Parses order used to browse data from XML node
     * @param node XML node containing information about order
     * @return Order used to browse data parsed from XML node,
     *         or NULL if order cannot be parsed
     */
    public eTypProhl parseOrder(Node node)
    {
        eTypProhl reti = null;
        if (node.getNodeName().equals(XMLFormat.XML_ORDER))
        {
            Node val = node.getFirstChild();
            if (val.getNodeValue().equals(XMLFormat.XML_ORDER_DEPTH))
            {
                reti = eTypProhl.DO_HLOUBKY;
            }
            else if (val.getNodeValue().equals(XMLFormat.XML_ORDER_LEVEL))
            {
                reti = eTypProhl.DO_SIRKY;
            }
        }
        return reti;
    }
    
    /**
     * Parses actual location
     * @param node XML node containing information about actual location
     * @return Actual location
     */
    public Location parseActualLocation(Node node)
    {
        NodeList nodes = node.getChildNodes();
        Location reti = null;
        for (int i = 0; i < nodes.getLength(); i++)
        {
            if (nodes.item(i).getNodeName().equals(XMLFormat.XML_LOCATION))
            {
                reti = this.parseLocation(nodes.item(i));
                break;
            }
        }
        return reti;
    }
}
