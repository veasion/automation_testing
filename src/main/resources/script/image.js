// image 爬虫
// @author luozhuowei

const images = [];
let path = env.getString('DESKTOP_DIR') + '\\images\\';

// 防反爬
auto.crawler();

toChromeDriver().activateDevTools(null);
toChromeDriver().addRequestHandler(function(request) {
    return request && /\.(jpg|png|jpeg|gif)(\?.*)?$/.test(request.uri);
}, function(request) {
    images.push(request.uri);
    println('图片: ' + request.uri);
});

openNotWait('https://www.juemei.com/');
findOne('css=div.search > input').setValue('女神');
findOne('css=div.search > button').tryClick();
sleep(200);
let windowHandle = switchToNextWindow();
waitForPageLoaded();

toChromeDriver().activateDevTools(windowHandle);
executeScript('location.reload();');
for (let i in images) {
    download(images[i], path);
}
