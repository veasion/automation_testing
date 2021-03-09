package cn.veasion.auto.opencv;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

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