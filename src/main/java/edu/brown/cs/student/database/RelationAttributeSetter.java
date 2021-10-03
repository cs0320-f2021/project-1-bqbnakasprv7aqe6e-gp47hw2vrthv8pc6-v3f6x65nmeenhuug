package edu.brown.cs.student.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation for setters for relation attributes. To be used with DBRelation 
 * subclasses.
 * 
 * @author lbrito2
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RelationAttributeSetter {
  String name();
}
