package edu.brown.cs.student.recommender;

import edu.brown.cs.student.bloomfilter.BloomFilterRecommender;

import java.util.HashMap;
import java.util.List;

/**
 * Class for GroupRecommender.
 */
public class GroupRecommender<T extends Item> implements Recommender<T> {
  BloomFilterRecommender<T> bloomFilterRecommender;

  public GroupRecommender(){}

  public List<T> getTopKRecommendations(T item, int k) {
    // TODO
    // get recommendations from kd tree and bloom filter recommenders
    // combine
    return null;
  }

  public void setBloomFilterRecommender(BloomFilterRecommender<T> bloomFilterRecommender) {
    this.bloomFilterRecommender = bloomFilterRecommender;
  }


}
