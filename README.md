# HgDB interactive examples

[![Join the chat at https://gitter.im/MercuryDB/hgdb-example](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/MercuryDB/hgdb-example?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

This project contains interactive examples of MercuryDB projects intended to
help walk new users through the features of MercuryDB.

For basic information about the files in this repo, see the READMEs associated
with each project directory.

For the most detailed information about this tutorial, see the wiki.

The `build.gradle.template` is a gradle build template that is used in each of the phases of the project.
It can be used as a good template for any other projects as well, as it separates the application, schema, and
database packages of the source code for the bootstrap process of MercuryDB to operate in an elegant way.

If the template is updated, run `gradle pushBuildTemplate` from the root of the project to update the build
files for each of the phases.
