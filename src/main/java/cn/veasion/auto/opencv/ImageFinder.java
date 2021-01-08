package cn.veasion.auto.opencv;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;

/**
 * 图片查找
 *
 * @author luozhuowei
 * @date 2021/1/8
 */
public class ImageFinder {

    private ImageFinder() {
    }

    public static Point findImage(ImageWrapper image, ImageWrapper template) {
        return findImage(image, template, 0.9f, null);
    }

    public static Point findImage(ImageWrapper image, ImageWrapper template, float threshold) {
        return findImage(image, template, threshold, null);
    }

    public static Point findImage(ImageWrapper image, ImageWrapper template, float threshold, Rect rect) {
        return findImage(image, template, 0.7f, threshold, rect, TemplateMatching.MAX_LEVEL_AUTO);
    }

    public static Point findImage(ImageWrapper image, ImageWrapper template, float weakThreshold, float threshold, Rect rect, int maxLevel) {
        if (image == null)
            throw new NullPointerException("image = null");
        if (template == null)
            throw new NullPointerException("template = null");
        Mat src = image.getMat();
        if (rect != null) {
            src = new Mat(src, rect);
        }
        Point point = TemplateMatching.fastTemplateMatching(src, template.getMat(), TemplateMatching.MATCHING_METHOD_DEFAULT, weakThreshold, threshold, maxLevel);
        if (point != null) {
            if (rect != null) {
                point.x += rect.x;
                point.y += rect.y;
            }
        }
        if (src != image.getMat()) {
            OpenCVHelper.release(src);
        }
        return point;
    }

}
