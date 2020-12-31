package cn.veasion.auto.captcha.image.hough;

import java.awt.image.BufferedImage;
import java.util.List;

public class Hough {

    private static final int edgeFilter = 30;

    private HoughSpace houghSpace;
    private BufferedImage spriteImage;
    private BufferedImage farbAenderungsImage;

    private HoughUtils eckenErkennungsWerkzeug = new HoughUtils();

    public Hough(BufferedImage spriteImage) {
        this.spriteImage = spriteImage;
    }

    public List<Gerade> lineRange() {
        farbAenderungsImage = eckenErkennungsWerkzeug.performFarbaenderungsErkennungRGB(spriteImage);
        farbAenderungsImage = eckenErkennungsWerkzeug.filterSprite(farbAenderungsImage, edgeFilter);
        houghSpace = eckenErkennungsWerkzeug.calcHoughSpace(farbAenderungsImage);
        return houghSpace.returnMaxima();
    }

}
