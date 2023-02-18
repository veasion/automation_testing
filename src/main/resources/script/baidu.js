// demo 演示 中国百度百科
// @author luozhuowei

// 百度 "中国"
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
    sleep(200);
}
