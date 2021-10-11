package edu.brown.cs.student.database.relations;

import edu.brown.cs.student.database.DBRelation;
import edu.brown.cs.student.database.RelationAttributeGetter;
import edu.brown.cs.student.database.RelationAttributeSetter;

public class StudentSkills extends DBRelation{
    private int id;
    private String studentName; 
    private int commenting; 
    private int testing; 
    private int oop; 
    private int algorithms; 
    private int teamwork; 
    private int frontend; 

    @Override
    public String getPrimaryKeyAttribute() {
        return "id";
    }
    
    @Override
    public String getPrimaryKeyValue() {
        return String.valueOf(this.id);
    }
    
    @Override
    public String getRelationName() {
        return "skills";
    }
    
    @Override
    protected String relationName() {
        return StudentSkills.name;
    }
    
    @RelationAttributeGetter(
        name = "id"
    ) 
    public int getId() {
        return this.id;
    }
        
    @RelationAttributeSetter(
        name = "id"
    )
    public void setID(int id) {
        this.id = id;
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
        name = "oop"
    )
    public int getOop() {
        return oop;
    }

    @RelationAttributeSetter(
        name = "oop"
    )
    public void setOop(int oop) {
        this.oop = oop;
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
        name = "name"
    )
    public String getStudentName() {
        return studentName;
    }

    @RelationAttributeSetter(
        name = "name"
    )
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
        
        
}
