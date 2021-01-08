package cn.veasion.auto.opencv;

import java.awt.Color;

/**
 * ColorUtils
 *
 * @author luozhuowei
 * @date 2021/1/8
 */
public class ColorUtils {

    private ColorUtils() {
    }

    public static int red(int color) {
        return (color >> 16) & 0xFF;
    }

    public static int green(int color) {
        return (color >> 8) & 0xFF;
    }

    public static int blue(int color) {
        return (color >> 0) & 0xFF;
    }

    public static int argb(int alpha, int red, int green, int blue) {
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

    public static int parseColor(String color) {
        return Color.decode(color).getRGB();
    }

    public static String colorToString(Color color) {
        String r = Integer.toHexString(color.getRed());
        r = r.length() < 2 ? ('0' + r) : r;
        String g = Integer.toHexString(color.getGreen());
        g = g.length() < 2 ? ('0' + g) : g;
        String b = Integer.toHexString(color.getBlue());
        b = b.length() < 2 ? ('0' + b) : b;
        return ('#' + r + g + b).toUpperCase();
    }

}
