@echo off
::chcp 65001
java -DFile.encoding=utf-8 -Djava.library.path=lib/dll -jar automation.jar -debug true -headless false
rem java -DFile.encoding=utf-8 -Djava.library.path=lib/dll -jar automation.jar -debug false -headless true -file script/crawler.js
pause

rem 运行自动化
rem -file 表示指定脚本执行
rem -debug 表示debug方式运行，可以通过控制台进行命令交互
rem -disable-gpu 表示是否禁用gpu
rem -headless 表示以后台隐身模式运行
rem -userAgent 表示以指定ua运行
rem 指定脚本执行示例
rem java -DFile.encoding=utf-8 -Djava.library.path=lib/dll -jar automation.jar -debug false -file script/crawler.js

