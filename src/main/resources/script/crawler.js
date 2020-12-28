// demo 演示 爬小说
// @author luozhuowei

let bookName = '斗破苍穹';
// 爬取前5章
let chapterCount = 5;
// 保存到桌面
let savePath = env.getString('DESKTOP_DIR') + '\\斗破苍穹.txt';

log.info('正在下载: ' + bookName);
log.info('保存路径: ' + savePath);

// 起点小说
open('https://www.qidian.com');
// 搜索
sendKeys('id=s-box', bookName);
tryClick('id=search-btn');
switchToNextWindow();
waitForPageLoaded();
sleep(500);

// 点击搜索结果
findOne('css=#result-list').findOne('linkText=' + bookName).tryClick();
switchToNextWindow();
waitForPageLoaded();

// 点击“免费试读”
findText('a', '免费试读', false).tryClick();
waitForPageLoaded();

// 关闭广告弹窗
tryClick('css=a.lbf-panel-close');
sleep(200);

for (let i = 0; i < chapterCount; i++) {
    // 小说主体
    let bookBody = findOne('css=#j_chapterBox div.main-text-wrap');
    // 移除评论
    executeScript("document.querySelectorAll('span.review-count').forEach(item => item.remove());");
    // 小说标题
    let title = bookBody.findOne('css=.j_chapterName').text();
    log.info(title);
    // 小说内容
    let context = bookBody.findOne('css=div.read-content.j_readContent').text().replace(/[\n]/g, '\r\n\r\n');
    // 保存小说
    writeText(savePath, title + '\r\n\r\n' + context + '\r\n\r\n\r\n\r\n', true);
    // 下一章
    let nextPage = findOne('id=j_chapterNext');
    if (nextPage) {
        nextPage.tryClick();
        waitForPageLoaded();
        sleep(200);
    } else {
        break;
    }
}
