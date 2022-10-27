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
import cz.upce.fei.skodaj.bdats.semestralprojecta.data.ProcesManualni;
import cz.upce.fei.skodaj.bdats.semestralprojecta.data.enumReorg;
import cz.upce.fei.skodaj.bdats.semestralprojecta.structs.AbstrLifo;
import cz.upce.fei.skodaj.bdats.semestralprojecta.structs.IAbstrLifo;
import java.util.Objects;

/**
 * Class which formats data into XML string
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public abstract class XMLFormatter
{
    /**
     * Generates spaces for actual level of indentation
     * @param level Actual level of indentation
     * @return Spaces for actual level of indentation
     */
    public static String indent(int level)
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
     * @return String containing element with its value
     */
    private static String element(String element, String value)
    {
        StringBuilder reti = new StringBuilder();
        if (Objects.nonNull(element) && Objects.nonNull(value))
        {
            reti.append("<").append(element).append(">")
                    .append(value)
            .append("</").append(element).append(">");
        }
        return reti.toString();
    }
    
    /**
     * Encodes process into XML string
     * @param p Process which will be encoded into XML string
     * @param indent Actual level of indentation
     * @return XML string containing encoded process
     */
    public static String encodeProcess(Proces p, int indent)
    {
        StringBuilder reti = new StringBuilder();
        reti.append(XMLFormatter.indent(indent))
        .append("<").append(XMLFormat.XML_PROCESS).append(">").append(System.lineSeparator())
        .append(XMLFormatter.indent(indent + 1))
        .append(XMLFormatter.element(XMLFormat.XML_PROCESS_TYPE, ((p instanceof ProcesManualni) ? XMLFormat.XML_PROCESS_TYPE_MANUAL : XMLFormat.XML_PROCESS_TYPE_ROBOTIC)))
        .append(System.lineSeparator())
        .append(XMLFormatter.indent(indent + 1))
        .append(XMLFormatter.element(XMLFormat.XML_PROCESS_ID, p.getId()));
        if (p instanceof ProcesManualni)
        {
            reti.append(System.lineSeparator())
            .append(XMLFormatter.indent(indent + 1));
        }
        reti.append(XMLFormatter.element(
                (p instanceof ProcesManualni ? XMLFormat.XML_PROCESS_PEOPLE : null),
                (p instanceof ProcesManualni ? String.valueOf(((ProcesManualni) p).getPocetLidi()) : null)
        ))
        .append(System.lineSeparator())
        .append(XMLFormatter.indent(indent + 1))
        .append(XMLFormatter.element(XMLFormat.XML_PROCESS_TIME, String.valueOf(p.getCas())))
        .append(System.lineSeparator())
        .append(XMLFormatter.indent(indent))
        .append("</").append(XMLFormat.XML_PROCESS).append(">").append(System.lineSeparator());
        return reti.toString();
    }
    
    /**
     * Encodes reorganization into XML string
     * @param reorganization Type of reorganization
     * @param processes Processes accepted for reorganization
     * @param indent Actual level of indentation
     * @return XML string containing encoded reorganization
     */
    public static String encodeReorganization(enumReorg reorganization, IAbstrLifo<Proces> processes, int indent)
    {
        StringBuilder reti = new StringBuilder();
        reti.append(XMLFormatter.indent(indent))
        .append("<").append(XMLFormat.XML_REORGANIZATION).append(">").append(System.lineSeparator())
        .append(XMLFormatter.indent(indent + 1))
        .append(XMLFormatter.element(XMLFormat.XML_REORGANIZATION_TYPE, reorganization == enumReorg.AGREGACE ? XMLFormat.XML_REORGANIZATION_TYPE_AGGREGATION : XMLFormat.XML_REORGANIZATION_TYPE_DECOMPOSITION))
        .append(System.lineSeparator())
        .append(XMLFormatter.encodeReorganizationList(processes, indent + 1))
        .append(XMLFormatter.indent(indent))
        .append("</").append(XMLFormat.XML_REORGANIZATION).append(">").append(System.lineSeparator());
        return reti.toString();
    }
    
    /**
     * Encodes processes accepted for reorganization to XML string
     * @param processes Processes accepted for reorganization
     * @param indent Actual level of indentation
     * @return XML string containing encoded processes accepted for reorganization
     */
    private static String encodeReorganizationList(IAbstrLifo<Proces> processes, int indent)
    {
        StringBuilder reti = new StringBuilder();
        reti.append(XMLFormatter.indent(indent)).append("<").append(XMLFormat.XML_REORGANIZATION_LIST).append(">").append(System.lineSeparator());
        // Create new stack (will have reversed order)
        IAbstrLifo<Proces> reversed = new AbstrLifo<>();
        for (Proces p: processes)
        {
            reversed.vloz(p);
        }
        for (Proces p: reversed)
        {
            reti.append(XMLFormatter.encodeProcess(p, indent + 1));
        }
        reti.append(XMLFormatter.indent(indent)).append("</").append(XMLFormat.XML_REORGANIZATION_LIST).append(">").append(System.lineSeparator());
        return reti.toString();
    }
}
