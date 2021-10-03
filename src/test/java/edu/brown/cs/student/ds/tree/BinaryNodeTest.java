package edu.brown.cs.student.ds.tree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.List;
import java.util.Optional;

import edu.brown.cs.student.ds.KVPair;

import org.junit.Test;
public class BinaryNodeTest {

	@Test 
	public void emptyNodesTest() {
		KVPair<String, String> datum = new KVPair<String,String>("", "");
		BinaryNode<String, String> node = new BinaryNode<String, String>(datum, Optional.empty(), Optional.empty());
		assertTrue(node.getLeftNode().isEmpty());
		assertTrue(node.getRightNode().isEmpty());
	}
}
