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
import java.util.Objects;

/**
 * Class abstracting common attributes for each process
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public abstract sealed class Proces
        permits ProcesRoboticky, ProcesManualni
{
    /**
     * Counter used for generating identifiers
     */
    private static long counter = 1;
    
    /**
     * Identifier of process
     */
    protected String id;
    
    /**
     * Time needed to complete process
     */
    protected int casProcesu;
    
    //<editor-fold defaultstate="collapsed" desc="Getters">
    public String getId()
    {
        return this.id;
    }
    
    public int getCas()
    {
        return this.casProcesu;
    }
    //</editor-fold>
    
    /**
     * Creates new process
     * @param cas Time needed to complete process
     */
    public Proces(int cas)
    {
        this.id = this.getIdFormat(Proces.counter);
        Proces.counter++;
        this.casProcesu = cas;
    }
    
    /**
     * Creates new process
     * @param cas Time needed to complete process
     * @param id Identifier of process
     */
    public Proces(int cas, String id)
    {
        this.casProcesu = cas;
        this.id = id;
    }
    
    /**
     * Increments counter of generated processes
     */
    public static void incrementCounter()
    {
        Proces.counter++;
    }
    
    /**
     * Gets formatted identifier of process
     * @param id Identifier which will be formatted
     * @return Formatted identifier of process
     */
    protected abstract String getIdFormat(long id);
    
    @Override
    public String toString()
    {
        StringBuilder reti = new StringBuilder();
        byte[] idIcon = new byte[]{(byte)0xf0, (byte)0x9f, (byte)0x86, (byte)0x94};
        reti.append(new String(idIcon, Charset.forName("UTF-8")));
        reti.append(" ID: ");
        reti.append(this.getId());
        reti.append(", ");
        byte[] clockIcon = new byte[]{(byte)0xf0, (byte)0x9f, (byte)0x95, (byte)0x92};
        reti.append(new String(clockIcon, Charset.forName("UTF-8")));
        reti.append(" CAS: ");
        reti.append(this.getCas());
        return reti.toString();
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Proces other = (Proces) obj;
        return Objects.equals(this.id, other.id);
    }
    
    
}
