package edu.brown.cs.student.main;

import java.sql.ResultSet;

/** 
 * Abstract class representing abstracted tuple of relational model.
 * 
 * @author lbrito2
 */
public abstract class DBRelation{
	// TODO interface vs abstract class? think of any abstract class methods 
	// we might want to have

	/**
	 * Name of the relation/table as it appears in the database.
	 */
	protected static String name;

	// NOTE: the below hack forces subclasses to set a relationName.
	public DBRelation() {
		name = this.setRelationName();
	}


	/**
	 * TODO
	 * 
	 * @return
	 */
	public abstract String getRelationName();

	/**
	 * TODO
	 * gntgs
	 * @return
	 */
	protected abstract String setRelationName();

	/**
	 * TODO 
	 * 
	 * @param rs
	 */
	// public abstract void parseResponse(ResultSet rs);

	/**
	 * TODO 
	 * 
	 * @param relation
	 * @return
	 */
	// public abstract boolean equals(Relation relation);
}    
 
