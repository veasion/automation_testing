@echo off
::chcp 65001
java -DFile.encoding=utf-8 -Djava.library.path=opencv/dll/x64 -jar automation.jar
rem java -DFile.encoding=utf-8 -Djava.library.path=opencv/dll/x64 -jar automation.jar -debug false -file script/crawler.js
pause

rem �����Զ���
rem -headless ������ʾ�Ժ�̨����ģʽ����
rem -h5 ������ʾ�ֻ�H5ģʽ
rem -file ��ʾָ���ű�ִ��
rem -debug ��ʾdebug��ʽ���У�����ͨ������̨���������
rem ָ���ű�ִ��ʾ��
rem java -DFile.encoding=utf-8 -Djava.library.path=opencv/dll/x64 -jar automation.jar -debug false -file script/crawler.js

