package cn.veasion.auto.opencv;

import java.awt.Graphics;
import javax.swing.*;

/**
 * ImageGui
 *
 * @author luozhuowei
 * @date 2021/3/2
 */
public class ImageGui extends JPanel {

    private ImageWrapper imageWrapper;
    private JFrame jframe = new JFrame();

    private ImageGui(String window, ImageWrapper imageWrapper) {
        super();
        jframe.add(this);
        jframe.setTitle(window);
        this.imageWrapper = imageWrapper;
        jframe.setSize(imageWrapper.getWidth(), imageWrapper.getHeight());
        jframe.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(imageWrapper.getImage(), 0, 0, null);
    }

    public static ImageGui show(String window, ImageWrapper imageWrapper) {
        ImageGui imageGui = new ImageGui(window, imageWrapper);
        imageGui.jframe.setVisible(true);
        return imageGui;
    }

}