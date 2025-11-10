```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class ShapeEditor extends JFrame {

    private List<Shape> shapes;
    private Point mouseDownPoint;
    private Point mouseDragPoint;

    public ShapeEditor() {
        shapes = new ArrayList<>();
        setLayout(new BorderLayout());

        // Create panel for drawing shapes
        JPanel drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Shape shape : shapes) {
                    shape.draw(g);
                }
            }
        };
        drawingPanel.setBackground(Color.WHITE);
        drawingPanel.setPreferredSize(new Dimension(800, 600));
        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseDownPoint = e.getPoint();
                Shape shape = getShapeAtPoint(e.getPoint());
                if (shape != null) {
                    shape.setEditing(true);
                }
            }
        });
        drawingPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mouseDragPoint = e.getPoint();
                if (shapes.stream().anyMatch(shape -> shape.isEditing())) {
                    Shape shape = shapes.stream().filter(s -> s.isEditing()).findFirst().orElse(null);
                    shape.resize(mouseDownPoint, mouseDragPoint);
                    drawingPanel.repaint();
                }
            }
        });
        drawingPanel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (shapes.stream().anyMatch(shape -> shape.isEditing())) {
                    Shape shape = shapes.stream().filter(s -> s.isEditing()).findFirst().orElse(null);
                    shape.resize(mouseDownPoint, new Point(mouseDownPoint.x + e.getWheelRotation() * 10, mouseDownPoint.y + e.getWheelRotation() * 10));
                    drawingPanel.repaint();
                }
            }
        });

        // Create panel for toolbar
        JPanel toolbar = new JPanel();
        JButton circleButton = new JButton("Circle");
        circleButton.addActionListener(e -> {
            Point center = new Point(drawingPanel.getWidth() / 2, drawingPanel.getHeight() / 2);
            shapes.add(new Circle(center, 50));
            drawingPanel.repaint();
        });
        JButton rectangleButton = new JButton("Rectangle");
        rectangleButton.addActionListener(e -> {
            Point topLeft = new Point(drawingPanel.getWidth() / 2 - 50, drawingPanel.getHeight() / 2 - 50);
            shapes.add(new Rectangle(topLeft, 100, 100));
            drawingPanel.repaint();
        });
        toolbar.add(circleButton);
        toolbar.add(rectangleButton);

        // Add panels to frame
        add(toolbar, BorderLayout.NORTH);
        add(drawingPanel, BorderLayout.CENTER);

        // Set up window
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private Shape getShapeAtPoint(Point point) {
        for (Shape shape : shapes) {
            if (shape.getBounds().contains(point)) {
                return shape;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        new ShapeEditor();
    }
}

abstract class Shape {
    private boolean editing;
    private Rectangle bounds;

    public Shape() {
        editing = false;
    }

    public void draw(Graphics g) {
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }

    public boolean isEditing() {
        return editing;
    }

    public void resize(Point oldPoint, Point newPoint) {
        bounds.x = newPoint.x;
        bounds.y = newPoint.y;
    }
}

class Circle extends Shape {
    private Point center;
    private int radius;

    public Circle(Point center, int radius) {
        this.center = center;
        this.radius = radius;
        bounds = new Rectangle(center.x - radius, center.y - radius, radius * 2, radius * 2);
    }

    @Override
    public void draw(Graphics g) {
        g.drawOval(center.x - radius, center.y - radius, radius * 2, radius * 2);
    }
}

class Rectangle extends Shape {
    private Point topLeft;
    private int width;
    private int height;

    public Rectangle(Point topLeft, int width, int height) {
        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
        bounds = new Rectangle(topLeft.x, topLeft.y, width, height);
    }

    @Override
    public void draw(Graphics g) {
        g.drawRect(topLeft.x, topLeft.y, width, height);
    }
}
```