package edu.brown.cs.student.database.relations;

import edu.brown.cs.student.database.DBRelation;
import edu.brown.cs.student.database.RelationAttributeGetter;
import edu.brown.cs.student.database.RelationAttributeSetter;

public class positiveClass extends DBRelation {
    int id;
    String trait;

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
            name = "trait"
    )
    public String getTrait() {
        return trait;
    }

    @RelationAttributeSetter(
            name = "trait"
    )
    public void setTrait(String trait) {
        this.trait = trait;
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
        return positiveClass.name;
    }

    @Override
    protected String relationName() {
        return "positive";
    }
}
