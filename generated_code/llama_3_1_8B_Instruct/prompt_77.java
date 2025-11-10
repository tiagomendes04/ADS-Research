```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class VirtualWhiteboard extends JPanel {

    private List<Shape> shapes;
    private List<Annotation> annotations;

    public VirtualWhiteboard() {
        shapes = new ArrayList<>();
        annotations = new ArrayList<>();
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(800, 600));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isEventDispatchThread()) {
                    createShape(e.getX(), e.getY());
                } else {
                    SwingUtilities.invokeLater(() -> createShape(e.getX(), e.getY()));
                }
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isEventDispatchThread()) {
                    updateShape(e.getX(), e.getY());
                } else {
                    SwingUtilities.invokeLater(() -> updateShape(e.getX(), e.getY()));
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

    private void createShape(int x, int y) {
        shapes.add(new Circle(x, y));
    }

    private void updateShape(int x, int y) {
        if (!shapes.isEmpty()) {
            shapes.get(shapes.size() - 1).update(x, y);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Shape shape : shapes) {
            shape.draw(g);
        }
        for (Annotation annotation : annotations) {
            annotation.draw(g);
        }
    }

    public void addAnnotation(String text, int x, int y) {
        annotations.add(new Annotation(text, x, y));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Virtual Whiteboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new VirtualWhiteboard());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static class Shape {
        protected int x, y;

        public Shape(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void draw(Graphics g) {
        }

        public void update(int x, int y) {
            this.x = x;
            this.y = y;
            repaint();
        }
    }

    public static class Circle extends Shape {
        public Circle(int x, int y) {
            super(x, y);
        }

        @Override
        public void draw(Graphics g) {
            g.fillOval(x - 10, y - 10, 20, 20);
        }
    }

    public static class Annotation {
        private String text;
        private int x, y;

        public Annotation(String text, int x, int y) {
            this.text = text;
            this.x = x;
            this.y = y;
        }

        public void draw(Graphics g) {
            g.drawString(text, x, y);
        }
    }
}
```