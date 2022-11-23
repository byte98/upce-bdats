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
package cz.upce.fei.skodaj.bdats.semestralprojectc.data;

import cz.upce.fei.skodaj.bdats.semestralprojectc.structs.eTypProhl;
import java.util.Iterator;

/**
 * Class defining methods for manager of castles
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public interface IPamatky
{
    /**
     * Integer representation of OK state
     */
    public static final int OK = 1;
    
    /**
     * Integer representation of error state
     */
    public static final int ERR = 0;

    /**
     * Sets actual position
     * @param pozice New actual position
     */
    public abstract void nastavPozici(Location pozice);
    
    /**
     * Imports data from text file
     * @return Number of imported castles
     */
    public abstract int importDatZTXT();
    
    /**
     * Adds castle managed by this manager
     * @param zamek Castle which will be managed by this manager
     * @return {@link IPamatky#OK}, if castle has been successfully added,
     *         {@link IPamatky#ERR} otherwise
     */
    public abstract int vlozZamek(Zamek zamek); 
    
    /**
     * Removes nearest castle from this manager of castles
     */
    public abstract void odeberZamek();
    
    /**
     * Gets nearest castle from this manager of castles
     * @return Nearest castle from this manager of castles
     */
    public abstract Zamek zpristupniZamek();
    
    /**
     * Deletes whole manager of castles
     */
    public abstract void zrus();
    
    /**
     * Gets iterator
     * @param typ Type of browsing of structure
     * @return Iterator with desired browser of structure
     */
    public abstract Iterator<Zamek> vytvorIterator(eTypProhl typ);
}
