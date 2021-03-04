// 天猫

let data = {
    username: null,
    password: null,
    search: '衣服',
    destArea: '上海',
    userAgent: 'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.135 Safari/537.36'
};

assertResult(data.username != null && data.password != null, "请设置账号密码");

auto.crawler();
if (data.userAgent) {
    toChromeDriver().setUserAgent(data.userAgent);
}
open('https://login.tmall.com/');
// iframe('id=J_loginIframe', function () {});
open(findOne('id=J_loginIframe').attr('src'));
type('id=fm-login-id', data.username);
type('id=fm-login-password', data.password);
sleep(1000);
let slider = findOne('id=baxia-dialog-content');
if (slider && slider.isDisplayed()) {
    // 滑块
    iframe('id=baxia-dialog-content', function () {
        let rect = findOne('id=nocaptcha').getRect();
        let source = findOne('id=nc_1_n1z').touch();
        // source.clickAndHold().moveByOffset(rect.width, 0).release().perform();
        let split = 5;
        source.clickAndHold();
        for (let i = 0; i < split; i++) {
            source.moveByOffset(rect.width / split, 0);
            sleep(100);
        }
        source.release().perform();
    });
    sleep(1000);
}
findText('button', '登录', false).tryClick();
waitForPageLoaded();
// image.loadByScreenshot().show();
type('id=mq', data.search + env.get('KEY_ENTER'));
waitForPageLoaded();
waitForElementDisplayed('id=J_ItemList', 20);
if (data.destArea) {
    click("className=fA-text");
    waitForElementPresent('id=J_FDestArea');
    findOne("id=J_FDestArea").findText('a', data.destArea).tryClick();
    waitForPageLoaded();
    sleep(1000);
}
let itemList = findOne('id=J_ItemList');
let productList = itemList.findDisplayed('css=div.product > div.product-iWrap');
let productData = [];
for (let i in productList) {
    let p = {};
    p.shop = productList[i].findOne('css=.productShop').text();
    p.mainImage = productList[i].findOne('css=.productImg-wrap img').attr('src');
    p.link = productList[i].findOne('css=.productImg-wrap a').attr('href');
    withNewWindow(function () {
        open(p.link);
        waitForPageLoaded();
        let details = findOne('id=J_DetailMeta');
        p.title = details.findOne('css=div.tb-detail-hd > h1').text();
        p.price = details.findOne('css=#J_StrPriceModBox span.tm-price').text();
        p.promoPrice = details.findOne('css=#J_PromoPrice span.tm-price').text();
        p.ext = details.findOne('css=.tm-ind-panel').text();
        p.images = [];
        let imgList = details.findDisplayed('css=#J_UlThumb img');
        for (let j in imgList) {
            p.images.push(imgList[j].attr('src'));
        }
        println(p);
        productData.push(p);
    });
    sleep(200);
}
println(productData.length);
println(productData);
