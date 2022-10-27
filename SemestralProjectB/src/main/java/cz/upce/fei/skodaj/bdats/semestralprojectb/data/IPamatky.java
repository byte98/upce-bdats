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
package cz.upce.fei.skodaj.bdats.semestralprojectb.data;

import cz.upce.fei.skodaj.bdats.semestralprojectb.structs.eTypKey;
import cz.upce.fei.skodaj.bdats.semestralprojectb.structs.eTypProhl;
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
     * Finds castle by its key
     * @param klic Key which will be used for searching of castle
     * @return Castle found by its key or NULL, if there is no such castle
     */
    public abstract Zamek najdiZamek(String klic);    
    
    /**
     * Removes castle from this manager of castles
     * @param klic Key of castle which will be removed
     * @return {@link IPamatky#OK}, if castle has been successfully removed,
     *         {@link IPamatky#ERR} otherwise
     */
    public abstract Zamek odeberZamek(String klic);
    
    /**
     * Deletes whole manager of castles
     */
    public abstract void zrus();
    
    /**
     * Rebuilds internal binary search tree to be more balanced using actually
     * set key
     */
    public abstract void prebuduj();
    
    /**
     * Sets type of key which will be used to store castles in internal
     * binary search tree
     * @param typ Type of key which will be used
     */
    public abstract void nastavKlic(eTypKey typ);
    
    /**
     * Founds nearest castle by its location,
     * if actual key is set to GPS location
     * @param key GPS location of origin point
     * @return Castle nearest to defined GPS position
     */
    public abstract Zamek najdiNejbliz(String key);
    
    /**
     * Gets iterator
     * @param typ Type of browsing of internal binary search tree
     * @return Iterator with desired browser of binary tree
     */
    Iterator<Zamek> vytvorIterator(eTypProhl typ);
}
