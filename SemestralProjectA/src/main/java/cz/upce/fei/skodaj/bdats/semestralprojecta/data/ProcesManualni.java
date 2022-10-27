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
 * Class representing process done by human being
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public final class ProcesManualni extends Proces
{
    /**
     * Identifier of manual processes
     */
    private static final char IDENTIFIER = 'O';
    
    /**
     * Number of human beings needed to complete process
     */
    private int pocetLidi;
    
    /**
     * Create new process which will be done by human beings
     * @param cas Time needed to complete process
     * @param pocetLidi Number of human beings needed to complete process
     */
    public ProcesManualni(int cas, int pocetLidi)
    {
        super(cas);
        this.pocetLidi = pocetLidi;
    }
    
    /**
     * Create new process which will be done by human beings
     * @param cas Time needed to complete process
     * @param pocetLidi Number of human beings needed to complete process
     * @param id Identifier of process
     */
    public ProcesManualni(int cas, int pocetLidi, String id)
    {
        super(cas, id);
        this.pocetLidi = pocetLidi;
    }
    
    @Override
    protected String getIdFormat(long id)
    {
        return String.format("%c%03d", ProcesManualni.IDENTIFIER, id);
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getter">
    public int getPocetLidi()
    {
        return this.pocetLidi;
    }
    //</editor-fold>
    
    /**
     * Checks, whether process is manual
     * @param id Identifier of process
     * @return TRUE if process is manual, FALSE otherwise
     */
    public static boolean isManual(String id)
    {
        return id.startsWith(String.valueOf(ProcesManualni.IDENTIFIER));
    }
    
    @Override
    public String toString()
    {
        StringBuilder reti = new StringBuilder(super.toString());
        reti.append(", ");
        byte[] iIcon = new byte[]{(byte)0xe2, (byte)0x84, (byte)0xb9};
        reti.append(new String(iIcon, Charset.forName("UTF-8")));
        reti.append(" DRUH: ");
        byte[] humanIcon = new byte[]{(byte)0xf0, (byte)0x9f, (byte)0x91, (byte)0xb7};
        reti.append(new String(humanIcon, Charset.forName("UTF-8")));
        reti.append(" MANUALNI, ");
        byte[] numberIcon = new byte[]{(byte)0xf0, (byte)0x9f, (byte)0x94, (byte)0xa2};
        reti.append(new String(numberIcon, Charset.forName("UTF-8")));
        reti.append(" POCET LIDI: ");
        reti.append(this.pocetLidi);
        return reti.toString();
    }
}
