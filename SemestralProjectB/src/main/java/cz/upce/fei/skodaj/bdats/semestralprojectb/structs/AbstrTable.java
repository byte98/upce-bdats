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
import java.util.Objects;

/**
 * Class representing table for storing values defined by keys
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class AbstrTable<K extends Comparable<K>, V> implements IAbstrTable<K, V>
{
    /**
     * Class representing node in binary search tree
     */
    private class Node<K extends Comparable<K>, V>
    {
        /**
         * Key defining position in tree
         */
        private K key;
        
        /**
         * Value of node
         */
        private V value;
        
        /**
         * Left children of node in tree
         */
        private Node<K, V> left;
        
        /**
         * Right children of node in tree
         */
        private Node<K, V> right;
        
        /**
         * Parent node in tree
         */
        private Node<K, V> parent;
        
        /**
         * Creates new node used in binary search tree
         * @param key Key defining position in tree
         * @param value Value of node
         */
        public Node(K key, V value)
        {
            this.key = key;
            this.value = value;
        }

        //<editor-fold defaultstate="collapsed" desc="Getters & setters">
        public K getKey()
        {
            return key;
        }
        public void setKey(K key)
        {
            this.key = key;
        }
        public V getValue()
        {
            return value;
        }
        public void setValue(V value)
        {
            this.value = value;
        }
        public Node<K, V> getLeft()
        {
            return left;
        }
        public void setLeft(Node<K, V> left)
        {
            this.left = left;
        }
        public Node<K, V> getRight()
        {
            return right;
        }
        public void setRight(Node<K, V> right)
        {
            this.right = right;
        }
        public Node<K, V> getParent()
        {
            return parent;
        }
        public void setParent(Node<K, V> parent)
        {
            this.parent = parent;
        }
        //</editor-fold>
    }
    
    /**
     * Root of binary search tree
     */
    private Node<K, V> root;
    
    /**
     * Counter of nodes in tree
     */
    private int counter;
    
    /**
     * Creates new table
     */
    public AbstrTable()
    {
        this.counter = 0;
    }
    
    @Override
    public void zrus()
    {
        this.root = null;
        System.gc();
    }

    @Override
    public boolean jePrazdny()
    {
        return this.counter == 0 || Objects.isNull(this.root);
    }
    
    /**
     * Finds node in tree defined by its key
     * @param key Key of node
     * @param node Actually checked node
     * @return Value of key
     */
    private V seek(K key, Node<K, V> node)
    {
        V reti = null;
        if (Objects.nonNull(node))
        {
            if (key.compareTo(node.getKey()) > 0)
            {
                reti = this.seek(key, node.getRight());
            }
            else if (key.compareTo(node.getKey()) < 0)
            {
                reti = this.seek(key, node.getLeft());
            }
            else
            {
                reti = node.getValue();
            }
        }
        return reti;
    }

    @Override
    public V najdi(K key)
    {
        V reti = null;
        reti = this.seek(key, this.root);
        return reti;
    }

    /**
     * Inserts value in tree
     * @param key Key defining value
     * @param value Value which will be inserted in tree
     * @param node Actually checked node
     */
    private void push(K key, V value, Node<K, V> node)
    {
        if (key.compareTo(node.getKey()) > 0)
        {
            if (Objects.isNull(node.getRight()))
            {
                Node<K, V> right = new Node<>(key, value);
                right.setParent(node);
                node.setRight(right);
            }
            else
            {
                this.push(key, value, node.getRight());
            }
        }
        else
        {
            if (Objects.isNull(node.getLeft()))
            {
                Node<K, V> left = new Node<>(key, value);
                left.setParent(node);
                node.setLeft(left);
            }
            else
            {
                this.push(key, value, node.getLeft());
            }
        }
    }
    
    @Override
    public void vloz(K key, V value)
    {
        if (this.jePrazdny())
        {
            this.root = new Node<>(key, value);
        }
        else
        {
            this.push(key, value, this.root);
        }
        this.counter++;
    }
    
    /**
     * Finds node with defined key
     * @param key Key which defines node in tree
     * @param node Actually checked node
     * @return Node with defined key or NULL, if there is no such a node
     */
    private Node<K, V> findNode(K key, Node<K, V> node)
    {
        Node<K, V> reti = null;
        if(key.compareTo(node.getKey()) < 0 && Objects.nonNull(node.getLeft()))
        {
            reti = this.findNode(key, node.getLeft());
        }
        else if (key.compareTo(node.getKey()) > 0 && Objects.nonNull(node.getRight()))
        {
            reti = this.findNode(key, node.getRight());
        }
        else
        {
            reti = node;
        }
        return reti;
    }

    /**
     * Removes node from tree
     * @param node Node which will be removed from tree
     * @return TRUE if node has been removed, FALSE otherwise
     */
    private boolean removeNode(Node<K, V> node)
    {
        boolean reti = false;
        if (this.root.equals(node) && Objects.isNull(node.getLeft()) && Objects.isNull(node.getRight())) // Node is root of the tree
        {
            this.root = null;
        }
        else if (Objects.isNull(node.getLeft()) && Objects.isNull(node.getRight())) // Node has no child
        {
            this.replace(node, null);
        }
        else if (Objects.nonNull(node.getLeft()) && Objects.isNull(node.getRight())) // Node has only left child
        {
            this.replace(node, node.getLeft());
        }
        else if (Objects.isNull(node.getLeft()) && Objects.nonNull(node.getRight())) // Node has only right child
        {
            this.replace(node, node.getRight());
        }
        else if (Objects.nonNull(node.getLeft()) && Objects.nonNull(node.getRight())) // Node has both childs
        {
            // Find left most item in right subtree
            Node<K, V> n = node.getRight();
            while (Objects.nonNull(n.getLeft()))
            {
                n = n.getLeft();
            }
            // Remove from its actual position
            n.getParent().setLeft(null);
            // Replace removed node with data from left most node in right subtree
            node.setKey(n.getKey());
            node.setValue(n.getValue());
            
        }
        
        return reti;
    }
    
    /**
     * Replaces node in tree
     * @param oldNode Old node which will be replaced
     * @param newNode New node which will be inserted into tree
     */
    private void replace(Node<K,V> oldNode, Node<K,V> newNode)
    {
        if (oldNode.getParent().getLeft().equals(oldNode)) // Node is left child of his parent
        {
            oldNode.getParent().setLeft(newNode);
        }
        else if (oldNode.getParent().getRight().equals(oldNode)) // Node is right child of his parent
        {
            oldNode.getParent().setRight(newNode);
        }
    }
    
    @Override
    public V odeber(K key)
    {
        V reti = null;
        if (this.jePrazdny() == false)
        {
            Node<K, V> node = this.findNode(key, this.root);
            if (Objects.nonNull(node))
            {
                reti = node.getValue();
                this.removeNode(node);
            }
        }
        return reti;
    }

    @Override
    public Iterator<V> vytvorIterator(eTypProhl typ)
    {
        Iterator<V> reti = null;
        if (typ == eTypProhl.DO_SIRKY)
        {
            reti = this.levelOrderIterator();
        }
        else
        {
            reti = this.depthFirstInOrderIterator();
        }
        return reti;
    }
    
    /**
     * Gets depth first iterator in his in-order variant
     * @return In-order depth first iterator
     */
    private Iterator<V> depthFirstInOrderIterator()
    {
        return new Iterator<V>(){
            /**
             * Structure holding nodes to visit
             */
            private IAbstrLifo<Node<K, V>> struct;
            
            { // Initialization of iterator
              this.struct = new AbstrLifo<>();
              if (Objects.nonNull(root))
              {
                  this.browseLeft(root);
              }
            };            
            
            @Override
            public boolean hasNext()
            {
                return this.struct.jePrazdny() == false;
            }

            /**
             * Browses left subtree of node
             * @param node Node which left subtree will be browsed
             */
            private void browseLeft(Node<K, V> node)
            {
                while (Objects.nonNull(node))
                {
                    this.struct.vloz(node);
                    node = node.getLeft();
                }
            }
            
            @Override
            public V next()
            {
                V reti = null;
                if (this.hasNext())
                {
                    Node<K, V> actual = this.struct.odeber();
                    if (Objects.nonNull(actual))
                    {                    
                        if (Objects.nonNull(actual.getRight()))
                        {
                            this.browseLeft(actual.getRight());
                        }
                        reti = actual.getValue();
                    }   
                }
                return reti;
            }
          
        };
    }
    
    /**
     * Gets level order iterator
     * @return 
     */
    private Iterator<V> levelOrderIterator()
    {
        return new Iterator<V>(){
            
            /**
             * Structure holding nodes to visit
             */
            private IAbstrFifo<Node<K,V>> struct;
            
            { // Initialization of iterator
                this.struct = new AbstrFifo();
                if (Objects.nonNull(root))
                {
                    struct.vloz(root);
                }
            };
            
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
                    Node<K,V> node = this.struct.odeber();
                    if (Objects.nonNull(node))
                    {                    
                        reti = node.getValue();
                        if (Objects.nonNull(node.getLeft()))
                        {
                            this.struct.vloz(node.getLeft());
                        }
                        if (Objects.nonNull(node.getRight()))
                        {
                            this.struct.vloz(node.getRight());
                        }
                    }
                }                
                return reti;
            }        
        };
    }
    
}
