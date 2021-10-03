package edu.brown.cs.student.ds.tree;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.lang.StackWalker.Option;
import java.util.ArrayList;
import java.util.Comparator;
import edu.brown.cs.student.main.MathBot;
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

  public KDTree(Collection<KVPair<K, double[]>> items) {
    Optional<KVPair<K, double[]>> probeItem = items.stream().findAny();

    dimension = probeItem.get().getValue().length;
    // TODO enforce every array in the collection to have the same number of
    // elements
    this.rootNode = constructKDTree(items, 0);
    
  }
  
  private Optional<BinaryNode<K, double[]>> constructKDTree(Collection<KVPair<K, double[]>> items, int depth) {
    List<KVPair<K, double[]>> itemsList = new ArrayList<KVPair<K, double[]>>(items);
    BinaryNode<K, double[]> tree;
    if (itemsList.size() == 1) {
      tree = new BinaryNode<K, double[]>(itemsList.get(0), Optional.empty(), Optional.empty());
      return Optional.of(tree);
    } else if (itemsList.size() == 0) {
      return Optional.empty(); 
    }
    // KDTree<T> output = new KDTree<T>(); 
    int sortIndex = depth % dimension;
    Collections.sort(itemsList, (e1, e2) -> {
      return (int) Math.round(e1.getValue()[sortIndex] - e2.getValue()[sortIndex]);
    });

    int medianIndex = (itemsList.size() / 2) - 1;
    KVPair<K, double[]> median = itemsList.get(medianIndex);
    tree = new BinaryNode<K, double[]>(median, 
      constructKDTree(itemsList.subList(0, medianIndex), depth += 1), 
      constructKDTree(itemsList.subList(medianIndex + 1, itemsList.size()), depth += 1));
    return Optional.of(tree);
  }   

  public List<KVPair<K, double[]>> kNearestNeighbors(double[] point, int k) {
    List<KVPair<K, double[]>> neighbors = new ArrayList<KVPair<K, double[]>>();
    return nearestNeighborRecursion(point, k, 0, this.rootNode, neighbors);
  }

  private List<KVPair<K, double[]>> nearestNeighborRecursion(
    double[] point, 
    int k, 
    int depth, 
    Optional<BinaryNode<K, double[]>> optionalCurrentNode, 
    List<KVPair<K, double[]>> neighbors) 
    {
    if (optionalCurrentNode.isEmpty()) {
      return neighbors; 
    } 
    BinaryNode<K, double[]> currentNode = optionalCurrentNode.get();
    neighbors.add(currentNode.getContent());
    Collections.sort(neighbors, (e1, e2) -> {
      double dist1 = mathbot.distance(point, e1.getValue());
      double dist2 = mathbot.distance(point, e2.getValue());

      return (int) Math.round(dist1 - dist2);
    });
    if (neighbors.size() > k) {
      neighbors.remove(neighbors.size() - 1);
    }

    int axis = depth % dimension;
    if (mathbot.distance(point, neighbors.get(neighbors.size() - 1).getValue()) 
      > Math.abs(point[axis] - currentNode.getContent().getValue()[axis]))
      {
        nearestNeighborRecursion(point, k, depth += 1, currentNode.getLeftNode(), neighbors);
        nearestNeighborRecursion(point, k, depth += 1, currentNode.getRightNode(), neighbors);
    } else if (currentNode.getContent().getValue()[axis] < point[axis]) {
      nearestNeighborRecursion(point, k, depth += 1, currentNode.getRightNode(), neighbors);
    } else { 
      nearestNeighborRecursion(point, k, depth += 1, currentNode.getLeftNode(), neighbors);
    }
    return neighbors;
  }
}
