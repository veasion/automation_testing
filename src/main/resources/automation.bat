@echo off
::chcp 65001
java -DFile.encoding=utf-8 -Djava.library.path=..\..\..\opencv\x64 -jar automation.jar
pause