package edu.brown.cs.student.database;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import edu.brown.cs.student.database.Database;

public class DatabaseTest {

	@Test 
	public void addRelationFunctionalityTest() {
		try {
			Database db = new Database();
			db.connect("./data/project-1/testDB1.sqlite3");
			db.addRelation(Review.class);
			List<Review> queryResult = db.where("review_text == \"Hello world!\"", "reviews");
      for (Review r : queryResult) {
        assertEquals(r.getId(), 1);
      }
		} catch (Exception e) {
      e.printStackTrace();
    }
	}

	@Test 
	public void twoRelationTypesTest () {
		try {
			Database db = new Database();
			db.connect("./data/project-1/testDB1.sqlite3");
			db.addRelation(Review.class);
      db.addRelation(User.class);
			List<Review> queryResult = db.where("review_text == \"Hello world!\"", "reviews");
      for (Review r : queryResult) {
        assertEquals(r.getId(), 1);
      }
			List<User> queryResult2 = db.where("user_id == \"1\"", "users");
      for (User r : queryResult2) {
        assertEquals(r.getUserID(), "1");
      }
		} catch (Exception e) {
      e.printStackTrace();
    }
	}

	@Test 
	public void rawQueryTest() {
		try { 
			Database db = new Database();
			db.connect("./data/project-1/testDB1.sqlite3");
			db.addRelation(Review.class);
			List<Review> result = db.rawQuery("SELECT * FROM reviews", Review.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
	} 

}
