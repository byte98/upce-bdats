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

import cz.upce.fei.skodaj.bdats.semestralprojectb.data.Location;
import cz.upce.fei.skodaj.bdats.semestralprojectb.data.Zamek;
import cz.upce.fei.skodaj.bdats.semestralprojectb.structs.eTypKey;
import cz.upce.fei.skodaj.bdats.semestralprojectb.structs.eTypProhl;
import java.util.Objects;

/**
 * Class which formats data into XML string
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class XMLFormatter
{
    /**
     * Reference to instance of this class
     */
    private static XMLFormatter instance;
    
    /**
     * Private constructor of class
     */
    private XMLFormatter(){}
    
    /**
     * Gets instance of XML formatter
     * @return Instance of XML formatter
     */
    public static XMLFormatter getInstance()
    {
        if (Objects.isNull(XMLFormatter.instance))
        {
            XMLFormatter.instance = new XMLFormatter();
        }
        return XMLFormatter.instance;
    }
    
    /**
     * Generates spaces for actual level of indentation
     * @param level Actual level of indentation
     * @return Spaces for actual level of indentation
     */
    public String indent(int level)
    {
        StringBuilder reti = new StringBuilder();
        for (int i = 0; i < level * XMLFormat.XML_SPACES; i++)
        {
            reti.append(" ");
        }
        return reti.toString();
    }
    
    /**
     * Generates element with its value
     * @param element Name of element
     * @param value Value of element
     * @param indent Actual level of indentation
     * @return String containing element with its value
     */
    private String element(String element, String value, int indent)
    {
        StringBuilder reti = new StringBuilder();
        if (Objects.nonNull(element) && Objects.nonNull(value))
        {
            reti.append(this.indent(indent)).append("<").append(element).append(">")
                    .append(value)
                .append("</").append(element).append(">");
        }
        return reti.toString();
    }
    
    /**
     * Formats castle into XML style string
     * @param castle Castle which will be formatted into XML string
     * @param indent Actual level of indentation
     * @return String in XML format containing information about castle
     */
    public String formatCastle(Zamek castle, int indent)
    {
        StringBuilder reti = new StringBuilder();
        reti.append(this.indent(indent)).append("<").append(XMLFormat.XML_CASTLE).append(">").append(System.lineSeparator())
            .append(this.element(XMLFormat.XML_CASTLE_ID, String.valueOf(castle.getId()), indent + 1)).append(System.lineSeparator())
            .append(this.element(XMLFormat.XML_CASTLE_NAME, castle.getName(), indent + 1)).append(System.lineSeparator())
            .append(this.formatLocation(castle.getLocation(), indent + 1))
            .append(this.indent(indent)).append("</").append(XMLFormat.XML_CASTLE).append(">").append(System.lineSeparator());
        return reti.toString();
    }
    
    /**
     * Formats GPS location into XML style string
     * @param loc Location which will be formatted into XML string
     * @param indent Actual level of indentation
     * @return String in XML format containing information about location
     */
    public String formatLocation(Location loc, int indent)
    {
        StringBuilder reti = new StringBuilder();
        reti.append(this.indent(indent)).append("<").append(XMLFormat.XML_LOCATION).append(">").append(System.lineSeparator())
            .append(this.element(XMLFormat.XML_LOCATION_LAT, String.valueOf(loc.getLatitude()), indent + 1)).append(System.lineSeparator())
            .append(this.element(XMLFormat.XML_LOCATION_LON, String.valueOf(loc.getLongitude()), indent + 1)).append(System.lineSeparator())
            .append(this.indent(indent)).append("</").append(XMLFormat.XML_LOCATION).append(">").append(System.lineSeparator());
        return reti.toString();
    }
    
    /**
     * Formats key used to store castles to XML style string
     * @param key Key used to store castles
     * @param indent Actual level of indentation
     * @return String in XML format containing key used to store data
     */
    public String formatKey(eTypKey key, int indent)
    {
        StringBuilder reti = new StringBuilder();
        reti.append(this.element(XMLFormat.XML_KEY, (
                key == eTypKey.GPS
                ? XMLFormat.XML_KEY_GPS
                : XMLFormat.XML_KEY_NAME
        ), indent)).append(System.lineSeparator());
        
        return reti.toString();
    }
    
    /**
     * Formats order used to browse data to XML style string
     * @param order Order used to browse data
     * @param indent Actual level of indentation
     * @return String in XML format containing order used to browse data
     */
    public String formatOrder(eTypProhl order, int indent)
    {
        StringBuilder reti = new StringBuilder();
        reti.append(this.element(XMLFormat.XML_ORDER, (
                order == eTypProhl.DO_HLOUBKY
                ? XMLFormat.XML_ORDER_DEPTH
                : XMLFormat.XML_ORDER_LEVEL
        ), indent)).append(System.lineSeparator());
        return reti.toString();
    }
    
    /**
     * Formats actually searched castle into XML style string
     * @param searched Actually searched castle
     * @param indent Actual level of indentation
     * @return String in XML format containing information about actually searched castle
     */
    public String formatSearched(Zamek searched, int indent)
    {
        StringBuilder reti = new StringBuilder();
        reti.append(this.indent(indent)).append("<").append(XMLFormat.XML_SEARCHED).append(">").append(System.lineSeparator());
        reti.append(this.formatCastle(searched, indent + 1));
        reti.append(this.indent(indent)).append("</").append(XMLFormat.XML_SEARCHED).append(">").append(System.lineSeparator());
        return reti.toString();
    }
    
    /**
     * Formats actually nearest castle into XML style string
     * @param nearest Nearest castle
     * @param indent Actual level of indentation
     * @return String in XML format containing information about actually searched castle
     */
    public String formatNearest(Zamek nearest, int indent)
    {
        StringBuilder reti = new StringBuilder();
        reti.append(this.indent(indent)).append("<").append(XMLFormat.XML_NEAREST).append(">").append(System.lineSeparator());
        reti.append(this.formatCastle(nearest, indent + 1));
        reti.append(this.indent(indent)).append("</").append(XMLFormat.XML_NEAREST).append(">").append(System.lineSeparator());
        return reti.toString();
    }
}
