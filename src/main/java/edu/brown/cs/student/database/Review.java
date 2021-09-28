package edu.brown.cs.student.database;

public class Review extends DBRelation {
    private String reviewText;
    private String reviewSummary;
    private String reviewDate;
    private int ID;
    

    @Override
    public String getRelationName() {
        return Review.name;
    }

    @Override
    protected String setRelationName() {
        return "reviews";
    }

    public String getReviewText() {
        return this.reviewText;
    }
    
    public String getReviewSummary() {
        return this.reviewSummary;
    }
    
    public String getReviewDate() {
        return this.reviewDate;
    }

    public int getID() {
        return this.ID;
    }
}
