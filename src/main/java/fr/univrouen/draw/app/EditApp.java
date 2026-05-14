package fr.univrouen.draw.app;

import fr.univrouen.draw.facade.DrawingFacade;
import fr.univrouen.draw.rendering.Canvas;

import javax.swing.JFrame;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class EditApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Vector Drawing Editor");
        Canvas canvas = new Canvas();
        frame.add(canvas);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        DrawingFacade facade = new DrawingFacade(canvas);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Vector Drawing Editor started.");
        try {
            while (true) {
                System.out.print("> ");
                String line = reader.readLine();
                if (line == null) break;
                if (!facade.execute(line)) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        frame.dispose();
        System.exit(0);
    }
}
