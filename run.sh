#!/bin/bash
mkdir -p bin
# Compile java source files
javac -d bin -sourcepath src -cp "lib/*" $(find src -name "*.java")

# Use rsync to copy non-java resources into bin folder
rsync -a --exclude="*.java" src/ bin/

if [ $? -eq 0 ]; then
    echo "Compilation and resource copy successful. Starting the application..."
    java -cp "bin:lib/*" personalfinancemanagement.PersonalFinanceManagement
else
    echo "Compilation failed."
fi
