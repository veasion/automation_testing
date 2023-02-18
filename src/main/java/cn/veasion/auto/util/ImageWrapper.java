package cn.veasion.auto.util;

import cn.veasion.auto.captcha.image.ImageGui;
import cn.veasion.auto.captcha.image.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigDecimal;

/**
 * ImageWrapper
 *
 * @author luozhuowei
 * @date 2021/1/8
 */
@Api.ClassInfo(desc = "图片")
public class ImageWrapper implements ApiDocumentGenerator.DocGenerator {

    private int mWidth;
    private int mHeight;
    private BufferedImage mImage;

    protected ImageWrapper(BufferedImage image) {
        mImage = image;
        mWidth = image.getWidth();
        mHeight = image.getHeight();
    }

    public ImageWrapper(int width, int height) {
        this(new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR));
    }

    @Api(generator = false)
    public static ImageWrapper ofImagePath(String path) throws IOException {
        return ofImage(ImageIO.read(new File(path)));
    }

    @Api(generator = false)
    public static ImageWrapper ofImage(BufferedImage image) {
        if (image == null) {
            return null;
        }
        return new ImageWrapper(image);
    }

    @Api("宽度")
    public int getWidth() {
        ensureNotRecycled();
        return mWidth;
    }

    @Api("高度")
    public int getHeight() {
        ensureNotRecycled();
        return mHeight;
    }

    @Api("保存")
    public void saveTo(String path) {
        ensureNotRecycled();
        if (mImage != null) {
            try {
                ImageIO.write(mImage, "png", new FileOutputStream(path));
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

    @Api("获取RGB值")
    public int getRGB(int x, int y) {
        ensureNotRecycled();
        return mImage.getRGB(x, y);
    }

    @Api(generator = false)
    public BufferedImage getImage() {
        ensureNotRecycled();
        return mImage;
    }

    @Api(generator = false)
    public void recycle() {
        mImage = null;
    }

    @Api(generator = false)
    public void ensureNotRecycled() {
        if (mImage == null) {
            throw new IllegalStateException("image has been recycled");
        }
    }

    @Api("克隆")
    public ImageWrapper clone() {
        ensureNotRecycled();
        return new ImageWrapper(ImageUtil.cloneImage(mImage));
    }

    @Api("查看图片")
    public void show() {
        ImageGui.show("查看图片", getImage());
    }

    @Api("查看图片")
    public void show(@Api.Param(jsType = "number") Object x, @Api.Param(jsType = "number") Object y) {
        show(x, y, null, null);
    }

    @Api("查看图片")
    public void show(@Api.Param(jsType = "number") Object x, @Api.Param(jsType = "number") Object y, @Api.Param(jsType = "number") Object width, @Api.Param(jsType = "number") Object height) {
        BufferedImage image = getImage();
        BufferedImage res = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        if (x != null && y != null) {
            Graphics2D graphics = res.createGraphics();
            graphics.drawImage(image, null, null);
            graphics.setColor(Color.RED);
            graphics.setStroke(new BasicStroke(3.0f));
            int pointX = parseInt(x);
            int pointY = parseInt(y);
            graphics.drawLine(Math.max(pointX - 100, 0), Math.max(pointY - 100, 0), pointX, pointY);
            graphics.drawLine(pointX, pointY - 20, pointX, pointY);
            graphics.drawLine(pointX - 20, pointY, pointX, pointY);
            if (width != null && height != null) {
                graphics.drawRect(pointX, pointY, parseInt(width), parseInt(height));
            }
            graphics.dispose();
        }
        ImageGui.show("查看图片", res);
    }

    private int parseInt(Object object) {
        return new BigDecimal(object.toString()).intValue();
    }

}
