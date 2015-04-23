Phase 1 of Gradle "People" example
Updated 2015-04-23

Build with:
    gradle build

Run with:
    gradle run


This project directory is intended to give a run through some of the basic
features of MercuryDB which resemble traditional NoSQL databases. This will
explore and explain the power of the generated *Table.java files and bytecode
hooks.

MercuryDB features we see in this phase are:

* @HgValue annotation

* Using @HgValue and public methods to create a representation of internal
fields as database table "values"

* HgStream class and creating streams of the rows in a table

* HgRelation class 

* TableID class and 
