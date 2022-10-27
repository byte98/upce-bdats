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

import cz.upce.fei.skodaj.bdats.semestralprojecta.data.IVyrobniProces;
import cz.upce.fei.skodaj.bdats.semestralprojecta.data.Proces;
import cz.upce.fei.skodaj.bdats.semestralprojecta.data.enumReorg;
import cz.upce.fei.skodaj.bdats.semestralprojecta.structs.IAbstrLifo;
import cz.upce.fei.skodaj.bdats.semestralprojecta.ui.ProgVyrobniProces;

/**
 * Interface defining classes which can save actual state of program
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public interface IStateSaver
{
    /**
     * Sets type of reorganization
     * @param reorg Type of reorganization
     * @param processes Processes accepted for reorganization
     */
    public abstract void setReorganization(enumReorg reorg, IAbstrLifo<Proces> processes);
    
    /**
     * Unset selected type of reorganization
     */
    public abstract void unsetReorganization();
    
    /**
     * Saves actual state of program
     * @param processes Manager of all processes
     */
    public default void save(IVyrobniProces processes)
    {
        this.save(processes, ProgVyrobniProces.FILE_SAVE);
    }
    
    /**
     * Saves actual state of program
     * @param processes Manager of all processes
     * @param file File to which state will be saved into
     */
    public abstract void save(IVyrobniProces processes, String file);
    
    /**
     * Sets actual process
     * @param process Actual process
     */
    public abstract void setActual(Proces process);
    
    /**
     * Unset actual process
     */
    public abstract void unsetActual();
}
