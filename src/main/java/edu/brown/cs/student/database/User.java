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
    return User.name;
  }

  @RelationAttributeSetter(
    name="user_id"
  )
  public void setUserID(String id) {
    this.id = id;
  }

  @RelationAttributeGetter(
    name="id"
  )
  public String getUserID() {
    return this.id;
  }

  @RelationAttributeSetter(
    name="weight"
  )
  public void setWeight(String weight) {
    this.weight = weight;
  }

  @RelationAttributeGetter(
    name="weight"
  )
  public String getWeight() {
    return this.weight;
  }

  @RelationAttributeSetter(
    name="bust_size"
  )
  public void setBustSize(String bustSize) {
    this.bustSize = bustSize;
  }

  @RelationAttributeGetter(
    name="bust_size"
  )
  public String getBustSize() {
    return this.bustSize;
  }

  @RelationAttributeSetter(
    name="height"
  )
  public void setHeight(String height) {
    this.height = height;
  }

  @RelationAttributeGetter(
    name="height"
  )
  public String getHeight() {
    return this.height;
  }

  @RelationAttributeSetter(
    name="age"
  )
  public void setAge(String age) {
    this.age = age;
  }

  @RelationAttributeGetter(
    name="age"
  )
  public String getAge() {
    return this.age;
  }

  @RelationAttributeSetter(
    name="body_type"
  )
  public void setBodyType(String bodyType) {
    this.bodyType = bodyType;
  }

  @RelationAttributeGetter(
    name="body_type"
  )
  public String getBodyType() {
    return this.bodyType;
  }

  @RelationAttributeSetter(
    name="horoscope"
  )
  public void setHoroscope(String horoscope) {
    this.horoscope = horoscope;
  }

  @RelationAttributeGetter(
    name="horoscope"
  )
  public String getHoroscope() {
    return this.horoscope;
  }
}
