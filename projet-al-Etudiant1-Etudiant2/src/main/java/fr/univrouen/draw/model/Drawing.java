package fr.univrouen.draw.model;

import java.util.ArrayList;
import java.util.List;

public class Drawing {
    private List<Shape> shapes;

    public Drawing() {
        this.shapes = new ArrayList<>();
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public Shape getShape(int index) {
        return shapes.get(index);
    }

    public void removeShape(int index) {
        shapes.remove(index);
    }

    public void removeShape(Shape shape) {
        shapes.remove(shape);
    }

    public int getShapeCount() {
        return shapes.size();
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public void accept(ShapeVisitor visitor) {
        for (Shape shape : shapes) {
            shape.accept(visitor);
        }
    }
}
