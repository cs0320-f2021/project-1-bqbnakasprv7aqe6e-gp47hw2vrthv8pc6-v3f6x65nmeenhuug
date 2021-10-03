package edu.brown.cs.student.ds.tree;

import java.util.Optional;
import edu.brown.cs.student.ds.KVPair;

/**
 * Implementation of node for binary trees. Data stored in nodes is key-value 
 * pair datatype.
 * 
 * @author lbrito2
 */
public class BinaryNode<K, V> {
	private Optional<BinaryNode<K, V>> left;
	private Optional<BinaryNode<K, V>> right; 
	private KVPair<K, V> content; 

	public BinaryNode(KVPair<K, V> content, Optional<BinaryNode<K, V>> left, Optional<BinaryNode<K, V>> right) {
    this.left = left;
    this.right = right;
    this.content = content;
	}

  public Optional<BinaryNode<K, V>> getLeftNode() {
    return this.left;
  }

  public Optional<BinaryNode<K, V>> getRightNode() {
    return this.right;
  }

  public KVPair<K, V> getContent() {
    return this.content;
  }

}
