/**
 * 等待页面加载
 *
 * @param {number?} seconds
 * @return void
 */
function waitForPageLoaded(seconds) {
    // 等待页面加载完成
    driver.waitForPageLoaded(seconds);
    // vue页面渲染完成
    waitForElementNotPresent("css=html.nprogress-busy", seconds);
}

/**
 * input标签value设值
 *
 * @param {string} target
 * @param {object} value
 * @param {function?} notExistFunction
 * @return void
 */
function setValue(target, value, notExistFunction) {
    let obj = findOne(target);
    if (obj) {
        value = value || '';
        obj.setValue(value.toString());
    } else if (notExistFunction) {
        notExistFunction();
    }
}
