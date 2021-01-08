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

    public static Point findColor(ImageWrapper imageWrapper, int color) {
        return findColor(imageWrapper, color, null);
    }

    public static Point findColor(ImageWrapper imageWrapper, int color, Rect region) {
        return findColor(imageWrapper, color, 0, region);
    }

    public static Point findColor(ImageWrapper imageWrapper, int color, int threshold) {
        return findColor(imageWrapper, color, threshold, null);
    }

    public static Point findColor(ImageWrapper image, int color, int threshold, Rect rect) {
        MatOfPoint matOfPoint = findColorInner(image, color, threshold, rect);
        if (matOfPoint == null) {
            return null;
        }
        Point point = matOfPoint.toArray()[0];
        if (rect != null) {
            point.x = point.x + rect.x;
            point.y = point.y + rect.y;
        }
        OpenCVHelper.release(matOfPoint);
        return point;
    }

    /**
     * 匹配多个颜色
     *
     * @param firstColor  第一个颜色
     * @param threshold   相识度 0-255，越小越匹配
     * @param rect        区域范围内查找，不指定则查整张图片
     * @param colorPoints 相对于第一个点的位置和颜色的数组, 如: [x1, y1, color1, x2, y2, color2, x3, y3, color3]
     * @return 第一个点坐标
     */
    public static Point findMultiColors(ImageWrapper image, int firstColor, int threshold, Rect rect, int[] colorPoints) {
        Point[] firstPoints = findAllPointsForColor(image, firstColor, threshold, rect);
        for (Point firstPoint : firstPoints) {
            if (firstPoint == null)
                continue;
            if (checksPath(image, firstPoint, threshold, colorPoints)) {
                return firstPoint;
            }
        }
        return null;
    }

    public static Point[] findAllPointsForColor(ImageWrapper image, int color, int threshold, Rect rect) {
        MatOfPoint matOfPoint = findColorInner(image, color, threshold, rect);
        if (matOfPoint == null) {
            return new Point[0];
        }
        Point[] points = matOfPoint.toArray();
        OpenCVHelper.release(matOfPoint);
        if (rect != null) {
            for (int i = 0; i < points.length; i++) {
                points[i].x = points[i].x + rect.x;
                points[i].y = points[i].y + rect.y;
            }
        }
        return points;
    }

    private static MatOfPoint findColorInner(ImageWrapper image, int color, int threshold, Rect rect) {
        Mat bi = new Mat();
        Mat mat = image.getMat();
        // HighGui.imshow("mat", mat);
        // HighGui.waitKey(0);

        // BGR
        Scalar lowerBound = new Scalar(ColorUtils.blue(color) - threshold, ColorUtils.green(color) - threshold, ColorUtils.red(color) - threshold);
        Scalar upperBound = new Scalar(ColorUtils.blue(color) + threshold, ColorUtils.green(color) + threshold, ColorUtils.red(color) + threshold);
        if (rect != null) {
            Mat m = new Mat(mat, rect);
            Core.inRange(m, lowerBound, upperBound, bi);
            OpenCVHelper.release(m);
        } else {
            Core.inRange(mat, lowerBound, upperBound, bi);
        }
        // ImageWrapper.ofImage(OpenCVHelper.matToImage(bi)).saveTo("C:\\Users\\user\\Desktop\\bi_" + System.currentTimeMillis()+".png");
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
            ColorDetector colorDetector = new ColorDetector.DifferenceDetector(color, threshold);
            x += startingPoint.x;
            y += startingPoint.y;
            if (x >= image.getWidth() || y >= image.getHeight()
                    || x < 0 || y < 0) {
                return false;
            }
            int c = image.pixel(x, y);
            if (!colorDetector.detectsColor(ColorUtils.red(c), ColorUtils.green(c), ColorUtils.blue(c))) {
                return false;
            }
        }
        return true;
    }
}