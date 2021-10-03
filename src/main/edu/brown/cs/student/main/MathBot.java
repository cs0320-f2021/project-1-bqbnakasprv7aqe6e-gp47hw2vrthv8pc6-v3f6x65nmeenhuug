package edu.brown.cs.student.main;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.checkerframework.checker.units.qual.K;

import java.util.Collections;
import java.util.Comparator;

public class MathBot {

  /**
   * Default constructor.
   */
  public MathBot() {

  }

  /**
   * Adds two numbers together.
   *
   * @param num1 the first number.
   * @param num2 the second number.
   * @return the sum of num1 and num2.
   */
  public double add(double num1, double num2) {
    return num1 + num2;
  }

  /**
   * Subtracts two numbers.
   *
   * @param num1 the first number.
   * @param num2 the second number.
   * @return the difference of num1 and num2.
   */
  public double subtract(double num1, double num2) {
    return num1 - num2;
  }

  /**
   * Return first k rows of data nearest to x, y, z according to the given
   * comparator. If k > length(data), set k = length(data).
   * 
   * @param k Number of neighbors to find.
   * @param data The data from which the k neighbors will be selected.
   * @param comparator Comparison function used to determine distance. 
   * @return The k "nearest" neighbors.
   * @author Student
   */
  public List<String[]> naiveNeighbors(int k, 
                                      List<String[]> data, 
                                      Comparator<String[]> comparator) {
    // Shuffle in order to ensure equally-distant points are sampled randomly.
    // Collections.shuffle(data);
    Collections.sort(data, comparator);
    int length = k > data.size() ? data.size() : k;

    return data.subList(0, length);
  }

  /**
   * Computes the Euclidean distance between two points r1 and r2. If vectors 
   * are of unequal dimension, consider only the first n components, where n 
   * is the dimension of the smaller vector.
   * @param r1 A vector of arbitrary dimension n.
   * @param r2 A vector of arbitrary dimension n.
   * @return Norm(r1 - r2); i.e., he distance between r1 and r2.
   * @author Student
   */
  public double distance(double[] r1, double[] r2) {
    double sum = 0;
    int dim = r1.length < r2.length ? r1.length : r2.length;
    for (int i = 0; i < dim; i++) {
      sum += Math.pow(r1[i] - r2[i], 2);
    }

    return Math.sqrt(sum);
  }
}
