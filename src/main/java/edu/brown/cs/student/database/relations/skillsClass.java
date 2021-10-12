package edu.brown.cs.student.database.relations;

import edu.brown.cs.student.database.DBRelation;
import edu.brown.cs.student.database.RelationAttributeGetter;
import edu.brown.cs.student.database.RelationAttributeSetter;

public class skillsClass extends DBRelation {
    int id;
    String names;
    int commenting;
    int testing;
    int OOP;
    int algorithms;
    int teamwork;
    int frontend;

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
            name = "names"
    )
    public String getNames() {
        return names;
    }

    @RelationAttributeSetter(
            name = "names"
    )
    public void setNames(String names) {
        this.names = names;
    }

    @RelationAttributeGetter(
            name = "commenting"
    )
    public int getCommenting() {
        return commenting;
    }

    @RelationAttributeSetter(
            name = "commenting"
    )
    public void setCommenting(int commenting) {
        this.commenting = commenting;
    }

    @RelationAttributeGetter(
            name = "testing"
    )
    public int getTesting() {
        return testing;
    }

    @RelationAttributeSetter(
            name = "testing"
    )
    public void setTesting(int testing) {
        this.testing = testing;
    }

    @RelationAttributeGetter(
            name = "OOP"
    )
    public int getOOP() {
        return OOP;
    }

    @RelationAttributeSetter(
            name = "OOP"
    )
    public void setOOP(int OOP) {
        this.OOP = OOP;
    }

    @RelationAttributeGetter(
            name = "algorithms"
    )
    public int getAlgorithms() {
        return algorithms;
    }

    @RelationAttributeSetter(
            name = "algorithms"
    )
    public void setAlgorithms(int algorithms) {
        this.algorithms = algorithms;
    }

    @RelationAttributeGetter(
            name = "teamwork"
    )
    public int getTeamwork() {
        return teamwork;
    }

    @RelationAttributeSetter(
            name = "teamwork"
    )
    public void setTeamwork(int teamwork) {
        this.teamwork = teamwork;
    }

    @RelationAttributeGetter(
            name = "frontend"
    )
    public int getFrontend() {
        return frontend;
    }

    @RelationAttributeSetter(
            name = "frontend"
    )
    public void setFrontend(int frontend) {
        this.frontend = frontend;
    }

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
        return skillsClass.name;
    }

    @Override
    protected String relationName() {
        return "skills";
    }
}
