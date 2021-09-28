package edu.brown.cs.student.database;

public class User extends DBRelation { 
  
  @Override
  protected String setRelationName() {
    return "users";
  }
  
  @Override
  public String getRelationName() {
    return User.name;
  }



}
