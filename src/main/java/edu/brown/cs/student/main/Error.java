package edu.brown.cs.student.main;

public class Error {
    public static void replError() {
        System.out.println("ERROR: Invalid input for REPL.");
    } 

    public static void badInputError() {
        System.out.println("ERROR: We couldn't process your input.");
    }
    
    public static void badRelationError(String relationName) {
        System.out.println("Ill-defined relation: " + relationName);
    }

    public static void noSuchRelationError(String relationName) {
        System.out.println("No relation \"" + relationName + "\"found in object-relational mapping.");
    }
}
