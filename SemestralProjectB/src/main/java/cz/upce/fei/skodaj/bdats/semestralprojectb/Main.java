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
package cz.upce.fei.skodaj.bdats.semestralprojectb;

import cz.upce.fei.skodaj.bdats.semestralprojectb.data.Dataset;
import cz.upce.fei.skodaj.bdats.semestralprojectb.data.Generator;
import cz.upce.fei.skodaj.bdats.semestralprojectb.data.IPamatky;
import cz.upce.fei.skodaj.bdats.semestralprojectb.data.Pamatky;
import cz.upce.fei.skodaj.bdats.semestralprojectb.data.Zamek;
import cz.upce.fei.skodaj.bdats.semestralprojectb.files.IStateLoader;
import cz.upce.fei.skodaj.bdats.semestralprojectb.files.XMLLoader;
import cz.upce.fei.skodaj.bdats.semestralprojectb.structs.eTypKey;
import cz.upce.fei.skodaj.bdats.semestralprojectb.structs.eTypProhl;
import cz.upce.fei.skodaj.bdats.semestralprojectb.ui.ProgPamatky;
import java.util.Iterator;

/**
 * Main class of program
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Main
{
    /**
     * Entry point of program
     * @param args Arguments of program
     */
    public static void main(String[] args)
    {        
        ProgPamatky program = new ProgPamatky();
        Dataset ds = Dataset.getInstance();
        program.run(args);        
    }
}
