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

import java.util.Iterator;
import java.util.Objects;

/**
 * Class which implements interface IAbstrDoubleList
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class AbstrDoubleList<T> implements IAbstrDoubleList<T>
{

    /**
     * Class representing node in list
     */
    private class Node
    {
        /**
         * Data stored in list
         */
        T data;
        
        /**
         * Reference to next element in list
         */
        Node next;
        
        /**
         * Reference to previous element in list
         */
        Node previous;
        
        /**
         * Creates new node
         * @param data Data which will be stored in node
         */
        Node(T data)
        {
            this.data = data;
        }
        
        /// <editor-fold defaultstate="collapsed" desc="Getters & setters">
        public T getData()
        {
            return this.data;
        }
        
        public Node getNext()
        {
            return this.next;
        }

        public void setNext(Node next)
        {
            this.next = next;
        }

        public Node getPrevious()
        {
            return this.previous;
        }

        public void setPrevious(Node previous)
        {
            this.previous = previous;
        }
        /// </editor-fold>
    }
    
    /**
     * Number of elements in the list
     */
    private int size;
    
    /**
     * Reference to the first element of the list
     */
    private Node head;
    
    /**
     * Reference to the last element of the list
     */
    private Node tail;
    
    /**
     * Reference to the actual element of the list
     */
    private Node actual;
    
    /**
     * Creates empty double list
     */
    public AbstrDoubleList()
    {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    
    /**
     * Inserts first node in the list
     * @param data Data which will be stored in node
     */
    private void insertFirstNode(T data)
    {
        Node node = new Node(data);
        this.head = node;
        this.tail = node;
        node.setNext(node);
        node.setPrevious(node);
        this.size++;
    }
    
    @Override
    public void zrus()
    {
        this.actual = null;
        this.head = null;
        this.tail = null;
        this.size = 0;
        System.gc();
    }

    @Override
    public boolean jePrazdny()
    {
        return this.size == 0;
    }

    @Override
    public void vlozPrvni(T data)
    {
        if (this.jePrazdny()) // Node will be first in the list
        {
            this.insertFirstNode(data);
        }
        else // There are already some nodes in the list
        {
            Node node = new Node(data);
            node.setNext(this.head);
            node.setPrevious(this.tail);
            this.head.setPrevious(node);     
            this.tail.setNext(node);
            this.head = node;
            this.size++;
        }
        this.defaultActual();
    }

    @Override
    public void vlozPosledni(T data)
    {
        if (this.jePrazdny()) // Node will be first in the list
        {
            this.insertFirstNode(data);
        }
        else // There are already some nodes in the list
        {
            Node node = new Node(data);
            node.setNext(this.head);
            node.setPrevious(this.tail);
            this.tail.setNext(node);
            this.head.setPrevious(node);
            this.tail = node;
            this.size++;
        }
        this.defaultActual();
    }

    @Override
    public void vlozNaslednika(T data)
    {
        if (Objects.nonNull(this.actual)) // Actual element is set
        {
            Node node = new Node(data);
            node.setNext(this.actual.getNext());
            this.actual.getNext().setPrevious(node);
            node.setPrevious(this.actual);
            this.actual.setNext(node);
            this.size++;
            if (this.actual.equals(this.tail))
            {
                this.tail = node;
            }
        }
    }

    @Override
    public void vlozPredchudce(T data)
    {
        if (Objects.nonNull(this.actual))
        {
            Node node = new Node(data);
            node.setNext(this.actual);
            node.setPrevious(this.actual.getPrevious());            
            this.actual.getPrevious().setNext(node);
            this.actual.setPrevious(node);
            this.size++;
            if (this.actual.equals(this.head))
            {
                this.head = node;
            }
        }
    }

    @Override
    public T zpristupniAktualni()
    {
        T reti = null;
        if (this.jePrazdny() == false && Objects.nonNull(this.actual))
        {
            reti = this.actual.getData();
        }
        return reti;
    }

    @Override
    public T zpristupniPrvni()
    {
        T reti = null;
        if (this.jePrazdny() == false)
        {
            this.actual = this.head;
            reti = this.actual.getData();
        }        
        return reti;
    }

    @Override
    public T zpristupniPosledni()
    {
        T reti = null;
        if (this.jePrazdny() == false)
        {
            this.actual = this.tail;
            reti = this.actual.getData();
        }
        return reti;
    }

    @Override
    public T zpristupniNaslednika()
    {
        T reti = null;
        if (Objects.nonNull(this.actual) && Objects.nonNull(this.actual.getNext()))
        {
            this.actual = this.actual.getNext();
            reti = this.actual.getData();            
        }
        return reti;
    }

    @Override
    public T zpristupniPredchudce()
    {
        T reti = null;
        if (Objects.nonNull(this.actual) && Objects.nonNull(this.actual.getPrevious()))
        {
            this.actual = this.actual.getPrevious();
            reti = this.actual.getData();            
        }
        return reti;
    }

    /**
     * Removes node from the list
     * @param node Node which will be removed from the list
     */
    private void removeNode(Node node)
    {
        if (this.jePrazdny() == false)
        {
            // Resolve bonds to other elements in the list
            if (Objects.nonNull(node.getPrevious()) && Objects.nonNull(node.getNext()))
            { // Node has both predecessor and successor
                node.getPrevious().setNext(node.getNext());
                node.getNext().setPrevious(node.getPrevious());
            }
            else if (Objects.nonNull(node.getPrevious()) && Objects.isNull(node.getNext()))
            { // Node has only predecessor
                node.getPrevious().setNext(null);
            }
            else if (Objects.isNull(node.getPrevious()) && Objects.nonNull(node.getNext()))
            { // Node has only successor
                node.getNext().setPrevious(null);
            }

            // Check both ends of list
            if (this.head.equals(node))
            {
                this.head = node.getNext();
            }
            if (this.tail.equals(node))
            {
                this.tail = node.getPrevious();
            }

            // Check, if node is actual one
            if (Objects.nonNull(this.actual) && this.actual.equals(node))
            {
                this.actual = this.head;
            }

            // Finish removing
            node = null;
            this.size--;
            System.gc(); 
        }
    }
    
    @Override
    public T odeberAktualni()
    {
        T reti = null;
        if (Objects.nonNull(this.actual))
        {
            reti = this.actual.getData();
            this.removeNode(this.actual);
        }
        this.defaultActual();
        return reti;
    }

    @Override
    public T odeberPrvni()
    {
        T reti = null;
        if (this.jePrazdny() == false)
        {
            reti = this.head.getData();
            this.removeNode(this.head);
        }
        this.defaultActual();
        return reti;
    }

    @Override
    public T odeberPosledni()
    {
        T reti = null;
        if (this.jePrazdny() == false)
        {
            reti = this.tail.getData();
            this.removeNode(this.tail);
        }
        this.defaultActual();
        return reti;
    }

    @Override
    public T odeberNaslednika()
    {
        T reti = null;
        if (Objects.nonNull(this.actual) && Objects.nonNull(this.actual.getNext()))
        {
            reti = this.actual.getNext().getData();
            this.removeNode(this.actual.getNext());
        }
        this.defaultActual();
        return reti;
    }

    @Override
    public T odeberPredchudce()
    {
        T reti = null;
        if (Objects.nonNull(this.actual) && Objects.nonNull(this.actual.getPrevious()))
        {
            reti = this.actual.getPrevious().getData();
            this.removeNode(this.actual.getPrevious());
        }
        this.defaultActual();
        return reti;
    }

    @Override
    public Iterator<T> iterator()
    {        
        return new Iterator<T>()
        {
            /**
             * Enumeration of all possible iterator states
             */
            private enum IteratorState
            {
                /**
                 * Iterator started
                 */
                STARTED,
                
                /**
                 * Iterator is running
                 */
                RUNNING,
                
                /**
                 * Iterator finished its job
                 */
                FINISHED
            };
            
            /**
             * Actually processed element by iterator
             */
            Node element = head;
            
            /**
             * Actual state of iterator
             */
            private IteratorState state = IteratorState.STARTED;
            
            @Override
            public boolean hasNext()
            {
                boolean reti = true;
                if (jePrazdny())
                {
                    reti = false;
                }
                else if (this.state == IteratorState.FINISHED)
                {
                    reti = false;
                }
                else if (size == 1 || (this.state == IteratorState.RUNNING && this.element.equals(tail)))
                {
                    this.state = IteratorState.FINISHED;
                }
                
                return reti;
            }
            
            @Override
            public T next()
            {
                T reti = null;
                if (Objects.nonNull(this.element))
                {
                    reti = this.element.getData();
                    if (this.hasNext())
                    {
                        this.element = element.getNext();
                    }
                    if (this.state == IteratorState.STARTED)
                    {
                        this.state = IteratorState.RUNNING;
                    }
                }                
                return reti;
            }
        };
    }
    
    /**
     * Sets default pointer to actual element
     */
    private void defaultActual()
    {
        if (this.jePrazdny())
        {
            this.actual = null;
        }
        else if (Objects.isNull(this.actual))
        {
            this.actual = this.head;
        }
    }
    
}
