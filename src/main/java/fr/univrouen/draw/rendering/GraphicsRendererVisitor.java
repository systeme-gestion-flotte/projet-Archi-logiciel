package fr.univrouen.draw.rendering;

import fr.univrouen.draw.model.*;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class GraphicsRendererVisitor implements ShapeVisitor {
    private Graphics2D g2d;

    public GraphicsRendererVisitor(Graphics2D g2d) {
        this.g2d = g2d;
    }

    private java.awt.Color getAwtColor(Color color) {
        switch (color) {
            case black: return java.awt.Color.BLACK;
            case red: return java.awt.Color.RED;
            case green: return java.awt.Color.GREEN;
            case blue: return java.awt.Color.BLUE;
            case yellow: return java.awt.Color.YELLOW;
            case cyan: return java.awt.Color.CYAN;
            case magenta: return java.awt.Color.MAGENTA;
            case white: return java.awt.Color.WHITE;
            default: return java.awt.Color.BLACK;
        }
    }

    @Override
    public void visit(Line line) {
        g2d.setColor(getAwtColor(line.getColor()));
        g2d.setStroke(new java.awt.BasicStroke(3.0f));
        g2d.draw(new Line2D.Double(line.getX1(), line.getY1(), line.getX2(), line.getY2()));
    }

    @Override
    public void visit(Rectangle rectangle) {
        g2d.setColor(getAwtColor(rectangle.getColor()));
        double width = Math.abs(rectangle.getX2() - rectangle.getX1());
        double height = Math.abs(rectangle.getY2() - rectangle.getY1());
        double x = Math.min(rectangle.getX1(), rectangle.getX2());
        double y = Math.min(rectangle.getY1(), rectangle.getY2());
        g2d.fill(new Rectangle2D.Double(x, y, width, height));
    }

    @Override
    public void visit(Circle circle) {
        g2d.setColor(getAwtColor(circle.getColor()));
        double x = circle.getCx() - circle.getR();
        double y = circle.getCy() - circle.getR();
        g2d.fill(new Ellipse2D.Double(x, y, circle.getR() * 2, circle.getR() * 2));
    }

    @Override
    public void visit(Ellipse ellipse) {
        g2d.setColor(getAwtColor(ellipse.getColor()));
        double x = ellipse.getCx() - ellipse.getRx();
        double y = ellipse.getCy() - ellipse.getRy();
        g2d.fill(new Ellipse2D.Double(x, y, ellipse.getRx() * 2, ellipse.getRy() * 2));
    }

    @Override
    public void visit(Group group) {
        for (Shape s : group.getShapes()) {
            s.accept(this);
        }
    }
    
    public void visit(Drawing drawing) {
        for (Shape s : drawing.getShapes()) {
            s.accept(this);
        }
    }
}
