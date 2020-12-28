# 自动化测试框架
## 简介

web 浏览器端自动化测试框架，通过 js 脚本执行自动化测试。

可用于web自动化测试 、爬虫、抢票等各种web端模拟操作。

同时有 chrome 插件支持元素定位。

API文档：https://veasion.github.io/automationjs-docs/



## 自动化测试示例

```js
// 百度搜索“中国”并打印搜索结果
open("https://www.baidu.com");
sendKeys('id=kw', '中国');
click("css=input[value='百度一下']");
waitForPageLoaded();
let list = findDisplayed('css=div#content_left > div');
for (let i in list) {
    println(list[i].text());
}
```



```js
// 百度搜索“中国”并进入百度百科
baiduSearch('中国');
// 获取搜索结果
let list = findDisplayed('css=div#content_left > div');
// 变量搜索结果
for (let i in list) {
    let element = list[i].findOne("css=h3 > a");
    // 判断结果是否为百度百科
    if (element && element.text().endsWith("百度百科")) {
        // 点击
        element.click();
        // 等待页面加载
        waitForPageLoaded(10);
        // 切换到新打开的窗口
        switchToNextWindow();
        break;
    }
}

function baiduSearch(str) {
    open("https://www.baidu.com");
    sendKeys('id=kw', str);
    click("css=input[value='百度一下']");
    waitForPageLoaded(5);
}
```



## 赞助

项目的发展离不开你的支持，请作者喝杯咖啡吧！

ps：辣条也行 ☕！

![支付宝](https://veasion.oss-cn-shanghai.aliyuncs.com/alipay.png?x-oss-process=image/resize,m_lfit,h_360,w_360)

