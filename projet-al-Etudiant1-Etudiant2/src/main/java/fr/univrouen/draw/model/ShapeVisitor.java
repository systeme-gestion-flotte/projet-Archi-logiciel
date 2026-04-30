package fr.univrouen.draw.model;

public interface ShapeVisitor {
    void visit(Line line);
    void visit(Rectangle rectangle);
    void visit(Circle circle);
    void visit(Ellipse ellipse);
    void visit(Group group);
}
