```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

class VirtualWhiteboard extends JFrame {
    private DrawingPanel drawingPanel;
    private String shapeType = "rectangle";

    public VirtualWhiteboard() {
        setTitle("Virtual Whiteboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel controlPanel = new JPanel();
        JButton rectangleButton = new JButton("Rectangle");
        JButton circleButton = new JButton("Circle");
        JButton freehandButton = new JButton("Freehand");

        rectangleButton.addActionListener(e -> shapeType = "rectangle");
        circleButton.addActionListener(e -> shapeType = "circle");
        freehandButton.addActionListener(e -> shapeType = "freehand");

        controlPanel.add(rectangleButton);
        controlPanel.add(circleButton);
        controlPanel.add(freehandButton);

        drawingPanel = new DrawingPanel();

        add(controlPanel, BorderLayout.NORTH);
        add(drawingPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VirtualWhiteboard whiteboard = new VirtualWhiteboard();
            whiteboard.setVisible(true);
        });
    }
}

class DrawingPanel extends JPanel {
    private List<Shape> shapes = new ArrayList<>();
    private Point startPoint;
    private Shape currentShape;
    private boolean isDrawing = false;

    public DrawingPanel() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(800, 550));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
                if (((VirtualWhiteboard) SwingUtilities.getWindowAncestor(DrawingPanel.this)).shapeType.equals("freehand")) {
                    currentShape = new Polygon();
                    ((Polygon) currentShape).addPoint(startPoint.x, startPoint.y);
                    isDrawing = true;
                } else {
                    currentShape = null;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (currentShape != null) {
                    shapes.add(currentShape);
                    currentShape = null;
                }
                isDrawing = false;
                repaint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDrawing) {
                    if (currentShape instanceof Polygon) {
                        ((Polygon) currentShape).addPoint(e.getX(), e.getY());
                        repaint();
                    }
                } else {
                    switch (((VirtualWhiteboard) SwingUtilities.getWindowAncestor(DrawingPanel.this)).shapeType) {
                        case "rectangle":
                            currentShape = new Rectangle(startPoint.x, startPoint.y, e.getX() - startPoint.x, e.getY() - startPoint.y);
                            break;
                        case "circle":
                            int width = e.getX() - startPoint.x;
                            int height = e.getY() - startPoint.y;
                            int diameter = Math.max(Math.abs(width), Math.abs(height));
                            currentShape = new Ellipse2D.Double(Math.min(startPoint.x, e.getX()), Math.min(startPoint.y, e.getY()), diameter, diameter);
                            break;
                    }
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Shape shape : shapes) {
            g2d.draw(shape);
        }

        if (currentShape != null) {
            g2d.draw(currentShape);
        }
    }
}
```