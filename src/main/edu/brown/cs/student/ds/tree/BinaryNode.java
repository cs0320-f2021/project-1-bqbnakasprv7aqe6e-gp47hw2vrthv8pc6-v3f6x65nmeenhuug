package edu.brown.cs.student.ds.tree;

public class BinaryNode<T> {
	private BinaryNode<T> left;
	private BinaryNode<T> right; 
	private T content; 

	public BinaryNode(T content, BinaryNode<T> left, BinaryNode<T> right) {
    this.left = left;
    this.right = right;
    this.content = content;
	}

  public BinaryNode<T> getLeftNode() {
    return this.left;
  }

  public BinaryNode<T> getRightNode() {
    return this.right;
  }

  public T getContent() {
    return this.content;
  }

}
