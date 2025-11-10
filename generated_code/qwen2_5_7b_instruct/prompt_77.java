```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VirtualWhiteboard extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private DrawingPanel drawingPanel;

    public VirtualWhiteboard() {
        setTitle("Virtual Whiteboard");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        drawingPanel = new DrawingPanel();
        add(drawingPanel);

        addShape("circle", 100, 100, 50);
        addShape("rectangle", 300, 100, 100, 50);
        addShape("line", 500, 100, 700, 100);
    }

    private void addShape(String type, int x, int y, int width, int height) {
        Shape shape = new Shape(type, x, y, width, height);
        drawingPanel.addShape(shape);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VirtualWhiteboard whiteboard = new VirtualWhiteboard();
            whiteboard.setVisible(true);
        });
    }

    private class DrawingPanel extends JPanel {
        private ArrayList<Shape> shapes = new ArrayList<>();

        public void addShape(Shape shape) {
            shapes.add(shape);
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Shape shape : shapes) {
                shape.draw(g);
            }
        }
    }

    private abstract class Shape {
        private String type;
        private int x, y, width, height;

        public Shape(String type, int x, int y, int width, int height) {
            this.type = type;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public void draw(Graphics g) {
            switch (type) {
                case "circle":
                    g.setColor(Color.BLUE);
                    g.fillOval(x, y, width, height);
                    break;
                case "rectangle":
                    g.setColor(Color.GREEN);
                    g.fillRect(x, y, width, height);
                    break;
                case "line":
                    g.setColor(Color.RED);
                    g.drawLine(x, y, x + width, y);
                    break;
            }
        }
    }
}
```