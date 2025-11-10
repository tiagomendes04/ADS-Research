```java
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

public class ShapeEditor extends JFrame {
    private final DrawPanel drawPanel = new DrawPanel();
    private final JToggleButton rectButton = new JToggleButton("Rectangle");
    private final JToggleButton circButton = new JToggleButton("Circle");
    private final ButtonGroup shapeGroup = new ButtonGroup();

    public ShapeEditor() {
        super("Shape Editor");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        shapeGroup.add(rectButton);
        shapeGroup.add(circButton);
        rectButton.setSelected(true);
        JPanel top = new JPanel();
        top.add(rectButton);
        top.add(circButton);
        add(top, BorderLayout.NORTH);
        add(drawPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private abstract static class MyShape {
        Color color = Color.BLUE;
        boolean selected = false;
        abstract void draw(Graphics2D g2);
        abstract boolean contains(Point2D p);
        abstract void setBounds(Point2D start, Point2D end);
        abstract void move(double dx, double dy);
        abstract Rectangle2D getBounds();
        void drawSelection(Graphics2D g2) {
            Rectangle2D b = getBounds();
            g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
                    new float[]{5}, 0));
            g2.setColor(Color.RED);
            g2.draw(b);
            for (int i = 0; i < 4; i++) {
                double cx = (i % 2 == 0) ? b.getX() : b.getX() + b.getWidth();
                double cy = (i < 2) ? b.getY() : b.getY() + b.getHeight();
                g2.fill(new Rectangle2D.Double(cx - 4, cy - 4, 8, 8));
            }
        }
    }

    private static class MyRectangle extends MyShape {
        Rectangle2D rect = new Rectangle2D.Double();
        @Override
        void draw(Graphics2D g2) {
            g2.setColor(color);
            g2.fill(rect);
            g2.setColor(Color.BLACK);
            g2.draw(rect);
            if (selected) drawSelection(g2);
        }
        @Override
        boolean contains(Point2D p) { return rect.contains(p); }
        @Override
        void setBounds(Point2D start, Point2D end) {
            rect.setFrameFromDiagonal(start, end);
        }
        @Override
        void move(double dx, double dy) { rect.setFrame(rect.getX() + dx, rect.getY() + dy,
                rect.getWidth(), rect.getHeight()); }
        @Override
        Rectangle2D getBounds() { return rect.getBounds2D(); }
    }

    private static class MyCircle extends MyShape {
        Ellipse2D ellipse = new Ellipse2D.Double();
        @Override
        void draw(Graphics2D g2) {
            g2.setColor(color);
            g2.fill(ellipse);
            g2.setColor(Color.BLACK);
            g2.draw(ellipse);
            if (selected) drawSelection(g2);
        }
        @Override
        boolean contains(Point2D p) { return ellipse.contains(p); }
        @Override
        void setBounds(Point2D start, Point2D end) {
            double cx = (start.getX() + end.getX()) / 2;
            double cy = (start.getY() + end.getY()) / 2;
            double rx = Math.abs(end.getX() - start.getX()) / 2;
            double ry = Math.abs(end.getY() - start.getY()) / 2;
            double r = Math.max(rx, ry);
            ellipse.setFrame(cx - r, cy - r, 2 * r, 2 * r);
        }
        @Override
        void move(double dx, double dy) { ellipse.setFrame(ellipse.getX() + dx,
                ellipse.getY() + dy, ellipse.getWidth(), ellipse.getHeight()); }
        @Override
        Rectangle2D getBounds() { return ellipse.getBounds2D(); }
    }

    private class DrawPanel extends JPanel {
        private final java.util.List<MyShape> shapes = new ArrayList<>();
        private MyShape current = null;
        private Point2D startPt = null;
        private boolean dragging = false;
        private int dragHandle = -1; // 0-3 for corners, -1 for move

        DrawPanel() {
            setBackground(Color.WHITE);
            MouseAdapter ma = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    Point2D p = e.getPoint();
                    MyShape hit = findShape(p);
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        if (hit != null) {
                            selectShape(hit);
                            dragHandle = getHandle(hit.getBounds(), p);
                            startPt = p;
                            dragging = true;
                        } else {
                            deselectAll();
                            startPt = p;
                            current = createShape();
                            shapes.add(current);
                            dragging = true;
                        }
                        repaint();
                    }
                }
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (!dragging) return;
                    Point2D p = e.getPoint();
                    if (current != null && startPt != null && dragHandle == -1) {
                        current.setBounds(startPt, p);
                    } else if (dragHandle >= 0 && current != null) {
                        Rectangle2D b = current.getBounds();
                        double x = b.getX(), y = b.getY(), w = b.getWidth(), h = b.getHeight();
                        double nx = x, ny = y, nw = w, nh = h;
                        switch (dragHandle) {
                            case 0: nx = p.getX(); ny = p.getY(); nw = (x + w) - p.getX(); nh = (y + h) - p.getY(); break;
                            case 1: ny = p.getY(); nw = p.getX() - x; nh = (y + h) - p.getY(); break;
                            case 2: nx = p.getX(); nw = (x + w) - p.getX(); nh = p.getY() - y; break;
                            case 3: nw = p.getX() - x; nh = p.getY() - y; break;
                        }
                        if (nw < 0) { nx += nw; nw = -nw; }
                        if (nh < 0) { ny += nh; nh = -nh; }
                        current.setBounds(new Point2D.Double(nx, ny), new Point2D.Double(nx + nw, ny + nh));
                    } else if (dragHandle == -1 && current != null) {
                        double dx = p.getX() - startPt.getX();
                        double dy = p.getY() - startPt.getY();
                        current.move(dx, dy);
                        startPt = p;
                    }
                    repaint();
                }
                @Override
                public void mouseReleased(MouseEvent e) {
                    dragging = false;
                    current = null;
                    startPt = null;
                    dragHandle = -1;
                }
            };
            addMouseListener(ma);
            addMouseMotionListener(ma);
        }

        private MyShape createShape() {
            if (rectButton.isSelected()) return new MyRectangle();
            else return new MyCircle();
        }

        private MyShape findShape(Point2D p) {
            for (int i = shapes.size() - 1; i >= 0; i--) {
                if (shapes.get(i).contains(p)) return shapes.get(i);
            }
            return null;
        }

        private void selectShape(MyShape s) {
            deselectAll();
            s.selected = true;
            current = s;
        }

        private void deselectAll() {
            for (MyShape s : shapes) s.selected = false;
        }

        private int getHandle(Rectangle2D b, Point2D p) {
            double tolerance = 6;
            for (int i = 0; i < 4; i++) {
                double hx = (i % 2 == 0) ? b.getX() : b.getX() + b.getWidth();
                double hy = (i < 2) ? b.getY() : b.getY() + b.getHeight();
                if (p.distance(hx, hy) <= tolerance) return i;
            }
            if (b.contains(p)) return -1; // move
            return -2;
        }

        @Override
        protected