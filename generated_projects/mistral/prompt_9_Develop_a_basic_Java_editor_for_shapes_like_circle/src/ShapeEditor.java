import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ShapeEditor extends JFrame {
    private ArrayList<Shape> shapes = new ArrayList<>();
    private Shape currentShape = null;
    private Point startPoint = null;
    private Point endPoint = null;
    private String currentTool = "rectangle";

    public ShapeEditor() {
        setTitle("Shape Editor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel toolbar = new JPanel();
        JButton rectangleButton = new JButton("Rectangle");
        JButton circleButton = new JButton("Circle");
        JButton resizeButton = new JButton("Resize");

        rectangleButton.addActionListener(e -> currentTool = "rectangle");
        circleButton.addActionListener(e -> currentTool = "circle");
        resizeButton.addActionListener(e -> currentTool = "resize");

        toolbar.add(rectangleButton);
        toolbar.add(circleButton);
        toolbar.add(resizeButton);

        DrawingPanel drawingPanel = new DrawingPanel();
        add(toolbar, BorderLayout.NORTH);
        add(drawingPanel, BorderLayout.CENTER);
    }

    private class DrawingPanel extends JPanel {
        public DrawingPanel() {
            setBackground(Color.WHITE);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (currentTool.equals("resize")) {
                        for (Shape shape : shapes) {
                            if (shape.contains(e.getPoint())) {
                                currentShape = shape;
                                startPoint = e.getPoint();
                                break;
                            }
                        }
                    } else {
                        startPoint = e.getPoint();
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (currentShape != null && currentTool.equals("resize")) {
                        endPoint = e.getPoint();
                        currentShape.resize(startPoint, endPoint);
                        repaint();
                        currentShape = null;
                    } else if (startPoint != null) {
                        endPoint = e.getPoint();
                        if (currentTool.equals("rectangle")) {
                            shapes.add(new RectangleShape(startPoint, endPoint));
                        } else if (currentTool.equals("circle")) {
                            shapes.add(new CircleShape(startPoint, endPoint));
                        }
                        repaint();
                    }
                    startPoint = null;
                    endPoint = null;
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (currentShape != null && currentTool.equals("resize")) {
                        endPoint = e.getPoint();
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
            if (currentShape != null && currentTool.equals("resize")) {
                currentShape.draw(g);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ShapeEditor editor = new ShapeEditor();
            editor.setVisible(true);
        });
    }
}

abstract class Shape {
    protected Point start;
    protected Point end;

    public Shape(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public abstract void draw(Graphics g);
    public abstract boolean contains(Point p);
    public abstract void resize(Point start, Point end);

    protected int getX() {
        return Math.min(start.x, end.x);
    }

    protected int getY() {
        return Math.min(start.y, end.y);
    }

    protected int getWidth() {
        return Math.abs(end.x - start.x);
    }

    protected int getHeight() {
        return Math.abs(end.y - start.y);
    }
}

class RectangleShape extends Shape {
    public RectangleShape(Point start, Point end) {
        super(start, end);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawRect(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public boolean contains(Point p) {
        return p.x >= getX() && p.x <= getX() + getWidth() &&
               p.y >= getY() && p.y <= getY() + getHeight();
    }

    @Override
    public void resize(Point start, Point end) {
        this.start = start;
        this.end = end;
    }
}

class CircleShape extends Shape {
    public CircleShape(Point start, Point end) {
        super(start, end);
    }

    @Override
    public void draw(Graphics g) {
        int diameter = Math.max(getWidth(), getHeight());
        int x = getX() + (getWidth() - diameter) / 2;
        int y = getY() + (getHeight() - diameter) / 2;
        g.setColor(Color.RED);
        g.drawOval(x, y, diameter, diameter);
    }

    @Override
    public boolean contains(Point p) {
        int centerX = getX() + getWidth() / 2;
        int centerY = getY() + getHeight() / 2;
        int radius = Math.max(getWidth(), getHeight()) / 2;
        int dx = p.x - centerX;
        int dy = p.y - centerY;
        return dx * dx + dy * dy <= radius * radius;
    }

    @Override
    public void resize(Point start, Point end) {
        this.start = start;
        this.end = end;
    }
}