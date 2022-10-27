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
     * Element defining root of XML document
     */
    public static final String XML_ROOT = "XML";
    
    /**
     * Element defining list with processes
     */
    public static final String XML_LIST = "DATA";
    
    /**
     * Element defining one single process
     */
    public static final String XML_PROCESS = "PROCESS";
    
    /**
     * Element defining type of process 
     */
    public static final String XML_PROCESS_TYPE = "TYPE";
    
    /**
     * Element defining robotic process
     */
    public static final String XML_PROCESS_TYPE_ROBOTIC = "ROBOTIC";
    
    /**
     * Element defining manual process
     */
    public static final String XML_PROCESS_TYPE_MANUAL = "MANUAL";
    
    /**
     * Element defining identifier of process
     */
    public static final String XML_PROCESS_ID = "ID";
    
    /**
     * Element defining time needed to complete process
     */
    public static final String XML_PROCESS_TIME = "TIME";
    
    /**
     * Element defining people needed to complete process
     */
    public static final String XML_PROCESS_PEOPLE = "PEOPLE";
    
    /**
     * Element defining reorganization
     */
    public static final String XML_REORGANIZATION = "REORGANIZATION";
    
    /**
     * Element defining type of reorganization
     */
    public static final String XML_REORGANIZATION_TYPE = "TYPE";
    
    /**
     * Element defining aggregation
     */
    public static final String XML_REORGANIZATION_TYPE_AGGREGATION = "AGGREGATION";
    
    /**
     * Element defining decomposition
     */
    public static final String XML_REORGANIZATION_TYPE_DECOMPOSITION = "DECOMPOSITION";
    
    /**
     * Element defining list of processes accepted to reorganization
     */
    public static final String XML_REORGANIZATION_LIST = "ITEMS";
    
    /**
     * Element defining actual process
     */
    public static final String XML_ACTUAL = "ACTUAL";
}
