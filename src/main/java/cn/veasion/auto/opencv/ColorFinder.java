package cn.veasion.auto.opencv;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;

/**
 * 颜色查找
 *
 * @author luozhuowei
 * @date 2021/1/8
 */
public class ColorFinder {

    private ColorFinder() {
    }

    /**
     * 查找颜色
     *
     * @param image 图片
     * @param color 颜色 rbg
     * @return 第一个点坐标
     */
    public static Point findColor(ImageWrapper image, int color) {
        return findColor(image, color, null);
    }


    /**
     * 查找颜色
     *
     * @param image  图片
     * @param color  颜色 rbg
     * @param region 区域范围内查找，不指定则查整张图片
     * @return 第一个点坐标
     */
    public static Point findColor(ImageWrapper image, int color, Rect region) {
        return findColor(image, color, 0, region);
    }

    /**
     * 查找颜色
     *
     * @param image     图片
     * @param color     颜色 rbg
     * @param threshold 相识度 0-255，越小越匹配
     * @return 第一个点坐标
     */
    public static Point findColor(ImageWrapper image, int color, int threshold) {
        return findColor(image, color, threshold, null);
    }

    /**
     * 查找颜色
     *
     * @param image     图片
     * @param color     颜色 rbg
     * @param threshold 相识度 0-255，越小越匹配
     * @param region    区域范围内查找，不指定则查整张图片
     * @return 第一个点坐标
     */
    public static Point findColor(ImageWrapper image, int color, int threshold, Rect region) {
        MatOfPoint matOfPoint = findColorInner(image, color, threshold, region);
        if (matOfPoint == null) {
            return null;
        }
        Point point = matOfPoint.toArray()[0];
        if (region != null) {
            point.x = point.x + region.x;
            point.y = point.y + region.y;
        }
        OpenCVHelper.release(matOfPoint);
        return point;
    }

    /**
     * 匹配多个颜色
     *
     * @param image       图片
     * @param firstColor  第一个颜色 rgb
     * @param colorPoints 相对于第一个点的位置和颜色的数组, 如: [x1, y1, color1, x2, y2, color2, x3, y3, color3]
     * @return 第一个点坐标
     */
    public static Point findMultiColors(ImageWrapper image, int firstColor, int[] colorPoints) {
        return findMultiColors(image, firstColor, 0, null, colorPoints);
    }

    /**
     * 匹配多个颜色
     *
     * @param image       图片
     * @param firstColor  第一个颜色 rgb
     * @param threshold   相识度 0-255，越小越匹配
     * @param region      区域范围内查找，不指定则查整张图片
     * @param colorPoints 相对于第一个点的位置和颜色的数组, 如: [x1, y1, color1, x2, y2, color2, x3, y3, color3]
     * @return 第一个点坐标
     */
    public static Point findMultiColors(ImageWrapper image, int firstColor, int threshold, Rect region, int[] colorPoints) {
        Point[] firstPoints = findAllColor(image, firstColor, threshold, region);
        for (Point firstPoint : firstPoints) {
            if (firstPoint == null) {
                continue;
            }
            if (checksPath(image, firstPoint, threshold, colorPoints)) {
                return firstPoint;
            }
        }
        return null;
    }

    /**
     * 查找颜色
     *
     * @param image     图片
     * @param color     第一个颜色 rgb
     * @param threshold 相识度 0-255，越小越匹配
     * @param region    区域范围内查找，不指定则查整张图片
     * @return 所有匹配坐标
     */
    public static Point[] findAllColor(ImageWrapper image, int color, int threshold, Rect region) {
        MatOfPoint matOfPoint = findColorInner(image, color, threshold, region);
        if (matOfPoint == null) {
            return new Point[0];
        }
        Point[] points = matOfPoint.toArray();
        OpenCVHelper.release(matOfPoint);
        if (region != null) {
            for (int i = 0; i < points.length; i++) {
                points[i].x = points[i].x + region.x;
                points[i].y = points[i].y + region.y;
            }
        }
        return points;
    }

    private static MatOfPoint findColorInner(ImageWrapper image, int color, int threshold, Rect region) {
        Mat bi = new Mat();
        Mat mat = image.getMat();
        // HighGui.imshow("mat", mat);
        // HighGui.waitKey(0);

        // BGR
        Scalar lowerBound = new Scalar(ColorUtils.blue(color) - threshold, ColorUtils.green(color) - threshold, ColorUtils.red(color) - threshold);
        Scalar upperBound = new Scalar(ColorUtils.blue(color) + threshold, ColorUtils.green(color) + threshold, ColorUtils.red(color) + threshold);
        if (region != null) {
            Mat m = new Mat(mat, region);
            Core.inRange(m, lowerBound, upperBound, bi);
            OpenCVHelper.release(m);
        } else {
            Core.inRange(mat, lowerBound, upperBound, bi);
        }
        // ImageWrapper.ofImage(OpenCVHelper.matToImage(bi)).saveTo("C:\\Users\\Veasion\\Desktop\\bi_" + System.currentTimeMillis() + ".png");
        Mat nonZeroPos = new Mat();
        Core.findNonZero(bi, nonZeroPos);
        MatOfPoint result;
        if (nonZeroPos.rows() == 0 || nonZeroPos.cols() == 0) {
            result = null;
        } else {
            result = new MatOfPoint(nonZeroPos);
        }
        OpenCVHelper.release(bi);
        OpenCVHelper.release(nonZeroPos);
        return result;
    }

    private static boolean checksPath(ImageWrapper image, Point startingPoint, int threshold, int[] colorPoints) {
        for (int i = 0; i < colorPoints.length; i += 3) {
            int x = colorPoints[i];
            int y = colorPoints[i + 1];
            int color = colorPoints[i + 2];
            x += startingPoint.x;
            y += startingPoint.y;
            if (x >= image.getWidth() || y >= image.getHeight()
                    || x < 0 || y < 0) {
                return false;
            }
            int pixel = image.getRGB(x, y);
            if (!ColorUtils.equalsColor(color, pixel, threshold)) {
                return false;
            }
        }
        return true;
    }
}