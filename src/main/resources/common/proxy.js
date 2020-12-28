// 代理
// @author luozhuowei

// idea 忽略 js 错误设置
// ctrl + alt + shift + h >  None

/**
 * 代理类（代理 obj 的方法 & 属性，不代理结果对象）
 *
 * @param obj 代理对象
 * @param method 代理函数调用(obj, name, args, apply)
 * @param property 代理属性调用(obj, name, apply)
 */
function JavascriptProxy(obj, method, property) {
    return {
        __noSuchProperty__: function (name) {
            return property ? property(obj, name, function () {
                return obj[name];
            }) : obj[name];
        },
        __noSuchMethod__: function () {
            let args = [];
            let name = arguments[0];
            for (let i = 1; i < arguments.length; i++) {
                args[i - 1] = arguments[i];
            }
            if (!obj[name]) {
                log.error('函数不存在 ====> ' + name);
            }
            return method ? method(obj, name, args, function () {
                return obj[name].apply(obj, args);
            }) : obj[name].apply(obj, args);
        }
    }
}

/**
 * 代理适配类 (代理 obj 和 结果对象的所有方法 & 属性)
 *
 * @param obj 初始对象
 * @param method 代理函数调用(obj, name, args, apply)
 * @param property 代理属性调用(obj, name, apply)
 */
function ProxyAdapter(obj, method, property) {
    obj = obj || {};
    return new JSAdapter()
    {
        __put__: function(name, value) {
            obj[name] = value;
        },
        __delete__: function(name) {
            delete obj[name];
        },
        __getIds__: function() {
            return Object.keys(obj);
        },
        __getValues__: function() {
            return Object.keys(obj);
        },
        __has__: function(name) {
            return obj[name] != null;
        },
        __get__: function(name) {
            return property ? property(obj, name, function () {
                return obj[name];
            }) : obj[name];
        },
        __call__: function(name) {
            if (!obj[name]) {
                log.error('函数不存在 ====> ' + name);
            }
            let args = [];
            for (let i = 1; i < arguments.length; i++) {
                args[i - 1] = arguments[i];
            }
            return method ? method(obj, name, args, function () {
                return obj[name].apply(obj, args);
            }) : obj[name].apply(obj, args);
        }
    }
}

/**
 * 函数日志
 *
 * @param obj 初始对象
 * @param varName 变量名称
 */
function LoggerProxy(obj, varName) {
    return new ProxyAdapter(obj, function (obj, name, args, apply) {
        let key = 'DISABLE_PROXY_LOG';
        let def = env.get(key);
        try {
            env.put(key, true)
            if (def != true) {
                log.invokeMethod(varName + '.' + name, args);
            }
            return apply();
        } finally {
            env.put(key, def || false);
        }
    });
}

/**
 * 函数日志代理，不影响 idea 提示解决方案
 *
 * @param v 变量名称
 */
function evalLoggerProxy(v) {
    // return new LoggerProxy({}, v);
    return eval("new LoggerProxy({}, '" + v + "')");
}