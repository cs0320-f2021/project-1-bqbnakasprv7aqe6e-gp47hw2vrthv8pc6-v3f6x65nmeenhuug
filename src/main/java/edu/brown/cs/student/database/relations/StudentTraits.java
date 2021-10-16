package edu.brown.cs.student.database.relations;

import edu.brown.cs.student.database.DBRelation;
import edu.brown.cs.student.database.RelationAttributeGetter;
import edu.brown.cs.student.database.RelationAttributeSetter;

public class StudentTraits extends DBRelation {
  int id;
  String interest;
  String positiveTrait; 
  String negativeTrait; 

  @Override
  public String getPrimaryKeyAttribute() {
    return "id";
  }

  @Override
  public Object getPrimaryKeyValue() {
    return this.id;
  }

  @Override
  public String getRelationName() {
    return interestsClass.name;
  }

  @Override
  protected String relationName() {
    return "interests";
  }

  @RelationAttributeGetter(
    name = "id"
  )
  public int getId() {
    return id;
  }

  @RelationAttributeSetter(
    name = "id"
  )
  public void setId(int id) {
    this.id = id;
  }

  @RelationAttributeGetter(
    name = "interest"
  )
  public String getInterest() {
    return interest;
  }

  @RelationAttributeSetter(
    name = "interest"
  )
  public void setInterest(String interest) {
    this.interest = interest;
  }

  @RelationAttributeGetter(
    name = "positiveTrait"
  )
  public String getPositiveTrait() {
    return positiveTrait;
  }

  @RelationAttributeSetter(
    name = "positiveTrait"
  )
  public void setPositiveTrait(String positiveTrait) {
    this.positiveTrait = positiveTrait;
  }

  @RelationAttributeGetter(
    name = "negativeTrait"
  )
  public String getNegativeTrait() {
    return negativeTrait;
  }

  @RelationAttributeSetter(
    name = "negativeTrait"
  )
  public void setNegativeTrait(String negativeTrait) {
    this.negativeTrait = negativeTrait;
  }
}
