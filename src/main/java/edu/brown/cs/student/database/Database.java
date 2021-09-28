package edu.brown.cs.student.database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.*;

import java.lang.reflect.*;

public class Database {
  private HashMap<String, Class<? extends DBRelation>> relations = new HashMap<String, Class<? extends DBRelation>>();;
 
  public Database(String filename) {

  }

  public void addRelation(Class<? extends DBRelation> relation) {
    try { 
      Method relationNameField = relation.getDeclaredMethod("getRelationName");
      DBRelation dummyRelation = relation.getDeclaredConstructor().newInstance();
      String relationName = relationNameField.invoke(dummyRelation).toString();
      relations.put(relationName, relation);
    } catch (Exception e) {
      // TODO actually handle this exception 
      e.printStackTrace();
    }
  }

  // public Relation where(String condition, String relation) {
  
  // }

}
