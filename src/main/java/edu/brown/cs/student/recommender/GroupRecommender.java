package edu.brown.cs.student.recommender;

import edu.brown.cs.student.bloomfilter.BloomFilterRecommender;
import edu.brown.cs.student.database.Database;
import edu.brown.cs.student.database.relations.*;
import edu.brown.cs.student.ds.KVPair;
import edu.brown.cs.student.ds.tree.KDTree;
import edu.brown.cs.student.main.ApiAggregator;

import java.sql.SQLException;
import java.util.*;

/**
 * Class for GroupRecommender.
 */
public class GroupRecommender<T extends Item> implements Recommender<T> {
  private Database database = new Database(); 
  private HashMap<String, Student> studentMap;
  private HashMap<String, List<KVPair<String, double[]>>> recommendationMap;

  /**
   * Default constructor
   */
  public GroupRecommender(){}

  // unsure if this should be handled another way -- must be able to access Student from Main
  // to call getTopKRecommendations
  public Student getStudent(int id) {
    return studentMap.get(id);
  }


  public List<T> getTopKRecommendations(T item, int k) {
    // TODO 
    Double falsePositivityRate = 0.0;
    BloomFilterRecommender bloomFilterRecommender = new BloomFilterRecommender<Student>(studentMap, falsePositivityRate);
    // bloomFilterRecommender.getTopKRecommendations(item, k);
    // TODO
    // get recommendations from kd tree and bloom filter recommenders
    // combine
    return null;
  }


//  public void setBloomFilterRecommender(BloomFilterRecommender<T> bloomFilterRecommender) {
//    this.bloomFilterRecommender = bloomFilterRecommender;
//  }

  public List<Collection<T>> getOptimalGroups(int teamSize) {
    // TODO
    // generate groups
    return null;
  }

  /**
   * Connect the instance's database object to the given database. 
   */
  public void connect(String path) throws SQLException, ClassNotFoundException {
    database.connect(path);
//    database.addRelation(interestsClass.class);
//    database.addRelation(negativeClass.class);
//    database.addRelation(positiveClass.class);
//    database.addRelation(skillsClass.class);
//    database.addRelation(StudentTraits.class);
//    database.addRelation(StudentSkills.class);
//    System.out.println(database.getRelations());
  }

  public void loadData() throws Exception {
    List<StudentTraits> traits = database.rawQuery(
        "SELECT i.id AS id, i.interest AS interest, p.trait AS positiveTrait, n.trait AS negativeTrait"
      + "FROM interests AS i JOIN negative AS n ON i.id = n.id JOIN positive AS p ON i.id = p.id",
      StudentTraits.class);

//    System.out.println(database.getRelations());

    List<StudentSkills> skillsQueryResult = database.rawQuery("SELECT * FROM skills", StudentSkills.class);
//    List<skillsClass> skillsQueryResult = database.rawQuery("SELECT * FROM skills", skillsClass.class);


    for (StudentSkills studentSkills : skillsQueryResult) {
      System.out.println(studentSkills);
      List<KVPair<String, Double>> skills = new ArrayList<KVPair<String, Double>>();

      skills.add(new KVPair<String, Double>("commenting", (double) studentSkills.getCommenting()));
      skills.add(new KVPair<String, Double>("testing", (double) studentSkills.getTesting()));
      skills.add(new KVPair<String, Double>("oop", (double) studentSkills.getOop()));
      skills.add(new KVPair<String, Double>("algorithms", (double) studentSkills.getAlgorithms()));
      skills.add(new KVPair<String, Double>("teamwork", (double) studentSkills.getTeamwork()));
      skills.add(new KVPair<String, Double>("frontend", (double) studentSkills.getFrontend()));

      Student student = new Student(String.valueOf(studentSkills.getId()));
      student.setSkills(skills);
      studentMap.put(student.getId(), student);
    }

    ArrayList<KVPair<String, double[]>> collection = new ArrayList(studentMap.size());

    for (Map.Entry<String, Student> student : studentMap.entrySet()) {
      Student value = student.getValue();
      List<KVPair<String, Double>> skills = value.getSkills();
      double[] collectionBuilder = new double[skills.size()];
      for (int i = 0; i < skills.size(); i++) {
        collectionBuilder[i] = skills.get(i).getValue();
      }
      collection.add(new KVPair(student.getKey(), collectionBuilder));
      }

    KDTree<String> tree = new KDTree(collection);
    for (Map.Entry<String, Student> student : studentMap.entrySet()) {
      Student value = student.getValue();
      List<KVPair<String, Double>> skills = value.getSkills();
      double[] invertedSkill = new double[skills.size()];
      for (int i = 0; i < skills.size(); i++) {
        double inverted = skills.get(i).getValue() - 10.0;
        invertedSkill[i] = inverted;
      }
      recommendationMap.put(student.getKey(), tree.kNearestNeighbors(invertedSkill, 10));
    }


    for (StudentTraits studentTraits : traits) {
      Student student = studentMap.get(String.valueOf(studentTraits.getId()));
      student.addTrait(studentTraits.getInterest());
      student.addTrait(studentTraits.getPositiveTrait());
      student.addTrait(studentTraits.getNegativeTrait());
    }
    // TODO 
    // After API result has been obtained, for each student in the result:
    // - obtain their id
    // - get the corresponding Student object from studentMap
    // - add the following traits to that studentObject's traits set (i.e., call
    //   student.addTrait() on the following): 
    //   - meeting 
    //   - grade 
    //   - meeting_times 
    //   - preferred_language
    // - call student.addSkill(years_of_experience), cast grade to a double
    //   (i.e. freshman = 1, soph. = 2) and call student.addSkill(casted_grade)
    //
    ApiAggregator apiAggregator = new ApiAggregator();

    List<Object> apiData = null;

    try {
      apiData = apiAggregator.getData("APIClass");
    } catch (Exception e) {
      e.printStackTrace();
    }

    System.out.println(apiData);

    for (Object apiDatum : apiData) {
      // Is it okay to cast here?
      APIClass datum = (APIClass) apiDatum;
      int id = datum.getId();
      Student student = studentMap.get(id);

      student.addTrait(datum.getMeeting());
      student.addTrait(datum.getGrade());
      student.addTrait(datum.getMeeting_times());
      student.addTrait(datum.getPreferred_language());

      student.addSkill(new KVPair<String, Double>("years_of_experience",
          (double) datum.getYears_of_experience()));
      if (datum.getCastedGrade() > 0.0) {
        student.addSkill(new KVPair<String, Double>("grade_level", datum.getCastedGrade()));
      }
    }
  }
}
