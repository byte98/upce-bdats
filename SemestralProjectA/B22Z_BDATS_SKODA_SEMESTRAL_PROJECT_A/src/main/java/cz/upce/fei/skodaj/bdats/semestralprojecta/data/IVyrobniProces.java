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

import cz.upce.fei.skodaj.bdats.semestralprojecta.structs.IAbstrLifo;

/**
 * Interface defining manager of all processes
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public interface IVyrobniProces extends Iterable
{
    /**
     * Imports data from file
     * @param soubor Path to file
     * @return Number of loaded processes
     */
    abstract int importDat(String soubor);
    
    /**
     * Inserts new process
     * @param proces Process which will be inserted
     * @param pozice Position to which process will be inserted
     */
    abstract void vlozProces(Proces proces, enumPozice pozice);
    
    /**
     * Gets access to process
     * @param pozice Position of process
     * @return Process on set position
     */
    abstract Proces zpristupniProces(enumPozice pozice);
    
    /**
     * Removes process
     * @param pozice Position of process
     * @return Removed process
     */
    abstract Proces odeberProces(enumPozice pozice);  
    
    /**
     * Selects candidates for reorganization
     * @param cas Time selector for reorganization
     * @param reorgan Type of reorganization
     * @return Stack like structure with candidates for reorganization
     */
    abstract IAbstrLifo<Proces> vytipujKandidatiReorg(int cas, enumReorg reorgan);
    
    /**
     * Performs a reorganization
     * @param reorgan Type of reorganization
     * @param zasobnik Structure with candidates for reorganization
     */
    abstract void reorganizace(enumReorg reorgan, IAbstrLifo<Proces> zasobnik);
    
    /**
     * Cancels all processes
     */
    abstract void zrus();
}
