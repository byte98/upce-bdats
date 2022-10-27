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
package cz.upce.fei.skodaj.bdats.semestralprojecta.data;

import java.nio.charset.Charset;

/**
 * Class representing process done by robot
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public final class ProcesRoboticky extends Proces
{
    /**
     * Identifier of robotic processes
     */
    private static final char IDENTIFIER = 'R';
    
    /**
     * Creates new robotic process
     * @param cas Time needed to complete process
     */
    public ProcesRoboticky(int cas)
    {
        super(cas);
    }
    
    /**
     * Creates new robotic process
     * @param cas Time needed to complete process
     * @param id Identifier of process
     */
    public ProcesRoboticky(int cas, String id)
    {
        super(cas, id);
    }
    
    @Override
    protected String getIdFormat(long id)
    {
        return String.format("%c%03d", ProcesRoboticky.IDENTIFIER, id);
    }
    
    /**
     * Checks, whether process is robotic
     * @param id Identifier of process
     * @return TRUE if process is robotic, FALSE otherwise
     */
    public static boolean isRobotic(String id)
    {
        return id.startsWith(String.valueOf(ProcesRoboticky.IDENTIFIER));
    }
    
    @Override
    public String toString()
    {
        StringBuilder reti = new StringBuilder(super.toString());
        reti.append(", ");
        byte[] iIcon = new byte[]{(byte)0xe2, (byte)0x84, (byte)0xb9};
        reti.append(new String(iIcon, Charset.forName("UTF-8")));
        reti.append(" DRUH: ");
        byte[] robotIcon = new byte[]{(byte)0xf0, (byte)0x9f, (byte)0xa4, (byte)0x96};
        reti.append(new String(robotIcon, Charset.forName("UTF-8")));
        reti.append(" ROBOTICKY");
        return reti.toString();
    }
}
