@echo off
::chcp 65001
java -DFile.encoding=utf-8 -Djava.library.path=opencv/dll/x64 -jar automation.jar
pause