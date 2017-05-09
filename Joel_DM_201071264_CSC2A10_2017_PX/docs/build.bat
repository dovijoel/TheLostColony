@echo off
cls
echo Setup JDK bin path before this script
echo Build script set to run in Project folder like eclipse
cd ..
set PRAC_BIN=./bin
set PRAC_DOCS=./docs
set PRAC_LIB=./lib/*
set PRAC_SRC=./src
echo *** Compiling ***
javac -sourcepath %PRAC_SRC% -cp "%PRAC_BIN%;%PRAC_LIB%" -d %PRAC_BIN% %PRAC_SRC%/Main.java

echo *** Building JavaDoc ***
javadoc -sourcepath %PRAC_SRC% -classpath %PRAC_BIN%;%PRAC_LIB% -use -author -d %PRAC_DOCS%\JavaDocs -subpackages .

echo *** Running application ***
java -cp %PRAC_BIN%;%PRAC_LIB% Main


echo *** Completed ***
cd %PRAC_DOCS%
pause