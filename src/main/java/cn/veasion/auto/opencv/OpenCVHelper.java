package cn.veasion.auto.opencv;

import cn.veasion.auto.captcha.image.ImageUtil;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

/**
 * OpenCVHelper
 *
 * @author luozhuowei
 * @date 2021/1/7
 */
public class OpenCVHelper {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static BufferedImage matToImage(Mat matrix) {
        // return (BufferedImage) org.opencv.highgui.HighGui.toBufferedImage(matrix);
        MatOfByte mob = new MatOfByte();
        Imgcodecs.imencode(".png", matrix, mob);
        byte[] byteArray = mob.toArray();
        BufferedImage bufImage;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return bufImage;
    }

    public static Mat imageToMat(BufferedImage bufferedImage) {
        // return imageToMat(bufferedImage, BufferedImage.TYPE_BYTE_GRAY, CvType.CV_8UC1);
        // return imageToMat(bufferedImage, BufferedImage.TYPE_4BYTE_ABGR, CvType.CV_8UC4);
        return imageToMat(bufferedImage, BufferedImage.TYPE_3BYTE_BGR, CvType.CV_8UC3);
    }

    /**
     * BufferedImage转换成Mat
     *
     * @param bufferedImage 要转换的BufferedImage
     * @param imageType     图片类型 如 BufferedImage.TYPE_3BYTE_BGR
     * @param matType       转换成mat的type 如 CvType.CV_8UC3
     */
    public static Mat imageToMat(BufferedImage bufferedImage, int imageType, int matType) {
        if (bufferedImage == null) {
            throw new IllegalArgumentException("bufferedImage == null");
        }
        bufferedImage = ImageUtil.toImageOfType(bufferedImage, imageType);
        byte[] pixels = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
        Mat mat = Mat.eye(bufferedImage.getHeight(), bufferedImage.getWidth(), matType);
        mat.put(0, 0, pixels);
        return mat;
    }

    public static void release(MatOfPoint mat) {
        if (mat == null)
            return;
        mat.release();
    }

    public static void release(Mat mat) {
        if (mat == null)
            return;
        mat.release();
    }

    public static void main(String[] args) throws IOException {
        ImageWrapper imageWrapper = ImageWrapper.ofImagePath("C:\\Users\\user\\Desktop\\test.jpg");
        ImageWrapper templateWrapper = ImageWrapper.ofImagePath("C:\\Users\\user\\Desktop\\template.png");
        Point imagePoint = ImageFinder.findImage(imageWrapper, templateWrapper);
        System.out.println("image: " + imagePoint); // 255, 58
        Point colorPoint = ColorFinder.findColor(imageWrapper, ColorUtils.parseColor("#ECC571"), 4);
        System.out.println("color: " + colorPoint); // 278, 62
        colorPoint = ColorFinder.findColor(imageWrapper, ColorUtils.parseColor("#E31716"), 4);
        System.out.println("color: " + colorPoint); // 233, 170
        colorPoint = ColorFinder.findColor(imageWrapper, ColorUtils.parseColor("#25E500"), 4);
        System.out.println("color: " + colorPoint); // 126, 225
        // TODO bug
        colorPoint = ColorFinder.findMultiColors(imageWrapper, ColorUtils.parseColor("#E51B1B"), 4, null, new int[]{
                // x, y, color
                0, 4, ColorUtils.parseColor("#D62629"),
                3, 4, ColorUtils.parseColor("#D62629")
        });
        System.out.println("multi color: " + colorPoint);
    }

}
