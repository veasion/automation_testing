// HTTP 公共方法
// @author luozhuowei

load(env.getPath('/common/proxy.js'));

const http = evalLoggerProxy('http');

/**
 * GET 请求
 *
 * @param {string} url 请求url/uri
 * @param {object?} headers 请求头
 * @return {object} { status: 200, success: true, data: (string), headers: {} }
 */
http.get = function (url, headers) {
    return http.request(url, 'GET', null, headers);
};

/**
 * POST 请求
 *
 * @param {string} url 请求url/uri
 * @param {object?} body 请求body内容
 * @param {object?} headers 请求头
 * @return {object} { status: 200, success: true, data: (string), headers: {} }
 */
http.post = function (url, body, headers) {
    return http.request(url, 'POST', body, headers);
};

/**
 * 请求
 *
 * @param {string} url 请求url/uri
 * @param {string?} method 请求方式 POST/GET 默认GET
 * @param {object?} body 请求body内容
 * @param {object?} headers 请求头
 * @return {object} { status: 200, success: true, data: (string), headers: {} }
 */
http.request = function (url, method, body, headers) {
    if (!headers) {
        headers = {};
    }
    if (!headers['Content-Type']) {
        headers['Content-Type'] = 'application/json;charset=UTF-8';
    }
    if (!headers['Cookie']) {
        headers['Cookie'] = http.getCookie();
    }
    if (body && typeof body === 'object') {
        body = JSON.stringify(body);
    }
    let response = request(url, method || 'GET', body, headers);
    if (!response.success) {
        throw new Error('请求失败！http response status: ' + response.status)
    }
    return response;
};

/**
 * 获取 Cookie
 *
 * @param {string?} key
 * @return {string|null}
 */
http.getCookie = function (key) {
    try {
        let cookies = executeScript('return document.cookie') + '';
        if (!key) {
            return cookies;
        }
        let start = cookies.indexOf(key + '=');
        if (start < 0) {
            return null;
        }
        let end = cookies.indexOf(';', start);
        if (end < 0) {
            end = cookies.length;
        }
        return cookies.substring(start + key.length + 1, end);
    } catch (e) {
        return null;
    }
};

/**
 * 获取当前浏览器URL
 *
 * @return {string}
 */
http.getCurrentUrl = function () {
    return executeScript('return location.href') + '';
};

/**
 * 获取当前浏览器URL域名
 *
 * @return {string}
 */
http.getCurrentDomainUrl = function () {
    return executeScript('return location.origin') + '/';
};

/**
 * 获取Trace数据
 *
 * @param {string} trace
 * @return {string|null}
 */
http.traceInfo = function (trace) {
    sleep(800);
    if (trace.indexOf('http') > -1) {
        trace = trace.substring(trace.indexOf('http'));
        trace = trace.replace('/zipkin//', '/zipkin/');
        trace = trace.replace('zipkin_ip', env.getString('ZIPKIN_IP'));
        let url = trace.replace('/zipkin/traces/', '/zipkin/api/v2/trace/');
        let response = request(url, 'GET');
        if (response.status === 404 && response.data) {
            response = request(url.replace('/v2/', '/v1/'), 'GET');
        }
        return response.data ? response.data.toString() : null;
    }
    return null;
};