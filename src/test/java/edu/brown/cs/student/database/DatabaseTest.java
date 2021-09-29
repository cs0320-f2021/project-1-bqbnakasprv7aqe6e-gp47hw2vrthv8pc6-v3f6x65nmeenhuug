package edu.brown.cs.student.database;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import edu.brown.cs.student.database.Database;

public class DatabaseTest {

	@Test 
	public void addRelationFunctionalityTest() {
		try {
			Database db = new Database("./data/project-1/testDB1.sqlite3");
			db.addRelation(Review.class);
			List<Review> queryResult = db.where("review_text == \"Hello world!\"", "reviews");
      for (Review r : queryResult) {
        assertEquals(r.getId(), 1);
      }
		} catch (Exception e) {
      e.printStackTrace();
    }
	}

}
