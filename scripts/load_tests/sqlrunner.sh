#!/usr/bin/env bash

# Author: Leon Föckersperger

set -e

cd sqlrunner
javac sqlrunner/SQLRunner.java
java -cp ".:../lib/postgresql-42.5.0.jar" sqlrunner.SQLRunner "$1" "$2" "$3" "$4"
cd ..

