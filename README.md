# Design 
The centerpiece of the database API is the `Database` class. Under the hood, 
this class uses Java's builtin SQL API to make queries to the database specified 
in the constructor. Each relation in the given database must be represented 
by an implementation of the `DBRelation` abstract class; this is in order to 
enforce an object-oriented approach where each tuple (row) of a relation (table) 
is represented by an instance of the class representing that tuple. New relation
representations may be added with the `addRelation` method.

Implementations of the `DBRelation` class must implement a `relationName` method 
which returns the name of the relation within the database this `DBRelation` 
represents. This is to enforce a non-null identifier which will be used to map 
the `DBRelation` object to the correct relation upon invoking `addRelation`. 
`DBRelation` subclasses should also have fields corresponding to each attribute
of that relation and, more importantly, _setters for those fields annotated with_
`@RelationAttribute(name=<attribute_name>)` where `attribute_name` is the 
name of the attribute that setter will obtain from queries as it appears in the 
database.


# README
To build use:
`mvn package`

To run use:
`./run`

To start the server use:
`./run --gui [--port=<port>]`
