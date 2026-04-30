package fr.univrouen.draw.persistence;

import fr.univrouen.draw.model.*;
import java.util.Locale;

public class XMLSerializerVisitor implements ShapeVisitor {
    private StringBuilder xml;

    public XMLSerializerVisitor() {
        this.xml = new StringBuilder();
    }

    public String getXml() {
        return xml.toString();
    }

    @Override
    public void visit(Line line) {
        xml.append(String.format(Locale.US, "<line x1=\"%.2f\" y1=\"%.2f\" x2=\"%.2f\" y2=\"%.2f\" color=\"%s\" />\n",
                line.getX1(), line.getY1(), line.getX2(), line.getY2(), line.getColor().name()));
    }

    @Override
    public void visit(Rectangle rectangle) {
        xml.append(String.format(Locale.US, "<rectangle x1=\"%.2f\" y1=\"%.2f\" x2=\"%.2f\" y2=\"%.2f\" color=\"%s\" />\n",
                rectangle.getX1(), rectangle.getY1(), rectangle.getX2(), rectangle.getY2(), rectangle.getColor().name()));
    }

    @Override
    public void visit(Circle circle) {
        xml.append(String.format(Locale.US, "<circle cx=\"%.2f\" cy=\"%.2f\" r=\"%.2f\" color=\"%s\" />\n",
                circle.getCx(), circle.getCy(), circle.getR(), circle.getColor().name()));
    }

    @Override
    public void visit(Ellipse ellipse) {
        xml.append(String.format(Locale.US, "<ellipse cx=\"%.2f\" cy=\"%.2f\" rx=\"%.2f\" ry=\"%.2f\" color=\"%s\" />\n",
                ellipse.getCx(), ellipse.getCy(), ellipse.getRx(), ellipse.getRy(), ellipse.getColor().name()));
    }

    @Override
    public void visit(Group group) {
        xml.append(String.format("<group name=\"%s\">\n", group.getName() != null ? group.getName() : ""));
        for (Shape s : group.getShapes()) {
            s.accept(this);
        }
        xml.append("</group>\n");
    }
    
    public void visit(Drawing drawing) {
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<!DOCTYPE drawing SYSTEM \"draw.dtd\">\n");
        xml.append("<drawing>\n");
        for (Shape s : drawing.getShapes()) {
            s.accept(this);
        }
        xml.append("</drawing>\n");
    }
}
