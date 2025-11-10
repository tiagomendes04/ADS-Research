```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ShapeEditor extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel canvasPanel;
    private JDrawingPanel drawingPanel;
    private JButton addCircleButton;
    private JButton addRectangleButton;
    private JButton resizeButton;
    private ArrayList<Shape> shapes;

    public ShapeEditor() {
        super("Shape Editor");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        canvasPanel = new JPanel();
        canvasPanel.setPreferredSize(new Dimension(500, 300));
        canvasPanel.setBackground(Color.WHITE);
        drawingPanel = new JDrawingPanel();
        add(canvasPanel, BorderLayout.CENTER);

        addCircleButton = new JButton("Add Circle");
        addCircleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addShape("Circle");
            }
        });
        add(addCircleButton, BorderLayout.SOUTH);

        addRectangleButton = new JButton("Add Rectangle");
        addRectangleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addShape("Rectangle");
            }
        });
        add(addRectangleButton, BorderLayout.SOUTH);

        resizeButton = new JButton("Resize");
        resizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic for resizing shapes
            }
        });
        add(resizeButton, BorderLayout.SOUTH);

        shapes = new ArrayList<>();
        setVisible(true);
    }

    private void addShape(String type) {
        if (type.equals("Circle")) {
            shapes.add(new Circle(drawingPanel));
        } else if (type.equals("Rectangle")) {
            shapes.add(new Rectangle(drawingPanel));
        }
    }
}
```