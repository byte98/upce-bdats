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
package cz.upce.fei.skodaj.bdats.semestralprojectb.data;

import java.nio.charset.Charset;

/**
 * Class representing castle
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Zamek
{
    /**
     * Counter of created castles
     */
    private static long counter = 0;
    
    /**
     * Location of castle
     */
    private final Location location;
    
    /**
     * Name of castle
     */
    private final String name;
    
    /**
     * Identifier of castle
     */
    private final long id;
    
    /**
     * Creates new castle
     * @param name Name of castle
     * @param location Location of castle
     */
    public Zamek(String name, Location location)
    {
        this.name = name;
        this.location = location;
        this.id = Zamek.counter;
        Zamek.counter++;
    }
    
    /**
     * Creates new castle
     * @param name Name of castle
     * @param location Location of castle
     * @param id Identifier of castle
     */
    public Zamek(String name, Location location, long id)
    {
        this.name = name;
        this.location = location;
        this.id = id;
        Zamek.counter = Math.max(id, Zamek.counter);
    }
    
    /**
     * Gets name of castle
     * @return Name of castle
     */
    public String getName()
    {
        return this.name;
    }
    
    /**
     * Gets location of castle
     * @return Location of castle
     */
    public Location getLocation()
    {
        return this.location;
    }
    
    /**
     * Gets identifier of castle
     * @return Identifier of castle
     */
    public long getId()
    {
        return this.id;
    }
    
    @Override
    public boolean equals(Object other)
    {
        boolean reti = false;
        if (other instanceof Zamek)
        {
            Zamek o = (Zamek) other;
            reti = this.getId() == o.getId();
        }
        return reti;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 47 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }
    
    @Override
    public String toString()
    {
        StringBuilder reti = new StringBuilder();
        byte[] idIcon = new byte[]{(byte)0xf0, (byte)0x9f, (byte)0x86, (byte)0x94};
        reti.append(new String(idIcon, Charset.forName("UTF-8")));
        reti.append(" ID: ");
        reti.append(this.getId());
        reti.append(", ");
        byte[] tagIcon = new byte[]{(byte)0xf0, (byte)0x9f, (byte)0x94, (byte)0xa4};
        reti.append(new String(tagIcon, Charset.forName("UTF-8")));
        reti.append(" NAZEV: ");
        reti.append(this.name);
        reti.append(", ");
        byte[] mapIcon = new byte[]{(byte)0xf0, (byte)0x9f, (byte)0x97, (byte)0xba};
        reti.append(new String(mapIcon, Charset.forName("UTF-8")));
        reti.append(String.format(" N %.6f°, E %.6f°", this.location.getLatitude(), this.location.getLongitude()));
        return reti.toString();
    }
}
