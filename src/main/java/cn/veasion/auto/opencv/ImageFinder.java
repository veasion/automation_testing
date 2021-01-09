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

    /**
     * 查找图片
     *
     * @param image    主图片
     * @param template 模板图片（被查找图片）
     * @return 模板所在位置左上角坐标
     */
    public static Point findImage(ImageWrapper image, ImageWrapper template) {
        return findImage(image, template, 0.9f, null);
    }

    /**
     * 查找图片
     *
     * @param image     主图片
     * @param template  模板图片（被查找图片）
     * @param threshold 相似度 0~1, 强阈值。该值用于检验最终匹配结果，以及在每一轮匹配中如果相似度大于该值则直接返回匹配结果。
     * @return 模板所在位置左上角坐标
     */
    public static Point findImage(ImageWrapper image, ImageWrapper template, float threshold) {
        return findImage(image, template, threshold, null);
    }

    /**
     * 查找图片
     *
     * @param image     主图片
     * @param template  模板图片（被查找图片）
     * @param threshold 相似度 0~1, 强阈值。该值用于检验最终匹配结果，以及在每一轮匹配中如果相似度大于该值则直接返回匹配结果。
     * @param region    找图区域
     * @return 模板所在位置左上角坐标
     */
    public static Point findImage(ImageWrapper image, ImageWrapper template, float threshold, Rect region) {
        return findImage(image, template, 0.7f, threshold, region, TemplateMatching.MAX_LEVEL_AUTO);
    }

    /**
     * 查找图片
     *
     * @param image         主图片
     * @param template      模板图片（被查找图片）
     * @param weakThreshold 弱阈值。该值用于在每一轮模板匹配中检验是否继续匹配。如果相似度小于该值，则不再继续匹配。
     * @param threshold     相似度 0~1, 强阈值。该值用于检验最终匹配结果，以及在每一轮匹配中如果相似度大于该值则直接返回匹配结果。
     * @param region        找图区域
     * @param maxLevel      金字塔的层次, -1 表示自动, level越大可能带来越高的找图效率，但也可能造成找图失败
     * @return 模板所在位置左上角坐标
     */
    public static Point findImage(ImageWrapper image, ImageWrapper template, float weakThreshold, float threshold, Rect region, int maxLevel) {
        if (image == null)
            throw new NullPointerException("image = null");
        if (template == null)
            throw new NullPointerException("template = null");
        Mat src = image.getMat();
        if (region != null) {
            src = new Mat(src, region);
        }
        Point point = TemplateMatching.fastTemplateMatching(src, template.getMat(), TemplateMatching.MATCHING_METHOD_DEFAULT, weakThreshold, threshold, maxLevel);
        if (point != null) {
            if (region != null) {
                point.x += region.x;
                point.y += region.y;
            }
        }
        if (src != image.getMat()) {
            OpenCVHelper.release(src);
        }
        return point;
    }

}
