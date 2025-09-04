#!/bin/bash

echo "Compiling Escape Room Game..."
javac *.java

if [ $? -ne 0 ]; then
    echo "Compilation failed! Please make sure Java is installed and in your PATH."
    exit 1
fi

echo "Compilation successful! Starting game..."
java Main
