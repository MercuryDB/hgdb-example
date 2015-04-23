# Phase 1 of *MercuryDB* "People" example

Updated 2015-04-23

    gradle build
    gradle run

# Overview

This project directory is intended to give a run through some of the basic
features of *MercuryDB* which resemble traditional NoSQL databases. This will
explore and explain the power of the generated `*Table.java` files and bytecode
hooks.

# Features

MercuryDB features we see in this phase are:

* The `@HgValue` and `@HgUpdate` annotations

* Using `@HgValue` on public methods to create a representation of internal
  fields as database table **values**

* Using methods marked `@HgUpdate` to update internal state and indexes

* The `HgDB` class and the `query` and `join` methods, which are the main
  interface to the database logic in *MercuryDB* applications

* The `HgStream` class and creating streams of the rows in a table

* Using the Java 8 Stream API to interact with `HgStream`s

* The `HgRelation` class and comparing fields of objects in the database

* The `TableID` class and creating table aliases for self-joins

* Joins, notably self-joins (we will cover joins between different tables as we
  add more table schema classes in future phases of this tutorial)
