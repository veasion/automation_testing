function load(url) {}
function loadWithNewGlobal(url) {}

/**
 * 单词
 */
function Words() {
    /**
     * getLocation<br>
     * <code>cn.veasion.auto.captcha.ocr.OcrResult$Words#getLocation</code>
     * @return {Location}
     */
    this.getLocation = function() { }
    /**
     * getWords<br>
     * <code>cn.veasion.auto.captcha.ocr.OcrResult$Words#getWords</code>
     * @return {string}
     */
    this.getWords = function() { }
}

/**
 * 元素
 */
function WebElementBinding() {
    /**
     * 父元素<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#parent</code>
     * @return {WebElementBinding}
     */
    this.parent = function() { }
    /**
     * 发送文字/模拟按键<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#type</code>
     * @param {object} key
     * @return {WebElementBinding}
     */
    this.type = function(key) { }
    /**
     * 获取值<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#value</code>
     * @return {string}
     */
    this.value = function() { }
    /**
     * 清空值<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#clear</code>
     * @return {WebElementBinding}
     */
    this.clear = function() { }
    /**
     * 设值<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#setValue</code>
     * @param {string} text
     * @return {WebElementBinding}
     */
    this.setValue = function(text) { }
    /**
     * 获取属性值<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#attr</code>
     * @param {string} attr
     * @return {object}
     */
    this.attr = function(attr) { }
    /**
     * 选择下拉框<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#select</code>
     * @param {string} label
     * @param {object} value
     * @return {WebElementBinding}
     */
    this.select = function(label, value) { }
    /**
     * 查找元素包含某文字的节点<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#findText</code>
     * @param {string} tagName
     * @param {string} text
     * @return {WebElementBinding}
     */
    this.findText = function(tagName, text) { }
    /**
     * 点击<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#click</code>
     * @return {WebElementBinding}
     */
    this.click = function() { }
    /**
     * 发送文字/模拟按键<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#sendKeys</code>
     * @param {object} key
     * @return {WebElementBinding}
     */
    this.sendKeys = function(key) { }
    /**
     * 通过label选择下拉框<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#selectByLabel</code>
     * @param {object} value
     * @return {WebElementBinding}
     */
    this.selectByLabel = function(value) { }
    /**
     * 通过value选择下拉框<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#selectByValue</code>
     * @param {object} value
     * @return {WebElementBinding}
     */
    this.selectByValue = function(value) { }
    /**
     * 点击这个元素区域<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#tryClick</code>
     * @return {WebElementBinding}
     */
    this.tryClick = function() { }
    /**
     * xpath<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#xpath</code>
     * @return {string}
     */
    this.xpath = function() { }
    /**
     * tagName<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#tagName</code>
     * @return {string}
     */
    this.tagName = function() { }
    /**
     * 获取文本内容<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#text</code>
     * @return {string}
     */
    this.text = function() { }
    /**
     * 是否可见<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#isDisplayed</code>
     * @return {boolean}
     */
    this.isDisplayed = function() { }
    /**
     * 父元素<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#parentByTag</code>
     * @param {string} tagName
     * @return {WebElementBinding}
     */
    this.parentByTag = function(tagName) { }
    /**
     * 右边兄弟节点集合<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#rightSibling</code>
     * @return {WebElementBinding[]}
     */
    this.rightSibling = function() { }
    /**
     * innerHTML<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#innerHtml</code>
     * @return {string}
     */
    this.innerHtml = function() { }
    /**
     * outerHTML<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#outerHtml</code>
     * @return {string}
     */
    this.outerHtml = function() { }
    /**
     * 左边兄弟节点集合<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#leftSibling</code>
     * @return {WebElementBinding[]}
     */
    this.leftSibling = function() { }
    /**
     * 触摸操作<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#touch</code>
     * @return {TouchActionsBinding}
     */
    this.touch = function() { }
    /**
     * 页面上突出显示<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#show</code>
     * @return {WebElementBinding}
     */
    this.show = function() { }
    /**
     * 子元素集合<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#childList</code>
     * @return {WebElementBinding[]}
     */
    this.childList = function() { }
    /**
     * 向目标元素发送文字/模拟按键<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#type</code>
     * @param {string} target
     * @param {object} key
     * @return {WebElementBinding}
     */
    this.type = function(target, key) { }
    /**
     * 查找多个元素<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#find</code>
     * @param {string} target
     * @return {WebElementBinding[]}
     */
    this.find = function(target) { }
    /**
     * 获取目标元素属性值<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#attr</code>
     * @param {string} target
     * @param {string} attr
     * @return {WebElementBinding}
     */
    this.attr = function(target, attr) { }
    /**
     * 选择下拉框<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#select</code>
     * @param {string} target
     * @param {string} label
     * @param {object} value
     * @return {WebElementBinding}
     */
    this.select = function(target, label, value) { }
    /**
     * 等待元素显示（可见）<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#waitForElementDisplayed</code>
     * @param {string} target
     * @param {number?} seconds
     * @return {WebElementBinding}
     */
    this.waitForElementDisplayed = function(target, seconds) { }
    /**
     * 等待元素消失<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#waitForElementNotPresent</code>
     * @param {string} target
     * @param {number?} seconds
     * @return {WebElementBinding}
     */
    this.waitForElementNotPresent = function(target, seconds) { }
    /**
     * 等待元素隐藏（不可见）<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#waitForElementNotDisplayed</code>
     * @param {string} target
     * @param {number?} seconds
     * @return {WebElementBinding}
     */
    this.waitForElementNotDisplayed = function(target, seconds) { }
    /**
     * 等待元素出现<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#waitForElementPresent</code>
     * @param {string} target
     * @param {number?} seconds
     * @return {WebElementBinding}
     */
    this.waitForElementPresent = function(target, seconds) { }
    /**
     * 查找多个可见元素<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#findDisplayed</code>
     * @param {string} target
     * @return {WebElementBinding[]}
     */
    this.findDisplayed = function(target) { }
    /**
     * 查找元素包含某文字的节点<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#findText</code>
     * @param {string} tagName
     * @param {string} text
     * @param {boolean} fuzzyMatches 是否模糊匹配
     * @return {WebElementBinding}
     */
    this.findText = function(tagName, text, fuzzyMatches) { }
    /**
     * 查找元素包含某文字的节点<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#findTextAll</code>
     * @param {string} tagName
     * @param {string} text
     * @param {boolean} fuzzyMatches 是否模糊匹配
     * @return {WebElementBinding[]}
     */
    this.findTextAll = function(tagName, text, fuzzyMatches) { }
    /**
     * 查找一个元素<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#findOne</code>
     * @param {string} target
     * @return {WebElementBinding}
     */
    this.findOne = function(target) { }
    /**
     * 查找一个元素并点击<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#click</code>
     * @param {string} target
     * @return {WebElementBinding}
     */
    this.click = function(target) { }
    /**
     * 查找多个元素<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#findList</code>
     * @param {string[]} targets
     * @return {WebElementBinding[]}
     */
    this.findList = function(targets) { }
    /**
     * 鼠标移动到目标元素<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#mouseOver</code>
     * @param {string} target
     * @return {WebElementBinding}
     */
    this.mouseOver = function(target) { }
    /**
     * 滚动到目标元素<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#scrollToCenter</code>
     * @param {string|null} target
     * @return {WebElementBinding}
     */
    this.scrollToCenter = function(target) { }
    /**
     * 向目标元素发送文字/模拟按键<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#sendKeys</code>
     * @param {string} target
     * @param {object} key
     * @return {WebElementBinding}
     */
    this.sendKeys = function(target, key) { }
    /**
     * 通过label选择下拉框<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#selectByLabel</code>
     * @param {string} target
     * @param {object} value
     * @return {WebElementBinding}
     */
    this.selectByLabel = function(target, value) { }
    /**
     * 通过value选择下拉框<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#selectByValue</code>
     * @param {string} target
     * @param {object} value
     * @return {WebElementBinding}
     */
    this.selectByValue = function(target, value) { }
    /**
     * 查找一个元素并点击<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#tryClick</code>
     * @param {string} target
     * @return {WebElementBinding}
     */
    this.tryClick = function(target) { }
    /**
     * 获取目标元素文本内容<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#text</code>
     * @param {string} target
     * @return {string}
     */
    this.text = function(target) { }
}

/**
 * 图片
 */
function Image() {
    /**
     * 根据图片链接OCR识别<br>
     * <code>cn.veasion.auto.bind.bean.Image#ocr</code>
     * @param {string} imgUrl
     * @return {OcrResult}
     */
    this.ocr = function(imgUrl) { }
    /**
     * 根据元素OCR识别验证码<br>
     * <code>cn.veasion.auto.bind.bean.Image#captchaByElement</code>
     * @param {WebElementBinding} element
     * @return {OcrResult}
     */
    this.captchaByElement = function(element) { }
    /**
     * 根据元素OCR识别<br>
     * <code>cn.veasion.auto.bind.bean.Image#ocrByElement</code>
     * @param {WebElementBinding} element
     * @return {OcrResult}
     */
    this.ocrByElement = function(element) { }
    /**
     * 根据图片链接OCR识别验证码<br>
     * <code>cn.veasion.auto.bind.bean.Image#captcha</code>
     * @param {string} imgUrl
     * @return {OcrResult}
     */
    this.captcha = function(imgUrl) { }
}

const image = new Image();

/**
 * 位置
 */
function Location() {
    /**
     * getLeft<br>
     * <code>cn.veasion.auto.captcha.ocr.OcrResult$Location#getLeft</code>
     * @return {number}
     */
    this.getLeft = function() { }
    /**
     * getWidth<br>
     * <code>cn.veasion.auto.captcha.ocr.OcrResult$Location#getWidth</code>
     * @return {number}
     */
    this.getWidth = function() { }
    /**
     * getTop<br>
     * <code>cn.veasion.auto.captcha.ocr.OcrResult$Location#getTop</code>
     * @return {number}
     */
    this.getTop = function() { }
    /**
     * getHeight<br>
     * <code>cn.veasion.auto.captcha.ocr.OcrResult$Location#getHeight</code>
     * @return {number}
     */
    this.getHeight = function() { }
}

/**
 * 控制台打印<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#println</code>
 * @param {object} message
 * @param {array?} args
 * @return void
 */
function println(message, args) { }
/**
 * 暂停多少毫秒<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#sleep</code>
 * @param {number} millis
 * @return void
 */
function sleep(millis) { }
/**
 * 打开页面并等待页面加载<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#open</code>
 * @param {string} url
 * @return void
 */
function open(url) { }
/**
 * info<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#info</code>
 * @return {string}
 */
function info() { }
/**
 * 数据库连接<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#createJdbcConnection</code>
 * @param {string} jdbcUrl
 * @param {string} user
 * @param {string} password
 * @return {JdbcConnectionBinding}
 */
function createJdbcConnection(jdbcUrl, user, password) { }
/**
 * 等待页面加载<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#waitForPageLoaded</code>
 * @param {number?} seconds
 * @return void
 */
function waitForPageLoaded(seconds) { }
/**
 * 切换窗口<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#switchToNextWindow</code>
 * @param {string?} windowHandle 指定窗口句柄，为 null 则切换为下一个窗口
 * @return void
 */
function switchToNextWindow(windowHandle) { }
/**
 * 向浏览器驱动执行 js 代码<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#executeScriptByParams</code>
 * @param {string} jsCode
 * @param {array?} args
 * @return {object}
 */
function executeScriptByParams(jsCode, args) { }
/**
 * Mysql数据库连接<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#createMysqlConnection</code>
 * @param {string} ip
 * @param {number} port
 * @param {string} database
 * @param {string} user
 * @param {string} password
 * @return {JdbcConnectionBinding}
 */
function createMysqlConnection(ip, port, database, user, password) { }
/**
 * 随机字符串<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#randCode</code>
 * @param {number} length
 * @return {string}
 */
function randCode(length) { }
/**
 * 运行新的js<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#runNewJs</code>
 * @param {string} path
 * @return void
 */
function runNewJs(path) { }
/**
 * 向浏览器驱动执行 js 代码<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#executeScript</code>
 * @param {string} jsCode
 * @return {object}
 */
function executeScript(jsCode) { }
/**
 * 截图<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#screenshot</code>
 * @param {string} path
 * @return {boolean}
 */
function screenshot(path) { }
/**
 * 打开并切换到新的窗口<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#openNewWindow</code>
 * @return void
 */
function openNewWindow() { }
/**
 * 暂停多少毫秒<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#pause</code>
 * @param {number} millis
 * @return void
 */
function pause(millis) { }
/**
 * 读取文本<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#readText</code>
 * @param {string} pathOrUrl
 * @param {string?} charsetName
 * @return {string}
 */
function readText(pathOrUrl, charsetName) { }
/**
 * 在新的窗口中执行函数<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#withNewWindow</code>
 * @param {Function} fun 函数
 * @return void
 */
function withNewWindow(fun) { }
/**
 * 计算<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#calculate</code>
 * @param {string} str 运算式
 * @param {number} n 保留几位小数
 * @return {string}
 */
function calculate(str, n) { }
/**
 * 格式化时间<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#formatDate</code>
 * @param {object} date
 * @param {string} pattern
 * @return {string}
 */
function formatDate(date, pattern) { }
/**
 * iframe<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#iframe</code>
 * @param {string} target
 * @param {Function} fun 函数
 * @return void
 */
function iframe(target, fun) { }
/**
 * 断言<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#assertResult</code>
 * @param {boolean} flag
 * @param {object} message
 * @return void
 */
function assertResult(flag, message) { }
/**
 * 获取当前窗口句柄<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#getWindowHandle</code>
 * @return {string}
 */
function getWindowHandle() { }
/**
 * 写文本文件<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#writeText</code>
 * @param {string} path
 * @param {string} context
 * @param {boolean} append
 * @param {string?} charsetName
 * @return void
 */
function writeText(path, context, append, charsetName) { }
/**
 * HTTP 请求<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#request</code>
 * @param {string|null} url 请求url/uri
 * @param {string?} method 请求方式 POST/GET 默认GET
 * @param {object?} content 请求body内容
 * @param {object?} headers 请求头
 * @return {object}
 */
function request(url, method, content, headers) { }
/**
 * 向目标元素发送文字/模拟按键<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#type</code>
 * @param {string} target
 * @param {object} key
 * @return {WebDriverBinding}
 */
function type(target, key) { }
/**
 * 查找多个元素<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#find</code>
 * @param {string} target
 * @return {WebElementBinding[]}
 */
function find(target) { }
/**
 * 获取目标元素属性值<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#attr</code>
 * @param {string} target
 * @param {string} attr
 * @return {WebDriverBinding}
 */
function attr(target, attr) { }
/**
 * 选择下拉框<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#select</code>
 * @param {string} target
 * @param {string} label
 * @param {object} value
 * @return {WebDriverBinding}
 */
function select(target, label, value) { }
/**
 * 等待元素显示（可见）<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#waitForElementDisplayed</code>
 * @param {string} target
 * @param {number?} seconds
 * @return {WebDriverBinding}
 */
function waitForElementDisplayed(target, seconds) { }
/**
 * 等待元素消失<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#waitForElementNotPresent</code>
 * @param {string} target
 * @param {number?} seconds
 * @return {WebDriverBinding}
 */
function waitForElementNotPresent(target, seconds) { }
/**
 * 等待元素隐藏（不可见）<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#waitForElementNotDisplayed</code>
 * @param {string} target
 * @param {number?} seconds
 * @return {WebDriverBinding}
 */
function waitForElementNotDisplayed(target, seconds) { }
/**
 * 等待元素出现<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#waitForElementPresent</code>
 * @param {string} target
 * @param {number?} seconds
 * @return {WebDriverBinding}
 */
function waitForElementPresent(target, seconds) { }
/**
 * 查找多个可见元素<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#findDisplayed</code>
 * @param {string} target
 * @return {WebElementBinding[]}
 */
function findDisplayed(target) { }
/**
 * 查找元素包含某文字的节点<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#findText</code>
 * @param {string} tagName
 * @param {string} text
 * @param {boolean} fuzzyMatches 是否模糊匹配
 * @return {WebElementBinding}
 */
function findText(tagName, text, fuzzyMatches) { }
/**
 * 查找元素包含某文字的节点<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#findTextAll</code>
 * @param {string} tagName
 * @param {string} text
 * @param {boolean} fuzzyMatches 是否模糊匹配
 * @return {WebElementBinding[]}
 */
function findTextAll(tagName, text, fuzzyMatches) { }
/**
 * 查找一个元素<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#findOne</code>
 * @param {string} target
 * @return {WebElementBinding}
 */
function findOne(target) { }
/**
 * 查找一个元素并点击<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#click</code>
 * @param {string} target
 * @return {WebDriverBinding}
 */
function click(target) { }
/**
 * 查找多个元素<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#findList</code>
 * @param {string[]} targets
 * @return {WebElementBinding[]}
 */
function findList(targets) { }
/**
 * 鼠标移动到目标元素<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#mouseOver</code>
 * @param {string} target
 * @return {WebDriverBinding}
 */
function mouseOver(target) { }
/**
 * 滚动到目标元素<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#scrollToCenter</code>
 * @param {string|null} target
 * @return {WebDriverBinding}
 */
function scrollToCenter(target) { }
/**
 * 向目标元素发送文字/模拟按键<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#sendKeys</code>
 * @param {string} target
 * @param {object} key
 * @return {WebDriverBinding}
 */
function sendKeys(target, key) { }
/**
 * 通过label选择下拉框<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#selectByLabel</code>
 * @param {string} target
 * @param {object} value
 * @return {WebDriverBinding}
 */
function selectByLabel(target, value) { }
/**
 * 通过value选择下拉框<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#selectByValue</code>
 * @param {string} target
 * @param {object} value
 * @return {WebDriverBinding}
 */
function selectByValue(target, value) { }
/**
 * 查找一个元素并点击<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#tryClick</code>
 * @param {string} target
 * @return {WebDriverBinding}
 */
function tryClick(target) { }
/**
 * 获取目标元素文本内容<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#text</code>
 * @param {string} target
 * @return {string}
 */
function text(target) { }

function WebDriverBinding() {
    /**
     * 控制台打印<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#println</code>
     * @param {object} message
     * @param {array?} args
     * @return void
     */
    this.println = function(message, args) { }
    /**
     * 暂停多少毫秒<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#sleep</code>
     * @param {number} millis
     * @return void
     */
    this.sleep = function(millis) { }
    /**
     * 打开页面并等待页面加载<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#open</code>
     * @param {string} url
     * @return void
     */
    this.open = function(url) { }
    /**
     * info<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#info</code>
     * @return {string}
     */
    this.info = function() { }
    /**
     * 数据库连接<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#createJdbcConnection</code>
     * @param {string} jdbcUrl
     * @param {string} user
     * @param {string} password
     * @return {JdbcConnectionBinding}
     */
    this.createJdbcConnection = function(jdbcUrl, user, password) { }
    /**
     * 等待页面加载<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#waitForPageLoaded</code>
     * @param {number?} seconds
     * @return void
     */
    this.waitForPageLoaded = function(seconds) { }
    /**
     * 切换窗口<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#switchToNextWindow</code>
     * @param {string?} windowHandle 指定窗口句柄，为 null 则切换为下一个窗口
     * @return void
     */
    this.switchToNextWindow = function(windowHandle) { }
    /**
     * 向浏览器驱动执行 js 代码<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#executeScriptByParams</code>
     * @param {string} jsCode
     * @param {array?} args
     * @return {object}
     */
    this.executeScriptByParams = function(jsCode, args) { }
    /**
     * Mysql数据库连接<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#createMysqlConnection</code>
     * @param {string} ip
     * @param {number} port
     * @param {string} database
     * @param {string} user
     * @param {string} password
     * @return {JdbcConnectionBinding}
     */
    this.createMysqlConnection = function(ip, port, database, user, password) { }
    /**
     * 随机字符串<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#randCode</code>
     * @param {number} length
     * @return {string}
     */
    this.randCode = function(length) { }
    /**
     * 运行新的js<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#runNewJs</code>
     * @param {string} path
     * @return void
     */
    this.runNewJs = function(path) { }
    /**
     * 向浏览器驱动执行 js 代码<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#executeScript</code>
     * @param {string} jsCode
     * @return {object}
     */
    this.executeScript = function(jsCode) { }
    /**
     * 截图<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#screenshot</code>
     * @param {string} path
     * @return {boolean}
     */
    this.screenshot = function(path) { }
    /**
     * 打开并切换到新的窗口<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#openNewWindow</code>
     * @return void
     */
    this.openNewWindow = function() { }
    /**
     * 暂停多少毫秒<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#pause</code>
     * @param {number} millis
     * @return void
     */
    this.pause = function(millis) { }
    /**
     * 读取文本<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#readText</code>
     * @param {string} pathOrUrl
     * @param {string?} charsetName
     * @return {string}
     */
    this.readText = function(pathOrUrl, charsetName) { }
    /**
     * 在新的窗口中执行函数<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#withNewWindow</code>
     * @param {Function} fun 函数
     * @return void
     */
    this.withNewWindow = function(fun) { }
    /**
     * 计算<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#calculate</code>
     * @param {string} str 运算式
     * @param {number} n 保留几位小数
     * @return {string}
     */
    this.calculate = function(str, n) { }
    /**
     * 格式化时间<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#formatDate</code>
     * @param {object} date
     * @param {string} pattern
     * @return {string}
     */
    this.formatDate = function(date, pattern) { }
    /**
     * iframe<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#iframe</code>
     * @param {string} target
     * @param {Function} fun 函数
     * @return void
     */
    this.iframe = function(target, fun) { }
    /**
     * 断言<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#assertResult</code>
     * @param {boolean} flag
     * @param {object} message
     * @return void
     */
    this.assertResult = function(flag, message) { }
    /**
     * 获取当前窗口句柄<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#getWindowHandle</code>
     * @return {string}
     */
    this.getWindowHandle = function() { }
    /**
     * 写文本文件<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#writeText</code>
     * @param {string} path
     * @param {string} context
     * @param {boolean} append
     * @param {string?} charsetName
     * @return void
     */
    this.writeText = function(path, context, append, charsetName) { }
    /**
     * HTTP 请求<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#request</code>
     * @param {string|null} url 请求url/uri
     * @param {string?} method 请求方式 POST/GET 默认GET
     * @param {object?} content 请求body内容
     * @param {object?} headers 请求头
     * @return {object}
     */
    this.request = function(url, method, content, headers) { }
    /**
     * 向目标元素发送文字/模拟按键<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#type</code>
     * @param {string} target
     * @param {object} key
     * @return {WebDriverBinding}
     */
    this.type = function(target, key) { }
    /**
     * 查找多个元素<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#find</code>
     * @param {string} target
     * @return {WebElementBinding[]}
     */
    this.find = function(target) { }
    /**
     * 获取目标元素属性值<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#attr</code>
     * @param {string} target
     * @param {string} attr
     * @return {WebDriverBinding}
     */
    this.attr = function(target, attr) { }
    /**
     * 选择下拉框<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#select</code>
     * @param {string} target
     * @param {string} label
     * @param {object} value
     * @return {WebDriverBinding}
     */
    this.select = function(target, label, value) { }
    /**
     * 等待元素显示（可见）<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#waitForElementDisplayed</code>
     * @param {string} target
     * @param {number?} seconds
     * @return {WebDriverBinding}
     */
    this.waitForElementDisplayed = function(target, seconds) { }
    /**
     * 等待元素消失<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#waitForElementNotPresent</code>
     * @param {string} target
     * @param {number?} seconds
     * @return {WebDriverBinding}
     */
    this.waitForElementNotPresent = function(target, seconds) { }
    /**
     * 等待元素隐藏（不可见）<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#waitForElementNotDisplayed</code>
     * @param {string} target
     * @param {number?} seconds
     * @return {WebDriverBinding}
     */
    this.waitForElementNotDisplayed = function(target, seconds) { }
    /**
     * 等待元素出现<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#waitForElementPresent</code>
     * @param {string} target
     * @param {number?} seconds
     * @return {WebDriverBinding}
     */
    this.waitForElementPresent = function(target, seconds) { }
    /**
     * 查找多个可见元素<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#findDisplayed</code>
     * @param {string} target
     * @return {WebElementBinding[]}
     */
    this.findDisplayed = function(target) { }
    /**
     * 查找元素包含某文字的节点<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#findText</code>
     * @param {string} tagName
     * @param {string} text
     * @param {boolean} fuzzyMatches 是否模糊匹配
     * @return {WebElementBinding}
     */
    this.findText = function(tagName, text, fuzzyMatches) { }
    /**
     * 查找元素包含某文字的节点<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#findTextAll</code>
     * @param {string} tagName
     * @param {string} text
     * @param {boolean} fuzzyMatches 是否模糊匹配
     * @return {WebElementBinding[]}
     */
    this.findTextAll = function(tagName, text, fuzzyMatches) { }
    /**
     * 查找一个元素<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#findOne</code>
     * @param {string} target
     * @return {WebElementBinding}
     */
    this.findOne = function(target) { }
    /**
     * 查找一个元素并点击<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#click</code>
     * @param {string} target
     * @return {WebDriverBinding}
     */
    this.click = function(target) { }
    /**
     * 查找多个元素<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#findList</code>
     * @param {string[]} targets
     * @return {WebElementBinding[]}
     */
    this.findList = function(targets) { }
    /**
     * 鼠标移动到目标元素<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#mouseOver</code>
     * @param {string} target
     * @return {WebDriverBinding}
     */
    this.mouseOver = function(target) { }
    /**
     * 滚动到目标元素<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#scrollToCenter</code>
     * @param {string|null} target
     * @return {WebDriverBinding}
     */
    this.scrollToCenter = function(target) { }
    /**
     * 向目标元素发送文字/模拟按键<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#sendKeys</code>
     * @param {string} target
     * @param {object} key
     * @return {WebDriverBinding}
     */
    this.sendKeys = function(target, key) { }
    /**
     * 通过label选择下拉框<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#selectByLabel</code>
     * @param {string} target
     * @param {object} value
     * @return {WebDriverBinding}
     */
    this.selectByLabel = function(target, value) { }
    /**
     * 通过value选择下拉框<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#selectByValue</code>
     * @param {string} target
     * @param {object} value
     * @return {WebDriverBinding}
     */
    this.selectByValue = function(target, value) { }
    /**
     * 查找一个元素并点击<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#tryClick</code>
     * @param {string} target
     * @return {WebDriverBinding}
     */
    this.tryClick = function(target) { }
    /**
     * 获取目标元素文本内容<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#text</code>
     * @param {string} target
     * @return {string}
     */
    this.text = function(target) { }
}

const driver = new WebDriverBinding();

/**
 * 识别结果
 */
function OcrResult() {
    /**
     * getContent<br>
     * <code>cn.veasion.auto.captcha.ocr.OcrResult#getContent</code>
     * @return {string}
     */
    this.getContent = function() { }
    /**
     * <br>
     * <code>cn.veasion.auto.captcha.ocr.OcrResult#getWordsList</code>
     * @return {Words[]}
     */
    this.getWordsList = function() { }
}

/**
 * 数据库连接
 */
function JdbcConnectionBinding() {
    /**
     * 执行增删改<br>
     * <code>cn.veasion.auto.bind.JdbcConnectionBinding#update</code>
     * @param {string} sql
     * @param {array} params
     * @return {number}
     */
    this.update = function(sql, params) { }
    /**
     * 关闭连接<br>
     * <code>cn.veasion.auto.bind.JdbcConnectionBinding#close</code>
     * @return void
     */
    this.close = function() { }
    /**
     * 执行新增，返回自增长id<br>
     * <code>cn.veasion.auto.bind.JdbcConnectionBinding#insert</code>
     * @param {string} sql
     * @param {array} params
     * @return {number}
     */
    this.insert = function(sql, params) { }
    /**
     * 列表查询<br>
     * <code>cn.veasion.auto.bind.JdbcConnectionBinding#query</code>
     * @param {string} sql
     * @param {array} params
     * @return {array}
     */
    this.query = function(sql, params) { }
    /**
     * 获取单个值<br>
     * <code>cn.veasion.auto.bind.JdbcConnectionBinding#queryOnly</code>
     * @param {string} sql
     * @param {array} params
     * @return {object}
     */
    this.queryOnly = function(sql, params) { }
    /**
     * 执行DDL<br>
     * <code>cn.veasion.auto.bind.JdbcConnectionBinding#executeDDL</code>
     * @param {string} sql
     * @return {number}
     */
    this.executeDDL = function(sql) { }
}

/**
 * 触摸
 */
function TouchActionsBinding() {
    /**
     * 单击<br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#click</code>
     * @return {TouchActionsBinding}
     */
    this.click = function() { }
    /**
     * 两次触摸点击<br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#doubleTap</code>
     * @return {TouchActionsBinding}
     */
    this.doubleTap = function() { }
    /**
     * flick<br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#flick</code>
     * @param {number} x
     * @param {number} y
     * @return {TouchActionsBinding}
     */
    this.flick = function(x, y) { }
    /**
     * 双击<br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#doubleClick</code>
     * @return {TouchActionsBinding}
     */
    this.doubleClick = function() { }
    /**
     * down<br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#down</code>
     * @param {number} x
     * @param {number} y
     * @return {TouchActionsBinding}
     */
    this.down = function(x, y) { }
    /**
     * 长按<br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#longPress</code>
     * @return {TouchActionsBinding}
     */
    this.longPress = function() { }
    /**
     * move<br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#move</code>
     * @param {number} x
     * @param {number} y
     * @return {TouchActionsBinding}
     */
    this.move = function(x, y) { }
    /**
     * flick<br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#flickByElement</code>
     * @param {number} xOffset
     * @param {number} yOffset
     * @param {number} speed
     * @return {TouchActionsBinding}
     */
    this.flickByElement = function(xOffset, yOffset, speed) { }
    /**
     * 执行<br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#perform</code>
     * @return {TouchActionsBinding}
     */
    this.perform = function() { }
    /**
     * up<br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#up</code>
     * @param {number} x
     * @param {number} y
     * @return {TouchActionsBinding}
     */
    this.up = function(x, y) { }
    /**
     * scroll<br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#scroll</code>
     * @param {number} x
     * @param {number} y
     * @return {TouchActionsBinding}
     */
    this.scroll = function(x, y) { }
    /**
     * 单次触摸点击<br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#singleTap</code>
     * @return {TouchActionsBinding}
     */
    this.singleTap = function() { }
    /**
     * scroll<br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#scrollByElement</code>
     * @param {number} x
     * @param {number} y
     * @return {TouchActionsBinding}
     */
    this.scrollByElement = function(x, y) { }
}

/**
 * 环境变量
 */
function EnvironmentBinding() {
    /**
     * 移除当前变量<br>
     * <code>cn.veasion.auto.bind.EnvironmentBinding#remove</code>
     * @param {string} key
     * @return void
     */
    this.remove = function(key) { }
    /**
     * 获取变量<br>
     * <code>cn.veasion.auto.bind.EnvironmentBinding#get</code>
     * @param {string} key
     * @return {object}
     */
    this.get = function(key) { }
    /**
     * 存储变量<br>
     * <code>cn.veasion.auto.bind.EnvironmentBinding#put</code>
     * @param {string} key
     * @param {object} value
     * @return void
     */
    this.put = function(key, value) { }
    /**
     * 获取变量<br>
     * <code>cn.veasion.auto.bind.EnvironmentBinding#getOrDefault</code>
     * @param {string} key
     * @param {object} defaultVal
     * @return {object}
     */
    this.getOrDefault = function(key, defaultVal) { }
    /**
     * 获取class决定路径<br>
     * <code>cn.veasion.auto.bind.EnvironmentBinding#getPath</code>
     * @param {string} path
     * @return {string}
     */
    this.getPath = function(path) { }
    /**
     * 获取变量<br>
     * <code>cn.veasion.auto.bind.EnvironmentBinding#getString</code>
     * @param {string} key
     * @return {string}
     */
    this.getString = function(key) { }
    /**
     * 获取源文件绝对路径<br>
     * <code>cn.veasion.auto.bind.EnvironmentBinding#getSourcePath</code>
     * @param {string} path
     * @return {string}
     */
    this.getSourcePath = function(path) { }
    /**
     * 存储全局变量<br>
     * <code>cn.veasion.auto.bind.EnvironmentBinding#putGlobal</code>
     * @param {string} key
     * @param {object} value
     * @return void
     */
    this.putGlobal = function(key, value) { }
    /**
     * translate<br>
     * <code>cn.veasion.auto.bind.EnvironmentBinding#translate</code>
     * @param {string} str
     * @return {object}
     */
    this.translate = function(str) { }
    /**
     * translate<br>
     * <code>cn.veasion.auto.bind.EnvironmentBinding#translate</code>
     * @param {string} str
     * @param {object} object
     * @return {object}
     */
    this.translate = function(str, object) { }
}

const env = new EnvironmentBinding();

/**
 * 日志
 */
function LoggerBinding() {
    /**
     * <br>
     * <code>cn.veasion.auto.bind.LoggerBinding#error</code>
     * @param {object} message
     * @param {object} e
     * @return void
     */
    this.error = function(message, e) { }
    /**
     * <br>
     * <code>cn.veasion.auto.bind.LoggerBinding#error</code>
     * @param {object} message
     * @return void
     */
    this.error = function(message) { }
    /**
     * <br>
     * <code>cn.veasion.auto.bind.LoggerBinding#debug</code>
     * @param {object} message
     * @return void
     */
    this.debug = function(message) { }
    /**
     * <br>
     * <code>cn.veasion.auto.bind.LoggerBinding#info</code>
     * @param {object} message
     * @return void
     */
    this.info = function(message) { }
    /**
     * <br>
     * <code>cn.veasion.auto.bind.LoggerBinding#warn</code>
     * @param {object} message
     * @return void
     */
    this.warn = function(message) { }
    /**
     * <br>
     * <code>cn.veasion.auto.bind.LoggerBinding#invokeMethod</code>
     * @param {string} name
     * @param {array} args
     * @return void
     */
    this.invokeMethod = function(name, args) { }
}

const log = new LoggerBinding();

