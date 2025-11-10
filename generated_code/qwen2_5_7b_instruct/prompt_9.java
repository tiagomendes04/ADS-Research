```java
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ShapeEditor extends JFrame {
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    private static final int DEFAULT_SIZE = 100;
    private static final int DEFAULT_COLOR = Color.BLACK;
    private static final String RECTANGLE_BUTTON_LABEL = "Rectangle";
    private static final String CIRCLE_BUTTON_LABEL = "Circle";
    private static final String RESIZE_BUTTON_LABEL = "Resize";

    private JButton rectangleButton;
    private JButton circleButton;
    private JButton resizeButton;
    private Color currentColor = DEFAULT_COLOR;
    private int currentSize = DEFAULT_SIZE;
    private int x, y;
    private boolean isDrawing = false;

    public ShapeEditor() {
        setTitle("Shape Editor");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        rectangleButton = new JButton(RECTANGLE_BUTTON_LABEL);
        circleButton = new JButton(CIRCLE_BUTTON_LABEL);
        resizeButton = new JButton(RESIZE_BUTTON_LABEL);
        rectangleButton.addActionListener(e -> currentColor = Color.BLACK);
        circleButton.addActionListener(e -> currentColor = Color.BLUE);
        resizeButton.addActionListener(e -> {
            currentSize = JOptionPane.showOptionDialog(null, "Enter size:", "Resize",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                    new String[]{"10", "50", "100"}, "10");
            currentSize = Integer.parseInt((String) resizeButton.getActionCommand());
        });
        buttonPanel.add(rectangleButton);
        buttonPanel.add(circleButton);
        buttonPanel.add(resizeButton);
        add(buttonPanel, BorderLayout.NORTH);

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                x = e.getX();
                y = e.getY();
                isDrawing = true;
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                x = e.getX();
                y = e.getY();
                isDrawing = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isDrawing = false;
            }
        });

        add(new DrawingPanel());
    }

    private class DrawingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (isDrawing) {
                if (rectangleButton.getModel().isArmed()) {
                    g.setColor(currentColor);
                    g.fillRect(x, y, currentSize, currentSize);
                } else if (circleButton.getModel().isArmed()) {
                    g.setColor(currentColor);
                    g.fillOval(x, y, currentSize, currentSize);
                }
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
```