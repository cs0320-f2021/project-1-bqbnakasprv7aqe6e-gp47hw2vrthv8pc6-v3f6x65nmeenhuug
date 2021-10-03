package edu.brown.cs.student.database;

/**
 * Abstract class representing abstracted tuple of relational model.
 * 
 * @author lbrito2
 */
public abstract class DBRelation {

	/**
	 * Name of the relation/table as it appears in the database.
	 */
	protected static String name;

	// NOTE: the below hack forces subclasses to set a relationName.
	public DBRelation() {
		name = this.relationName();
	}

	public abstract String getPrimaryKeyAttribute();

	public abstract String getPrimaryKeyValue();
	/**
	 * TODO
	 * 
	 * @return
	 */
	public abstract String getRelationName();

	/**
	 * TODO
	 * 
	 * @return
	 */
	protected abstract String relationName();

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
 
