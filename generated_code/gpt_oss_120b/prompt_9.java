```java
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class ShapeEditor extends JFrame {
    enum Tool { CIRCLE, RECTANGLE }

    private Tool currentTool = Tool.CIRCLE;
    private final CanvasPanel canvas = new CanvasPanel();

    public ShapeEditor() {
        super("Shape Editor");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JToggleButton circleBtn = new JToggleButton("Circle");
        JToggleButton rectBtn = new JToggleButton("Rectangle");
        circleBtn.setSelected(true);
        ButtonGroup group = new ButtonGroup();
        group.add(circleBtn);
        group.add(rectBtn);

        circleBtn.addActionListener(e -> currentTool = Tool.CIRCLE);
        rectBtn.addActionListener(e -> currentTool = Tool.RECTANGLE);

        JPanel top = new JPanel();
        top.add(circleBtn);
        top.add(rectBtn);
        add(top, BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ShapeEditor().setVisible(true));
    }

    // ---------------------------------------------------------------------

    abstract static class ShapeItem {
        Color color = Color.BLUE;
        boolean selected = false;

        abstract void draw(Graphics2D g);
        abstract boolean contains(Point p);
        abstract void move(int dx, int dy);
        abstract void resize(int dx, int dy, int corner);
        abstract Rectangle getBounds();
    }

    static class CircleShape extends ShapeItem {
        int x, y, r; // center (x,y) and radius

        CircleShape(int cx, int cy, int radius) {
            this.x = cx;
            this.y = cy;
            this.r = radius;
        }

        @Override
        void draw(Graphics2D g) {
            g.setColor(color);
            g.setStroke(new BasicStroke(selected ? 3 : 1));
            g.drawOval(x - r, y - r, 2 * r, 2 * r);
            if (selected) drawHandles(g);
        }

        @Override
        boolean contains(Point p) {
            return p.distance(x, y) <= r;
        }

        @Override
        void move(int dx, int dy) {
            x += dx;
            y += dy;
        }

        @Override
        void resize(int dx, int dy, int corner) {
            int dr = (int) Math.hypot(dx, dy);
            r = Math.max(5, r + (corner == 0 ? dr : -dr));
        }

        @Override
        Rectangle getBounds() {
            return new Rectangle(x - r, y - r, 2 * r, 2 * r);
        }

        private void drawHandles(Graphics2D g) {
            int s = 6;
            g.setColor(Color.RED);
            g.fillRect(x - r - s / 2, y - s / 2, s, s); // left
            g.fillRect(x + r - s / 2, y - s / 2, s, s); // right
            g.fillRect(x - s / 2, y - r - s / 2, s, s); // top
            g.fillRect(x - s / 2, y + r - s / 2, s, s); // bottom
        }
    }

    static class RectShape extends ShapeItem {
        int x, y, w, h; // top-left (x,y), width w, height h

        RectShape(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }

        @Override
        void draw(Graphics2D g) {
            g.setColor(color);
            g.setStroke(new BasicStroke(selected ? 3 : 1));
            g.drawRect(x, y, w, h);
            if (selected) drawHandles(g);
        }

        @Override
        boolean contains(Point p) {
            return new Rectangle(x, y, w, h).contains(p);
        }

        @Override
        void move(int dx, int dy) {
            x += dx;
            y += dy;
        }

        @Override
        void resize(int dx, int dy, int corner) {
            switch (corner) {
                case 0: // top-left
                    x += dx; y += dy; w -= dx; h -= dy; break;
                case 1: // top-right
                    y += dy; w += dx; h -= dy; break;
                case 2: // bottom-left
                    x += dx; w -= dx; h += dy; break;
                case 3: // bottom-right
                    w += dx; h += dy; break;
            }
            if (w < 5) w = 5;
            if (h < 5) h = 5;
        }

        @Override
        Rectangle getBounds() {
            return new Rectangle(x, y, w, h);
        }

        private void drawHandles(Graphics2D g) {
            int s = 6;
            g.setColor(Color.RED);
            g.fillRect(x - s / 2, y - s / 2, s, s); // TL
            g.fillRect(x + w - s / 2, y - s / 2, s, s); // TR
            g.fillRect(x - s / 2, y + h - s / 2, s, s); // BL
            g.fillRect(x + w - s / 2, y + h - s / 2, s, s); // BR
        }
    }

    // ---------------------------------------------------------------------

    class CanvasPanel extends JPanel implements MouseListener, MouseMotionListener {
        private final java.util.List<ShapeItem> shapes = new ArrayList<>();
        private ShapeItem activeShape = null;
        private Point prevPt = null;
        private boolean dragging = false;
        private boolean resizing = false;
        private int resizeCorner = -1; // 0..3 for rect, 0..3 for circle handles

        CanvasPanel() {
            setBackground(Color.WHITE);
            addMouseListener(this);
            addMouseMotionListener(this);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            for (ShapeItem s : shapes) s.draw(g2);
        }

        // ---------- Mouse handling ----------
        @Override public void mousePressed(MouseEvent e) {
            Point p = e.getPoint();
            activeShape = findShape(p);
            if (SwingUtilities.isLeftMouseButton(e)) {
                if (activeShape != null) {
                    // check for resize handle
                    resizeCorner = getResizeCorner(activeShape, p);
                    if (resizeCorner != -1) {
                        resizing = true;
                    } else {
                        dragging = true;
                    }
                    activeShape.selected = true;
                } else {
                    // start new shape
                    dragging = false;
                    resizing = false;
                    activeShape = null;
                    createShape(p);
                }
                prevPt = p;
                repaint();
            }
        }

        @Override public void mouseReleased(MouseEvent e) {
            dragging = false;
            resizing = false;
            resizeCorner = -1;
            activeShape = null;
            prevPt = null;
            repaint();
        }

        @Override public void mouseDragged(MouseEvent e) {
            if (prevPt == null) return;
            Point cur = e.getPoint();
            int dx = cur.x - prevPt.x;
            int dy = cur.y - prevPt.y;

            if (resizing && activeShape != null) {
                activeShape.resize(dx, dy, resizeCorner);
            } else if (dragging && activeShape != null) {
                activeShape.move(dx, dy);
            } else if (activeShape == null) { // drawing new shape
                // temporary shape preview (not stored)
                // handled by mouseReleased when shape is finalized
            }
            prevPt = cur;
            repaint();
        }

        @Override public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                ShapeItem s = findShape(e.getPoint());
                if (s != null) {
                    s.color = new Color(
                            (float)Math.random(),
                            (float)Math.random(),
                            (float)Math.random()
                    );
                    repaint();
                }
            }
        }

        // ---------- Helpers ----------
        private ShapeItem findShape(Point p) {
            for (int i = shapes.size() - 1; i >= 0; i--) {
                ShapeItem s = shapes.get(i);
                if (s.contains(p)) {
                    return s;
                }
            }
            return null;
        }

        private int getResizeCorner(ShapeItem s, Point p) {
            Rectangle b = s.getBounds();
            int sSize = 6;
            Rectangle[] handles = new Rectangle[4];
            handles[0] = new Rectangle(b.x - sSize/2, b.y - sSize/2, sSize, sSize); // TL
            handles[1] = new Rectangle(b.x + b.width - sSize/2, b.y - sSize/2, sSize, sSize); // TR
            handles[2] = new Rectangle(b.x - sSize/2, b.y + b.height - sSize/2, sSize, sSize); // BL
            handles[3] = new Rectangle(b.x + b.width - sSize/2, b.y + b.height - sSize/2, sSize, sSize); // BR
            for (int i = 0; i < 4; i++) {
                if (handles[i].contains(p)) return i;
            }
            return -1;
        }

        private void createShape(Point start) {
            ShapeItem newShape;
            if (currentTool == Tool.CIRCLE) {
                newShape = new CircleShape(start.x, start.y, 0);
            } else {
                newShape = new RectShape(start.x, start.y, 0, 0);
            }
            shapes.add(newShape);
            activeShape = newShape;
            resizing = true; // start resizing to set size
        }

        // Unused mouse events
        @Override public void mouseEntered(MouseEvent e) {}
        @Override public void mouseExited(MouseEvent e) {}
        @Override public void mouseMoved(MouseEvent e) {}
    }
}
```