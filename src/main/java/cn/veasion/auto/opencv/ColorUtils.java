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

    public static int rgb(int red, int green, int blue) {
        return (red << 16) | (green << 8) | blue;
    }

    public static int argb(int alpha, int red, int green, int blue) {
        return (alpha << 24) | rgb(red, green, blue);
    }

    public static int parseColor(String color) {
        return Color.decode(color).getRGB();
    }

    public static String colorToString(int color) {
        return colorToString(new Color(color));
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

    public static boolean equalsColor(int color1, int color2, int threshold) {
        int r1 = red(color1);
        int g1 = green(color1);
        int b1 = blue(color1);
        int r2 = red(color2);
        int g2 = green(color2);
        int b2 = blue(color2);
        return (Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2) <= threshold * 3);
    }

}
