package edu.brown.cs.student.ds.tree;

import java.util.Optional;

public class BinaryNode<T> {
	private Optional<BinaryNode<T>> left;
	private Optional<BinaryNode<T>> right; 
	private T content; 

	public BinaryNode(T content, Optional<BinaryNode<T>> left, Optional<BinaryNode<T>> right) {
    this.left = left;
    this.right = right;
    this.content = content;
	}

  public Optional<BinaryNode<T>> getLeftNode() {
    return this.left;
  }

  public Optional<BinaryNode<T>> getRightNode() {
    return this.right;
  }

  public T getContent() {
    return this.content;
  }

}
