package edu.brown.cs.student.ds.tree;

import static org.junit.Assert.assertEquals;
import java.util.List;

import org.junit.Test;

import java.util.ArrayList;

public class KDTreeTest {
  @Test 
  public void basicFunctionalityTest() {
    List<double[]> items = new ArrayList<double[]>();
    double[] point1 = {7., 2.};
    double[] point2 = {5., 4.};
    double[] point3 = {2., 3.};
    double[] point4 = {4., 7.};
    double[] point5 = {9., 6.};
    double[] point6 = {8., 1.};
    items.add(point1);
    items.add(point2);
    items.add(point3);
    items.add(point4);
    items.add(point5);
    items.add(point6);

    List<double[]> neighbors = new ArrayList<double[]>(); 
    neighbors.add(point1); 
    neighbors.add(point6);
    try {
      KDTree kdtree = new KDTree(items);
      
      double[] testPoint = {7. ,1.5};
      List<double[]> result = kdtree.kNearestNeighbors(testPoint, 2);
      for (int i = 0; i < result.size(); i++) {
        assertEquals(result.get(i), neighbors.get(i));
      }

    } catch (Exception e) {
      e.printStackTrace();
    }


  }
}
