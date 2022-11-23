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
 * Interface defining basic functionality of double list
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public interface IAbstrDoubleList<T> extends Iterable<T>
{
    /**
     * Deletes whole list
     */
    abstract void zrus();
    
    /**
     * Checks, whether is list empty
     * @return TRUE if list is empty, FALSE otherwise
     */
    abstract boolean jePrazdny();
    
    /**
     * Adds data to first position of list
     * @param data Data which will be added to first position of list
     */
    abstract void vlozPrvni(T data);
    
    /**
     * Adds data to the end of the list
     * @param data Data which will be added to the end of the list
     */
    abstract void vlozPosledni(T data);
    
    /**
     * Adds data after actual element of the list
     * @param data Data which will be added after actual element of the list
     */
    abstract void vlozNaslednika(T data);
    
    /**
     * Adds date before actual element of the list
     * @param data Data which will be added before actual element of the list
     */
    abstract void vlozPredchudce(T data);
    
    /**
     * Gets actual element of the list and sets next one as actual
     * @return Actual element of the list
     */
    abstract T zpristupniAktualni();
    
    /**
     * Gets first element of the list
     * @return First element of the list
     */
    abstract T zpristupniPrvni();
    
    /**
     * Gets last element of the list
     * @return Last element of the list
     */
    abstract T zpristupniPosledni();
    
    /**
     * Gets next element after actual one
     * @return Next element from the list after actual one
     */
    abstract T zpristupniNaslednika();
    
    /**
     * Gets previous element before actual one
     * @return Previous element from the list before actual one
     */
    abstract T zpristupniPredchudce();
    
    /**
     * Removes actual element from the list and sets first element as actual one
     * @return Actual element of the list
     */
    abstract T odeberAktualni();
    
    /**
     * Removes first element of the list
     * @return First element of the list
     */
    abstract T odeberPrvni();
    
    /**
     * Removes last element of the list
     * @return Last element of the list
     */
    abstract T odeberPosledni();
    
    /**
     * Removes next element after actual one
     * @return Next element of the list after actual one
     */
    abstract T odeberNaslednika();
    
    /**
     * Removes previous element before actual one
     * @return Previous element of the list before actual one
     */
    abstract T odeberPredchudce();
}
