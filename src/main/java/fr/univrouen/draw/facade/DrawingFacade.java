package fr.univrouen.draw.facade;

import fr.univrouen.draw.model.*;
import fr.univrouen.draw.rendering.Canvas;
import fr.univrouen.draw.persistence.XMLSerializerVisitor;
import fr.univrouen.draw.persistence.XMLParser;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DrawingFacade {
    private Canvas canvas;
    private Drawing drawing;

    public DrawingFacade(Canvas canvas) {
        this.canvas = canvas;
        this.drawing = canvas.getDrawing();
    }

    public boolean execute(String commandLine) {
        String[] parts = commandLine.trim().split("\\s+");
        if (parts.length == 0 || parts[0].isEmpty()) return true;

        String cmd = parts[0].toLowerCase();
        try {
            switch (cmd) {
                case "new":
                    drawing = new Drawing();
                    canvas.setDrawing(drawing);
                    break;
                case "load":
                    if (parts.length > 1) {
                        drawing = XMLParser.load(parts[1]);
                        canvas.setDrawing(drawing);
                    }
                    break;
                case "save":
                    if (parts.length > 1) {
                        XMLSerializerVisitor serializer = new XMLSerializerVisitor();
                        serializer.visit(drawing);
                        try (PrintWriter out = new PrintWriter(new FileWriter(parts[1]))) {
                            out.print(serializer.getXml());
                        }
                    }
                    break;
                case "line":
                    if (parts.length == 6) {
                        drawing.addShape(new Line(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]),
                                Double.parseDouble(parts[3]), Double.parseDouble(parts[4]), Color.valueOf(parts[5])));
                        canvas.repaint();
                    }
                    break;
                case "rect":
                    if (parts.length == 6) {
                        drawing.addShape(new Rectangle(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]),
                                Double.parseDouble(parts[3]), Double.parseDouble(parts[4]), Color.valueOf(parts[5])));
                        canvas.repaint();
                    }
                    break;
                case "circ":
                    if (parts.length == 5) {
                        drawing.addShape(new Circle(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]),
                                Double.parseDouble(parts[3]), Color.valueOf(parts[4])));
                        canvas.repaint();
                    }
                    break;
                case "elli":
                    if (parts.length == 6) {
                        drawing.addShape(new Ellipse(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]),
                                Double.parseDouble(parts[3]), Double.parseDouble(parts[4]), Color.valueOf(parts[5])));
                        canvas.repaint();
                    }
                    break;
                case "list":
                    List<Shape> shapes = drawing.getShapes();
                    for (int i = 0; i < shapes.size(); i++) {
                        Shape s = shapes.get(i);
                        String shapeDesc = s.getClass().getSimpleName().toLowerCase();
                        if (s instanceof Group) {
                            shapeDesc += " [" + ((Group)s).getName() + "]";
                        }
                        System.out.println((i + 1) + " " + shapeDesc);
                    }
                    break;
                case "grp":
                    if (parts.length >= 3) {
                        String[] indicesStr = parts[1].split(",");
                        List<Integer> indices = new ArrayList<>();
                        for (String idxStr : indicesStr) {
                            indices.add(Integer.parseInt(idxStr) - 1);
                        }
                        Collections.sort(indices, Collections.reverseOrder());
                        
                        StringBuilder labelBuilder = new StringBuilder();
                        for (int i = 2; i < parts.length; i++) {
                            labelBuilder.append(parts[i]).append(i == parts.length - 1 ? "" : " ");
                        }
                        
                        Group group = new Group(labelBuilder.toString());
                        for (int idx : indices) {
                            if (idx >= 0 && idx < drawing.getShapeCount()) {
                                group.addShape(drawing.getShape(idx));
                                drawing.removeShape(idx);
                            }
                        }
                        drawing.addShape(group);
                        canvas.repaint();
                    }
                    break;
                case "ugrp":
                    if (parts.length == 2) {
                        int idx = Integer.parseInt(parts[1]) - 1;
                        if (idx >= 0 && idx < drawing.getShapeCount()) {
                            Shape s = drawing.getShape(idx);
                            if (s instanceof Group) {
                                Group group = (Group) s;
                                drawing.removeShape(idx);
                                for (Shape child : group.getShapes()) {
                                    drawing.addShape(child);
                                }
                                canvas.repaint();
                            }
                        }
                    }
                    break;
                case "quit":
                    return false;
                default:
                    System.out.println("Unknown command: " + cmd);
            }
        } catch (Exception e) {
            System.out.println("Error executing command: " + e.getMessage());
        }
        return true;
    }
}
