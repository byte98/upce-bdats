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
package cz.upce.fei.skodaj.bdats.semestralprojectb.structs;

import java.util.Iterator;

/**
 * Class implementing first in - first out type of structure
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class AbstrFifo<T> implements IAbstrFifo<T>
{
    /**
     * List containing data stored in structure
     */
    private IAbstrDoubleList<T> list;
    
    /**
     * Creates new first in - first out type of structure
     */
    public AbstrFifo()
    {
        this.list = new AbstrDoubleList();
    }

    @Override
    public void zrus()
    {
        this.list.zrus();
    }

    @Override
    public boolean jePrazdny()
    {
        return this.list.jePrazdny();
    }

    @Override
    public void vloz(T data)
    {
        this.list.vlozPosledni(data);
    }

    @Override
    public T odeber()
    {
        return this.list.odeberPrvni();
    }

    @Override
    public Iterator<T> iterator()
    {
        return this.list.iterator();
    }
}
