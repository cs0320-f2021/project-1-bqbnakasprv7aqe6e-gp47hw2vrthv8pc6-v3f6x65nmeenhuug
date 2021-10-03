package edu.brown.cs.student.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

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
        assertEquals(r.getID(), 1);
      }
		} catch (Exception e) {
      e.printStackTrace();
		}
	}

	@Test 
	public void twoRelationTypesTest() {
		try {
			Database db = new Database();
			db.connect("./data/project-1/testDB1.sqlite3");
			db.addRelation(Review.class);
      db.addRelation(User.class);
			List<Review> queryResult = db.where("review_text == \"Hello world!\"", "reviews");

      for (Review r : queryResult) {
        assertEquals(r.getID(), 1);
      }
			List<User> queryResult2 = db.where("user_id == \"1\"", "users");

      for (User u : queryResult2) {
        assertEquals(u.getUserID(), "1");
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

	@Test
	public void testInsert() {
		try {
			Database db = new Database();
			db.connect("./data/project-1/testDB1.sqlite3");
			db.addRelation(Review.class);
			db.addRelation(User.class);

			Review rave = new Review();
			rave.setReviewText("mind-blowing, life-changing, never-to-be-forgotten");
			rave.setReviewSummary("very pleased");
			rave.setReviewDate("10.03.2021");
			rave.setID(4);

			db.insert(rave);

			List<Review> queryResult = db.where("review_text == \"mind-blowing, " +
					"life-changing, never-to-be-forgotten\"", "reviews");

			for (Review r : queryResult) {
				assertEquals(r.getID(), 4);
			}

			List<Review> queryResult2 = db.where("review_date == \"10.03.2021\"", "reviews");

			for (Review r : queryResult2) {
				assertEquals(r.getID(), 4);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	@Test
//	public void testDelete() {
//		try {
//			Database db = new Database();
//			db.connect("./data/project-1/testDB1.sqlite3");
//			db.addRelation(Review.class);
//			db.addRelation(User.class);
//
//			List<Review> queryResult = db.where("review_text == \"Hello world!\"", "reviews");
//
//			for (Review r : queryResult) {
//				db.delete(r);
//				assertThrows(Exception.class, () -> db.where("review_text == \"Hello world!\"", "reviews"));
//			}
//
//			List<User> queryResult2 = db.where("user_id == \"1\"", "users");
//
//			for (User u : queryResult2) {
//				db.delete(u);
//				assertThrows(Exception.class, () -> db.where("user_id == \"1\"", "users"));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

//	public void testUpdate() {
//		try {
//			Database db = new Database();
//			db.connect("./data/project-1/testDB1.sqlite3");
//			db.addRelation(Review.class);
//			db.addRelation(User.class);
//
//			Review rave = new Review();
//			rave.setReviewText("mind-blowing, life-changing, never-to-be-forgotten");
//			rave.setReviewSummary("very pleased");
//			rave.setReviewDate("10.03.2021");
//			rave.setID(4);
//
//			db.insert(rave);
//
//			List<Review> queryResult = db.where("review_text == \"mind-blowing, " +
//					"life-changing, never-to-be-forgotten\"", "reviews");
//
//			for (Review r : queryResult) {
//				assertEquals(r.getID(), 4);
//			}
//
//			List<Review> queryResult2 = db.where("review_date == \"10.03.2021\"", "reviews");
//
//			for (Review r : queryResult2) {
//				assertEquals(r.getID(), 4);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}


}
