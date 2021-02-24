// 常用方法
// @author luozhuowei

load(env.getPath('/common/proxy.js'));

const auto = evalLoggerProxy('auto');

/**
 * 加载通用js
 *
 * @param {string} name js文件名称，多个用英文逗号隔开
 */
auto.loadCommon = function (name) {
    if (!auto.moduleLoaded) {
        auto.moduleLoaded = [];
    }
    if (name.indexOf(',') > -1) {
        name.split(',').forEach(function (n) {
            auto.loadCommon(n);
        });
        return
    }
    name = name.trim();
    if (!name.endsWith(".js")) {
        name = name + ".js";
    }
    if (auto.moduleLoaded.indexOf(name) !== -1) {
        return;
    }
    load(env.getSourcePath("/common/") + name);
    auto.moduleLoaded.push(name);
};

/**
 * 随机整数
 *
 * @param {number} m 最小数
 * @param {number} n 最大数
 * @return {number} 随机数
 */
auto.randNumber = function (m, n) {
    n = n + 1;
    return Math.floor(Math.random() * (m - n) + n);
};

/**
 * 随机手机号
 */
auto.randMobile = function () {
    return '151' + randCode(8);
};

/**
 * 滚动->顶部
 */
auto.scrollHome = function (count) {
    count = count ? count : 5;
    for (let i = 0; i < count; i++) {
        type('xpath=/html/body', env.get('KEY_HOME'));
        sleep(200);
    }
};

/**
 * 滚动->底部
 */
auto.scrollEnd = function (count) {
    count = count ? count : 5;
    for (let i = 0; i < count; i++) {
        type('xpath=/html/body', env.get('KEY_END'));
        sleep(200);
    }
};

/**
 * 滚动
 *
 * @param {WebElementBinding} element
 * @param {object?} x
 * @param {object?} y
 */
auto.scroll = function (element, x, y) {
    if (x == null && y == null) {
        executeScriptByParams('arguments[0].scrollTo(arguments[0].scrollWidth, arguments[0].scrollHeight)', [element]);
    } else if (x != null && y == null) {
        executeScriptByParams('arguments[0].scrollTo(arguments[1], arguments[0].scrollHeight)', [element, x]);
    } else if (x == null && y != null) {
        executeScriptByParams('arguments[0].scrollTo(arguments[0].scrollWidth, arguments[1])', [element, y]);
    } else {
        executeScriptByParams('arguments[0].scrollTo(arguments[1], arguments[2])', [element, x, y]);
    }
};

/**
 * 依赖模块
 *
 * @param {string} name 依赖模块名称
 * @param {object?} args 参数，如 { audit: true, status: 1 }
 * @param {string?} argsVar
 * @return {object} result
 */
auto.dependency = function (name, args, argsVar) {
    let result = null;
    auto.args = 'ARGS';
    auto.result = 'RESULT';
    withNewWindow(function () {
        name = name.trim();
        log.info('正在依赖' + name + '数据...');
        env.remove(auto.args);
        env.remove(auto.result);
        if (argsVar) {
            args = env.getOrDefault(argsVar, args);
        }
        env.putGlobal(auto.args, args || null);
        let path = '/dependency/' + name;
        if (path.endsWith(".js")) {
            path = env.getSourcePath(path);
        } else {
            path = env.getSourcePath(path + ".js");
        }
        runNewScript(path);
        result = env.get(auto.result);
        env.putGlobal(auto.args, null);
    });
    return result;
};

/**
 * 获取 icon 路径
 *
 * @param {string?} name
 */
auto.getIcon = function (name) {
    let iconPath = env.getPath('/files/');
    return iconPath + (name || 'icon_128.png')
};

/**
 * 查找左边同胞元素下的元素
 *
 * @param {WebElementBinding} element
 * @param {string} target
 * @return {WebElementBinding}
 */
auto.findLeftSibling = function (element, target) {
    let list = element.leftSibling();
    for (let i in list) {
        let obj = list[i].findOne(target);
        if (obj != null) {
            return obj;
        }
    }
    return null;
};

/**
 * 查找右边同胞元素下的元素
 *
 * @param {WebElementBinding} element
 * @param {string} target
 * @return {WebElementBinding}
 */
auto.findRightSibling = function (element, target) {
    let list = element.rightSibling();
    for (let i in list) {
        let obj = list[i].findOne(target);
        if (obj != null) {
            return obj;
        }
    }
    return null;
};