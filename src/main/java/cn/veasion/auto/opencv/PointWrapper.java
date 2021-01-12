package cn.veasion.auto.opencv;

import cn.veasion.auto.util.Api;
import cn.veasion.auto.util.ApiDocumentGenerator;
import org.opencv.core.Point;

/**
 * PointWrapper
 *
 * @author luozhuowei
 * @date 2021/1/12
 */
@Api.ClassInfo(desc = "点位置")
public class PointWrapper implements ApiDocumentGenerator.DocGenerator {

    private double x;
    private double y;

    public PointWrapper(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Api(generator = false)
    public static PointWrapper ofPoint(Point point) {
        if (point == null) {
            return null;
        }
        return new PointWrapper(point.x, point.y);
    }

    @Api(generator = false)
    public static PointWrapper ofPoint(java.awt.Point point) {
        if (point == null) {
            return null;
        }
        return new PointWrapper(point.x, point.y);
    }

    @Api("x")
    public double getX() {
        return x;
    }

    @Api("y")
    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PointWrapper{");
        sb.append("x=").append(x);
        sb.append(", y=").append(y);
        sb.append('}');
        return sb.toString();
    }
}
