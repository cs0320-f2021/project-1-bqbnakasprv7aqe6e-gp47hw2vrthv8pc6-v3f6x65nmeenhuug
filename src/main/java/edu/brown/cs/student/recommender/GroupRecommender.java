package edu.brown.cs.student.recommender;

import edu.brown.cs.student.bloomfilter.BloomFilterRecommender;
import edu.brown.cs.student.database.Database;
import edu.brown.cs.student.database.exceptions.BadRelationException;
import edu.brown.cs.student.database.relations.*;
import edu.brown.cs.student.ds.KVPair;
import edu.brown.cs.student.main.ApiAggregator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Class for GroupRecommender.
 */
public class GroupRecommender<T extends Item> implements Recommender<T> {
  private Database database = new Database(); 
  private HashMap<String, Student> studentMap;

  /**
   * Default constructor
   */
  public GroupRecommender(){}

  public List<T> getTopKRecommendations(T item, int k) {
    // TODO 
    Double falsePositivityRate = 0.0;
    BloomFilterRecommender bloomFilterRecommender = new BloomFilterRecommender<Student>(studentMap, falsePositivityRate);
    // TODO
    // get recommendations from kd tree and bloom filter recommenders
    // combine
    return null;
  }


//  public void setBloomFilterRecommender(BloomFilterRecommender<T> bloomFilterRecommender) {
//    this.bloomFilterRecommender = bloomFilterRecommender;
//  }


  /**
   * Connect the instance's database object to the given database. 
   */
  public void connect(String path) throws SQLException, ClassNotFoundException {
    database.connect(path);
  }

  private double casted_grade(String gradeLevel) {
    switch (gradeLevel) {
      case "Freshman":
        return 1.0;
      case "Sophomore":
        return 2.0;
      case "Junior":
        return 3.0;
      case "Senior":
        return 4.0;
      case "Grad":
        return 5.0; // this is an assumption
      default:
        return -1.0;
    }
  }

  public void loadData() throws SQLException, BadRelationException {
    List<StudentTraits> traits = database.rawQuery(
        "SELECT i.id AS id, i.interest AS interest, p.trait AS positiveTrait, n.trait AS negativeTrait"
      + "FROM interests AS i JOIN negative AS n ON i.id = n.id JOIN positive AS p ON i.id = p.id",
      StudentTraits.class);
    List<StudentSkills> skillsQueryResult = database.rawQuery("SELECT * FROM skills", StudentSkills.class);

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
      if (casted_grade(datum.getGrade()) > 0) {
        student.addSkill(new KVPair<String, Double>("grade_level", casted_grade(datum.getGrade())));
      }
    }
  }
   
}
