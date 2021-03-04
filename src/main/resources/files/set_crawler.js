// 反爬
// 修改 chromedriver, 搜索 $cdc_asdjflasutopfhvcZLmcfl_ 替换成 $abc_asdjflasutopfhvcZLmcfl_
try {
    window.navigator.chrome = undefined;
    Object.defineProperty(navigator, 'languages', {get: () => ['zh-CN', 'zh', 'en']});
    // Object.defineProperty(navigator, 'plugins', {get: () => [1, 2, 3, 4, 5, 6]});
    Object.defineProperties(navigator, {webdriver: {get: () => undefined}});
} catch (e) {
    log.error('修改navigator失败', e);
}
// open('https://bot.sannysoft.com/');
