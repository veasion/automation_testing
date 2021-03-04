function load(url) {}
function loadWithNewGlobal(url) {}

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
     * <code>cn.veasion.auto.bind.LoggerBinding#warn</code>
     * @param {object} message
     * @return void
     */
    this.warn = function(message) { }
    /**
     * <br>
     * <code>cn.veasion.auto.bind.LoggerBinding#info</code>
     * @param {object} message
     * @return void
     */
    this.info = function(message) { }
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
 * 点位置
 */
function PointWrapper() {
    /**
     * x<br>
     * <code>cn.veasion.auto.opencv.PointWrapper#getX</code>
     * @return {number}
     */
    this.getX = function() { }
    /**
     * y<br>
     * <code>cn.veasion.auto.opencv.PointWrapper#getY</code>
     * @return {number}
     */
    this.getY = function() { }
}

/**
 * 触摸
 */
function TouchActionsBinding() {
    /**
     * 释放<br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#release</code>
     * @return {TouchActionsBinding}
     */
    this.release = function() { }
    /**
     * 释放<br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#release</code>
     * @param {WebElementBinding} target
     * @return {TouchActionsBinding}
     */
    this.release = function(target) { }
    /**
     * <br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#move</code>
     * @param {number} x
     * @param {number} y
     * @return {TouchActionsBinding}
     */
    this.move = function(x, y) { }
    /**
     * 单击-左键<br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#click</code>
     * @return {TouchActionsBinding}
     */
    this.click = function() { }
    /**
     * 单击元素-左键<br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#click</code>
     * @param {WebElementBinding} element
     * @return {TouchActionsBinding}
     */
    this.click = function(element) { }
    /**
     * <br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#sendKeys</code>
     * @param {WebElementBinding} target
     * @param {string} key
     * @return {TouchActionsBinding}
     */
    this.sendKeys = function(target, key) { }
    /**
     * <br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#sendKeys</code>
     * @param {string} key
     * @return {TouchActionsBinding}
     */
    this.sendKeys = function(key) { }
    /**
     * <br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#flick</code>
     * @param {WebElementBinding} element
     * @param {number} xOffset
     * @param {number} yOffset
     * @param {number} speed
     * @return {TouchActionsBinding}
     */
    this.flick = function(element, xOffset, yOffset, speed) { }
    /**
     * <br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#flick</code>
     * @param {number} x
     * @param {number} y
     * @return {TouchActionsBinding}
     */
    this.flick = function(x, y) { }
    /**
     * 单击并按住<br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#clickAndHold</code>
     * @param {WebElementBinding} element
     * @return {TouchActionsBinding}
     */
    this.clickAndHold = function(element) { }
    /**
     * 单击并按住<br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#clickAndHold</code>
     * @return {TouchActionsBinding}
     */
    this.clickAndHold = function() { }
    /**
     * <br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#moveByOffset</code>
     * @param {number} xOffset
     * @param {number} yOffset
     * @return {TouchActionsBinding}
     */
    this.moveByOffset = function(xOffset, yOffset) { }
    /**
     * 两次触摸点击<br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#doubleTap</code>
     * @param {WebElementBinding} element
     * @return {TouchActionsBinding}
     */
    this.doubleTap = function(element) { }
    /**
     * <br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#up</code>
     * @param {number} x
     * @param {number} y
     * @return {TouchActionsBinding}
     */
    this.up = function(x, y) { }
    /**
     * 长按<br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#longPress</code>
     * @param {WebElementBinding} element
     * @return {TouchActionsBinding}
     */
    this.longPress = function(element) { }
    /**
     * <br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#moveToElement</code>
     * @param {WebElementBinding} element
     * @param {number} xOffset
     * @param {number} yOffset
     * @return {TouchActionsBinding}
     */
    this.moveToElement = function(element, xOffset, yOffset) { }
    /**
     * <br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#moveToElement</code>
     * @param {WebElementBinding} element
     * @return {TouchActionsBinding}
     */
    this.moveToElement = function(element) { }
    /**
     * <br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#pause</code>
     * @param {number} millis
     * @return {TouchActionsBinding}
     */
    this.pause = function(millis) { }
    /**
     * 单次触摸点击<br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#singleTap</code>
     * @param {WebElementBinding} element
     * @return {TouchActionsBinding}
     */
    this.singleTap = function(element) { }
    /**
     * 执行<br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#perform</code>
     * @return {TouchActionsBinding}
     */
    this.perform = function() { }
    /**
     * 单击-右键<br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#contextClick</code>
     * @return {TouchActionsBinding}
     */
    this.contextClick = function() { }
    /**
     * 单击元素-右键<br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#contextClick</code>
     * @param {WebElementBinding} element
     * @return {TouchActionsBinding}
     */
    this.contextClick = function(element) { }
    /**
     * <br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#down</code>
     * @param {number} x
     * @param {number} y
     * @return {TouchActionsBinding}
     */
    this.down = function(x, y) { }
    /**
     * <br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#scroll</code>
     * @param {number} x
     * @param {number} y
     * @return {TouchActionsBinding}
     */
    this.scroll = function(x, y) { }
    /**
     * <br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#scroll</code>
     * @param {WebElementBinding} element
     * @param {number} x
     * @param {number} y
     * @return {TouchActionsBinding}
     */
    this.scroll = function(element, x, y) { }
    /**
     * <br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#dragAndDrop</code>
     * @param {WebElementBinding} source
     * @param {number} xOffset
     * @param {number} yOffset
     * @return {TouchActionsBinding}
     */
    this.dragAndDrop = function(source, xOffset, yOffset) { }
    /**
     * <br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#dragAndDrop</code>
     * @param {WebElementBinding} source
     * @param {WebElementBinding} target
     * @return {TouchActionsBinding}
     */
    this.dragAndDrop = function(source, target) { }
    /**
     * 双击<br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#doubleClick</code>
     * @return {TouchActionsBinding}
     */
    this.doubleClick = function() { }
    /**
     * 双击<br>
     * <code>cn.veasion.auto.bind.TouchActionsBinding#doubleClick</code>
     * @param {WebElementBinding} element
     * @return {TouchActionsBinding}
     */
    this.doubleClick = function(element) { }
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
 * 输入<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#input</code>
 * @param {string} title
 * @return {string}
 */
function input(title) { }
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
 * 在新的浏览器驱动中执行脚本<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#runScriptWithNewDriver</code>
 * @param {object} env
 * @param {string} path
 * @return {EnvironmentBinding}
 */
function runScriptWithNewDriver(env, path) { }
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
 * 向浏览器驱动执行 js 代码<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#executeScriptByParams</code>
 * @param {string} jsCode
 * @param {array?} args
 * @return {object}
 */
function executeScriptByParams(jsCode, args) { }
/**
 * iframe<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#iframe</code>
 * @param {string} target
 * @param {Function} fun 函数
 * @return void
 */
function iframe(target, fun) { }
/**
 * 随机字符串<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#randCode</code>
 * @param {number} length
 * @return {string}
 */
function randCode(length) { }
/**
 * 断言<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#assertResult</code>
 * @param {boolean} flag
 * @param {object} message
 * @return void
 */
function assertResult(flag, message) { }
/**
 * 计算<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#calculate</code>
 * @param {string} str 运算式
 * @param {number} n 保留几位小数
 * @return {string}
 */
function calculate(str, n) { }
/**
 * 向浏览器驱动执行 js 代码<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#executeScript</code>
 * @param {string} jsCode
 * @return {object}
 */
function executeScript(jsCode) { }
/**
 * 点击坐标<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#clickPoint</code>
 * @param {number} x
 * @param {number} y
 * @return void
 */
function clickPoint(x, y) { }
/**
 * 格式化时间<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#formatDate</code>
 * @param {object} date
 * @param {string} pattern
 * @return {string}
 */
function formatDate(date, pattern) { }
/**
 * 暂停多少毫秒<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#pause</code>
 * @param {number} millis
 * @return void
 */
function pause(millis) { }
/**
 * 鼠标动作<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#newTouchActions</code>
 * @return {TouchActionsBinding}
 */
function newTouchActions() { }
/**
 * 运行新的脚本<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#runNewScript</code>
 * @param {string} path
 * @return void
 */
function runNewScript(path) { }
/**
 * 打开并切换到新的窗口<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#openNewWindow</code>
 * @return void
 */
function openNewWindow() { }
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
 * 截图<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#screenshot</code>
 * @param {string|null} path
 * @return {boolean}
 */
function screenshot(path) { }
/**
 * 获取当前窗口句柄<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#getWindowHandle</code>
 * @return {string}
 */
function getWindowHandle() { }
/**
 * 在新的窗口中执行函数<br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#withNewWindow</code>
 * @param {Function} fun 函数
 * @return void
 */
function withNewWindow(fun) { }
/**
 * <br>
 * <code>cn.veasion.auto.bind.WebDriverBinding#toChromeDriver</code>
 * @return {ChromeDriverBinding}
 */
function toChromeDriver() { }
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
 * 获取目标元素文本内容<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#text</code>
 * @param {string} target
 * @return {string}
 */
function text(target) { }
/**
 * 等待元素隐藏（不可见）<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#waitForElementNotDisplayed</code>
 * @param {string} target
 * @param {number?} seconds
 * @return {WebDriverBinding}
 */
function waitForElementNotDisplayed(target, seconds) { }
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
 * <code>cn.veasion.auto.bind.SearchContextBinding#findTextAll</code>
 * @param {string} tagName
 * @param {string} text
 * @param {boolean} fuzzyMatches 是否模糊匹配
 * @return {WebElementBinding[]}
 */
function findTextAll(tagName, text, fuzzyMatches) { }
/**
 * 查找一个元素并点击<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#click</code>
 * @param {string} target
 * @return {WebDriverBinding}
 */
function click(target) { }
/**
 * 鼠标移动到目标元素<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#mouseOver</code>
 * @param {string} target
 * @return {WebDriverBinding}
 */
function mouseOver(target) { }
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
 * 查找一个元素并点击<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#tryClick</code>
 * @param {string} target
 * @return {WebDriverBinding}
 */
function tryClick(target) { }
/**
 * 向目标元素发送文字/模拟按键<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#sendKeys</code>
 * @param {string} target
 * @param {object} key
 * @return {WebDriverBinding}
 */
function sendKeys(target, key) { }
/**
 * 查找多个元素<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#findList</code>
 * @param {string[]} targets
 * @return {WebElementBinding[]}
 */
function findList(targets) { }
/**
 * 滚动到目标元素<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#scrollToCenter</code>
 * @param {string|null} target
 * @return {WebDriverBinding}
 */
function scrollToCenter(target) { }
/**
 * 通过label选择下拉框<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#selectByLabel</code>
 * @param {string} target
 * @param {object} value
 * @return {WebDriverBinding}
 */
function selectByLabel(target, value) { }
/**
 * 查找一个元素<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#findOne</code>
 * @param {string} target
 * @return {WebElementBinding}
 */
function findOne(target) { }
/**
 * 通过value选择下拉框<br>
 * <code>cn.veasion.auto.bind.SearchContextBinding#selectByValue</code>
 * @param {string} target
 * @param {object} value
 * @return {WebDriverBinding}
 */
function selectByValue(target, value) { }

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
     * 输入<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#input</code>
     * @param {string} title
     * @return {string}
     */
    this.input = function(title) { }
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
     * 在新的浏览器驱动中执行脚本<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#runScriptWithNewDriver</code>
     * @param {object} env
     * @param {string} path
     * @return {EnvironmentBinding}
     */
    this.runScriptWithNewDriver = function(env, path) { }
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
     * 向浏览器驱动执行 js 代码<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#executeScriptByParams</code>
     * @param {string} jsCode
     * @param {array?} args
     * @return {object}
     */
    this.executeScriptByParams = function(jsCode, args) { }
    /**
     * iframe<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#iframe</code>
     * @param {string} target
     * @param {Function} fun 函数
     * @return void
     */
    this.iframe = function(target, fun) { }
    /**
     * 随机字符串<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#randCode</code>
     * @param {number} length
     * @return {string}
     */
    this.randCode = function(length) { }
    /**
     * 断言<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#assertResult</code>
     * @param {boolean} flag
     * @param {object} message
     * @return void
     */
    this.assertResult = function(flag, message) { }
    /**
     * 计算<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#calculate</code>
     * @param {string} str 运算式
     * @param {number} n 保留几位小数
     * @return {string}
     */
    this.calculate = function(str, n) { }
    /**
     * 向浏览器驱动执行 js 代码<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#executeScript</code>
     * @param {string} jsCode
     * @return {object}
     */
    this.executeScript = function(jsCode) { }
    /**
     * 点击坐标<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#clickPoint</code>
     * @param {number} x
     * @param {number} y
     * @return void
     */
    this.clickPoint = function(x, y) { }
    /**
     * 格式化时间<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#formatDate</code>
     * @param {object} date
     * @param {string} pattern
     * @return {string}
     */
    this.formatDate = function(date, pattern) { }
    /**
     * 暂停多少毫秒<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#pause</code>
     * @param {number} millis
     * @return void
     */
    this.pause = function(millis) { }
    /**
     * 鼠标动作<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#newTouchActions</code>
     * @return {TouchActionsBinding}
     */
    this.newTouchActions = function() { }
    /**
     * 运行新的脚本<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#runNewScript</code>
     * @param {string} path
     * @return void
     */
    this.runNewScript = function(path) { }
    /**
     * 打开并切换到新的窗口<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#openNewWindow</code>
     * @return void
     */
    this.openNewWindow = function() { }
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
     * 截图<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#screenshot</code>
     * @param {string|null} path
     * @return {boolean}
     */
    this.screenshot = function(path) { }
    /**
     * 获取当前窗口句柄<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#getWindowHandle</code>
     * @return {string}
     */
    this.getWindowHandle = function() { }
    /**
     * 在新的窗口中执行函数<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#withNewWindow</code>
     * @param {Function} fun 函数
     * @return void
     */
    this.withNewWindow = function(fun) { }
    /**
     * <br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#toChromeDriver</code>
     * @return {ChromeDriverBinding}
     */
    this.toChromeDriver = function() { }
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
     * 获取目标元素文本内容<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#text</code>
     * @param {string} target
     * @return {string}
     */
    this.text = function(target) { }
    /**
     * 等待元素隐藏（不可见）<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#waitForElementNotDisplayed</code>
     * @param {string} target
     * @param {number?} seconds
     * @return {WebDriverBinding}
     */
    this.waitForElementNotDisplayed = function(target, seconds) { }
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
     * <code>cn.veasion.auto.bind.SearchContextBinding#findTextAll</code>
     * @param {string} tagName
     * @param {string} text
     * @param {boolean} fuzzyMatches 是否模糊匹配
     * @return {WebElementBinding[]}
     */
    this.findTextAll = function(tagName, text, fuzzyMatches) { }
    /**
     * 查找一个元素并点击<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#click</code>
     * @param {string} target
     * @return {WebDriverBinding}
     */
    this.click = function(target) { }
    /**
     * 鼠标移动到目标元素<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#mouseOver</code>
     * @param {string} target
     * @return {WebDriverBinding}
     */
    this.mouseOver = function(target) { }
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
     * 查找一个元素并点击<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#tryClick</code>
     * @param {string} target
     * @return {WebDriverBinding}
     */
    this.tryClick = function(target) { }
    /**
     * 向目标元素发送文字/模拟按键<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#sendKeys</code>
     * @param {string} target
     * @param {object} key
     * @return {WebDriverBinding}
     */
    this.sendKeys = function(target, key) { }
    /**
     * 查找多个元素<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#findList</code>
     * @param {string[]} targets
     * @return {WebElementBinding[]}
     */
    this.findList = function(targets) { }
    /**
     * 滚动到目标元素<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#scrollToCenter</code>
     * @param {string|null} target
     * @return {WebDriverBinding}
     */
    this.scrollToCenter = function(target) { }
    /**
     * 通过label选择下拉框<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#selectByLabel</code>
     * @param {string} target
     * @param {object} value
     * @return {WebDriverBinding}
     */
    this.selectByLabel = function(target, value) { }
    /**
     * 查找一个元素<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#findOne</code>
     * @param {string} target
     * @return {WebElementBinding}
     */
    this.findOne = function(target) { }
    /**
     * 通过value选择下拉框<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#selectByValue</code>
     * @param {string} target
     * @param {object} value
     * @return {WebDriverBinding}
     */
    this.selectByValue = function(target, value) { }
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
     * translate<br>
     * <code>cn.veasion.auto.bind.EnvironmentBinding#translate</code>
     * @param {string} str
     * @param {object} object
     * @return {object}
     */
    this.translate = function(str, object) { }
    /**
     * translate<br>
     * <code>cn.veasion.auto.bind.EnvironmentBinding#translate</code>
     * @param {string} str
     * @return {object}
     */
    this.translate = function(str) { }
    /**
     * 存储全局变量<br>
     * <code>cn.veasion.auto.bind.EnvironmentBinding#putGlobal</code>
     * @param {string} key
     * @param {object} value
     * @return void
     */
    this.putGlobal = function(key, value) { }
    /**
     * 获取源文件绝对路径<br>
     * <code>cn.veasion.auto.bind.EnvironmentBinding#getSourcePath</code>
     * @param {string} path
     * @return {string}
     */
    this.getSourcePath = function(path) { }
}

const env = new EnvironmentBinding();

/**
 * chromeDriver
 */
function ChromeDriverBinding() {
    /**
     * 执行cdp命令<br>
     * <code>cn.veasion.auto.bind.ChromeDriverBinding#executeCdpCommand</code>
     * @param {string} commandName
     * @param {object} parameters
     * @return {object}
     */
    this.executeCdpCommand = function(commandName, parameters) { }
    /**
     * <br>
     * <code>cn.veasion.auto.bind.ChromeDriverBinding#setUserAgent</code>
     * @param {string} userAgent
     * @return void
     */
    this.setUserAgent = function(userAgent) { }
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
     * 输入<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#input</code>
     * @param {string} title
     * @return {string}
     */
    this.input = function(title) { }
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
     * 在新的浏览器驱动中执行脚本<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#runScriptWithNewDriver</code>
     * @param {object} env
     * @param {string} path
     * @return {EnvironmentBinding}
     */
    this.runScriptWithNewDriver = function(env, path) { }
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
     * 向浏览器驱动执行 js 代码<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#executeScriptByParams</code>
     * @param {string} jsCode
     * @param {array?} args
     * @return {ChromeDriverBinding}
     */
    this.executeScriptByParams = function(jsCode, args) { }
    /**
     * iframe<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#iframe</code>
     * @param {string} target
     * @param {Function} fun 函数
     * @return void
     */
    this.iframe = function(target, fun) { }
    /**
     * 随机字符串<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#randCode</code>
     * @param {number} length
     * @return {string}
     */
    this.randCode = function(length) { }
    /**
     * 断言<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#assertResult</code>
     * @param {boolean} flag
     * @param {object} message
     * @return void
     */
    this.assertResult = function(flag, message) { }
    /**
     * 计算<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#calculate</code>
     * @param {string} str 运算式
     * @param {number} n 保留几位小数
     * @return {string}
     */
    this.calculate = function(str, n) { }
    /**
     * 向浏览器驱动执行 js 代码<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#executeScript</code>
     * @param {string} jsCode
     * @return {ChromeDriverBinding}
     */
    this.executeScript = function(jsCode) { }
    /**
     * 点击坐标<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#clickPoint</code>
     * @param {number} x
     * @param {number} y
     * @return void
     */
    this.clickPoint = function(x, y) { }
    /**
     * 格式化时间<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#formatDate</code>
     * @param {object} date
     * @param {string} pattern
     * @return {string}
     */
    this.formatDate = function(date, pattern) { }
    /**
     * 暂停多少毫秒<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#pause</code>
     * @param {number} millis
     * @return void
     */
    this.pause = function(millis) { }
    /**
     * 鼠标动作<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#newTouchActions</code>
     * @return {TouchActionsBinding}
     */
    this.newTouchActions = function() { }
    /**
     * 运行新的脚本<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#runNewScript</code>
     * @param {string} path
     * @return void
     */
    this.runNewScript = function(path) { }
    /**
     * 打开并切换到新的窗口<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#openNewWindow</code>
     * @return void
     */
    this.openNewWindow = function() { }
    /**
     * HTTP 请求<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#request</code>
     * @param {string|null} url 请求url/uri
     * @param {string?} method 请求方式 POST/GET 默认GET
     * @param {object?} content 请求body内容
     * @param {object?} headers 请求头
     * @return {ChromeDriverBinding}
     */
    this.request = function(url, method, content, headers) { }
    /**
     * 截图<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#screenshot</code>
     * @param {string|null} path
     * @return {boolean}
     */
    this.screenshot = function(path) { }
    /**
     * 获取当前窗口句柄<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#getWindowHandle</code>
     * @return {string}
     */
    this.getWindowHandle = function() { }
    /**
     * 在新的窗口中执行函数<br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#withNewWindow</code>
     * @param {Function} fun 函数
     * @return void
     */
    this.withNewWindow = function(fun) { }
    /**
     * <br>
     * <code>cn.veasion.auto.bind.WebDriverBinding#toChromeDriver</code>
     * @return {ChromeDriverBinding}
     */
    this.toChromeDriver = function() { }
    /**
     * 向目标元素发送文字/模拟按键<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#type</code>
     * @param {string} target
     * @param {object} key
     * @return {ChromeDriverBinding}
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
     * @return {ChromeDriverBinding}
     */
    this.attr = function(target, attr) { }
    /**
     * 选择下拉框<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#select</code>
     * @param {string} target
     * @param {string} label
     * @param {object} value
     * @return {ChromeDriverBinding}
     */
    this.select = function(target, label, value) { }
    /**
     * 获取目标元素文本内容<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#text</code>
     * @param {string} target
     * @return {string}
     */
    this.text = function(target) { }
    /**
     * 等待元素隐藏（不可见）<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#waitForElementNotDisplayed</code>
     * @param {string} target
     * @param {number?} seconds
     * @return {ChromeDriverBinding}
     */
    this.waitForElementNotDisplayed = function(target, seconds) { }
    /**
     * 等待元素显示（可见）<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#waitForElementDisplayed</code>
     * @param {string} target
     * @param {number?} seconds
     * @return {ChromeDriverBinding}
     */
    this.waitForElementDisplayed = function(target, seconds) { }
    /**
     * 等待元素消失<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#waitForElementNotPresent</code>
     * @param {string} target
     * @param {number?} seconds
     * @return {ChromeDriverBinding}
     */
    this.waitForElementNotPresent = function(target, seconds) { }
    /**
     * 等待元素出现<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#waitForElementPresent</code>
     * @param {string} target
     * @param {number?} seconds
     * @return {ChromeDriverBinding}
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
     * <code>cn.veasion.auto.bind.SearchContextBinding#findTextAll</code>
     * @param {string} tagName
     * @param {string} text
     * @param {boolean} fuzzyMatches 是否模糊匹配
     * @return {WebElementBinding[]}
     */
    this.findTextAll = function(tagName, text, fuzzyMatches) { }
    /**
     * 查找一个元素并点击<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#click</code>
     * @param {string} target
     * @return {ChromeDriverBinding}
     */
    this.click = function(target) { }
    /**
     * 鼠标移动到目标元素<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#mouseOver</code>
     * @param {string} target
     * @return {ChromeDriverBinding}
     */
    this.mouseOver = function(target) { }
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
     * 查找一个元素并点击<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#tryClick</code>
     * @param {string} target
     * @return {ChromeDriverBinding}
     */
    this.tryClick = function(target) { }
    /**
     * 向目标元素发送文字/模拟按键<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#sendKeys</code>
     * @param {string} target
     * @param {object} key
     * @return {ChromeDriverBinding}
     */
    this.sendKeys = function(target, key) { }
    /**
     * 查找多个元素<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#findList</code>
     * @param {string[]} targets
     * @return {WebElementBinding[]}
     */
    this.findList = function(targets) { }
    /**
     * 滚动到目标元素<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#scrollToCenter</code>
     * @param {string|null} target
     * @return {ChromeDriverBinding}
     */
    this.scrollToCenter = function(target) { }
    /**
     * 通过label选择下拉框<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#selectByLabel</code>
     * @param {string} target
     * @param {object} value
     * @return {ChromeDriverBinding}
     */
    this.selectByLabel = function(target, value) { }
    /**
     * 查找一个元素<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#findOne</code>
     * @param {string} target
     * @return {WebElementBinding}
     */
    this.findOne = function(target) { }
    /**
     * 通过value选择下拉框<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#selectByValue</code>
     * @param {string} target
     * @param {object} value
     * @return {ChromeDriverBinding}
     */
    this.selectByValue = function(target, value) { }
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
     * <br>
     * <code>cn.veasion.auto.bind.WebElementBinding#show</code>
     * @return void
     */
    this.show = function() { }
    /**
     * 获取文本内容<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#text</code>
     * @return {string}
     */
    this.text = function() { }
    /**
     * outerHTML<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#outerHtml</code>
     * @return {string}
     */
    this.outerHtml = function() { }
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
     * 子元素集合<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#childList</code>
     * @return {WebElementBinding[]}
     */
    this.childList = function() { }
    /**
     * 获取位置区域 x/y/height/width<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#getRect</code>
     * @return {object}
     */
    this.getRect = function() { }
    /**
     * 保存成图片<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#saveAsImage</code>
     * @param {string|null} path
     * @return void
     */
    this.saveAsImage = function(path) { }
    /**
     * 右边兄弟节点集合<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#rightSibling</code>
     * @return {WebElementBinding[]}
     */
    this.rightSibling = function() { }
    /**
     * 父元素<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#parentByTag</code>
     * @param {string} tagName
     * @return {WebElementBinding}
     */
    this.parentByTag = function(tagName) { }
    /**
     * 左边兄弟节点集合<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#leftSibling</code>
     * @return {WebElementBinding[]}
     */
    this.leftSibling = function() { }
    /**
     * 获取css值<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#cssValue</code>
     * @param {string} propertyName
     * @return {string}
     */
    this.cssValue = function(propertyName) { }
    /**
     * 点击<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#click</code>
     * @return {WebElementBinding}
     */
    this.click = function() { }
    /**
     * 查找元素包含某文字的节点<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#findText</code>
     * @param {string} tagName
     * @param {string} text
     * @return {WebElementBinding}
     */
    this.findText = function(tagName, text) { }
    /**
     * 点击这个元素区域<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#tryClick</code>
     * @return {WebElementBinding}
     */
    this.tryClick = function() { }
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
     * 是否可见<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#isDisplayed</code>
     * @return {boolean}
     */
    this.isDisplayed = function() { }
    /**
     * 通过value选择下拉框<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#selectByValue</code>
     * @param {object} value
     * @return {WebElementBinding}
     */
    this.selectByValue = function(value) { }
    /**
     * innerHTML<br>
     * <code>cn.veasion.auto.bind.WebElementBinding#innerHtml</code>
     * @return {string}
     */
    this.innerHtml = function() { }
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
     * 获取目标元素文本内容<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#text</code>
     * @param {string} target
     * @return {string}
     */
    this.text = function(target) { }
    /**
     * 等待元素隐藏（不可见）<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#waitForElementNotDisplayed</code>
     * @param {string} target
     * @param {number?} seconds
     * @return {WebElementBinding}
     */
    this.waitForElementNotDisplayed = function(target, seconds) { }
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
     * <code>cn.veasion.auto.bind.SearchContextBinding#findTextAll</code>
     * @param {string} tagName
     * @param {string} text
     * @param {boolean} fuzzyMatches 是否模糊匹配
     * @return {WebElementBinding[]}
     */
    this.findTextAll = function(tagName, text, fuzzyMatches) { }
    /**
     * 查找一个元素并点击<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#click</code>
     * @param {string} target
     * @return {WebElementBinding}
     */
    this.click = function(target) { }
    /**
     * 鼠标移动到目标元素<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#mouseOver</code>
     * @param {string} target
     * @return {WebElementBinding}
     */
    this.mouseOver = function(target) { }
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
     * 查找一个元素并点击<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#tryClick</code>
     * @param {string} target
     * @return {WebElementBinding}
     */
    this.tryClick = function(target) { }
    /**
     * 向目标元素发送文字/模拟按键<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#sendKeys</code>
     * @param {string} target
     * @param {object} key
     * @return {WebElementBinding}
     */
    this.sendKeys = function(target, key) { }
    /**
     * 查找多个元素<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#findList</code>
     * @param {string[]} targets
     * @return {WebElementBinding[]}
     */
    this.findList = function(targets) { }
    /**
     * 滚动到目标元素<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#scrollToCenter</code>
     * @param {string|null} target
     * @return {WebElementBinding}
     */
    this.scrollToCenter = function(target) { }
    /**
     * 通过label选择下拉框<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#selectByLabel</code>
     * @param {string} target
     * @param {object} value
     * @return {WebElementBinding}
     */
    this.selectByLabel = function(target, value) { }
    /**
     * 查找一个元素<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#findOne</code>
     * @param {string} target
     * @return {WebElementBinding}
     */
    this.findOne = function(target) { }
    /**
     * 通过value选择下拉框<br>
     * <code>cn.veasion.auto.bind.SearchContextBinding#selectByValue</code>
     * @param {string} target
     * @param {object} value
     * @return {WebElementBinding}
     */
    this.selectByValue = function(target, value) { }
}

/**
 * 位置
 */
function Location() {
    /**
     * getHeight<br>
     * <code>cn.veasion.auto.captcha.ocr.OcrResult$Location#getHeight</code>
     * @return {number}
     */
    this.getHeight = function() { }
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
     * getLeft<br>
     * <code>cn.veasion.auto.captcha.ocr.OcrResult$Location#getLeft</code>
     * @return {number}
     */
    this.getLeft = function() { }
}

/**
 * 文件
 */
function FileBean() {
    /**
     * 写文本文件<br>
     * <code>cn.veasion.auto.bind.bean.FileBean#writeText</code>
     * @param {string} path
     * @param {string} context
     * @param {boolean} append
     * @param {string?} charsetName
     * @return void
     */
    this.writeText = function(path, context, append, charsetName) { }
    /**
     * 写文本文件<br>
     * <code>cn.veasion.auto.bind.bean.FileBean#writeText</code>
     * @param {string} path
     * @param {string} context
     * @param {boolean} append
     * @return void
     */
    this.writeText = function(path, context, append) { }
    /**
     * 读取文本<br>
     * <code>cn.veasion.auto.bind.bean.FileBean#readText</code>
     * @param {string} pathOrUrl
     * @return {string}
     */
    this.readText = function(pathOrUrl) { }
    /**
     * 读取文本<br>
     * <code>cn.veasion.auto.bind.bean.FileBean#readText</code>
     * @param {string} pathOrUrl
     * @param {string?} charsetName
     * @return {string}
     */
    this.readText = function(pathOrUrl, charsetName) { }
}

const file = new FileBean();

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
     * 执行DDL<br>
     * <code>cn.veasion.auto.bind.JdbcConnectionBinding#executeDDL</code>
     * @param {string} sql
     * @return {number}
     */
    this.executeDDL = function(sql) { }
    /**
     * 获取单个值<br>
     * <code>cn.veasion.auto.bind.JdbcConnectionBinding#queryOnly</code>
     * @param {string} sql
     * @param {array} params
     * @return {object}
     */
    this.queryOnly = function(sql, params) { }
}

/**
 * 图片
 */
function ImageWrapper() {
    /**
     * 克隆<br>
     * <code>cn.veasion.auto.opencv.ImageWrapper#clone</code>
     * @return {object}
     */
    this.clone = function() { }
    /**
     * 克隆<br>
     * <code>cn.veasion.auto.opencv.ImageWrapper#clone</code>
     * @return {ImageWrapper}
     */
    this.clone = function() { }
    /**
     * 查看图片<br>
     * <code>cn.veasion.auto.opencv.ImageWrapper#show</code>
     * @return void
     */
    this.show = function() { }
    /**
     * 查看图片<br>
     * <code>cn.veasion.auto.opencv.ImageWrapper#show</code>
     * @param {number} x
     * @param {number} y
     * @param {number} width
     * @param {number} height
     * @return void
     */
    this.show = function(x, y, width, height) { }
    /**
     * 查看图片<br>
     * <code>cn.veasion.auto.opencv.ImageWrapper#show</code>
     * @param {number} x
     * @param {number} y
     * @return void
     */
    this.show = function(x, y) { }
    /**
     * 高度<br>
     * <code>cn.veasion.auto.opencv.ImageWrapper#getHeight</code>
     * @return {number}
     */
    this.getHeight = function() { }
    /**
     * 宽度<br>
     * <code>cn.veasion.auto.opencv.ImageWrapper#getWidth</code>
     * @return {number}
     */
    this.getWidth = function() { }
    /**
     * 获取RGB值<br>
     * <code>cn.veasion.auto.opencv.ImageWrapper#getRGB</code>
     * @param {number} x
     * @param {number} y
     * @return {number}
     */
    this.getRGB = function(x, y) { }
    /**
     * 保存<br>
     * <code>cn.veasion.auto.opencv.ImageWrapper#saveTo</code>
     * @param {string} path
     * @return void
     */
    this.saveTo = function(path) { }
}

/**
 * 图片
 */
function ImageBean() {
    /**
     * 加载图片<br>
     * <code>cn.veasion.auto.bind.bean.ImageBean#load</code>
     * @param {string} path
     * @return {ImageWrapper}
     */
    this.load = function(path) { }
    /**
     * 截图<br>
     * <code>cn.veasion.auto.bind.bean.ImageBean#loadByScreenshot</code>
     * @return {ImageWrapper}
     */
    this.loadByScreenshot = function() { }
    /**
     * 元素渲染成图片<br>
     * <code>cn.veasion.auto.bind.bean.ImageBean#loadByElement</code>
     * @param {WebElementBinding} element
     * @return {ImageWrapper}
     */
    this.loadByElement = function(element) { }
    /**
     * 根据图片链接OCR识别<br>
     * <code>cn.veasion.auto.bind.bean.ImageBean#ocrByUrl</code>
     * @param {string} imgUrl
     * @return {OcrResult}
     */
    this.ocrByUrl = function(imgUrl) { }
    /**
     * 加载网络图片<br>
     * <code>cn.veasion.auto.bind.bean.ImageBean#loadByUrl</code>
     * @param {string} url
     * @return {ImageWrapper}
     */
    this.loadByUrl = function(url) { }
    /**
     * 根据图片链接OCR识别验证码<br>
     * <code>cn.veasion.auto.bind.bean.ImageBean#captchaByUrl</code>
     * @param {string} imgUrl
     * @return {OcrResult}
     */
    this.captchaByUrl = function(imgUrl) { }
    /**
     * 根据元素OCR识别<br>
     * <code>cn.veasion.auto.bind.bean.ImageBean#ocrByElement</code>
     * @param {WebElementBinding} element
     * @return {OcrResult}
     */
    this.ocrByElement = function(element) { }
    /**
     * 根据图片OCR识别<br>
     * <code>cn.veasion.auto.bind.bean.ImageBean#ocrByImage</code>
     * @param {ImageWrapper} imageWrapper
     * @return {OcrResult}
     */
    this.ocrByImage = function(imageWrapper) { }
    /**
     * 根据图片OCR识别验证码<br>
     * <code>cn.veasion.auto.bind.bean.ImageBean#captchaByImage</code>
     * @param {ImageWrapper} imageWrapper
     * @return {OcrResult}
     */
    this.captchaByImage = function(imageWrapper) { }
    /**
     * 查找图片<br>
     * <code>cn.veasion.auto.bind.bean.ImageBean#findImage</code>
     * @param {ImageWrapper} image 主图片
     * @param {ImageWrapper} template 模板图片（被查找图片）
     * @param {number} threshold 相似度 0~1, 强阈值。该值用于检验最终匹配结果，以及在每一轮匹配中如果相似度大于该值则直接返回匹配结果
     * @return {PointWrapper}
     */
    this.findImage = function(image, template, threshold) { }
    /**
     * 查找图片<br>
     * <code>cn.veasion.auto.bind.bean.ImageBean#findImage</code>
     * @param {ImageWrapper} image 主图片
     * @param {ImageWrapper} template 模板图片（被查找图片）
     * @param {number} threshold 相似度 0~1, 强阈值。该值用于检验最终匹配结果，以及在每一轮匹配中如果相似度大于该值则直接返回匹配结果
     * @param {array?} region 找图区域 [x, y, width, height]
     * @return {PointWrapper}
     */
    this.findImage = function(image, template, threshold, region) { }
    /**
     * 查找图片<br>
     * <code>cn.veasion.auto.bind.bean.ImageBean#findImage</code>
     * @param {ImageWrapper} image 主图片
     * @param {ImageWrapper} template 模板图片（被查找图片）
     * @return {PointWrapper}
     */
    this.findImage = function(image, template) { }
    /**
     * 根据元素OCR识别验证码<br>
     * <code>cn.veasion.auto.bind.bean.ImageBean#captchaByElement</code>
     * @param {WebElementBinding} element
     * @return {OcrResult}
     */
    this.captchaByElement = function(element) { }
    /**
     * 匹配多个颜色<br>
     * <code>cn.veasion.auto.bind.bean.ImageBean#findMultiColors</code>
     * @param {ImageWrapper} image 图片
     * @param {string} firstColor 颜色代码 eg: #FFFFFF
     * @param {number} threshold 相识度 0-255，越小越匹配
     * @param {array} colorPoints 相对于第一个点的位置和颜色的数组, 如: [[x, y, color], [0, 3, '#FFFFFF'], [1, 6, '#000000']]
     * @param {array?} region 查找区域 [x, y, width, height]
     * @return {PointWrapper}
     */
    this.findMultiColors = function(image, firstColor, threshold, colorPoints, region) { }
    /**
     * 匹配多个颜色<br>
     * <code>cn.veasion.auto.bind.bean.ImageBean#findMultiColors</code>
     * @param {ImageWrapper} image 图片
     * @param {string} firstColor 颜色代码 eg: #FFFFFF
     * @param {number} threshold 相识度 0-255，越小越匹配
     * @param {array} colorPoints 相对于第一个点的位置和颜色的数组, 如: [[x, y, color], [0, 3, '#FFFFFF'], [1, 6, '#000000']]
     * @return {PointWrapper}
     */
    this.findMultiColors = function(image, firstColor, threshold, colorPoints) { }
    /**
     * 查找颜色<br>
     * <code>cn.veasion.auto.bind.bean.ImageBean#findColor</code>
     * @param {ImageWrapper} image 图片
     * @param {string} color 颜色代码 eg: #FFFFFF
     * @param {number} threshold 相识度 0-255，越小越匹配
     * @return {PointWrapper}
     */
    this.findColor = function(image, color, threshold) { }
    /**
     * 查找颜色<br>
     * <code>cn.veasion.auto.bind.bean.ImageBean#findColor</code>
     * @param {ImageWrapper} image 图片
     * @param {string} color 颜色代码 eg: #FFFFFF
     * @param {number} threshold 相识度 0-255，越小越匹配
     * @param {array?} region 查找区域 [x, y, width, height]
     * @return {PointWrapper}
     */
    this.findColor = function(image, color, threshold, region) { }
    /**
     * 查找颜色<br>
     * <code>cn.veasion.auto.bind.bean.ImageBean#findAllColor</code>
     * @param {ImageWrapper} image 图片
     * @param {string} color 颜色代码 eg: #FFFFFF
     * @param {number} threshold 相识度 0-255，越小越匹配
     * @param {array?} region 查找区域 [x, y, width, height]
     * @return {array}
     */
    this.findAllColor = function(image, color, threshold, region) { }
    /**
     * 查找颜色<br>
     * <code>cn.veasion.auto.bind.bean.ImageBean#findAllColor</code>
     * @param {ImageWrapper} image 图片
     * @param {string} color 颜色代码 eg: #FFFFFF
     * @param {number} threshold 相识度 0-255，越小越匹配
     * @return {array}
     */
    this.findAllColor = function(image, color, threshold) { }
}

const image = new ImageBean();

