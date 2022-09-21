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
package cz.upce.fei.skodaj.bdats.semestralprojecta.structs;

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
    private static class Node<T>
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
        
        public Node<T> getNext()
        {
            return this.next;
        }

        public void setNext(Node<T> next)
        {
            this.next = next;
        }

        public Node<T> getPrevious()
        {
            return this.previous;
        }

        public void setPrevious(Node<T> previous)
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
    AbstrDoubleList()
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
        Node<T> node = new Node<>(data);
        this.head = node;
        this.tail = node;
        node.setNext(node);
        node.setPrevious(node);
        this.size++;
    }
    
    @Override
    public void zrus()
    {
        
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
            Node<T> node = new Node<>(data);
            node.setNext(this.head);
            node.setPrevious(this.tail);
            this.head.setPrevious(node);
            this.tail.setNext(this.head);
            this.head = node;
            this.size++;
        }
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
            Node<T> node = new Node<>(data);
            node.setNext(this.head);
            node.setPrevious(this.tail);
            this.tail.setNext(node);
            this.head.setPrevious(node);
            this.tail = node;
            this.size++;
        }
    }

    @Override
    public void vlozNaslednika(T data)
    {
        if (Objects.nonNull(this.actual)) // Actual element is set
        {
            Node<T> node = new Node<>(data);
            node.setNext(this.actual.getNext());
            this.actual.getNext().setPrevious(node);
            node.setPrevious(this.actual);
            this.actual.setNext(node);
            this.size++;
        }
    }

    @Override
    public void vlozPredchudce(T data)
    {
        if (Objects.nonNull(this.actual))
        {
            Node<T> node = new Node<>(data);
            node.setPrevious(this.actual.getPrevious());
            this.actual.getPrevious().setNext(node);
            node.setNext(this.actual);
            this.actual.setPrevious(node);
            this.size++;
        }
    }

    @Override
    public T zpristupniAktualni()
    {
        T reti = null;
        if (this.jePrazdny() == false && Objects.nonNull(this.actual))
        {
            reti = (T) this.actual.getData();
        }
        return reti;
    }

    @Override
    public T zpristupniPrvni()
    {
        T reti = null;
        if (this.jePrazdny() == false)
        {
            reti = (T) this.head.getData();
        }
        return reti;
    }

    @Override
    public T zpristupniPosledni()
    {
        T reti = null;
        if (this.jePrazdny() == false)
        {
            reti = (T) this.tail.getData();
        }
        return reti;
    }

    @Override
    public T zpristupniNaslednika() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public T zpristupniPredchudce() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public T odeberAktualni() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public T odeberPrvni() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public T odeberPosledni() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public T odeberNaslednika() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public T odeberPredchudce() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
