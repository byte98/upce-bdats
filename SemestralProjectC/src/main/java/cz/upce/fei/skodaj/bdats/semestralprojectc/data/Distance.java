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
package cz.upce.fei.skodaj.bdats.semestralprojectc.data;

import java.util.Objects;

/**
 * Class representing distance to some defined point
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Distance implements Comparable
{
    /**
     * Origin location to which every other location distance will be measured to
     */
    private static Location origin;
    
    /**
     * Location from which distance will be measured
     */
    private Location location;
    
    /**
     * Creates new distance from to defined point
     * @param l Location from which distance will be measured
     */
    public Distance(Location l)
    {
        this.location = l;
    }
    
    /**
     * Gets distance to defined point
     * @return Distance to some defined default point
     */
    public double getDistance()
    {
        return Distance.origin.distanceTo(this.location);
    }
    
    /**
     * Sets new origin for all distances
     * @param origin New origin for all distances
     */
    public static void setOrigin(Location origin)
    {
        Distance.origin = origin;
    }
    
    /**
     * Gets distance from location to some defined point
     * @param loc Location to which distance will be computed
     * @return Distance from location to some defined point
     */
    public static double getDistance(Location loc)
    {
        double reti = Double.NaN;
        if (Objects.nonNull(Distance.origin))
        {
            reti = loc.distanceTo(Distance.origin);
        }
        return reti;
    }

    @Override
    public int compareTo(Object o)
    {
        int reti = 0;
        if (o instanceof Distance)
        {
            Distance d = (Distance)o;
            Double d1 = this.getDistance();
            Double d2 = d.getDistance();
            reti = d1.compareTo(d2);
        }
        return reti;
    }
}
