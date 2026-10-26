#!/usr/bin/env sh
# Compiles the project into ./out and starts the Navigator demo.
set -e
cd "$(dirname "$0")"
rm -rf out
javac --release 17 -d out $(find src/main/java -name "*.java")
java -cp out kz.aitu.navigator.Main
