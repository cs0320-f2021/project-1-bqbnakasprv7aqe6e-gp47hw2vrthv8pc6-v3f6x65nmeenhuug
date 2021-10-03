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

public class KDTree {
  private final int dimension;
  private Optional<BinaryNode<double[]>> rootNode; 
  private static MathBot mathbot = new MathBot();

  public KDTree(Collection<double[]> items) throws Exception {
    Optional<double[]> probeItem = items.stream().findAny();

    if (probeItem.isEmpty()) {
      throw new Exception("Empty KDTree input"); //TODO make this an actual exception type
    } else {
      dimension = probeItem.get().length;
    }
    // TODO enforce every array in the collection to have the same number of
    // elements
    this.rootNode = constructKDTree(items, 0);
    
  }
  
  private Optional<BinaryNode<double[]>> constructKDTree(Collection<double[]> items, int depth) {
    List<double[]> itemsList = new ArrayList<double[]>(items);
    BinaryNode<double[]> tree;
    if (itemsList.size() == 1) {
      tree = new BinaryNode<double[]>(itemsList.get(0), Optional.empty(), Optional.empty());
      return Optional.of(tree);
    } else if (itemsList.size() == 0) {
      return Optional.empty(); 
    }
    // KDTree<T> output = new KDTree<T>(); 
    int sortIndex = depth % dimension;
    Collections.sort(itemsList, (e1, e2) -> {
      return (int) Math.round(e1[sortIndex] - e2[sortIndex]);
    });

    int medianIndex = (itemsList.size() / 2) - 1;
    double[] median = itemsList.get(medianIndex);
    tree = new BinaryNode<double[]>(median, 
      constructKDTree(itemsList.subList(0, medianIndex), depth += 1), 
      constructKDTree(itemsList.subList(medianIndex + 1, itemsList.size()), depth += 1));
    return Optional.of(tree);
  }   

  public List<double[]> kNearestNeighbors(double[] point, int k) {
    List<double[]> neighbors = new ArrayList<double[]>();
    return nearestNeighborRecursion(point, k, 0, this.rootNode, neighbors);
  }

  public List<double[]> nearestNeighborRecursion(
    double[] point, 
    int k, 
    int depth, 
    Optional<BinaryNode<double[]>> optionalCurrentNode, 
    List<double[]> neighbors) 
    {
    if (optionalCurrentNode.isEmpty()) {
      return neighbors; 
    } 
    BinaryNode<double[]> currentNode = optionalCurrentNode.get();
    neighbors.add(currentNode.getContent());
    Collections.sort(neighbors, (e1, e2) -> {
      double dist1 = mathbot.distance(point, e1);
      double dist2 = mathbot.distance(point, e2);

      return (int) Math.round(dist1 - dist2);
    });
    if (neighbors.size() > k) {
      neighbors.remove(neighbors.size() - 1);
    }

    int axis = depth % dimension;
    if (mathbot.distance(point, neighbors.get(neighbors.size() - 1)) 
      > Math.abs(point[axis] - currentNode.getContent()[axis]))
      {
        nearestNeighborRecursion(point, k, depth += 1, currentNode.getLeftNode(), neighbors);
        nearestNeighborRecursion(point, k, depth += 1, currentNode.getRightNode(), neighbors);
    } else if (currentNode.getContent()[axis] < point[axis]) {
      nearestNeighborRecursion(point, k, depth += 1, currentNode.getRightNode(), neighbors);
    } else { 
      nearestNeighborRecursion(point, k, depth += 1, currentNode.getLeftNode(), neighbors);
    }
    return neighbors;
  }
}
