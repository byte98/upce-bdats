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
 * Interface defining classes which can save actual state of program
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public interface IStateSaver
{
    
    /**
     * Sets manager of all castles
     * @param manager Manager of castles
     */
    public abstract void setManager(IPamatky manager);
    
    /**
     * Sets type of key used for storing data
     * @param type 
     */
    public abstract void setKey(eTypKey type);
    
    /**
     * Sets type of order which will be used for browsing structure
     * @param type Order used for browsing structure
     */
    public abstract void setOrder(eTypProhl type);
    
    /**
     * Sets actually selected castle
     * @param castle Actually selected castle
     */
    public abstract void setSelected(Zamek castle);
    
    /**
     * Unsets selected castle
     */
    public abstract void unsetSelected();
    
    /**
     * Sets nearest castle
     * @param nearest Nearest castle
     */
    public abstract void setNearest(Zamek nearest);
    
    /**
     * Unsets nearest castle
     */
    public abstract void unsetNearest();
    
    /**
     * Sets searched castle
     * @param searched Searched castle
     */
    public abstract void setSearched(Zamek searched);
    
    /**
     * Unsets searched castle
     */
    public abstract void unsetSearched();
    
    /**
     * Saves actual state of program
     */
    public default void save()
    {
        this.save(ProgPamatky.FILE_SAVE);
    }
    
    /**
     * Saves actual state of program
     * @param file File to which state will be saved into
     */
    public abstract void save(String file);
    
}
