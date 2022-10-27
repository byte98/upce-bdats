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

import cz.upce.fei.skodaj.bdats.semestralprojectb.ui.FXMLMainWindowController;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class representing GPS location
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Location implements Comparable
{
    /**
     * Longitude coordinate
     */
    private double longitude;
    
    /**
     * Latitude coordinate
     */
    private double latitude;
    
    /**
     * Creates new GPS location
     * @param latitude Latitude coordinate
     * @param longitude Longitude coordinate
     */
    public Location(double latitude, double longitude)
    {
        this.longitude = longitude;
        this.latitude = latitude;
    }
    
    /**
     * Creates new GPS location
     * @param latDegrees Degrees part of latitude coordinate
     * @param latMinutes Minutes part of latitude coordinate
     * @param lonDegrees Degrees part of longitude coordinate
     * @param lonMinutes Minutes part of longitude coordinate
     */
    public Location (int latDegrees, double latMinutes, int lonDegrees, double lonMinutes)
    {
        this.longitude = this.toDecimal(lonDegrees, lonMinutes);
        this.latitude = this.toDecimal(latDegrees, latMinutes);
    }
    
    /**
     * Converts degrees minutes style location into decimal one
     * @param degrees Degrees of coordinate
     * @param minutes Minutes of coordinate
     * @return Decimal number containing location
     */
    private double toDecimal(int degrees, double minutes)
    {
        return (double) degrees + (minutes / (double)60);
    }
    
    /**
     * Gets longitude coordinate of GPS location
     * @return Longitude coordinate of GPS location
     */
    public double getLongitude()
    {
        return this.longitude;
    }
    
    
    /**
     * Gets latitude coordinate of GPS location
     * @return Latitude coordinate of GPS location
     */
    public double getLatitude()
    {
        return this.latitude;
    }

    @Override
    public int compareTo(Object o)
    {
        int reti = Integer.MIN_VALUE;
        if (o instanceof Location)
        {
            Location other = (Location) o;
            
        }
        return reti;
    }
    
    @Override
    public boolean equals(Object o)
    {
        boolean reti = false;
        if (o instanceof Location)
        {
            Location l = (Location) o;
            reti = (this.getLatitude() == l.getLatitude() && this.getLongitude() == l.getLongitude());
        }
        return reti;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 23 * hash + (int) (Double.doubleToLongBits(this.longitude) ^ (Double.doubleToLongBits(this.longitude) >>> 32));
        hash = 23 * hash + (int) (Double.doubleToLongBits(this.latitude) ^ (Double.doubleToLongBits(this.latitude) >>> 32));
        return hash;
    }
    
    @Override
    public String toString()
    {
        StringBuilder reti = new StringBuilder();
        reti.append(String.format("%02.08f", this.latitude).replace(",", "."));
        reti.append(";");
        reti.append(String.format("%02.08f", this.longitude).replace(",", "."));
        return reti.toString();
    }
    
    /**
     * Creates location from its textual representation
     * @param input Textual representation of location
     * @return Location created from its textual representation
     *         or NULL, if that can not be done
     */
    public static Location fromString(String input)
    {
        Location reti = null;
        String[] parts = input.split(";");
        if (parts.length == 2)
        {
            double lat = Double.NaN;
            double lon = Double.NaN;
            try
            {
                lat = Double.parseDouble(parts[0]);
                lon = Double.parseDouble(parts[1]);
                reti = new Location(lat, lon);
            }
            catch(NumberFormatException ex)
            {
                Logger.getLogger(Location.class.getName()).log(Level.WARNING, null, ex);
            }
        }
        return reti;
    }
    
    /**
     * Computes distance between this and other point
     * @param loc Location of other point
     * @return Distance between this and other point
     * @author {@link http://www.movable-type.co.uk/scripts/latlong.html}
     */
    public double distanceTo(Location loc)
    {
        double reti = Double.NaN;
        
        // Coordinates of location
        double lat1 = this.getLatitude();
        double lon1 = this.getLongitude();
        double lat2 = loc.getLatitude();
        double lon2 = loc.getLongitude();
        
        final double R = 6371e3; // Radius of earth (in meters)
        
        double f1 = lat1 * Math.PI / 180;
        double f2 = lat2 * Math.PI / 180;
        double dF = (lat2 - lat1) * Math.PI / 180;
        double dL = (lon2 - lon1) * Math.PI / 180;
        
        double a = Math.sin(dF / 2) * Math.sin(dF / 2) + 
                   Math.cos(f1) * Math.cos(f2) * 
                   Math.sin(dL / 2) * Math.sin(dL / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        reti = R * c;
        return reti;
    }
}
