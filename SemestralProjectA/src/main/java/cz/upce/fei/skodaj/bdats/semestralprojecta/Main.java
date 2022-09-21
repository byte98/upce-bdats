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
package cz.upce.fei.skodaj.bdats.semestralprojecta;

import cz.upce.fei.skodaj.bdats.semestralprojecta.structs.AbstrDoubleList;
import cz.upce.fei.skodaj.bdats.semestralprojecta.structs.IAbstrDoubleList;

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
        IAbstrDoubleList<Integer> list = new AbstrDoubleList<>();
        for (int i = 0; i < 5; i++)
        {
            list.vlozPrvni(i);
        }
        Main.printList(list);
    }
    
    /**
     * Prints content of the list to standard output
     * @param list List which content will be printed
     */
    private static void printList(IAbstrDoubleList list)
    {
        /*
        for(Object e: list)
        {
            System.out.println(String.format("%s ", e.toString()));
        }
        */
    }
}
