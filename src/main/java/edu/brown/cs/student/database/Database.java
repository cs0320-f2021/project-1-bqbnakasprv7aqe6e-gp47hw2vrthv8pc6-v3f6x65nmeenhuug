package edu.brown.cs.student.database;

import java.sql.Array;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.sql.*;
import java.util.Date;
import java.util.StringJoiner;


import java.lang.reflect.*;

import edu.brown.cs.student.database.exceptions.BadRelationException;
import edu.brown.cs.student.main.Error;


public class Database {
  private HashMap<String, Class<? extends DBRelation>> relations = new HashMap<String, Class<? extends DBRelation>>();
  private Connection conn = null;
 
  /**
   * Default constructor. Instantiates a connection to the database via Java's 
   * builtin SQL API. 
   * 
   * @param filename path to .sqlite3 file
   * @throws SQLException 
   * @throws ClassNotFoundException
   */
  public Database() {

  }
  
  public void clear() {
    conn = null; 
    clearRelations();
  }

  public void clearRelations() { 
    relations.clear(); 
  }

  public void connect(String filename) throws SQLException, ClassNotFoundException {
    this.clear();
    Class.forName("org.sqlite.JDBC");
    String urlToDB = "jdbc:sqlite:" + filename;
    conn = DriverManager.getConnection(urlToDB);
    Statement stat = conn.createStatement();
    stat.executeUpdate("PRAGMA foreign_keys=ON;");
  }

  /**
   * Add a new relation/table to this database instance. 
   * 
   * @param relation a relation class (see DBRelation). Note that the argument
   * should be of the form `ClassName.class`.
   */
  public <T extends DBRelation> void addRelation(Class<T> relation) {
    try { 
      Method relationNameField = relation.getDeclaredMethod("getRelationName");
      T dummyRelation = relation.getDeclaredConstructor().newInstance();
      String relationName = relationNameField.invoke(dummyRelation).toString();
      relations.put(relationName, relation);
    } catch (Exception e) {
      // TODO actually handle this exception well lol
      e.printStackTrace();
    }
  }

  public <T extends DBRelation> List<T> where(String condition, String relationName) throws BadRelationException, SQLException {
    @SuppressWarnings("unchecked") // TODO explain why this is okay/talk to TA
    Class<T> relationClass = (Class<T>) relations.get(relationName);

    List<T> queryResult = new ArrayList<T>();

    if (Objects.isNull(relationClass)) {
      Error.noSuchRelationError(relationName);
    }

    try {
      // TODO consider putting this HashMap in the DBRelation abstract class
      HashMap<String, Method> setterHashMap = new HashMap<String, Method>();
      Method[] relationMethods = relationClass.getDeclaredMethods();
      Stream<Method> methodStream = Arrays.stream(relationMethods);
      Method[] relationAttributeSetters = methodStream.filter((m) ->
        m.isAnnotationPresent(RelationAttributeSetter.class)).toArray(Method[]::new);

      for (Method setter : relationAttributeSetters) {
        String attributeName = setter.getAnnotation(RelationAttributeSetter.class).name();
        setterHashMap.put(attributeName, setter);
      }

      StringJoiner joiner = new StringJoiner(",");

      String[] attributeArray = setterHashMap.keySet().toArray(String[]::new);
      for (String attributeName : attributeArray) {
        joiner.add(attributeName);
      }

      String queryAttributes = joiner.toString();
        
      String query = "SELECT " + queryAttributes + " FROM " + relationName + " WHERE " + condition;
      // TODO could additionally take an array of Strings as input to use SQL api's formatting 
      // (replacing ? with those strings)
      PreparedStatement prep = conn.prepareStatement(query);
      ResultSet rs = prep.executeQuery();
      
      
      while (rs.next()) {
        T resultItem = relationClass.getDeclaredConstructor().newInstance();
        int i = 1;
        for (String attr : attributeArray) {
          Method setter = setterHashMap.get(attr);
          Type[] attributeTypes = setter.getGenericParameterTypes();
          for (Type type : attributeTypes) {
            String typeName = type.getTypeName();
            switch (typeName) {
              case "boolean":
              case "java.lang.Boolean":
                setter.invoke(resultItem, rs.getBoolean(i));
                break;
              case "int":
              case "java.Lang.Integer":
                setter.invoke(resultItem, rs.getInt(i));
                break;
              case "float":
              case "java.lang.Float":
                setter.invoke(resultItem, rs.getFloat(i));
                break;
              case "double":
              case "java.lang.Double":
                setter.invoke(resultItem, rs.getDouble(i));
                break;
              case "java.lang.String":
                setter.invoke(resultItem, rs.getString(i));
                break;
              default:
                System.out.println("datatype not in switch statement: " + typeName);
                break;
            }
          }
          i++;
        }
        queryResult.add(resultItem);
      }
      rs.close();
      return queryResult;
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
        | NoSuchMethodException | SecurityException e) {
      throw new BadRelationException("Bad relation: " + relationClass.getName());
    } 
  }

  public <T extends DBRelation> List<T> rawQuery(String query, Class<T> relationClass) throws SQLException, BadRelationException {
    List<T> queryResult = new ArrayList<T>();

    try {
      // TODO consider putting this HashMap in the DBRelation abstract class
      HashMap<String, Method> setterHashMap = new HashMap<String, Method>();
      Method[] relationMethods = relationClass.getDeclaredMethods();
      Stream<Method> methodStream = Arrays.stream(relationMethods);
      Method[] relationAttributeSetters = methodStream.filter((m) ->
        m.isAnnotationPresent(RelationAttributeSetter.class)).toArray(Method[]::new);

      for (Method setter : relationAttributeSetters) {
        String attributeName = setter.getAnnotation(RelationAttributeSetter.class).name();
        setterHashMap.put(attributeName, setter);
      }


        
      PreparedStatement prep = conn.prepareStatement(query);
      ResultSet rs = prep.executeQuery();
      
      List<String> attributeList = new ArrayList<String>(); 
      ResultSetMetaData metadata = prep.getMetaData();
      for (int i = 1; i <= metadata.getColumnCount(); i++) {
        attributeList.add(metadata.getColumnName(i));
      }
      
      while (rs.next()) {
        T resultItem = relationClass.getDeclaredConstructor().newInstance();
        int i = 1;
        for (String attr : attributeList) {
          Method setter = setterHashMap.get(attr);
          Type[] attributeTypes = setter.getGenericParameterTypes();
          for (Type type : attributeTypes) {
            String typeName = type.getTypeName();
            switch (typeName) {
              case "boolean":
              case "java.lang.Boolean":
                setter.invoke(resultItem, rs.getBoolean(i));
                break;
              case "int":
              case "java.Lang.Integer":
                setter.invoke(resultItem, rs.getInt(i));
                break;
              case "float":
              case "java.lang.Float":
                setter.invoke(resultItem, rs.getFloat(i));
                break;
              case "double":
              case "java.lang.Double":
                setter.invoke(resultItem, rs.getDouble(i));
                break;
              case "java.lang.String":
                setter.invoke(resultItem, rs.getString(i));
                break;
              default:
                System.out.println("datatype not in switch statement: " + typeName);
                break;
            }
          }
          i++;
        }
        queryResult.add(resultItem);
      }
      rs.close();
      return queryResult;
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
        | NoSuchMethodException | SecurityException e) {
      throw new BadRelationException("Bad relation: " + relationClass.getName());
    } 
  }

  public <T extends DBRelation> void insert(T item) {
    Class<? extends DBRelation> relationClass = item.getClass();
    HashMap<String, Method> getterHashMap = new HashMap<String, Method>();
    Method[] relationMethods = relationClass.getDeclaredMethods();
    Stream<Method> methodStream = Arrays.stream(relationMethods);
    Method[] relationAttributeSetters = methodStream.filter((m) ->
        m.isAnnotationPresent(RelationAttributeGetter.class)).toArray(Method[]::new);

    for (Method getter : relationAttributeSetters) {
      String attributeName = getter.getAnnotation(RelationAttributeGetter.class).name();
      getterHashMap.put(attributeName, getter);
    }

    String[] attributeArray = getterHashMap.keySet().toArray(String[]::new);
    Method[] valueArray = getterHashMap.values().toArray(Method[]::new);
    /* Above line throws exception because we're trying to store Methods in a 
     * String[] (see line 237, declaration of getterHashMap); if I'm following
     * your code correctly what we should be doing here is invoking the getter,
     * obtaining the result, and parsing it to a String. */
    // update: changed entrySet to values and works, but still suspect
    StringJoiner attributeJoiner = new StringJoiner(",");
    StringJoiner valueJoiner = new StringJoiner(",");

    for (String attributeName : attributeArray) {
      attributeJoiner.add(attributeName);
    }

    for (Method valueGetter : valueArray) {
      try {
        valueJoiner.add(valueGetter.invoke(item).toString());
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    }
// prep = conn.prepareStatement(INSERT INTO + (column attribute names, comma separated) + VALUES + (actual values for
    // attributes, comma separated))
    PreparedStatement prep;

    try {
      prep = conn.prepareStatement(
          "INSERT INTO " + attributeJoiner + " VALUES " + valueJoiner + ";");
      prep.addBatch();
      prep.executeBatch();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  public <T extends DBRelation> void delete(T item) {
    String condition = item.getPrimaryKeyAttribute() +" = " + item.getPrimaryKeyValue();
    PreparedStatement prep;

    try {
      prep = conn.prepareStatement(
          "DELETE FROM " + item.getRelationName() + "WHERE " + condition
      );
      prep.executeUpdate();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

}
