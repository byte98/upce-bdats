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
package cz.upce.fei.skodaj.bdats.semestralprojectc.structs;

import java.util.Iterator;

/**
 * Interface abstracting all methods for data structure heap
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public interface IAbstrHeap<K extends Comparable, V> extends Iterable<V>
{
    /**
     * Default order for iterator
     */
    public static final eTypProhl DEFAULT_ORDER = eTypProhl.DO_SIRKY;
    
    /**
     * Builds heap data structure
     * @param data Data which will be stored in heap data structure
     * @param keys Keys for data which defines priorities in heap
     */
    public default void vybuduj(V[] data, K[] keys)
    {
        if (data.length == keys.length)
        {
            for(int i = 0; i < data.length; i++)
            {
                this.vloz(data[i], keys[i]);
            }
        }
    }
    
    /**
     * Rebuilds heap data structure
     */
    public abstract void prebuduj();
    
    /**
     * Deletes whole structure
     */
    public abstract void zrus();
    
    /**
     * Inserts data into heap
     * @param data Data which will be inserted into structure
     * @param key Key defining position in heap
     */
    public abstract void vloz(V data, K key);
    
    /**
     * Checks, whether data structure is empty
     * @return TRUE if data structure is empty, FALSE otherwise
     */
    public abstract boolean jePrazdny();
    
    /**
     * Removes maximal item from heap
     * @return Maximal item in heap
     */
    public abstract V odeberMax();
    
    /**
     * Gets maximal item in heap
     * @return Maximal item in heap
     */
    public abstract V zpristupniMax();
    
    /**
     * Gets iterator of data structure
     * @param order Order of elements returned by iterator
     * @return Iterator of elements from data structure by defined order 
     */
    public abstract Iterator<V> iterator(eTypProhl order);
    
    @Override
    public default Iterator<V> iterator()
    {
        return this.iterator(IAbstrHeap.DEFAULT_ORDER);
    }
}
