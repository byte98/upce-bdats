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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Generator of castles
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Generator
{
    /**
     * Array with all hard consonants
     */
    private static final String[] HARD_CONSONANTS = new String[]{
        "h", "ch", "k", "r", "d", "t", "n"
    };
    
    /**
     * Array with all soft consonants
     */
    private static final String[] SOFT_CONSONANTS = new String[]{
        "c", "j"
    };
    
    /**
     * Array with all double consonants
     */
    private static final String[] DOUBLE_CONSONANTS = new String[]{
        "b", "f", "l", "m", "p", "s", "v", "z"
    };
    
    /**
     * Array with all vowels
     */
    private static final String[] VOWELS = new String[]{
        "a", "e", "i", "o", "u", "y"
    };
    
    /**
     * The most north value allowed for location
     */
    private static final double NORTH = 51.05570479;
    
    /**
     * The most south value allowed for location
     */
    private static final double SOUTH = 48.55180780;
    
    /**
     * The most west value allowed for location
     */
    private static final double WEST = 12.09081161;
    
    /**
     * The most east value allowed for location
     */
    private static final double EAST = 18.85925389;
    
    /**
     * Minimal length for name of castle
     */
    private static final int NAME_MIN = 3;
    
    /**
     * Maximal length for name of castle
     */
    private static final int NAME_MAX = 16;
    
    /**
     * Reference to instance of generator
     */
    private static Generator instance;
    
    /**
     * List of all generated names
     */
    private List<String> generatedNames;
    
    /**
     * List of all generated locations
     */
    private List<Location> generatedLocations;
    
    /**
     * Creates new generator
     */
    private Generator()
    {
        this.generatedNames = new ArrayList<>();
        this.generatedLocations = new ArrayList<>();
    }
    
    /**
     * Gets instance of generator
     * @return Instance of generator
     */
    public static Generator getInstance()
    {
        if (Objects.isNull(Generator.instance))
        {
            Generator.instance = new Generator();
        }
        return Generator.instance;
    }
    
    /**
     * Generates random castle
     * @return New randomly generated castle
     */
    public Zamek generateRandom()
    {
        String name = this.randomName(ThreadLocalRandom.current().nextInt(Generator.NAME_MIN, Generator.NAME_MAX));
        this.generatedNames.add(name);
        Location loc = this.randomLocation();
        this.generatedLocations.add(loc);
        return new Zamek(name, loc);
    }
    
    /**
     * Generates array with random castles
     * @param count Desired length of array
     * @return Array with random castles
     */
    public Zamek[] generateRandom(int count)
    {
        Zamek[] reti = new Zamek[count];
        for (int i = 0; i < reti.length; i++)
        {
            reti[i] = this.generateRandom();
        }
        return reti;
    }
    
    /**
     * Generates known castle
     * @return 
     */
    public Zamek generateKnown()
    {
        String name = this.knownName();
        this.generatedNames.add(name);
        Location loc = Dataset.getInstance().getByName(name);
        if (Objects.isNull(loc))
        {
            loc = this.randomLocation();
        }
        this.generatedLocations.add(loc);
        return new Zamek(name, loc);
    }
    
    /**
     * Generates array with known castles
     * @param count Desired length of array
     * @return Array with known castles
     */
    public Zamek[] generateKnown(int count)
    {
        Zamek[] reti = new Zamek[count];
        for (int i = 0; i < reti.length; i++)
        {
            reti[i] = this.generateKnown();
        }
        return reti;
    }
    
    /**
     * Generates known name
     * @return Known name of castle
     */
    private String knownName()
    {
        String reti = "";
        String[] candidates = new String[Dataset.getInstance().count()];
        int idx = 0;
        for (String name: Dataset.getInstance().getNames())
        {
            candidates[idx] = name;
            idx++;
        }
        candidates = this.shuffleArray(candidates);
        for (String name: candidates)
        {
            if(this.generatedNames.contains(name) == false)
            {
                reti = name;
                break;
            }
        }
        if (reti.equals(""))
        {
            reti = this.randomName(ThreadLocalRandom.current().nextInt(Generator.NAME_MIN, Generator.NAME_MAX));
        }
        return reti;
    }
    
    /**
     * Randomly shuffles item in array
     * @param array Array which items will be randomly shuffled
     * @return Array with randomly shuffled items
     */
    private String[] shuffleArray(String[] array)
    {
        String[] reti = new String[array.length];
        for (int i = 0; i < reti.length; i++)
        {
            reti[i] = new String(array[i]);
        }
        Random rnd = ThreadLocalRandom.current();
        for (int i = reti.length - 1; i > 0; i--)
        {
          int index = rnd.nextInt(i + 1);
          String a = reti[index];
          reti[index] = reti[i];
          reti[i] = a;
        }
        return reti;
    }
    
    /**
     * Generates random location
     * @return Random location
     */
    private Location randomLocation()
    {
        Location reti = new Location(
                this.randomLatitude(),
                this.randomLongitude()
        );
        if (this.generatedLocations.contains(reti))
        {
            reti = this.randomLocation();
        }
        return reti;
    }
    
    /**
     * Generates random latitude
     * @return Random latitude
     */
    private double randomLatitude()
    {
        double reti = Double.NaN;
        reti = ThreadLocalRandom.current().nextDouble(Generator.SOUTH, Generator.NORTH);
        return reti;
    }
    
    /**
     * Generates random longitude
     * @return Random longitude
     */
    private double randomLongitude()
    {
        double reti = Double.NaN;
        reti = ThreadLocalRandom.current().nextDouble(Generator.WEST, Generator.EAST);
        return reti;
    }
    
    /**
     * Generates random name for castle
     * @param length Length of name
     * @return Random name for castle
     */
    private String randomName(int length)
    {
        StringBuilder reti = new StringBuilder();
        if (length > 1)
        {
            String prev = this.randomChar(null);
            if (prev.equals("ch"))
            {
                reti.append("Ch");
            }
            else
            {
                reti.append(prev.toUpperCase());
            }            
            for (int i = 0; i < length - 1; i++)
            {
                prev = this.randomChar(prev);
                reti.append(prev);
            }
        }
        if (this.generatedNames.contains(reti.toString()))
        {
            reti = new StringBuilder(this.randomName(length));
        }
        return reti.toString();
    }
    
    /**
     * Generates random character
     * @param previous Previously generated character
     * @return Random character
     */
    private String randomChar(String previous)
    {
        String reti = "";
        if (Objects.isNull(previous))
        {
           reti = this.randomLetter();
        }
        else
        {
            int RLprop = ThreadLocalRandom.current().nextInt(0, 5);
            if (RLprop == 3 && (previous.equals("r") || previous.equals("l")))
            {
                reti = this.randomConsonant();
                while(reti.equals(previous))
                {
                    reti = this.randomConsonant();
                }
            }
            else if (this.isVowel(previous))
            {
                reti = this.randomConsonant();
            }
            else if (this.isHardConsonant(previous))
            {
                reti = this.randomVowel(true);
                while(reti.equals("i"))
                {
                    reti = this.randomVowel(true);
                }
            }
            else if (this.isSoftConsonant(previous))
            {
                reti = this.randomVowel(false);
            }
            else
            {
                reti = this.randomVowel(true);
            }
        }
        return reti;
    }
    
    /**
     * Generates random letter
     * @return Randomly generated letter
     */
    private String randomLetter()
    {
        String reti = "";
        int group = ThreadLocalRandom.current().nextInt(0, 2);
        if (group == 0)
        {
            reti = this.randomConsonant();
        }
        else
        {
            reti = this.randomVowel(false);
        }
        return reti;
    }
    
    /**
     * Generates random vowel
     * @param yres Flag, whether Y is also valid result
     * @return Random vowel
     */
    private String randomVowel(boolean yres)
    {
        String reti = "";
        reti = Generator.VOWELS[ThreadLocalRandom.current().nextInt(0, Generator.VOWELS.length)];
        if (yres == false)
        {
            while (reti.equals("y"))
            {
                reti = Generator.VOWELS[ThreadLocalRandom.current().nextInt(0, Generator.VOWELS.length)];
            }
        }                
        return reti;
    }
    
    /**
     * Generates random consonant
     * @return Random consonant
     */
    private String randomConsonant()
    {
        String reti = "";
        int group = ThreadLocalRandom.current().nextInt(0, 4);
        if (group == 0)
        {
            reti = Generator.HARD_CONSONANTS[ThreadLocalRandom.current().nextInt(0, Generator.HARD_CONSONANTS.length)];
        }
        else if (group == 1)
        {
            reti = Generator.SOFT_CONSONANTS[ThreadLocalRandom.current().nextInt(0, Generator.SOFT_CONSONANTS.length)];
        }
        else
        {
            reti = Generator.DOUBLE_CONSONANTS[ThreadLocalRandom.current().nextInt(0, Generator.DOUBLE_CONSONANTS.length)];
        }
        return reti;
    }
    
    /**
     * Checks, whether letter is vowel
     * @param letter Letter which will be checked
     * @return TRUE if letter is vowel, FALSE otherwise
     */
    private boolean isVowel(String letter)
    {
        return this.arrayContains(Generator.VOWELS, letter);
    }
    
    /**
     * Checks, whether letter is consonant
     * @param letter Letter which will be checked
     * @return TRUE if letter is consonant, FALSE otherwise
     */
    private boolean isConsonant(String letter)
    {
        return (this.isHardConsonant(letter) || 
               this.isSoftConsonant(letter) ||
               this.arrayContains(Generator.DOUBLE_CONSONANTS, letter));
    }
    
    
    /**
     * Checks, whether letter is soft consonant
     * @param letter Letter which will be checked
     * @return TRUE if letter is soft consonant, FALSE otherwise
     */
    private boolean isSoftConsonant(String letter)
    {
        return this.arrayContains(Generator.SOFT_CONSONANTS, letter);
    }
        
    /**
     * Checks, whether letter is hard consonant
     * @param letter Letter which will be checked
     * @return TRUE if letter is hard consonant, FALSE otherwise
     */
    private boolean isHardConsonant(String letter)
    {
        return this.arrayContains(Generator.HARD_CONSONANTS, letter);
    }
    
    /**
     * Checks, whether array contains item
     * @param array Array which will be searched
     * @param item Item which will be checked
     * @return TRUE if array contains item, FALSE otherwise
     */
    private boolean arrayContains(String[] array, String item)
    {
        boolean reti = false;
        for (String s: array)
        {
            if (s.equals(item))
            {
                reti = true;
                break;
            }
                    
        }
        return reti;
    }
}
