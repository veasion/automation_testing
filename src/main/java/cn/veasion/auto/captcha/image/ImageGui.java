package cn.veasion.auto.captcha.image;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * ImageGui
 *
 * @author luozhuowei
 * @date 2021/3/2
 */
public class ImageGui extends JPanel {

    private static final long serialVersionUID = 1L;

    private BufferedImage image;
    private JFrame jframe = new JFrame();

    private ImageGui(String window, BufferedImage image) {
        super();
        jframe.add(this);
        jframe.setTitle(window);
        this.image = image;
        jframe.setSize(image.getWidth(), image.getHeight());
        jframe.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }

    public static ImageGui show(String window, BufferedImage image) {
        ImageGui imageGui = new ImageGui(window, image);
        imageGui.jframe.setVisible(true);
        imageGui.jframe.setLocationRelativeTo(null);
        return imageGui;
    }

}