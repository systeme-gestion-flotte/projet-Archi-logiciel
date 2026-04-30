package fr.univrouen.draw.rendering;

import fr.univrouen.draw.model.Drawing;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Canvas extends JPanel {
    private Drawing drawing;

    public Canvas() {
        this.drawing = new Drawing();
        setBackground(java.awt.Color.WHITE);
    }

    public void setDrawing(Drawing drawing) {
        this.drawing = drawing;
        repaint();
    }

    public Drawing getDrawing() {
        return drawing;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (drawing != null) {
            GraphicsRendererVisitor visitor = new GraphicsRendererVisitor(g2d);
            visitor.visit(drawing);
        }
    }
}
