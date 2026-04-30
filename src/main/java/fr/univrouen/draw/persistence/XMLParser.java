package fr.univrouen.draw.persistence;

import fr.univrouen.draw.model.*;
import fr.univrouen.draw.model.Color;
import fr.univrouen.draw.model.Rectangle;
import fr.univrouen.draw.model.Shape;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;

public class XMLParser {
    public static Drawing load(String filePath) throws Exception {
        Drawing drawing = new Drawing();
        File xmlFile = new File(filePath);
        if (!xmlFile.exists()) return drawing;

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setValidating(false);
        dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        NodeList childNodes = doc.getDocumentElement().getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Shape shape = parseShape((Element) node);
                if (shape != null) {
                    drawing.addShape(shape);
                }
            }
        }
        return drawing;
    }

    private static Shape parseShape(Element element) {
        String tagName = element.getTagName();
        if ("line".equals(tagName)) {
            double x1 = Double.parseDouble(element.getAttribute("x1"));
            double y1 = Double.parseDouble(element.getAttribute("y1"));
            double x2 = Double.parseDouble(element.getAttribute("x2"));
            double y2 = Double.parseDouble(element.getAttribute("y2"));
            Color color = Color.valueOf(element.getAttribute("color"));
            return new Line(x1, y1, x2, y2, color);
        } else if ("rectangle".equals(tagName)) {
            double x1 = Double.parseDouble(element.getAttribute("x1"));
            double y1 = Double.parseDouble(element.getAttribute("y1"));
            double x2 = Double.parseDouble(element.getAttribute("x2"));
            double y2 = Double.parseDouble(element.getAttribute("y2"));
            Color color = Color.valueOf(element.getAttribute("color"));
            return new Rectangle(x1, y1, x2, y2, color);
        } else if ("circle".equals(tagName)) {
            double cx = Double.parseDouble(element.getAttribute("cx"));
            double cy = Double.parseDouble(element.getAttribute("cy"));
            double r = Double.parseDouble(element.getAttribute("r"));
            Color color = Color.valueOf(element.getAttribute("color"));
            return new Circle(cx, cy, r, color);
        } else if ("ellipse".equals(tagName)) {
            double cx = Double.parseDouble(element.getAttribute("cx"));
            double cy = Double.parseDouble(element.getAttribute("cy"));
            double rx = Double.parseDouble(element.getAttribute("rx"));
            double ry = Double.parseDouble(element.getAttribute("ry"));
            Color color = Color.valueOf(element.getAttribute("color"));
            return new Ellipse(cx, cy, rx, ry, color);
        } else if ("group".equals(tagName)) {
            String name = element.getAttribute("name");
            Group group = new Group(name);
            NodeList childNodes = element.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node node = childNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Shape childShape = parseShape((Element) node);
                    if (childShape != null) {
                        group.addShape(childShape);
                    }
                }
            }
            return group;
        }
        return null;
    }
}
