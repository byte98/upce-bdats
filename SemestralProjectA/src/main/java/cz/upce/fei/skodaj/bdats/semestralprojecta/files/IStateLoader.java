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
package cz.upce.fei.skodaj.bdats.semestralprojecta.files;

import cz.upce.fei.skodaj.bdats.semestralprojecta.data.Proces;
import cz.upce.fei.skodaj.bdats.semestralprojecta.data.enumReorg;
import cz.upce.fei.skodaj.bdats.semestralprojecta.structs.IAbstrDoubleList;
import cz.upce.fei.skodaj.bdats.semestralprojecta.structs.IAbstrLifo;
import cz.upce.fei.skodaj.bdats.semestralprojecta.ui.ProgVyrobniProces;

/**
 * Interface defining behaviour of loaders of actual state of the program
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public interface IStateLoader
{
    /**
     * Loads actual state of program
     * @param file Path to file from which state will be loaded
     */
    public abstract void load(String file);
    
    /**
     * Loads actual state of program
     */
    public default void load()
    {
      this.load(ProgVyrobniProces.FILE_SAVE);
    }
    
    /**
     * Checks, whether data has been loaded
     * @return TRUE if data has been successfully loaded, FALSE otherwise
     */
    public abstract boolean loaded();
            
    
    /**
     * Gets loaded processes
     * @return Processes loaded from file
     */
    public abstract IAbstrDoubleList<Proces> getProcesses();
    
    /**
     * Checks, whether reorganization is in progress
     * @return TRUE if reorganization is in progress, FALSE otherwise
     */
    public abstract boolean getReorgActive();
    
    /**
     * Gets type of loaded reorganization
     * @return Type of loaded reorganization
     */
    public abstract enumReorg getReorgType();
    
    /**
     * Gets processes accepted for reorganization
     * @return Processes accepted for reorganization
     */
    public abstract IAbstrLifo<Proces> getReorgList();
    
    /**
     * Gets actual process
     * @return Actual process
     */
    public abstract Proces getActual();
}
