// image 爬虫
// @author luozhuowei

let imageKey = null;
const imageMap = {};
let path = env.getString('DESKTOP_DIR') + '\\images\\';

toChromeDriver().activateDevTools();
toChromeDriver().addRequestHandler(function(request) {
    return request && /\.(jpg|png|jpeg|gif)(\?.*)?$/.test(request.uri);
}, function(request) {
    if (imageKey) {
        imageMap[imageKey] = imageMap[imageKey] || []
        imageMap[imageKey].push(request.uri);
        println(imageKey + '监听图片: ' + request.uri);
    }
});

open('https://www.juemei.com/');
findOne('css=div.search > input').setValue('女神');
imageKey = 'image';
findOne('css=div.search > button').tryClick();
sleep(200);
switchToNextWindow();
waitForPageLoaded();

toChromeDriver().activateDevTools();
executeScript('location.reload();');
for (let i in imageMap['image']) {
    download(imageMap['image'][i], path);
}
