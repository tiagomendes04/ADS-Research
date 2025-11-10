```java
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class VirtualWhiteboard extends JPanel {
    private List<Shape> shapes;
    private List<Annotation> annotations;

    public VirtualWhiteboard() {
        shapes = new ArrayList<>();
        annotations = new ArrayList<>();
        setBackground(Color.WHITE);

        SetFocusable(this);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    addShape(e.getX(), e.getY());
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    addAnnotation(e.getX(), e.getY());
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    addShape(e.getX(), e.getY());
                }
            }
        });
    }

    private void addShape(int x, int y) {
        shapes.add(new Shape(x, y, Color.BLACK));
    }

    private void addAnnotation(int x, int y) {
        annotations.add(new Annotation(x, y, Color.BLACK));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for (Shape shape : shapes) {
            g.fill(shape);
        }
        for (Annotation annotation : annotations) {
            g.fill(annotation);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Virtual Whiteboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new VirtualWhiteboard());
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}

class Shape {
    int x, y;
    Color color;

    public Shape(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
}

class Annotation {
    int x, y;
    Color color;

    public Annotation(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
}
```