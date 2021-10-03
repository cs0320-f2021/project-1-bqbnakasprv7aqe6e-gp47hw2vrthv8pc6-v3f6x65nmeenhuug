package edu.brown.cs.student.ds;

/**
 * Simple key-value pair implementation. 
 * 
 * @author lbrito2
 */
public class KVPair<K, V> {
  private K key; 
  private V value; 
  
  public KVPair(K key, V value) { 
    this.key = key; 
    this.value = value; 
  }

  public K getKey() {
    return this.key; 
  }

  public V getValue() {
    return this.value; 
  }
}
