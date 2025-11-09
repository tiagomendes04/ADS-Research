**ShapeEditor.java**
```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class ShapeEditor extends JPanel {

    private List<Shape> shapes = new ArrayList<>();
    private Shape selectedShape;
    private int x, y;

    public ShapeEditor() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                x = e.getX();
                y = e.getY();
                selectedShape = null;
                for (Shape shape : shapes) {
                    if (shape.contains(x, y)) {
                        selectedShape = shape;
                        break;
                    }
                }
                if (selectedShape == null) {
                    shapes.add(new Circle(x, y));
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (selectedShape != null) {
                    selectedShape.resize(e.getX() - x, e.getY() - y);
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedShape != null) {
                    selectedShape.move(e.getX() - x, e.getY() - y);
                }
            }
        });

        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Shape shape : shapes) {
            shape.draw(g);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Shape Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ShapeEditor());
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}

interface Shape {
    void draw(Graphics g);
    void move(int dx, int dy);
    void resize(int w, int h);
    boolean contains(int x, int y);
}

class Circle implements Shape {
    private int x, y, radius;

    public Circle(int x, int y) {
        this.x = x;
        this.y = y;
        radius = 10;
    }

    @Override
    public void draw(Graphics g) {
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    @Override
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    @Override
    public void resize(int w, int h) {
        radius = Math.min(w, h);
    }

    @Override
    public boolean contains(int x, int y) {
        return (x - this.x) * (x - this.x) + (y - this.y) * (y - this.y) <= radius * radius;
    }
}

class Rectangle implements Shape {
    private int x, y, width, height;

    public Rectangle(int x, int y) {
        this.x = x;
        this.y = y;
        width = 50;
        height = 50;
    }

    @Override
    public void draw(Graphics g) {
        g.fillRect(x, y, width, height);
    }

    @Override
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    @Override
    public void resize(int w, int h) {
        width = w;
        height = h;
    }

    @Override
    public boolean contains(int x, int y) {
        return x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height;
    }
}
```
This code creates a basic shape editor with a circle and a rectangle. The shapes can be drawn, moved, and resized by dragging the mouse. The shapes are stored in a list and redrawn on every repaint event.