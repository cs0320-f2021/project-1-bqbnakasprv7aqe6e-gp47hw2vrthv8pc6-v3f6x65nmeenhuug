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
public class GroupRecommender implements Recommender<Student> {
  private Database database = new Database(); 
  private HashMap<String, Student> studentMap = new HashMap<String, Student>();
  private HashMap<String, List<KVPair<String, double[]>>> recommendationMap =
      new HashMap<String, List<KVPair<String, double[]>>>();

  /**
   * Default constructor
   */
  public GroupRecommender(){}

  // unsure if this should be handled another way -- must be able to access Student from Main
  // to call getTopKRecommendations
  public Student getStudent(int id) {
    return studentMap.get(id);
  }


  public List<Student> getTopKRecommendations(Student item, int k) {
    // TODO 
    Double falsePositivityRate = 0.0;
    BloomFilterRecommender<Student> bloomFilterRecommender = new BloomFilterRecommender<Student>(studentMap, falsePositivityRate);
    List<Student> bloomFilterRecommendations =  bloomFilterRecommender.getTopKRecommendations(item, k);
    // bloomFilterRecommender.getTopKRecommendations(item, k);
    // TODO
    // get recommendations from kd tree and bloom filter recommenders
    // combine
    return null;
  }


//  public void setBloomFilterRecommender(BloomFilterRecommender<T> bloomFilterRecommender) {
//    this.bloomFilterRecommender = bloomFilterRecommender;
//  }

  public List<Collection<Student>> getOptimalGroups(int teamSize) {
    // TODO
    // generate groups
    return null;
  }

  /**
   * Connect the instance's database object to the given database. 
   */
  public void connect(String path) throws SQLException, ClassNotFoundException {
//    database = database.connect(path);
    database.connect(path);
    database.addRelation(interestsClass.class);
    database.addRelation(negativeClass.class);
    database.addRelation(positiveClass.class);
    database.addRelation(skillsClass.class);
//    database.addRelation(StudentTraits.class);
//    database.addRelation(StudentSkills.class);
//    System.out.println(database.getRelations());
  }

  public void loadData() throws Exception {
//    List<StudentTraits> traits = database.rawQuery(
//        "SELECT interests.id AS id, interests.interest AS interest, positive.trait AS positiveTrait, negative.trait AS negativeTrait"
//      + "FROM interests JOIN negative ON negative.id = interests.id JOIN positive ON interests.id = positive.id",
//      StudentTraits.class);

//    database.addRelation(interestsClass.class);
//    database.addRelation(negativeClass.class);
//    database.addRelation(positiveClass.class);
//    database.addRelation(skillsClass.class);

    System.out.println(database.getRelations());

//    List<StudentTraits> traits = database.rawQuery(
//        "SELECT i.id AS id, i.interest AS interest, p.trait AS positiveTrait, n.trait AS negativeTrait"
//      + " FROM interests AS i JOIN negative AS n ON i.id = n.id JOIN positive AS p ON i.id = p.id",
//      StudentTraits.class);

    List<StudentTraits> traits = database.rawQuery(
        "SELECT interests.id AS id, interests.interest AS interest, positive.trait AS positiveTrait, "
            + "negative.trait AS negativeTrait FROM interests JOIN negative ON negative.id = interests.id "
            + "JOIN positive ON interests.id = positive.id",
        StudentTraits.class);

    List<StudentSkills> skillsQueryResult = database.rawQuery("SELECT * FROM skills", StudentSkills.class);
//    List<skillsClass> skillsQueryResult = database.rawQuery("SELECT * FROM skills", skillsClass.class);


    for (StudentSkills studentSkills : skillsQueryResult) {
//      System.out.println(studentSkills);
      List<KVPair<String, Double>> skills = new ArrayList<KVPair<String, Double>>();

      skills.add(new KVPair<String, Double>("commenting", (double) studentSkills.getCommenting()));
      skills.add(new KVPair<String, Double>("testing", (double) studentSkills.getTesting()));
      skills.add(new KVPair<String, Double>("oop", (double) studentSkills.getOop()));
      skills.add(new KVPair<String, Double>("algorithms", (double) studentSkills.getAlgorithms()));
      skills.add(new KVPair<String, Double>("teamwork", (double) studentSkills.getTeamwork()));
      skills.add(new KVPair<String, Double>("frontend", (double) studentSkills.getFrontend()));

      Student student = new Student(String.valueOf(studentSkills.getId()));
//      System.out.println(student);
//      System.out.println(student.getId());
      student.setSkills(skills);
      studentMap.put(student.getId(), student);
    }

    ArrayList<KVPair<String, double[]>> collection = new ArrayList();

//    for (String id : studentMap.keySet()) {
//      Student student = id.
//    }
    for (Map.Entry<String, Student> studentEntry : studentMap.entrySet()) {
      Student student = studentEntry.getValue();
//      System.out.println("student: ");
//      System.out.println(value);
      List<KVPair<String, Double>> skills = student.getSkills();
      double[] collectionBuilder = new double[skills.size()];
      for (int i = 0; i < skills.size(); i++) {
        collectionBuilder[i] = skills.get(i).getValue();
      }
//      System.out.println("cb 0:");
//      System.out.println(collectionBuilder[0]);
//      System.out.println("cb l:");
//      System.out.println(collectionBuilder[skills.size() - 1]);
      collection.add(new KVPair(studentEntry.getKey(), collectionBuilder));
      }

    System.out.println("c0:");
    System.out.println(collection.get(0).getKey());
    KDTree<String> tree = new KDTree(collection);

    for (Map.Entry<String, Student> studentEntry : studentMap.entrySet()) {
      Student student = studentEntry.getValue();
//      System.out.println("student id:");
//      System.out.println(student.getId());
      List<KVPair<String, Double>> skills = student.getSkills();
      double[] invertedSkill = new double[skills.size()];
      for (int i = 0; i < skills.size(); i++) {
//        System.out.println("skills " + i);
//        System.out.println(skills.get(i).getValue());
        double inverted = Math.abs(skills.get(i).getValue() - 10.0);
        invertedSkill[i] = inverted;
      }
//      System.out.println(invertedSkill[0]);
      // Change number of recommendations to get from KDTree here
      int recs = 10;
      recommendationMap.put(studentEntry.getKey(), tree.kNearestNeighbors(invertedSkill, recs));
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

//    System.out.println("apiData: ");
//    System.out.println(apiData);

    for (Object apiDatum : apiData) {
      // Is it okay to cast here?
      APIClass datum = (APIClass) apiDatum;
      int id = datum.getId();
      Student student = studentMap.get(String.valueOf(id));

//      System.out.println(datum);
//      System.out.println(id);
//      System.out.println(student);

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
    System.out.println(recommendationMap.size());
//    System.out.println(recommendationMap.keySet());
    System.out.println(recommendationMap.get("1").size());
//    System.out.println(recommendationMap.values());
  }
}
