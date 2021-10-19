package edu.brown.cs.student.recommender;

import edu.brown.cs.student.bloomfilter.BloomFilterRecommender;
import edu.brown.cs.student.database.Database;
import edu.brown.cs.student.database.exceptions.BadRelationException;
import edu.brown.cs.student.database.relations.*;
import edu.brown.cs.student.ds.KVPair;

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
  public GroupRecommender(){

  }

  public List<T> getTopKRecommendations(T item, int k) {
    // TODO 
    Double falsePositivityRate = 0.0;
    BloomFilterRecommender bf = new BloomFilterRecommender<Student>(studentMap, falsePositivityRate);
    // TODO
    // get recommendations from kd tree and bloom filter recommenders
    // combine
    return null;
  }



  /**
   * Connect the instance's database object to the given database. 
   */
  public void connect(String path) throws SQLException, ClassNotFoundException {
    database.connect(path);
  }

  public void loadData() throws SQLException, BadRelationException {
    List<StudentTraits> traits = database.rawQuery(
        "SELECT i.id AS id, interest, p.trait AS positiveTrait, n.trait AS negativeTrait"
      + "FROM interests AS i JOIN negative AS n ON i.id = n.id JOIN positive AS p on i.id = p.id", 
      StudentTraits.class);
    List<StudentSkills> skillsQueryResult = database.rawQuery("SELECT * FROM skills", StudentSkills.class);
    for (StudentSkills studentSkills : skillsQueryResult) {
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
    
  }
   
}
