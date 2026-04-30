package fr.univrouen.draw.app;

import fr.univrouen.draw.model.Drawing;
import fr.univrouen.draw.persistence.XMLParser;
import fr.univrouen.draw.rendering.GraphicsRendererVisitor;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;

public class V2BmpApp {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java fr.univrouen.draw.app.V2BmpApp <input.vec> <output.png>");
            System.exit(1);
        }

        try {
            Drawing drawing = XMLParser.load(args[0]);

            int width = 800;
            int height = 600;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);

            GraphicsRendererVisitor renderer = new GraphicsRendererVisitor(g2d);
            renderer.visit(drawing);

            g2d.dispose();

            File outputFile = new File(args[1]);
            ImageIO.write(image, "png", outputFile);

            System.out.println("Converted " + args[0] + " to " + args[1]);
        } catch (Exception e) {
            System.err.println("Error converting file: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
