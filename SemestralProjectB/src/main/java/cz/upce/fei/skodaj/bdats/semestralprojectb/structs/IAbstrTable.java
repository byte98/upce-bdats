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
package cz.upce.fei.skodaj.bdats.semestralprojectb.structs;

import java.util.Iterator;

/**
 * Interface abstracting methods for tables
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public interface IAbstrTable<K extends Comparable<K>, V>
{
    /**
     * Deletes whole table
     */
    public abstract void zrus();
    
    /**
     * Checks, whether table is empty
     * @return TRUE if table is empty, FALSE otherwise
     */
    public abstract boolean jePrazdny();
    
    /**
     * Finds value by key
     * @param key Key which defines value
     * @return Value defined by key or NULL, if there is no such an key in table
     */
    public abstract V najdi(K key);
    
    /**
     * Inserts item in table
     * @param key Key defining item
     * @param value Value of item
     */
    public abstract void vloz (K key, V value);
    
    /**
     * Removes item from table
     * @param key Key defining item
     * @return Value of item defined by key
     */
    V odeber(K key);
    
    /**
     * Creates iterator of values
     * @param typ Type of way browsing through table
     * @return Iterator of values
     */
    Iterator<V> vytvorIterator (eTypProhl typ);
}
