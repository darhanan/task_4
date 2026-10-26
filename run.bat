@echo off
rem Compiles the project into .\out and starts the Navigator demo.
cd /d "%~dp0"
if exist out rmdir /s /q out
dir /s /b src\main\java\*.java > sources.txt
javac --release 17 -d out @sources.txt
del sources.txt
java -cp out kz.aitu.navigator.Main
