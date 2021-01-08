package cn.veasion.auto.captcha.image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;

import cn.veasion.auto.captcha.image.hough.Gerade;
import cn.veasion.auto.captcha.image.hough.Hough;
import cn.veasion.auto.captcha.image.hough.HoughUtils;
import cn.veasion.auto.util.Constants;

/**
 * 图片操作类
 *
 * @author zhuowei.luo
 */
public class ImageUtil {

    /**
     * 等比例压缩图片
     */
    public static BufferedImage zoomImage(BufferedImage resource, float resize) {
        return zoomImage(resource, (int) (resource.getWidth() * resize), (int) (resource.getHeight() * resize));
    }

    /**
     * 固定宽高压缩图片
     */
    public static BufferedImage zoomImage(BufferedImage resource, int toWidth, int toHeight) {
        BufferedImage result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);
        result.getGraphics().drawImage(resource.getScaledInstance(toWidth, toHeight, Image.SCALE_SMOOTH), 0, 0, null);
        return result;
    }

    /**
     * 图片转字节
     */
    public static byte[] imageToBytes(Image image, String format) throws IOException {
        BufferedImage bImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics bg = bImage.getGraphics();
        bg.drawImage(image, 0, 0, null);
        bg.dispose();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(bImage, format == null ? "png" : format, out);
        return out.toByteArray();
    }

    /**
     * 图片转Base64
     */
    public static String imageToBase64(Image image, String format) throws IOException {
        return new String(Base64.getEncoder().encode(ImageUtil.imageToBytes(image, format)));
    }

    /**
     * 图片旋转 (失真比较大)
     *
     * @param src   原图片
     * @param angel 角度
     */
    public static BufferedImage rotate(final BufferedImage src, final double angel) {
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        // 重新计算图片旋转大小
        Rectangle rectDes = calcRotatedSize(new Rectangle(new Dimension(srcWidth, srcHeight)), angel);

        BufferedImage res;
        res = new BufferedImage(rectDes.width, rectDes.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = res.createGraphics();
        g2.setColor(Color.white);
        g2.fillRect(0, 0, rectDes.width, rectDes.height);
        // 旋转
        g2.translate((rectDes.width - srcWidth) / 2, (rectDes.height - srcHeight) / 2);
        g2.rotate(Math.toRadians(angel), srcWidth / 2, srcHeight / 2);
        g2.drawImage(src, null, null);
        g2.dispose();
        return res;
    }

    /**
     * 计算矩形旋转后的大小
     */
    public static Rectangle calcRotatedSize(Rectangle src, double angel) {
        angel = Math.abs(angel);
        if (angel >= 90) {
            if (angel / 90 % 2 == 1) {
                int temp = src.height;
                src.height = src.width;
                src.width = temp;
            }
            angel = angel % 90;
        }
        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
        double angelAlpha = (Math.PI - Math.toRadians(angel)) / 2;
        double angelDaltaWidth = Math.atan((double) src.height / src.width);
        double angelDaltaHeight = Math.atan((double) src.width / src.height);

        int lenDaltaWidth = (int) (len * Math.cos(Math.PI - angelAlpha - angelDaltaWidth));
        int lenDaltaHeight = (int) (len * Math.cos(Math.PI - angelAlpha - angelDaltaHeight));
        int desWidth = src.width + lenDaltaWidth * 2;
        int desHeight = src.height + lenDaltaHeight * 2;
        return new Rectangle(new Dimension(desWidth, desHeight));
    }

    /**
     * 图片转RGB
     */
    public static int[][][] imageToRGBArray(BufferedImage source) {
        int[][][] rgbArray = new int[source.getHeight()][source.getWidth()][3];
        for (int r = 0; r < source.getHeight(); r++)
            for (int c = 0; c < source.getWidth(); c++) {
                rgbArray[r][c][0] = (source.getRGB(c, r) >> 16) & 255;
                rgbArray[r][c][1] = (source.getRGB(c, r) >> 8) & 255;
                rgbArray[r][c][2] = (source.getRGB(c, r) >> 0) & 255;
            }
        return rgbArray;
    }

    /**
     * 图片矫正
     */
    public static BufferedImage imageHough(BufferedImage source) {
        List<Gerade> angles = new Hough(source).lineRange();
        double avg = HoughUtils.avgAngle(angles);
        double angel = HoughUtils.avgRangeAngle(angles, Constants.HOUGH_RANGE_VALUE);
        if (Math.round(Math.abs(avg - angel)) > Constants.HOUGH_RANGE_VALUE * 2) {
            angel = avg;
        }
        return rotate(source, Math.round(-angel));
    }

    /**
     * 图片截取
     */
    public static byte[] subImage(File imgFile, int x, int y, int w, int h) throws IOException {
        BufferedImage image = ImageIO.read(imgFile);
        image = image.getSubimage(x, y, w, h);
        return imageToBytes(image, null);
    }

    /**
     * 图片克隆
     */
    public static BufferedImage cloneImage(BufferedImage bufferedImage) {
        BufferedImage image = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), bufferedImage.getType());
        Graphics2D g = image.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.drawImage(bufferedImage, 0, 0, null);
        g.dispose();
        return image;
    }

    /**
     * 图片类型转换
     */
    public static BufferedImage toImageOfType(BufferedImage bufferedImage, int type) {
        if (bufferedImage == null) {
            throw new IllegalArgumentException("bufferedImage == null");
        }
        if (bufferedImage.getType() == type) {
            return bufferedImage;
        }
        BufferedImage image = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), type);
        Graphics2D g = image.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.drawImage(bufferedImage, 0, 0, null);
        g.dispose();
        return image;
    }

}
