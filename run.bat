@echo off
title Sistema de Vendas
if not exist bin mkdir bin
javac -d bin src\*.java
java -cp bin Main