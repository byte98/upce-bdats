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
import cz.upce.fei.skodaj.bdats.semestralprojecta.data.ProcesRoboticky;
import java.util.Objects;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Class which parses XML to state of program
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public abstract class XMLParser
{
    /**
     * Parses XML node containing information about process into process
     * @param n Node containing information about process
     * @return Process loaded from XML node
     */
    public static Proces parseProcess(Node n)
    {
        Proces reti = null;
        if (n.getNodeName().equals(XMLFormat.XML_PROCESS))
        {
            int time = Integer.MIN_VALUE;
            String id = null;
            int people = Integer.MIN_VALUE;
            boolean isManual = false;
            NodeList nodes = n.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++)
            {
                Node node = nodes.item(i);
                switch (node.getNodeName())
                {
                    case XMLFormat.XML_PROCESS_ID: id = nodes.item(i).getTextContent(); break;
                    case XMLFormat.XML_PROCESS_TYPE: isManual = nodes.item(i).getTextContent().equals(XMLFormat.XML_PROCESS_TYPE_MANUAL); break;
                    case XMLFormat.XML_PROCESS_PEOPLE: people = Integer.parseInt(nodes.item(i).getTextContent()); break;
                    case XMLFormat.XML_PROCESS_TIME: time = Integer.parseInt(nodes.item(i).getTextContent()); break;
                }
            }
            if (Objects.nonNull(id) && time != Integer.MIN_VALUE)
            {
                if (isManual && people != Integer.MIN_VALUE)
                {
                    reti = new ProcesManualni(time, people, id);
                }
                else if (isManual == false)
                {
                    reti = new ProcesRoboticky(time, id);
                }
            }
        }
        return reti;
    }
}
