package fr.univrouen.draw.model;

public class Ellipse implements Shape {
    private double cx, cy, rx, ry;
    private Color color;

    public Ellipse(double cx, double cy, double rx, double ry, Color color) {
        this.cx = cx;
        this.cy = cy;
        this.rx = rx;
        this.ry = ry;
        this.color = color;
    }

    public double getCx() { return cx; }
    public double getCy() { return cy; }
    public double getRx() { return rx; }
    public double getRy() { return ry; }
    public Color getColor() { return color; }

    @Override
    public void accept(ShapeVisitor visitor) {
        visitor.visit(this);
    }
}
