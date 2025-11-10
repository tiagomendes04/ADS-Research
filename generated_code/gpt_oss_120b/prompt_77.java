```java
// File: src/main/java/com/whiteboard/WhiteboardApp.java
package com.whiteboard;

import javax.swing.*;

public class WhiteboardApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Virtual Whiteboard");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 800);
            WhiteboardPanel board = new WhiteboardPanel();
            frame.add(board);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
```

```java
// File: src/main/java/com/whiteboard/WhiteboardPanel.java
package com.whiteboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;

public class WhiteboardPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

    private enum Tool { SELECT, PEN, LINE, RECT, OVAL, TEXT }

    private Tool currentTool = Tool.PEN;
    private final List<Drawable> drawables = new ArrayList<>();
    private Drawable currentDrawable = null;
    private Point2D startPoint;
    private final Deque<Command> undoStack = new ArrayDeque<>();
    private final Deque<Command> redoStack = new ArrayDeque<>();

    public WhiteboardPanel() {
        setBackground(Color.WHITE);
        addMouseListener(this);
        addMouseMotionListener(this);
        setFocusable(true);
        addKeyListener(this);
        setupToolbar();
    }

    private void setupToolbar() {
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        for (Tool tool : Tool.values()) {
            JButton btn = new JButton(tool.name());
            btn.addActionListener(e -> currentTool = tool);
            toolbar.add(btn);
        }
        JButton undoBtn = new JButton("Undo");
        undoBtn.addActionListener(e -> undo());
        toolbar.add(undoBtn);
        JButton redoBtn = new JButton("Redo");
        redoBtn.addActionListener(e -> redo());
        toolbar.add(redoBtn);
        this.setLayout(new BorderLayout());
        this.add(toolbar, BorderLayout.NORTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        for (Drawable d : drawables) {
            d.draw(g2);
        }
        if (currentDrawable != null) {
            currentDrawable.draw(g2);
        }
        g2.dispose();
    }

    // Mouse events
    @Override
    public void mousePressed(MouseEvent e) {
        requestFocusInWindow();
        startPoint = e.getPoint();
        switch (currentTool) {
            case PEN:
                currentDrawable = new PenStroke(startPoint);
                break;
            case LINE:
                currentDrawable = new LineShape(startPoint, startPoint);
                break;
            case RECT:
                currentDrawable = new RectShape(startPoint, 0, 0);
                break;
            case OVAL:
                currentDrawable = new OvalShape(startPoint, 0, 0);
                break;
            case TEXT:
                String txt = JOptionPane.showInputDialog(this, "Enter text:");
                if (txt != null && !txt.isEmpty()) {
                    currentDrawable = new TextShape(startPoint, txt);
                    addDrawable(currentDrawable);
                    currentDrawable = null;
                }
                break;
            case SELECT:
                // selection not implemented for brevity
                break;
        }
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (currentDrawable == null) return;
        Point2D p = e.getPoint();
        switch (currentTool) {
            case PEN:
                ((PenStroke) currentDrawable).addPoint(p);
                break;
            case LINE:
                ((LineShape) currentDrawable).setEnd(p);
                break;
            case RECT:
                updateRect((RectShape) currentDrawable, p);
                break;
            case OVAL:
                updateRect((OvalShape) currentDrawable, p);
                break;
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (currentDrawable != null) {
            addDrawable(currentDrawable);
            currentDrawable = null;
        }
        repaint();
    }

    private void updateRect(ResizableShape shape, Point2D end) {
        double x = Math.min(startPoint.getX(), end.getX());
        double y = Math.min(startPoint.getY(), end.getY());
        double w = Math.abs(startPoint.getX() - end.getX());
        double h = Math.abs(startPoint.getY() - end.getY());
        shape.setRect(x, y, w, h);
    }

    private void addDrawable(Drawable d) {
        drawables.add(d);
        undoStack.push(new AddCommand(d));
        redoStack.clear();
    }

    // Undo/Redo
    private void undo() {
        if (!undoStack.isEmpty()) {
            Command cmd = undoStack.pop();
            cmd.undo();
            redoStack.push(cmd);
            repaint();
        }
    }

    private void redo() {
        if (!redoStack.isEmpty()) {
            Command cmd = redoStack.pop();
            cmd.redo();
            undoStack.push(cmd);
            repaint();
        }
    }

    // Unused mouse/key events
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseMoved(MouseEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyPressed(KeyEvent e) {
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z) undo();
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Y) redo();
    }
    @Override public void keyReleased(KeyEvent e) {}

    // Drawable interface and concrete classes
    private interface Drawable {
        void draw(Graphics2D g2);
    }

    private static class PenStroke implements Drawable {
        private final GeneralPath path = new GeneralPath();

        PenStroke(Point2D start) {
            path.moveTo(start.getX(), start.getY());
        }

        void addPoint(Point2D p) {
            path.lineTo(p.getX(), p.getY());
        }

        @Override public void draw(Graphics2D g2) {
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLACK);
            g2.draw(path);
        }
    }

    private static class LineShape implements Drawable {
        private Point2D start, end;

        LineShape(Point2D s, Point2D e) { start = s; end = e; }

        void setEnd(Point2D e) { end = e; }

        @Override public void draw(Graphics2D g2) {
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLUE);
            g2.draw(new Line2D.Double(start, end));
        }
    }

    private abstract static class ResizableShape implements Drawable {
        protected Rectangle2D rect = new Rectangle2D.Double();

        void setRect(double x, double y, double w, double h) {
            rect.setRect(x, y, w, h);
        }
    }

    private static class RectShape extends ResizableShape {
        @Override public void draw(Graphics2D g2) {
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.RED);
            g2.draw(rect);
        }
    }

    private static class OvalShape extends ResizableShape {
        @Override public void draw(Graphics2D g2) {
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.MAGENTA);
            g2.draw(new Ellipse2D.Double(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight()));
        }
    }

    private static class TextShape implements Drawable {
        private final Point2D loc;
        private final String text;

        TextShape(Point2D p, String t) { loc = p; text = t; }

        @Override public void draw(Graphics2D g2) {
            g2.setColor(Color.DARK_GRAY);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 18));
            g2.drawString(text, (float) loc.getX(), (float) loc.getY());
        }
    }

    // Command pattern for undo/redo
    private interface Command {
        void undo();
        void redo();
    }

    private class AddCommand implements Command {
        private final Drawable d;

        AddCommand(Drawable d) { this.d = d; }

        @Override public void undo() { drawables.remove(d); }
        @Override public void redo() { drawables.add(d); }
    }
}
```