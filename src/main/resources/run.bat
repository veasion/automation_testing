@echo off
::chcp 65001
java -DFile.encoding=utf-8 -Djava.library.path=lib/dll -jar automation.jar -debug true -headless false
rem java -DFile.encoding=utf-8 -Djava.library.path=lib/dll -jar automation.jar -debug false -headless true -file script/crawler.js
pause

rem �����Զ���
rem -file ��ʾָ���ű�ִ��
rem -debug ��ʾdebug��ʽ���У�����ͨ������̨���������
rem -disable-gpu ��ʾ�Ƿ����gpu
rem -headless ��ʾ�Ժ�̨����ģʽ����
rem -userAgent ��ʾ��ָ��ua����
rem ָ���ű�ִ��ʾ��
rem java -DFile.encoding=utf-8 -Djava.library.path=lib/dll -jar automation.jar -debug false -file script/crawler.js

