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

/**
 * Interface defining behavior of 'last-in-first-out' structure
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public abstract interface IAbstrLifoFifo<T> extends Iterable<T>
{
    /**
     * Deletes whole structure
     */
    void zrus();
    
    /**
     * Checks, whether structure is empty or not
     * @return TRUE if structure is empty, FALSE otherwise
     */
    boolean jePrazdny();
    
    /**
     * Inserts data into structure
     * @param data Data which will be inserted into structure
     */
    void vloz(T data);
    
    /**
     * Gets and removes data from structure
     * @return Data removed from structure
     */
    T odeber();
    
}
