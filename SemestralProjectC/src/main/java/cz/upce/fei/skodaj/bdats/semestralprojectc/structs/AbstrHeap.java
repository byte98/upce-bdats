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
package cz.upce.fei.skodaj.bdats.semestralprojectc.structs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Class representing implementation of heap data structure
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class AbstrHeap<K extends Comparable, V> implements IAbstrHeap<K, V>
{
    /**
     * Computes index of left child
     * @param idx Index of parent
     * @return Index of left child
     */
    private static final int left(int idx) {return (idx << 1) + 1;}
    
    /**
     * Computes index of right child
     * @param idx Index of parent
     * @return Index of right child
     */
    private static final int right(int idx) {return (idx << 1) + 2;}
    
    /**
     * Gets index of parent node
     * @param idx Index of child
     * @return Index of parent
     */
    private static final int parent(int idx) {return (idx - 1) >> 1;}
    
    /**
     * Coefficient used to store data in heap in ascending way
     */
    public static final int ASC = -1;
    
    /**
     * Coefficient used to store data in heap in descending way
     */
    public static final int DESC = 1;
    
    /**
     * Indexes of data defining its correct order in heap
     */
    private int[] indexes;
    
    /**
     * List with data stored in heap
     */
    private final List<V> data;
    
    /**
     * Keys defining data position
     */
    private final List<K> keys;
    
    /**
     * Coefficient defining order of items stored in heap
     */
    private final int orderCoeff;
    
    /**
     * Creates new heap
     * @param order Coefficient defining order of items stored in heap.
     *              It should be {@link AbstrHeap#ASC} for ascending order 
     *              or {@link AbstrHeap#DESC} for descending one.
     */
    public AbstrHeap(int order)
    {
        this.data = new ArrayList<>();
        this.keys = new ArrayList<>();
        this.indexes = new int[0];
        this.orderCoeff = order;
    }
    
    /**
     * Compare two keys
     * @param k1 First key which will be compared
     * @param k2 Second key which will be compared
     * @return Result of comparison
     */
    private int compare(K k1, K k2)
    {
        return k1.compareTo(k2) * this.orderCoeff;
    }
    
    /**
     * Compare two items defined by its index
     * @param i1 Index of first item which will be compared
     * @param i2 Index of second item which will be compared
     * @return Result of comparison
     */
    private int compare(int i1, int i2)
    {
        return this.compare(this.keys.get(this.indexes[i1]),
                this.keys.get(this.indexes[i2]));
    }
    
    /**
     * Propagates item in heap
     * @param idx Index of item which will be propagated
     */
    private void propagate(int idx)
    {
        if (idx > 0)
        {
            int parent = AbstrHeap.parent(idx);
            if (this.compare(idx, parent) > 0)
            {
                this.swap(idx, parent);
                this.propagate(parent);
            }
        }
    }
    
    /**
     * Swaps two items in data array
     * @param i1 Index of first element to be swapped
     * @param i2 Index of second element to be swapped
     */
    private void swap(int i1, int i2)
    {
        int temp = this.indexes[i1];
        this.indexes[i1] = this.indexes[i2];
        this.indexes[i2] = temp;
    }
    
    /**
     * Makes heap from subtree defined by index of its root
     * @param root Index of root element in subtree which will be transformed
     *             into heap
     */
    private void heapify(int root)
    {
        int higherVal = root;
        int left = AbstrHeap.left(root);
        int right = AbstrHeap.right(root);
        if (left < this.data.size() && this.compare(left, root) > 0)
        {
            higherVal = left;
        }
        if (right < this.data.size() && this.compare(right, higherVal) > 0)
        {
            higherVal = right;
        }
        if (higherVal != root)
        {
            this.swap(higherVal, root);
            this.heapify(higherVal);
        }
    }
    
    /**
     * Decrement indexes
     * @param i Minimal index (excluded) which will be incremented
     */
    private void decrementIndex(int i)
    {
        for (int c = 0; c < this.data.size(); c++)
        {
            if (this.indexes[c] > i)
            {
                this.indexes[c] -= 1;
            }
        }
    }

    @Override
    public void prebuduj()
    {
        for (int i = this.data.size() - 1; i >= 0; i--)
        {
            this.propagate(i);
        }
    }

    @Override
    public void zrus()
    {
        this.data.clear();
        this.keys.clear();
        this.indexes = new int[0];
        System.gc();
    }

    @Override
    public boolean jePrazdny() 
    {
        return (this.data.size() * this.keys.size() * this.indexes.length) == 0;
    }
    
    @Override
    public void vloz(V data, K key)
    {
        if (this.data.size() + 1 >= this.indexes.length)
        {
            int[] newIndexes = new int[
                    Math.max(this.data.size() + 1, 2 * this.indexes.length)
            ];
            System.arraycopy(this.indexes, 0,
                    newIndexes, 0, this.indexes.length);
            this.indexes = newIndexes;
        }
        this.data.add(data);
        this.keys.add(key);
        this.indexes[this.data.size() - 1] = this.data.size() - 1;
        this.propagate(this.data.size() - 1);
    }

    @Override
    public V odeberMax() 
    {
        V reti = null;
        if (this.jePrazdny() == false)
        {
            int rem = this.indexes[0];
            reti = this.data.get(rem);
            this.decrementIndex(rem);
            this.data.remove(rem);
            this.keys.remove(rem);
            this.swap(0, this.data.size());
            this.heapify(0);
        }
        return reti;
    }

    @Override
    public V zpristupniMax()
    {
        V reti = null;
        if (this.jePrazdny() == false)
        {
            reti = this.data.get(this.indexes[0]);
        }        
        return reti;
    }
    
    

    @Override
    public Iterator<V> iterator(eTypProhl order)
    {
        return new Iterator<V>()
        {
            /**
             * Structure holding indexes needed to visit
             */
            private IAbstrLifoFifo<Integer> struct;
            
            { // Initialization of iterator
                this.struct = (
                        order == eTypProhl.DO_SIRKY
                        ? new AbstrFifo()
                        : new AbstrLifo()
                );
                if (jePrazdny() == false)
                {
                    this.struct.vloz(0);
                }                
            }
            
            @Override
            public boolean hasNext()
            {
                return struct.jePrazdny() == false;
            }

            @Override
            public V next()
            {
                V reti = null;
                if (this.hasNext())
                {
                    int idx = this.struct.odeber();
                    if (idx < data.size() && Objects.nonNull(
                            data.get(indexes[idx])))
                    {                    
                        reti = data.get(indexes[idx]);
                        if (AbstrHeap.left(idx) < data.size() &&
                                Objects.nonNull(
                                        data.get(indexes[AbstrHeap.left(idx)])
                                )
                           )
                        {
                            this.struct.vloz(AbstrHeap.left(idx));
                        }
                        if (AbstrHeap.right(idx) < data.size() &&
                                Objects.nonNull(
                                        data.get(indexes[AbstrHeap.right(idx)])
                                )
                           )
                        {
                            this.struct.vloz(AbstrHeap.right(idx));
                        }
                    }
                }                
                return reti;
            }
        };
    }
}
