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


  public String getReviewText() {
    return this.reviewText;
  }
  
  @RelationAttribute(
    name = "review_text"
  )
  public void setReviewText(String reviewText) {
    this.reviewText = reviewText;
  }
  
  public String getReviewSummary() {
    return this.reviewSummary;
  }
  
  @RelationAttribute(
    name="review_summary"
  )
  public void setReviewSummary(String reviewSummary) {
    this.reviewSummary = reviewSummary;
  }
  
  public String getReviewDate() {
    return this.reviewDate;
  }

  @RelationAttribute(
    name="review_date"
  )
  public void setReviewData(String reviewDate) {
    this.reviewDate = reviewDate;
  }

  public int getId() {
    return this.id;
  }

  @RelationAttribute(
    name="id"
  )
  public void setID(int id) {
    this.id = id;
  }

}
