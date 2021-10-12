package edu.brown.cs.student.recommender;

import edu.brown.cs.student.database.RelationAttributeGetter;

public class APIClass {
    private int id;
    private String name;
    private String meeting;
    private String grade;
    private Double years_of_experience;
    private String horoscope;
    private String meeting_times;
    private String preferred_language;
    private String marginalized_groups;
    private String prefer_group;

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getGrade() {
        return this.grade;
    }

    public String getMeeting() {
        return this.meeting;
    }

    public Double getYears_of_experience() {
        return this.years_of_experience;
    }

    public String getHoroscope() {
        return this.horoscope;
    }

    public String getMeeting_times() {
        return this.meeting_times;
    }

    public String getPreferred_language() {
        return this.preferred_language;
    }

    public String getMarginalized_groups() {
        return this.marginalized_groups;
    }

    public String getPrefer_group() {
        return this.prefer_group;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMeeting(String meeting) {
        this.meeting = meeting;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setYears_of_experience(Double years_of_experience) {
        this.years_of_experience = years_of_experience;
    }

    public void setHoroscope(String horoscope) {
        this.horoscope = horoscope;
    }

    public void setMeeting_times(String meeting_times) {
        this.meeting_times = meeting_times;
    }

    public void setPreferred_language(String preferred_language) {
        this.preferred_language = preferred_language;
    }

    public void setMarginalized_groups(String marginalized_groups) {
        this.marginalized_groups = marginalized_groups;
    }

    public void setPrefer_group(String prefer_group) {
        this.prefer_group = prefer_group;
    }
}

// Create a class for each table of the ORM to fil the fields of that class with column types
// each user is a new instance of the class

// use this class for ApiAggregator