```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class ShapeEditor {
    private JFrame frame;
    private JPanel panel;
    private Graphics2D g2d;
    private Color color;
    private int x, y, width, height;

    public ShapeEditor() {
        color = Color.BLACK;
        frame = new JFrame("Shape Editor");
        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(color);
                g.fillOval(x, y, width, height);
            }
        };
        g2d = (Graphics2D) panel.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                x = e.getX();
                y = e.getY();
            }
        });
        frame.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                g2d.setColor(color);
                g2d.fillOval(x, y, width, height);
                panel.repaint();
            }
        });
        frame.addMouseMotionListener(new MouseWheelAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() > 0) {
                    height += 10;
                } else {
                    height -= 10;
                }
                panel.repaint();
            }
        });
    }

    public void resize(int width, int height) {
        x = width / 2;
        y = height / 2;
        width = width;
        height = height;
        panel.setPreferredSize(new Dimension(width, height));
        panel.repaint();
    }

    public void paintCircle(int x, int y, int radius) {
        g2d.setColor(color);
        g2d.fillOval(x, y, radius * 2, radius * 2);
    }

    public void paintRectangle(int x, int y, int width, int height) {
        g2d.setColor(color);
        g2d.fillRect(x, y, width, height);
    }

    public void changeColor(Color color) {
        this.color = color;
        panel.repaint();
    }

    public static void main(String[] args) {
        new ShapeEditor();
    }
}
```