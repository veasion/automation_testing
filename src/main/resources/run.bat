@echo off
::chcp 65001
java -DFile.encoding=utf-8 -Djava.library.path=opencv/dll/x64 -jar automation.jar
rem java -DFile.encoding=utf-8 -Djava.library.path=opencv/dll/x64 -jar automation.jar -debug false -file script/crawler.js
pause

rem 运行自动化
rem -headless 参数表示以后台隐身模式运行
rem -h5 参数表示手机H5模式
rem -file 表示指定脚本执行
rem -debug 表示debug方式运行，可以通过控制台进行命令交互
rem 指定脚本执行示例
rem java -DFile.encoding=utf-8 -Djava.library.path=opencv/dll/x64 -jar automation.jar -debug false -file script/crawler.js

