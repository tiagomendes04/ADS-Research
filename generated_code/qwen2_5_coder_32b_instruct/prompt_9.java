```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Shape {
    int x, y, width, height;
    Color color;

    Shape(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    void draw(Graphics g) {}

    boolean contains(Point p) {
        return false;
    }

    void resize(Point p) {}
}

class Circle extends Shape {
    Circle(int x, int y, int diameter, Color color) {
        super(x, y, diameter, diameter, color);
    }

    @Override
    void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, width, height);
    }

    @Override
    boolean contains(Point p) {
        int dx = p.x - (x + width / 2);
        int dy = p.y - (y + height / 2);
        return dx * dx + dy * dy <= (width / 2) * (width / 2);
    }

    @Override
    void resize(Point p) {
        width = Math.abs(p.x - x);
        height = width;
    }
}

class Rectangle extends Shape {
    Rectangle(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
    }

    @Override
    void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    @Override
    boolean contains(Point p) {
        return p.x >= x && p.x <= x + width && p.y >= y && p.y <= y + height;
    }

    @Override
    void resize(Point p) {
        width = p.x - x;
        height = p.y - y;
    }
}

class ShapeEditor extends JPanel {
    private Shape selectedShape;
    private Point startPoint;
    private ArrayList<Shape> shapes = new ArrayList<>();
    private final Color SHAPE_COLOR = Color.BLUE;

    ShapeEditor() {
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
                if (e.isShiftDown()) {
                    selectedShape = new Rectangle(startPoint.x, startPoint.y, 0, 0, SHAPE_COLOR);
                } else {
                    selectedShape = new Circle(startPoint.x, startPoint.y, 0, SHAPE_COLOR);
                }
                shapes.add(selectedShape);
                repaint();
            }

            public void mouseReleased(MouseEvent e) {
                selectedShape = null;
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (selectedShape != null) {
                    selectedShape.resize(e.getPoint());
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Shape shape : shapes) {
            shape.draw(g);
        }
    }
}

public class BasicShapeEditor {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Basic Shape Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ShapeEditor());
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}
```