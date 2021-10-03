package edu.brown.cs.student.ds.tree;

import static org.junit.Assert.assertEquals;
import java.util.List;
import edu.brown.cs.student.ds.KVPair;

import org.junit.Test;

import java.util.ArrayList;

public class KDTreeTest {
  @Test 
  public void basicFunctionalityTest() {
    List<KVPair<String, double[]>> items = new ArrayList<KVPair<String, double[]>>();
    double[] point1 = {7., 2.};
    double[] point2 = {5., 4.};
    double[] point3 = {2., 3.};
    double[] point4 = {4., 7.};
    double[] point5 = {9., 6.};
    double[] point6 = {8., 1.};
    KVPair<String, double[]> item1 = new KVPair<String,double[]>("", point1);
    KVPair<String, double[]> item2 = new KVPair<String,double[]>("", point2);
    KVPair<String, double[]> item3 = new KVPair<String,double[]>("", point3);
    KVPair<String, double[]> item4 = new KVPair<String,double[]>("", point4);
    KVPair<String, double[]> item5 = new KVPair<String,double[]>("", point5);
    KVPair<String, double[]> item6 = new KVPair<String,double[]>("", point6);
    items.add(item1);
    items.add(item2);
    items.add(item3);
    items.add(item4);
    items.add(item5);
    items.add(item6);

    List<KVPair<String, double[]>> neighbors = new ArrayList<KVPair<String, double[]>>(); 
    neighbors.add(item1); 
    neighbors.add(item6);
    try {
      KDTree<String> kdtree = new KDTree<String>(items);
      
      double[] testPoint = {7. ,1.5};
      List<KVPair<String, double[]>> result = kdtree.kNearestNeighbors(testPoint, 2);
      for (int i = 0; i < result.size(); i++) {
        assertEquals(result.get(i), neighbors.get(i));
      }

    } catch (Exception e) {
      e.printStackTrace();
    }


  }
}
