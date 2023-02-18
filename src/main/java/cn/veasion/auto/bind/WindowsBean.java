package cn.veasion.auto.bind;

import cn.veasion.auto.bind.base.AbstractInitializingBean;
import cn.veasion.auto.util.ImageWrapper;
import cn.veasion.auto.core.ResultProxy;
import cn.veasion.auto.util.Api;
import cn.veasion.auto.util.PointWrapper;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

/**
 * WindowsBean
 *
 * @author luozhuowei
 * @date 2021/3/2
 */
@SuppressWarnings("unused")
@Api.ClassInfo(value = "windows", desc = "桌面")
public class WindowsBean extends AbstractInitializingBean {

    private static Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    @Api
    public String cmd(String command) throws IOException {
        Process p = Runtime.getRuntime().exec(command);
        String line;
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\r\n");
        }
        reader.close();
        p.destroy();
        return sb.toString();
    }

    @Api("截图")
    public ImageWrapper getScreenshot() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRectangle = new Rectangle(screenSize);
        BufferedImage screenCapture = robot.createScreenCapture(screenRectangle);
        return ImageWrapper.ofImage(screenCapture);
    }

    @Api("宽度")
    public int getWidth() {
        return Toolkit.getDefaultToolkit().getScreenSize().width;
    }

    @Api("高度")
    public int getHeight() {
        return Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    @Api("点击")
    @ResultProxy(value = false, interval = true)
    public WindowsBean click() {
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        return this;
    }

    @Api("鼠标移动")
    @ResultProxy(value = false, interval = true)
    public WindowsBean mouseMove(@Api.Param(jsType = "number") Object x, @Api.Param(jsType = "number") Object y) {
        int pointX = new BigDecimal(x.toString()).intValue();
        int pointY = new BigDecimal(y.toString()).intValue();
        moveMouse(pointX, pointY);
        return this;
    }

    @Api("获取鼠标位置")
    @ResultProxy(value = false, interval = true)
    public PointWrapper getMouseLocation() {
        return PointWrapper.ofPoint(MouseInfo.getPointerInfo().getLocation());
    }

    @Api("点击坐标")
    @ResultProxy(value = false, interval = true)
    public WindowsBean clickPoint(@Api.Param(jsType = "number") Object x, @Api.Param(jsType = "number") Object y) {
        mouseMove(x, y);
        click();
        return this;
    }

    @Api("键入字符串")
    @ResultProxy(value = false, interval = true)
    public WindowsBean sendKey(String text) {
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        return this;
    }

    @Api
    @ResultProxy(value = false, interval = true)
    public WindowsBean keyPress(int keycode) {
        robot.keyPress(keycode);
        return this;
    }

    @Api
    @ResultProxy(value = false, interval = true)
    public WindowsBean keyRelease(int keycode) {
        robot.keyRelease(keycode);
        return this;
    }

    @Api
    @ResultProxy(value = false, interval = true)
    public WindowsBean leftMousePress() {
        robot.mousePress(InputEvent.BUTTON1_MASK);
        return this;
    }

    @Api
    @ResultProxy(value = false, interval = true)
    public WindowsBean leftMouseRelease() {
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        return this;
    }

    @Api
    @ResultProxy(value = false, interval = true)
    public WindowsBean rightMousePress() {
        robot.mousePress(InputEvent.BUTTON3_MASK);
        return this;
    }

    @Api
    @ResultProxy(value = false, interval = true)
    public WindowsBean rightMouseRelease() {
        robot.mouseRelease(InputEvent.BUTTON3_MASK);
        return this;
    }

    @Api
    @ResultProxy(value = false, interval = true)
    public WindowsBean mousePress(int buttons) {
        robot.mousePress(buttons);
        return this;
    }

    @Api
    @ResultProxy(value = false, interval = true)
    public WindowsBean mouseRelease(int buttons) {
        robot.mouseRelease(buttons);
        return this;
    }

    @Api
    @ResultProxy(value = false, log = false)
    public String getDesktopDir() {
        return javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
    }

    private void moveMouse(int x, int y) {
        robot.mouseMove(-1, -1);
        for (int i = 0; i < 5; i++) {
            robot.mouseMove(x, y);
        }
    }

}
