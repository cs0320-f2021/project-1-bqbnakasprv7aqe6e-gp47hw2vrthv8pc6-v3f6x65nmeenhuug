package edu.brown.cs.student.database;

public class User extends DBRelation {
  private String id;
  private String weight; 
  private String bustSize; 
  private String height; 
  private String age; 
  private String bodyType; 
  private String horoscope;

  @Override
  public String getPrimaryKeyAttribute() {
    return "user_id";
  }
  @Override
  public String getPrimaryKeyValue() {
    return this.id;
  }

  @Override
  protected String relationName() {
    return "users";
  }
  
  @Override
  public String getRelationName() {
    return name;
  }

  @RelationAttribute(
    name="user_id"
  )
  public void setUserID(String id) {
    this.id = id;
  }

  public String getUserID() {
    return this.id;
  }

  @RelationAttribute(
    name="weight"
  )
  public void setWeight(String weight) {
    this.weight = weight;
  }

  public String getWeight() {
    return this.weight;
  }

  @RelationAttribute(
    name="bust_size"
  )
  public void setBustSize(String bustSize) {
    this.bustSize = bustSize;
  }

  public String getBustSize() {
    return this.bustSize;
  }

  @RelationAttribute(
    name="height"
  )
  public void setHeight(String height) {
    this.height = height;
  }

  public String getHeight() {
    return this.height;
  }

  @RelationAttribute(
    name="age"
  )
  public void setAge(String age) {
    this.age = age;
  }

  public String getAge() {
    return this.age;
  }

  @RelationAttribute(
    name="body_type"
  )
  public void setBodyType(String bodyType) {
    this.bodyType = bodyType;
  }

  public String getBodyType() {
    return this.bodyType;
  }

  @RelationAttribute(
    name="horoscope"
  )
  public void setHoroscope(String horoscope) {
    this.horoscope = horoscope;
  }

  public String getHoroscope() {
    return this.horoscope;
  }
}
