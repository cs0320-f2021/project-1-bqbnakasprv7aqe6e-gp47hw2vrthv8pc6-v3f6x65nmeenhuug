package edu.brown.cs.student.database;


public class Review extends DBRelation {
  private String reviewText;
  private String reviewSummary;
  private String reviewDate;
  private int id;
  

  @Override
  public String getRelationName() {
    return Review.name;
  }

  @Override
  protected String relationName() {
    return "reviews";
  }


  @RelationAttributeGetter(
    name="review_text"
  )
  public String getReviewText() {
    return this.reviewText;
  }
  
  @RelationAttributeSetter(
    name = "review_text"
  )
  public void setReviewText(String reviewText) {
    this.reviewText = reviewText;
  }

  @RelationAttributeGetter(
    name="review_summary"
  )
  public String getReviewSummary() {
    return this.reviewSummary;
  }
  
  @RelationAttributeSetter(
    name="review_summary"
  )
  public void setReviewSummary(String reviewSummary) {
    this.reviewSummary = reviewSummary;
  }
  
  @RelationAttributeGetter(
    name="review_date"
  )
  public String getReviewDate() {
    return this.reviewDate;
  }

  @RelationAttributeSetter(
    name="review_date"
  )
  public void setReviewData(String reviewDate) {
    this.reviewDate = reviewDate;
  }

  @RelationAttributeGetter(
    name="id"
  )
  public int getId() {
    return this.id;
  }

  @RelationAttributeSetter(
    name="id"
  )
  public void setID(int id) {
    this.id = id;
  }

}
