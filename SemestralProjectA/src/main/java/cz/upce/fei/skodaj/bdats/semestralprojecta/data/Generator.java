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

import java.util.concurrent.ThreadLocalRandom;

/**
 * Class which can generate multiple instances of data
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public abstract class Generator
{
    /**
     * Minimal time used for generating processes
     */
    private static final int TIME_MIN = 1;
    
    /**
     * Maximal time used for generating processes
     */
    private static final int TIME_MAX = 128;
    
    /**
     * Minimal number of people used for generating processes
     */
    private static final int PEOPLE_MIN = 1;
    
    /**
     * Maximal number of people used for generating processes
     */
    private static final int PEOPLE_MAX = 64;
    
    /**
     * Generates manual process
     * @return Newly created manual process
     */
    public static ProcesManualni manual()
    {
        return new ProcesManualni(
                ThreadLocalRandom.current().nextInt(Generator.TIME_MIN, Generator.TIME_MAX),
                ThreadLocalRandom.current().nextInt(Generator.PEOPLE_MIN, Generator.PEOPLE_MAX)
        );
    }
    
    /**
     * Generates robotic process
     * @return Newly created robotic process
     */
    public static ProcesRoboticky robotic()
    {
        return new ProcesRoboticky(
                ThreadLocalRandom.current().nextInt(Generator.TIME_MIN, Generator.TIME_MAX)
        );
    }
    
    /**
     * Generates random process
     * @return Newly created process
     */
    public static Proces random()
    {
        Proces reti = null;
        if (ThreadLocalRandom.current().nextBoolean())
        {
            reti = Generator.robotic();
        }
        else
        {
            reti = Generator.manual();
        }
        return reti;
    }
    
    /**
     * Adds random process to structure
     * @param manager Manager of structure
     * @param position Position of new process
     */
    public static void addToStructure(IVyrobniProces manager, enumPozice position)
    {
        manager.vlozProces(Generator.random(), position);
    }
    
    /**
     * Adds random process to structure
     * @param manager Manager of structure
     * @param position Position of new process
     * @param count Number of newly created processes
     */
    public static void addToStructure(IVyrobniProces manager, enumPozice position, int count)
    {
        for (int i = 0; i < count; i++)
        {
            Generator.addToStructure(manager, position);
        }
    }
}
