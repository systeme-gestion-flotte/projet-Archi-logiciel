package fr.univrouen.draw.app;

import fr.univrouen.draw.model.Drawing;
import fr.univrouen.draw.model.Shape;
import fr.univrouen.draw.persistence.XMLParser;
import fr.univrouen.draw.persistence.XMLSerializerVisitor;

import java.io.FileWriter;
import java.io.PrintWriter;

public class MergeApp {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Usage: java fr.univrouen.draw.app.MergeApp <file1.vec> <file2.vec> <output.vec>");
            System.exit(1);
        }

        try {
            Drawing d1 = XMLParser.load(args[0]);
            Drawing d2 = XMLParser.load(args[1]);

            Drawing merged = new Drawing();
            for (Shape s : d1.getShapes()) merged.addShape(s);
            for (Shape s : d2.getShapes()) merged.addShape(s);

            XMLSerializerVisitor serializer = new XMLSerializerVisitor();
            serializer.visit(merged);
            try (PrintWriter out = new PrintWriter(new FileWriter(args[2]))) {
                out.print(serializer.getXml());
            }

            System.out.println("Merged " + args[0] + " and " + args[1] + " into " + args[2]);
        } catch (Exception e) {
            System.err.println("Error merging files: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
