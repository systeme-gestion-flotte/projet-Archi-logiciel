package fr.univrouen.draw.model;

public interface Shape {
    void accept(ShapeVisitor visitor);
}
