# Project 1: Sprint (part 1)
By Lucas Brito `lbrito2`, Anna Swanson `aswanso2`, Damon Burdell `dburdell`. 

# Design 
`Main.java` contains the REPL implementation, including the read and print 
architecture and delegating the computational work to `MathBot.java` and 
`CSVParser.java`. 

## Database Class
The centerpiece of the database API is the `Database` class. Under the hood, 
this class uses Java's builtin SQL API to make queries to the database specified 
in the constructor. Each relation in the given database must be represented 
by an implementation of the `DBRelation` abstract class; this is in order to 
enforce an object-oriented approach where each tuple (row) of a relation (table) 
is represented by an instance of the class representing that tuple. New relation
representations may be added with the `addRelation` method.

The database object supports `where` queries, insertions, deletions, updates, 
and "raw" SQL queries (`rawQuery`). Insertions, deletions, and updates do not 
require the user specify the relation that will be updated; this is handled 
internally using `DBRelation`'s `relationName` field. 

## DBRelation Abstract Class
Implementations of the `DBRelation` class must implement a `relationName` method 
which returns the name of the relation within the database this `DBRelation` 
represents. This is to enforce a non-null identifier which will be used to map 
the `DBRelation` object to the correct relation upon invoking `addRelation`. 
`DBRelation` subclasses should also have fields corresponding to each attribute
of that relation and, more importantly, setters and getters for that relation 
attribute annotated with `@RelationAttributeSetter(name=<attribute_name>)` and 
`@RelationAttributeGetter(name=<attribute_name>)` respectively where
`attribute_name` is the name of the attribute that setter will obtain from
queries as it appears in the database.

The Database API is designed so that the `DBRelation` representation of a 
database's relation need not include every attribute that actually appears in 
that relation---queries are generated such that the `SELECT` statement only 
includes attributes denoted by annotated setter methods (see above). 

## K-d Tree Implementation 
The program's `similar` command utilizes an efficient K-d Tree nearest neighbor 
search to produce similar users. The present K-d Tree implementation uses 
`BinaryNode` objects; each node contains a key and value pair, where the value 
is necessarily an array of doubles. The philosophy behind this design is that 
the nearest neighbor search algorithm requires an Euclidean metric, but 
usage cases might require metadata be stored with each point.  

## MathBot Class
`MathBot.java` contains implementations of addition, subtraction, 
k nearest-neighbors, and Euclidean distance. `distance` takes
arbitrary-dimension vectors for generality. `naive_neighbors` currently takes 
in an `ArrayList` of strings as the datatype for the data from which the nearest
neighbors will be determined for this is the datatype produced by `CSVParser`;
additionally, the method has been abstracted to take as input a user-specified
sorting `Comparator` which will be used to define "nearness."

## CSVParser Class
`CSVParser.java` is constructed without arguments, and uses a `read()` method 
to, using the built-in `File` object, read a specified path. The class does not 
take the path as an argument to the constructor in order to allow the user to 
use the same instance to read multiple `.csv` files. Upon calling `read()`, the 
instance will use `Collections.clear()` to dump the parsed data. The class 
uses `ArrayList` for flexibility (especially in size of array when actually 
parsing the `.csv` file). The object does not parse column data types, leaving 
datatype decisions up to the user. 

# Tests 
System testing ensures that the REPL appropriately handles: 
- Errors (`error.test`)
- Addition functionality (`adding.test`)
- Subtraction functionality (`subtraction.test`)
- Multiple commands (`multiplecommands.test`)
- No-neighbor result (`0-neighbors.test`)
- One-star result (`1-star.test`)
- Bad filenames (`no_stars_file.test`)
- Coordinates coinciding precisely with existing star (`coords_on_star.test`)
- Extra spaces in commands (`double_space.test`)
- Exclusion of named star in stars command with `ProperName` (`exclude_star/test`)
- Bad commands (`incorrect_command.test`)
- Spaces in arguments (`name_with_spaces.test`)
- Numerical arguments in quotes (`number_with_quotes.test`)
- Dump data upon re-parsing new CSV (`reload_data.test`)

## MathBot
Unit testing of `MathBot` ensures addition, subtraction, and nearest-neighbors 
implementations handle large and small numbers well, ensurs that nearest
neighbors produces a random sample of equally-distant objects, distance between 
vectors with unequal dimensions, k values larger than the dataset, and 
non-Euclidean metrics given to the nearest-neighbors implementation. 

## CSVParser
Unit testing of `CSVParser` ensures that its index-finder functionality 
appropriately handles nonexistent column names, that `read()` throws the 
appropriate exception given a bad filename, that a `CSVParser` that has not 
parsed a `.csv` is still capable of returning (empty) values for rows (see above 
for column case), that instances dump parsed `.csv`s upon calling `read()`, and
that getters work properly. 

## CommandHandler
Unit testing of `CommandHandler` ensures that command are parsed properly, that 
commands are capable of mutating data outside their scope, and that adding 
duplicate commands does not throw any errors. 

## KDTree
Unit testing of `KDTree` ensures proper construction of tree, a functional 
nearest neighbors algorithm, proper construction given empty data. 

## BinaryNode 
Unit testing of `BinaryNode` ensures the implementation properly handles 
construction given empty nodes. 

## Error
`Error` has not been tested due to trivial functionality. 

## KVPair 
`KVPair` has not been tested due to trivial functionality (there are only 
getter and setter methods).

# Usage 
To build use:
`mvn package`

To run use:
`./run`

To start the server use:
`./run --gui [--port=<port>]`

To test addition and subtraction and basic REPL functionality: 
`./cs32-test src/test/system/*.test`

To test stars-related commands: 
`./cs32-test src/test/system/stars/*.test`

To test users-related commands: 
`./cs32-test src/test/system.users/*.test`

To run all unit tests: 
`mvn test`