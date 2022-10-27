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
package cz.upce.fei.skodaj.bdats.semestralprojectb.files;

import cz.upce.fei.skodaj.bdats.semestralprojectb.data.IPamatky;
import cz.upce.fei.skodaj.bdats.semestralprojectb.data.Zamek;
import cz.upce.fei.skodaj.bdats.semestralprojectb.structs.eTypKey;
import cz.upce.fei.skodaj.bdats.semestralprojectb.structs.eTypProhl;
import cz.upce.fei.skodaj.bdats.semestralprojectb.ui.ProgPamatky;

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
      this.load(ProgPamatky.FILE_SAVE);
    }
    
    /**
     * Checks, whether data has been loaded
     * @return TRUE if data has been successfully loaded, FALSE otherwise
     */
    public abstract boolean loaded();
    
    /**
     * Gets loaded data
     * @param manager Manager to which data will be loaded
     */
    public abstract void getData(IPamatky manager);
            
    /**
     * Gets type of key used to store data
     * @return Type of key used to store data
     */
    public abstract eTypKey getKey();
    
    /**
     * Gets order used to browse data
     * @return Type of order used to browse data
     */
    public abstract eTypProhl getOrder();
    
    /**
     * Checks, whether selected castle is set
     * @return TRUE if selected castle is set, FALSE otherwise
     */
    public abstract boolean hasSelected();
    
    /**
     * Gets selected castle
     * @return Selected castle or NULL if no castle is selected
     */
    public abstract Zamek getSelected();
    
    /**
     * Checks, whether searched castle is set
     * @return TRUE if selected castle is set, FALSE otherwise
     */
    public abstract boolean hasSearched();
    
    /**
     * Gets searched castle
     * @return Searched castle or NULL if no castle is searched
     */
    public abstract Zamek getSearched();
    
    /**
     * Checks, whether nearest castle is set
     * @return TRUE if nearest castle is set, FALSE otherwise
     */
    public abstract boolean hasNearest();
    
    /**
     * Gets nearest castle
     * @return Nearest castle or NULL if no nearest castle is set
     */
    public abstract Zamek getNearest();
}
