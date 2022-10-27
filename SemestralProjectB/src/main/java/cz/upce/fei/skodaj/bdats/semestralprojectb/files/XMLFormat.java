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

/**
 * Definition of used XML format
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public abstract class XMLFormat
{
    /**
     * Definition of header of XML documents
     */
    public static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    
    /**
     * Number of spaces used for indentation
     */
    public static final int XML_SPACES = 4;
    
    /**
     * Element defining list of castles
     */
    public static final String XML_DATA = "DATA";
    
    /**
     * Element defining root of XML document
     */
    public static final String XML_ROOT = "XML";
    
    /**
     * Element defining castle
     */
    public static final String XML_CASTLE = "CASTLE";
    
    /**
     * Element defining identifier of castle
     */
    public static final String XML_CASTLE_ID = "ID";
    
    /**
     * Element defining name of castle
     */
    public static final String XML_CASTLE_NAME = "NAME";
    
    /**
     * Element defining GPS location
     */
    public static final String XML_LOCATION = "LOCATION";
    
    /**
     * Element defining latitude of GPS location
     */
    public static final String XML_LOCATION_LAT = "LATITUDE";
    
    /**
     * Element defining longitude of GPS location
     */
    public static final String XML_LOCATION_LON = "LONGITUDE";
    
    /**
     * Element defining key used to store data
     */
    public static final String XML_KEY = "KEY";
    
    /**
     * Element defining key used to store data is name
     */
    public static final String XML_KEY_NAME = "NAME";
    
    /**
     * Element defining key used to store data is GPS location
     */
    public static final String XML_KEY_GPS = "GPS";
    
    /**
     * Element defining order used to browse data
     */
    public static final String XML_ORDER = "ORDER";
    
    /**
     * Element defining order used to browse data is depth first order
     */
    public static final String XML_ORDER_DEPTH = "DEPTH";
    
    /**
     * Element defining order used to browse data is level order
     */
    public static final String XML_ORDER_LEVEL = "LEVEL";
    
    /**
     * Element defining actually searched castle
     */
    public static final String XML_SEARCHED = "SEARCHED";
    
    /**
     * Element defining nearest castle
     */
    public static final String XML_NEAREST = "NEAREST";
    
    /**
     * Element defining selected castle
     */
    public static final String XML_SELECTED = "SELECTED";
}
