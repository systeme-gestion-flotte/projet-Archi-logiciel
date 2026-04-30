package fr.univrouen.draw.model;

public class Circle implements Shape {
    private double cx, cy, r;
    private Color color;

    public Circle(double cx, double cy, double r, Color color) {
        this.cx = cx;
        this.cy = cy;
        this.r = r;
        this.color = color;
    }

    public double getCx() { return cx; }
    public double getCy() { return cy; }
    public double getR() { return r; }
    public Color getColor() { return color; }

    @Override
    public void accept(ShapeVisitor visitor) {
        visitor.visit(this);
    }
}
