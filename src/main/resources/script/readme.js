// JS 自动化测试 > 函数案例
// @author luozhuowei

// 打开网页
open('http://www.baidu.com');

// 获取当前窗口句柄
let currentHandle = getWindowHandle();
// 打开并切换到新的窗口
openNewWindow();
// 切换下一个窗口
switchToNextWindow();
// 切换窗口
switchToNextWindow(currentHandle);
// 新窗口执行代码
withNewWindow(function () {
    open('http://www.baidu.com');
});

// 点击
click('css=button');
tryClick('css=button');
findOne('css=button').click();

// 获取元素的文本和值
text('css=span');
findOne('css=span').text();
findOne('css=input').value();

// 输入
type('css=input', '123456');
sendKeys('css=input', '123456');

// 随机8位数
randCode(8);

// 查找一个元素
let element = findOne('id=div123');
// 点击元素的父元素的父元素
element.parent().parent().click();
// 获取 element 的子元素
element.childList();
// 获取 element 的左边同胞元素
element.leftSibling();
// 获取 element 的右边同胞元素
element.rightSibling();
// 查找一个 element 下的 span 文字等于xxx的元素
element.findText('span', 'xxx', false);
// 查找所有 element 下的 label 文字等于xxx的元素
element.findTextAll('label', 'xxx', false);
// 获取 outerHtml 源码
element.outerHtml();
// 获取 innerHtml 源码
element.innerHtml();
// 获取元素 xpath
element.xpath();

// 查找多个元素
let elements = find('css=ul li');
// 遍历元素并打印文本
for (let i = 0; i < elements.length; i++) {
    println(elements[i].text());
}

// iframe 中操作
iframe('id=iframe', function () {
    println(findOne('css=body'));
});

// 执行浏览器端脚本
let result = executeScript('return 1+1');
println('1+1 = ' + result);
// 点击查询按钮
executeScriptByParams('arguments[0].click()', [findOne('name=OmsOrderList_Query_query')]);

// 暂停500毫秒
sleep(500);
pause(500);

// 等待页面加载完成
waitForPageLoaded();

// 等待元素出现和消失
waitForElementDisplayed('id=xxx');
waitForElementNotDisplayed('id=xxx');

// 断言, true 通过, false 则抛出异常
assertResult(false, '异常');

// 操作env遍历
// env.putGlobal('key', 'value');
env.put('key', 'value');
let value = env.get('key');
println(env.get('key'));

// log 日志
try {
    log.info('info');
} catch (e) {
    log.error('发生错误', e);
}

// 滚动滚动条
auto.scrollHome(1);
auto.scrollEnd(1);
// 滚动到某元素
scrollToCenter('css=div input');
// table body 滚动
let tableBody = findOne('css=.el-table__body-wrapper');
// 滚动到最右边
auto.scroll(tableBody, null, 0);
// 滚动到最下边
auto.scroll(tableBody, 0, null);
// 滚动到最右下边
auto.scroll(tableBody, null, null);
// 滚动还原
auto.scroll(tableBody, 0, 0);

// 鼠标移动到元素上
mouseOver('css=input');

// 格式化时间
let dateStr = formatDate(new Date(), 'yyyy-MM-dd HH:mm:ss');
println(dateStr);

// 加载common下的通用JS模块
auto.loadCommon('http');

// 上传图片
type("css=input[type='file']", auto.getIcon());

// 提交 bug 到 jira
jira.createIssue('自动化测试异常', '执行xxx发生异常');

// 遍历数组
let array = [{ name: 'name1' }, { name: 'name2' }];
for (let i in array) {
    println(array[i].name);
}
array.forEach(function(item) {
    println(item.name)
});

// 运行新的js文件，命令 > run script/baidu.js
runNewJs(env.getSourcePath('/script/baidu.js'));

// 重新加载并运行当前js文件，命令 > reload
runNewJs(env.getString('filePath'));

// 浏览器截图
screenshot('C:\\Users\\user\\Desktop\\temp.png');

// 写文件
writeText('C:\\Users\\user\\Desktop\\test.txt', 'hello', false);
// 读取本地文本文件
readText('C:\\Users\\user\\Desktop\\test.txt', 'utf-8');
// 读取resource/script/baidu.js目录文件
readText(env.getSourcePath('/script/baidu.js'), 'utf-8');
// 读取网络文本
readText('http://www.baidu.com', 'utf-8');

// translate
env.translate("name: ${USER_NAME}");
env.translate("${$[0].name}", [{name: 'veasion'}]);
env.translate("${list[0]}", {list: [{name: 'veasion1'}]});
env.translate("name: ${USER_NAME}, age: ${user.age}", {user: {age: 13}});
env.translate("name1: ${list[0].name}, name2: ${list[1].name}", {list: [{name: 'veasion1'}, {name: 'veasion2'}]});

// 计算
let calResult = calculate('√(3*3)+4.99+(5.99+6.99)*1.06^2', 2);
//  22.57
println(calResult);

// http
auto.loadCommon('http');
http.get('http://www.baidu.com');
http.post('/api/ouser-web/mobileLogin/login.do', { username: 'superadmin', password: '123456'});
let response = http.request('/oms-web/so/list.do', 'POST');
println('response: ' + response.data);
let ut = http.getCookie('ut');
println('ut: ' + ut);

// 操作数据库
let db = createMysqlConnection('127.0.0.1', 3306, 'user', 'root', '123456');
// db = createJdbcConnection('jdbc:mysql://127.0.0.1:3306/user?useUnicode=true&characterEncoding=utf-8', 'root', '123456');
db.query('select id, user_name, sex from t_user where status = ? limit ?', [1, 10]);
db.queryOnly('select database()', null);
db.insert('insert into t_user(user_name, sex) values (?, ?)', ['veasion', '男']);
db.update('update t_user set user_name = ? where id = ?', ['xxx', 1]);
db.close();

// 手机端触摸操作
let h5Element = findOne('css=li');
h5Element.touch().click();
h5Element.touch().doubleClick();
h5Element.touch().singleTap().scrollByElement(100, 200).perform();

// 依赖数据模块
println(auto.dependency('demo', {name: 'xxx'}));

// ocr
println(image.ocrByElement(findOne('css=.ver-code-img img')).getContent());
// ocr captcha
println(image.captchaByElement(findOne('css=.ver-code-img img')).getContent());

// 元素保存为图片
findOne('id=div-test').saveAsImage('C:\\Users\\user\\Desktop\\abc.png');