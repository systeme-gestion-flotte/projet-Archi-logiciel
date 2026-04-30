package fr.univrouen.draw.model;

import java.util.ArrayList;
import java.util.List;

public class Group implements Shape {
    private String name;
    private List<Shape> shapes;

    public Group(String name) {
        this.name = name != null ? name : "";
        this.shapes = new ArrayList<>();
    }

    public String getName() { return name; }
    public List<Shape> getShapes() { return shapes; }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public void removeShape(Shape shape) {
        shapes.remove(shape);
    }

    @Override
    public void accept(ShapeVisitor visitor) {
        visitor.visit(this);
    }
}
