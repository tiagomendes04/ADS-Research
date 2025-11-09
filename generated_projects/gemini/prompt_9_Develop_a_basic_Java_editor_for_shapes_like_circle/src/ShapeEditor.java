import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class ShapeEditor extends JFrame {

    private JPanel canvas;
    private JComboBox<String> shapeSelector;
    private JButton deleteButton;

    private List<Shape> shapes = new ArrayList<>();
    private Shape selectedShape = null;
    private int startX, startY;
    private boolean isResizing = false;

    public ShapeEditor() {
        setTitle("Shape Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Shape shape : shapes) {
                    shape.draw(g);
                }
                if (selectedShape != null) {
                    g.setColor(Color.BLUE);
                    selectedShape.drawOutline(g);
                }
            }
        };
        canvas.setBackground(Color.WHITE);
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();
                selectedShape = null;
                for (int i = shapes.size() - 1; i >= 0; i--) {
                    Shape shape = shapes.get(i);
                    if (shape.contains(startX, startY)) {
                        selectedShape = shape;
                        shapes.remove(i);
                        shapes.add(selectedShape); // Move to top for drawing order
                        canvas.repaint();
                        break;
                    }
                }

                if (selectedShape != null && selectedShape.isNearBorder(startX, startY)) {
                    isResizing = true;
                } else {
                    isResizing = false;
                }

                if(selectedShape == null){
                     String selectedShapeType = (String) shapeSelector.getSelectedItem();
                     if (selectedShapeType != null) {
                        switch (selectedShapeType) {
                            case "Circle":
                                selectedShape = new Circle(startX, startY, 0);
                                break;
                            case "Rectangle":
                                selectedShape = new Rectangle(startX, startY, 0, 0);
                                break;
                        }
                     }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
               if(selectedShape != null && !isResizing){
                   shapes.add(selectedShape);
               }
               selectedShape = null;
               isResizing = false;
               canvas.repaint();
            }
        });

        canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedShape != null) {
                    int currentX = e.getX();
                    int currentY = e.getY();

                    if (isResizing) {
                        selectedShape.resize(startX, startY, currentX, currentY);
                        startX = currentX;
                        startY = currentY;
                    } else {
                        selectedShape.move(currentX - startX, currentY - startY);
                    }
                    canvas.repaint();
                    startX = currentX;
                    startY = currentY;
                } else {
                    if(shapeSelector.getSelectedItem() != null){
                        int endX = e.getX();
                        int endY = e.getY();

                        String selectedShapeType = (String) shapeSelector.getSelectedItem();
                        if(selectedShape != null){
                            shapes.remove(selectedShape);
                        }

                        switch (selectedShapeType) {
                            case "Circle":
                                selectedShape = new Circle(startX, startY, Math.abs(endX - startX));
                                break;
                            case "Rectangle":
                                selectedShape = new Rectangle(startX, startY, endX - startX, endY - startY);
                                break;
                        }
                        canvas.repaint();
                    }

                }
            }
        });

        JPanel controlPanel = new JPanel();
        shapeSelector = new JComboBox<>(new String[]{"Circle", "Rectangle"});
        controlPanel.add(shapeSelector);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            if (selectedShape != null) {
                shapes.remove(selectedShape);
                selectedShape = null;
                canvas.repaint();
            }
        });
        controlPanel.add(deleteButton);

        add(canvas, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.NORTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ShapeEditor::new);
    }
}

interface Shape {
    void draw(Graphics g);
    boolean contains(int x, int y);
    void move(int dx, int dy);
    void resize(int startX, int startY, int endX, int endY);
    void drawOutline(Graphics g);
    boolean isNearBorder(int x, int y);
}

class Circle implements Shape {
    private int x, y, radius;

    public Circle(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
    }

    @Override
    public boolean contains(int x, int y) {
        return Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2) <= Math.pow(radius, 2);
    }

    @Override
    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public void resize(int startX, int startY, int endX, int endY) {
        radius = Math.abs(endX - x);
    }

    @Override
    public void drawOutline(Graphics g) {
        g.drawOval(x - radius - 3, y - radius - 3, 2 * radius + 6, 2 * radius + 6);
    }

    @Override
    public boolean isNearBorder(int x, int y) {
        double distance = Math.sqrt(Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2));
        return Math.abs(distance - radius) <= 5;
    }
}

class Rectangle implements Shape {
    private int x, y, width, height;

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, width, height);
    }

    @Override
    public boolean contains(int x, int y) {
        return x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height;
    }

    @Override
    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public void resize(int startX, int startY, int endX, int endY) {
      if(endX > x && endY > y){
          width = endX - x;
          height = endY - y;
      } else if(endX < x && endY < y){
          width = x - endX;
          height = y - endY;
          x = endX;
          y = endY;
      } else if(endX < x && endY > y){
          width = x - endX;
          height = endY - y;
          x = endX;
      } else {
          width = endX - x;
          height = y - endY;
          y = endY;
      }
    }

    @Override
    public void drawOutline(Graphics g) {
        g.drawRect(x - 3, y - 3, width + 6, height + 6);
    }

    @Override
    public boolean isNearBorder(int x, int y) {
        int borderWidth = 5;
        return (x >= this.x - borderWidth && x <= this.x + borderWidth && y >= this.y - borderWidth && y <= this.y + height + borderWidth) ||
               (x >= this.x + width - borderWidth && x <= this.x + width + borderWidth && y >= this.y - borderWidth && y <= this.y + height + borderWidth) ||
               (y >= this.y - borderWidth && y <= this.y + borderWidth && x >= this.x - borderWidth && x <= this.x + width + borderWidth) ||
               (y >= this.y + height - borderWidth && y <= this.y + height + borderWidth && x >= this.x - borderWidth && x <= this.x + width + borderWidth);
    }
}