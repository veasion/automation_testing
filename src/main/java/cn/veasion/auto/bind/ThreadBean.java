package cn.veasion.auto.bind;

import cn.veasion.auto.bind.base.AbstractInitializingBean;
import cn.veasion.auto.core.ResultProxy;
import cn.veasion.auto.util.Api;
import cn.veasion.auto.util.AutomationException;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * ThreadBean
 *
 * @author luozhuowei
 * @date 2021/5/29
 */
@SuppressWarnings("unused")
@Api.ClassInfo(value = "thread", desc = "线程")
public class ThreadBean extends AbstractInitializingBean {

    @Api("多线程执行")
    @ResultProxy(value = false)
    public void runAndWaitThreads(@Api.Param(desc = "函数集合", jsType = "Function[]") ScriptObjectMirror... functions) throws InterruptedException {
        if (functions == null || functions.length <= 0) {
            return;
        }
        for (int i = 0; i < functions.length; i++) {
            if (functions[i] == null || !functions[i].isFunction()) {
                throw new AutomationException("threads `functions[" + i + "]` is not function");
            }
        }
        CountDownLatch countDownLatch = new CountDownLatch(functions.length);
        for (ScriptObjectMirror function : functions) {
            new Thread(() -> {
                try {
                    function.call(null);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            }).start();
        }
        countDownLatch.await();
    }

    @Api("线程安全集合")
    @ResultProxy(value = false, log = false)
    public List<Object> synchronizedList() {
        return Collections.synchronizedList(new ArrayList<>());
    }

    @Api("线程安全字典")
    @ResultProxy(value = false, log = false)
    public Map<Object, Object> synchronizedMap() {
        return Collections.synchronizedMap(new HashMap<>());
    }

}
