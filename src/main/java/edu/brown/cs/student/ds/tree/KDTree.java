package edu.brown.cs.student.ds.tree;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.lang.StackWalker.Option;
import java.util.ArrayList;
import java.util.Comparator;
import edu.brown.cs.student.main.MathBot;
// import jdk.tools.jlink.internal.SymLinkResourcePoolEntry;

import java.util.Optional;
import edu.brown.cs.student.ds.KVPair;


/**
 * Implementation of K-d Tree. Each node stores a key-value pair (see BinaryNode)
 * the value of which must be an array of doubles (for K-d trees definitionally 
 * represent a collection of points in k-dimensional space.)
 * 
 * @author lbrito2
 */
public class KDTree<K> {
  private int dimension = 1;
  private Optional<BinaryNode<K, double[]>> rootNode; 
  private static MathBot mathbot = new MathBot();
  private List<KVPair<K, double[]>> neighbors = new ArrayList<KVPair<K, double[]>>(); 
  /**
   * Default constructor.
   * 
   * @param items a collection of the key-value pairs that will constitute the 
   *  tree.
   */
  public KDTree(Collection<KVPair<K, double[]>> items) {
    Optional<KVPair<K, double[]>> probeItem = items.stream().findAny();
    if (probeItem.isEmpty()) {
      this.rootNode = Optional.empty();
    } else {
      dimension = probeItem.get().getValue().length;
      this.rootNode = constructKDTree(items, 0);
    }
  }
  
  /**
   * Private method used to recursively construct the KDTree.
   * Uses method outlined in https://en.wikipedia.org/wiki/K-d_tree
   *
   * @param items the collection of key-value pairs to be inserted
   * @param depth integer representing number of layers that have been traversed
   * @return an optional binary node
   */
  private Optional<BinaryNode<K, double[]>> constructKDTree(Collection<KVPair<K, double[]>> items, int depth) {
    List<KVPair<K, double[]>> itemsList = new ArrayList<KVPair<K, double[]>>(items);
    BinaryNode<K, double[]> tree;
    if (itemsList.size() == 1) {
      tree = new BinaryNode<K, double[]>(itemsList.get(0), Optional.empty(), Optional.empty());
      return Optional.of(tree);
    } else if (itemsList.size() == 0) {
      return Optional.empty(); 
    }
    int sortIndex = depth % dimension;
    Collections.sort(itemsList, (e1, e2) -> {
      return (int) Math.signum(e1.getValue()[sortIndex] - e2.getValue()[sortIndex]);
    });

    int medianIndex = (itemsList.size() / 2);
    KVPair<K, double[]> median = itemsList.get(medianIndex);
    tree = new BinaryNode<K, double[]>(median, 
      constructKDTree(itemsList.subList(0, medianIndex), depth += 1), 
      constructKDTree(itemsList.subList(medianIndex + 1, itemsList.size()), depth += 1));
    return Optional.of(tree);
  }   

  /**
   * Obtain the k nearest neighbors in this KDTree to the given point. 
   * 
   * @param point an array of doubles 
   * @param k integer representing number of neighbors desired
   * @return a list of key-value pairs, the k nearest neighbors to `point`
   */
  public List<KVPair<K, double[]>> kNearestNeighbors(double[] point, int k) {
    neighbors.clear();
    nearestNeighborRecursion(point, k, 0, this.rootNode);
    Collections.sort(neighbors, (e1, e2) -> {
      double dist1 = mathbot.distance(point, e1.getValue());
      double dist2 = mathbot.distance(point, e2.getValue());

      return (int) Math.signum(dist1 - dist2);
    });
    return neighbors;
  }

  /**
   * Recursive method to obtain the k nearest neighbors of the given point.
   * 
   * @param point a double array, the target point
   * @param k integer representing number of neighbors desired
   * @param depth integer representing number of layers that have been traversed
   * @param optionalCurrentNode optional current node that the algorithm is 
   *  visiting.
   * @return a list of key-value pairs, the k nearest neighbors for that 
   *  particular recursion.
   */
  private void nearestNeighborRecursion(
    double[] point, 
    int k, 
    int depth, 
    Optional<BinaryNode<K, double[]>> optionalCurrentNode)
    {
  
    if (optionalCurrentNode.isEmpty()) {
      return;
    } 
    BinaryNode<K, double[]> currentNode = optionalCurrentNode.get();
    neighbors.add(currentNode.getContent());
    Collections.sort(neighbors, (e1, e2) -> {
      double dist1 = mathbot.distance(point, e1.getValue());
      double dist2 = mathbot.distance(point, e2.getValue());

      return (int) Math.signum(dist1 - dist2);
    });

    if (neighbors.size() > k) {
      neighbors = neighbors.subList(0, k);
    }

    int axis = depth % dimension;
    if (mathbot.distance(point, neighbors.get(neighbors.size() - 1).getValue()) 
      > Math.abs(point[axis] - currentNode.getContent().getValue()[axis]) 
      || neighbors.size() < k)
    {
      nearestNeighborRecursion(point, k, depth += 1, currentNode.getLeftNode());
      nearestNeighborRecursion(point, k, depth += 1, currentNode.getRightNode());
    } else if (currentNode.getContent().getValue()[axis] < point[axis]) {
      nearestNeighborRecursion(point, k, depth += 1, currentNode.getRightNode());
    } else { 
      nearestNeighborRecursion(point, k, depth += 1, currentNode.getLeftNode());
    }
    // return;
  }
}
