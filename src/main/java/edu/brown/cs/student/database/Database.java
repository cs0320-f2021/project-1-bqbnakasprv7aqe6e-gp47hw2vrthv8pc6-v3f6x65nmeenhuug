package edu.brown.cs.student.database;

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
  private static Connection conn = null;
 
  /**
   * Default constructor. Instantiates a connection to the database via Java's 
   * builtin SQL API. 
   * 
   * @param filename path to .sqlite3 file
   * @throws SQLException 
   * @throws ClassNotFoundException
   */
  public Database(String filename) throws SQLException, ClassNotFoundException {
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
        m.isAnnotationPresent(RelationAttribute.class)).toArray(Method[]::new);

      for (Method setter : relationAttributeSetters) {
        String attributeName = setter.getAnnotation(RelationAttribute.class).name();
        setterHashMap.put(attributeName, setter);
      }

      StringJoiner joiner = new StringJoiner(",");

      String[] attributeArray = setterHashMap.keySet().toArray(String[]::new);
      for (String attributeName : attributeArray) {
        joiner.add(attributeName);
      }

      String queryAttributes = joiner.toString();
        
      String query = "SELECT " + queryAttributes + " FROM " + relationName + " WHERE " + condition;
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
      return queryResult;
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
        | NoSuchMethodException | SecurityException e) {
      throw new BadRelationException("Bad relation: " + relationClass.getName());
    } 


  }

}
