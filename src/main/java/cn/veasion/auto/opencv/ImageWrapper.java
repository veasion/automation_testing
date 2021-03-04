package cn.veasion.auto.opencv;

import cn.veasion.auto.captcha.image.ImageUtil;
import cn.veasion.auto.util.Api;
import cn.veasion.auto.util.ApiDocumentGenerator;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

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

    private Mat mMat;
    private int mWidth;
    private int mHeight;
    private BufferedImage mImage;

    protected ImageWrapper(Mat mat) {
        mMat = mat;
        mWidth = mat.cols();
        mHeight = mat.rows();
    }

    protected ImageWrapper(BufferedImage image) {
        mImage = image;
        mWidth = image.getWidth();
        mHeight = image.getHeight();
    }

    protected ImageWrapper(BufferedImage image, Mat mat) {
        mImage = image;
        mMat = mat;
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

    @Api(generator = false)
    public static ImageWrapper ofMat(Mat mat) {
        if (mat == null) {
            return null;
        }
        return new ImageWrapper(mat);
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

    @Api(generator = false)
    public Mat getMat() {
        ensureNotRecycled();
        if (mMat == null && mImage != null) {
            return OpenCVHelper.imageToMat(mImage);
        }
        return mMat;
    }

    @Api(generator = false)
    public Mat getMat(int imageType, int matType) {
        ensureNotRecycled();
        if (mMat == null && mImage != null) {
            return OpenCVHelper.imageToMat(mImage, imageType, matType);
        }
        return mMat;
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
        } else {
            Imgcodecs.imwrite(path, mMat);
        }
    }

    @Api("获取RGB值")
    public int getRGB(int x, int y) {
        ensureNotRecycled();
        if (mImage != null) {
            return mImage.getRGB(x, y);
        }
        // return getImage().getRGB(x, y);
        double[] channels = mMat.get(x, y);
        return ColorUtils.rgb((int) channels[2], (int) channels[1], (int) channels[0]);
    }

    @Api(generator = false)
    public BufferedImage getImage() {
        ensureNotRecycled();
        if (mImage == null && mMat != null) {
            return OpenCVHelper.matToImage(mMat);
        }
        return mImage;
    }

    @Api(generator = false)
    public void recycle() {
        if (mImage != null) {
            mImage = null;
        }
        if (mMat != null) {
            OpenCVHelper.release(mMat);
            mMat = null;
        }
    }

    @Api(generator = false)
    public void ensureNotRecycled() {
        if (mImage == null && mMat == null) {
            throw new IllegalStateException("image has been recycled");
        }
    }

    @Api("克隆")
    public ImageWrapper clone() {
        ensureNotRecycled();
        if (mImage == null) {
            return ImageWrapper.ofMat(mMat.clone());
        }
        if (mMat == null) {
            return ImageWrapper.ofImage(ImageUtil.cloneImage(mImage));
        }
        return new ImageWrapper(ImageUtil.cloneImage(mImage), mMat.clone());
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
