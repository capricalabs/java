package edu.bellevuecollege.cs211.linkedlist.student;
/**
 * Programming Assignment #2
 * @author Dan Netzer
 */

import java.util.ArrayList;
import java.util.Iterator;

import edu.bellevuecollege.cs211.linkedlist.exceptions.NodeNotFoundException;

public class LinkedListt<T> implements Iterable<T>, Iterator<T>
{
  public class Node
  {
    /**
     * The next node in the Linked List.
     */
    Node next;

    /**
     * The value of the element located in the Node.
     */
    T value;

    /**
     * Returns the next node in the linked list.
     * 
     * @return The next node in the linked list.
     */
    public Node getNext()
    {
      return next;
    }

    /**
     * Set the next node in the linked list.
     * 
     * @param next
     *            The node to be added to the LinkedList.
     */
    public void setNext(Node next)
    {
      this.next = next;
    }

    /**
     * Return the value contained in the node.
     * 
     * @return the value contained in the node.
     */
    public T getValue()
    {
      return value;
    }

    /**
     * Set the node with the value given.
     * 
     * @param value
     *            The value to be placed in the node.
     */
    public void setValue(T value)
    {
      this.value = value;
    }

    public String toString()
    {
      return value.toString();
    }
  }

  /**
   * The root of your linked list, the very first element, do not lose this reference!!
   */

  Node root;
  //location that the iterator is pointing 
  Node location;
  
  //keep track of size of linked list
  int size;
  
  /**
   * The value of the element located in the Node.
   */
  boolean NodeRemovable;
  
  public LinkedList()
  { 
    root = new Node();
    location = root;
    size=0;
  }

  /**
   * Return the number of elements in the LinkedList
   * 
   * @return The size of the LinkedList
   */
  public int getSize()
  {
    return this.size;
  }

  /**
   * Return true if the LinkedList is empty, false otherwise
   * 
   * @return true if the LinkedList is empty, false otherwise
   */
  public boolean isEmpty()
  {
    if(getSize()>0) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * Insert a node at the front of the linked list. The first variable should now point to this node. Wrap it in a
   * node and add it to the list. Do not add the Node if it already exists in the list.
   * 
   * @param node
   *            The node to be inserted into the linked list.
   * @return true if inserted, false if already in list and cannot be inserted.
   */

  public boolean insertFront(T element)
  {
    if(contains(element)){
      return false;
    }
    
    Node front = new Node();
    front.setValue(element);
    Node temp = root;
    
    Node append = temp.getNext();
    
    if(append!=null){
      front.setNext(append);
    }

    temp.setNext(front);
    

    size++;
    return true;
  }

  /**
   * Insert a node at the back of the linked list. Wrap it in a node and add it to the list. Do not add the Node if it
   * already exists in the list.
   * 
   * @param node
   *            The node to be inserted into the linked list.
   * @return true if inserted, false if already in list and cannot be inserted.
   */  

  public boolean insertBack(T element)
  {
    if(contains(element)){
      return false;
    }

    Node find = root.getNext();

    while(find.getNext() !=null) {
      find = find.getNext();
    }
    
    Node back = new Node();
    back.setValue(element);
    find.setNext(back);
    
    size++;
    return true;
  }

  /**
   * Insert the given node after the currentNode given. Wrap it in a node and add it in a position after the node
   * specified by the variable {@code currentNode}. Throws a NodeNotFoundException if it can't found the node given.
   * Do not add the Node if it already exists in the list.
   * 
   * @param currentNode
   *            The node to look for to add the given node behind.
   * @param node
   *            The element to be inserted into the linked list.
   * @throws NodeNotFoundException
   *             Thrown if the element given is not found
   * @return true if inserted, false if already in list and cannot be inserted.
   */

  public boolean insertAfter(T currentElement, T element) throws NodeNotFoundException
  {
    if(! contains(currentElement)){
      throw new NodeNotFoundException("Did not find element.");
    }
    
    if(contains(element)) {
      return false;
    }
    
    Node find = root.getNext();
    Node after = new Node();
    after.setValue(element);

    while(find != null) {
      if(find.getValue() != null && currentElement.equals(find.getValue())){
        Node append = find.getNext();
        find.setNext(after);
        find.getNext().setNext(append);
        size++;
        return true;
      }

      find = find.getNext();
    }
    
    return false;
  }

  /**
   * Insert the given node before the currentNode given. Wrap it in a node and add it in a position after the node
   * specified by the variable {@code currentNode}. Throws a NodeNotFoundException if it can't found the node given.
   * Do not add the Node if it already exists in the list.
   * 
   * @param currentNode
   *            The node to look for to add the given node in front of.
   * @param node
   *            The element to be inserted into the linked list.
   * 
   * @throws NodeNotFoundException
   *             Thrown if the element given is not found
   * @return true if inserted, false if already in list and cannot be inserted.
   */

  public boolean insertBefore(T currentElement, T element) throws NodeNotFoundException
  {
    if(! contains(currentElement)){
      throw new NodeNotFoundException("Did not find element.");
    }
    
    if(contains(element)) {
      return false;
    }
    
    Node newNode = new Node();
    newNode.setValue(element);
    Node previousNode=null;
    Node currentNode=root.getNext();

    while(currentNode != null) {
      if(currentNode.getValue() != null && currentElement.equals(currentNode.getValue())){
        if(previousNode==null){
          root=newNode;
        } else {
          previousNode.setNext(newNode);
        }
        
        newNode.setNext(currentNode);
        size++;
        return true;
      }
      
      previousNode = currentNode;
      currentNode = currentNode.getNext();
    }
    
    return false;
  }

  /**
   * Remove the node matches the given element. Return the element that is removed. Throws NodeNotFoundException if
   * the element is not found.
   * 
   * @param element
   *            The element to find and remove.
   * @return Return the node that contains the element that was removed.
   * @throws NodeNotFoundException
   *             Thrown if the element to be found can't be found.
   */

  public T remove(T element) throws NodeNotFoundException
  {
    Node previousNode=null;
    Node currentNode=root;
    while(currentNode != null) {
      if(currentNode.getValue() != null && element.equals(currentNode.getValue())){
        if(previousNode==null){
          root=currentNode.getNext();
        } else {
          previousNode.setNext(currentNode.getNext());
        }
        
        size--;
        return currentNode.getValue();
      }
      
      previousNode = currentNode;
      currentNode = currentNode.getNext();
    }
    
    throw new NodeNotFoundException("Did not find element.");
  }

  /**
   * Remove all nodes in the LinkedList, return all nodes in an ArrayList.
   * 
   * 
   * @return Returns all nodes in an ArrayList.
   */

  public ArrayList<T> removeAll() throws NodeNotFoundException
  {
    ArrayList<T> converted = convert();
    root = new Node();
    size=0;
    return converted;
  }

  /**
   * Return true if the element passed in is in the linked list.
   * 
   * @param element
   *            The element to check for.
   * @return true if the element exists in the linked list, false otherwise.
   */
  public boolean contains(T element)
  {
    Node find = root;
    while(find != null) {
      if(find.getValue() != null && element.equals(find.getValue())){
        return true;
      }

      find = find.getNext();
    }
    
    return false;
  }

  /**
   * Find an element and return it if it is found, otherwise return null
   * 
   * @param element
   *            The element to look for.
   * @return The element if found, null if not.
   */
  public T findElement(T element)
  {
    Node find = root;
    while(find != null) {
      if(find.getValue() != null && element.equals(find.getValue())){
        return find.getValue();
      }

      find = find.getNext();
    }
    
    return null;
  }

  /**
   * Find an element and return it if it is found, otherwise return null
   * 
   * @param element
   *            The element to look for.
   * @return The element if found, null if not.
   */
  public Node findNode(T element)
  {
    Node find = root;
    while(find != null) {
      if(find.getValue() != null && element.equals(find.getValue())){
        return find;
      }

      find = find.getNext();
    }
    
    return null;
  }

  /**
   * Converts the LinkedList to an ArrayList.
   * 
   * @return An ArrayList containing all elements that are contained within the linked list.
   */
  public ArrayList<T> convert()
  {
    ArrayList<T> nodes = new ArrayList<T>();
    
    Node find = root;
    while(find != null) {
      if(find.getValue()!=null){
        nodes.add(find.getValue());
      }

      find = find.getNext();
    }
    
    return nodes;
  }

  /**
   * Return the linked list as a string in the format element -> element -> element. For example
   * "first -> second -> third"
   * 
   * @return This linked list in the form of a string.
   */
  @Override
  public String toString()
  {
    if(root==null){
      return "[]";
    } else {

      Node find = root.getNext();
      String result = find.toString() ;
      find = find.getNext();

      while(find != null) {
        result += " -> " + find.getValue();
        find = find.getNext();
      }

      return result;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Iterable#iterator()
   */
  @Override
  public Iterator<T> iterator()
  {
    location=root;
    NodeRemovable=false;

    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Iterator#hasNext()
   */
  @Override
  public boolean hasNext()
  {
    return this.location.getNext() != null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Iterator#next()
   */
  @Override
  public T next()
  {
    this.location = location.getNext();
    NodeRemovable=true;

    return location.getValue();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Iterator#remove()
   */
  @Override
  public void remove()
  {
    if(NodeRemovable==false){
      throw new IllegalStateException();
    }
    
    NodeRemovable=false;
    Node previousNode=null;
    Node currentNode=root.getNext();
    while(currentNode != null) {
      if(currentNode.getValue() != null && location.getValue().equals(currentNode.getValue())){
        if(previousNode==null){
          root.setNext(currentNode.getNext());
        } else {
          previousNode.setNext(currentNode.getNext());
        }
        size--;
        return;
      }
      
      previousNode = currentNode;
      currentNode = currentNode.getNext();
    } 
  } 
}