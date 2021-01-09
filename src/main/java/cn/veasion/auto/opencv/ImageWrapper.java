package cn.veasion.auto.opencv;

import cn.veasion.auto.captcha.image.ImageUtil;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * ImageWrapper
 *
 * @author luozhuowei
 * @date 2021/1/8
 */
public class ImageWrapper {

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

    public static ImageWrapper ofImagePath(String path) throws IOException {
        return ofImage(ImageIO.read(new File(path)));
    }

    public static ImageWrapper ofImage(BufferedImage image) {
        if (image == null) {
            return null;
        }
        return new ImageWrapper(image);
    }

    public static ImageWrapper ofMat(Mat mat) {
        if (mat == null) {
            return null;
        }
        return new ImageWrapper(mat);
    }

    public int getWidth() {
        ensureNotRecycled();
        return mWidth;
    }

    public int getHeight() {
        ensureNotRecycled();
        return mHeight;
    }

    public Mat getMat() {
        ensureNotRecycled();
        if (mMat == null && mImage != null) {
            return OpenCVHelper.imageToMat(mImage);
        }
        return mMat;
    }

    public Mat getMat(int imageType, int matType) {
        ensureNotRecycled();
        if (mMat == null && mImage != null) {
            return OpenCVHelper.imageToMat(mImage, imageType, matType);
        }
        return mMat;
    }

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

    public int getRGB(int x, int y) {
        ensureNotRecycled();
        if (mImage != null) {
            return mImage.getRGB(x, y);
        }
        // return getImage().getRGB(x, y);
        double[] channels = mMat.get(x, y);
        return ColorUtils.rgb((int) channels[2], (int) channels[1], (int) channels[0]);
    }

    public BufferedImage getImage() {
        ensureNotRecycled();
        if (mImage == null && mMat != null) {
            return OpenCVHelper.matToImage(mMat);
        }
        return mImage;
    }

    public void recycle() {
        if (mImage != null) {
            mImage = null;
        }
        if (mMat != null) {
            OpenCVHelper.release(mMat);
            mMat = null;
        }
    }

    public void ensureNotRecycled() {
        if (mImage == null && mMat == null)
            throw new IllegalStateException("image has been recycled");
    }

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

}
