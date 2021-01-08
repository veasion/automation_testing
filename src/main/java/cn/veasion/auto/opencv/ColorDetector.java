package cn.veasion.auto.opencv;

/**
 * ColorDetector
 *
 * @author luozhuowei
 * @date 2021/1/8
 */
public interface ColorDetector {

    boolean detectsColor(int red, int green, int blue);

    abstract class AbstractColorDetector implements ColorDetector {

        protected final int mColor;
        protected final int mR, mG, mB;

        public AbstractColorDetector(int color) {
            mColor = color;
            mR = ColorUtils.red(color);
            mG = ColorUtils.green(color);
            mB = ColorUtils.blue(color);
        }
    }

    class EqualityDetector extends AbstractColorDetector {
        public EqualityDetector(int color) {
            super(color);
        }

        @Override
        public boolean detectsColor(int red, int green, int blue) {
            return mR == red && mG == green && mB == blue;
        }
    }

    class DifferenceDetector extends AbstractColorDetector {
        private final int mThreshold;

        public DifferenceDetector(int color, int threshold) {
            super(color);
            mThreshold = threshold * 3;
        }

        @Override
        public boolean detectsColor(int r, int g, int b) {
            return Math.abs(r - mR) + Math.abs(g - mG) + Math.abs(b - mB) <= mThreshold;
        }
    }

    class RDistanceDetector extends AbstractColorDetector {

        private final int mThreshold;

        public RDistanceDetector(int color, int threshold) {
            super(color);
            mThreshold = threshold;
        }

        @Override
        public boolean detectsColor(int R, int G, int B) {
            return Math.abs(mR - R) <= mThreshold;
        }
    }

    class RGBDistanceDetector extends AbstractColorDetector {

        private final int mThreshold;

        public RGBDistanceDetector(int color, int threshold) {
            super(color);
            mThreshold = threshold * threshold * 3;
        }

        @Override
        public boolean detectsColor(int r, int g, int b) {
            int dR = r - mR;
            int dG = g - mG;
            int dB = b - mB;
            int d = dR * dR + dG * dG + dB * dB;
            return d <= mThreshold;
        }
    }

    class WeightedRGBDistanceDetector extends AbstractColorDetector {

        private final int mThreshold;
        private final int mR, mG, mB;

        public WeightedRGBDistanceDetector(int color, int threshold) {
            super(color);
            mR = (color & 0xff0000) >> 16;
            mG = (color & 0x00ff00) >> 8;
            mB = color & 0xff;
            mThreshold = threshold * threshold * 8;
        }

        @Override
        public boolean detectsColor(int r, int g, int b) {
            int dR = r - mR;
            int dG = g - mG;
            int dB = b - mB;
            double meanR = (mR + r) / 2;
            double weightR = 2 + meanR / 256;
            double weightG = 4.0;
            double weightB = 2 + (255 - meanR) / 256;
            return weightR * dR * dR + weightG * dG * dG + weightB * dB * dB <= mThreshold;
        }
    }

    class HDistanceDetector extends AbstractColorDetector {

        private final int mH;
        private final int mThreshold;

        public HDistanceDetector(int color, int threshold) {
            super(color);
            mH = getH(mR, mG, mB);
            mThreshold = threshold;
        }

        @Override
        public boolean detectsColor(int r, int g, int b) {
            return Math.abs(mH - getH(r, g, b)) <= mThreshold;
        }

        private static int getH(int r, int g, int b) {
            int max, min, h;
            if (r > g) {
                min = Math.min(g, b);
                max = Math.max(r, b);
            } else {
                min = Math.min(r, b);
                max = Math.max(g, b);
            }
            if (r == max) {
                h = (g - b) / (max - min) * 60;
            } else if (g == max) {
                h = 120 + (b - r) / (max - min) * 60;
            } else {
                h = 240 + (r - g) / (max - min) * 60;
            }
            if (h < 0) h = h + 360;
            return h;
        }
    }

    class HSDistanceDetector extends AbstractColorDetector {

        private final int mH, mS;
        private final int mThreshold;

        public HSDistanceDetector(int color, int threshold) {
            super(color);
            long hs = getHS(mR, mG, mB);
            mH = (int) (hs & 0xffffffffL);
            mS = (int) ((hs >> 32) & 0xffffffffL);
            mThreshold = threshold * 3729600 / 255;
        }

        public HSDistanceDetector(int color, float similarity) {
            this(color, (int) (1.0f - similarity) * 255);
        }

        @Override
        public boolean detectsColor(int r, int g, int b) {
            long hs = getHS(r, g, b);
            int dH = (int) (hs & 0xffffffffL) - mH;
            int dS = (int) ((hs >> 32) & 0xffffffffL) - mS;
            return dH * dH + dS * dS <= mThreshold;
        }

        private static long getHS(int r, int g, int b) {
            int max, min, h;
            if (r > g) {
                min = Math.min(g, b);
                max = Math.max(r, b);
            } else {
                min = Math.min(r, b);
                max = Math.max(g, b);
            }
            if (r == max) {
                h = (g - b) / (max - min) * 60;
            } else if (g == max) {
                h = 120 + (b - r) / (max - min) * 60;
            } else {
                h = 240 + (r - g) / (max - min) * 60;
            }
            if (h < 0) h = h + 360;
            int s = (max - min) * 100 / max;
            return h & ((long) s << 32);
        }
    }

}