/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab5;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Professional
 */
public class LineClippingPanel extends javax.swing.JPanel
{

    public static final int INSIDE = 0;
    public static final int LEFT   = 1;
    public static final int RIGHT  = 2;
    public static final int BOTTOM = 4;
    public static final int TOP    = 8;

    private int xMin;
    private int xMax;
    private int yMin;
    private int yMax;

    private LineClipper clipper;

    private class LineSegment {
        public int x0;
        public int y0;
        public int x1;
        public int y1;

        public LineSegment(int x0, int y0, int x1, int y1) {
            this.x0 = x0;
            this.y0 = y0;
            this.x1 = x1;
            this.y1 = y1;
        }

        @Override
        public String toString() {
            return "LineSegment(x0: " + x0 + ", y0: " + y0 + "; x1: " + x1 + ", y1: " + y1 + ")";
        }
    }

    public interface LineClipper {
        public LineSegment clip(LineSegment clip);
    }

    public class LiangBarsky implements LineClipper {

        /**
         * Execute line clipping using Liang-Barsky
         * @param line Line segment to work with
         * @return Clipped line
         */
        public LineSegment clip(LineSegment line) {
            System.out.println("\nExecuting Liang-Barsky...");
            double u1 = 0, u2 = 1;
            int x0 = line.x0, y0 = line.y0, x1 = line.x1, y1 = line.y1;
            int dx = x1 - x0, dy = y1 - y0;
            int p[] = {-dx, dx, -dy, dy};
            int q[] = {x0 - xMin, xMax - x0, y0 - yMin, yMax - y0};
            for (int i = 0; i < 4; i++) {
                if (p[i] == 0) {
                    if (q[i] < 0) {
                        return null;
                    }
                } else {
                    double u = (double) q[i] / p[i];
                    if (p[i] < 0) {
                        u1 = Math.max(u, u1);
                    } else {
                        u2 = Math.min(u, u2);
                    }
                }
            }
            System.out.println("u1: " + u1 + ", u2: " + u2);
            if (u1 > u2) {
                return null;
            }
            int nx0, ny0, nx1, ny1;
            nx0 = (int) (x0 + u1 * dx);
            ny0 = (int) (y0 + u1 * dy);
            nx1 = (int) (x0 + u2 * dx);
            ny1 = (int) (y0 + u2 * dy);
            return new LineSegment(nx0, ny0, nx1, ny1);
        }
    }

    /**
     * Constructor
     * @param xMin Bottom side of rectangle
     * @param yMin Left side of rectangle
     * @param xMax Top side of rectangle
     * @param yMax Right side of rectangle
     * @param clipperOption Code for LineClipper algorithm to use (0: Cohen-Sutherland, 1: Liang-Barsky)
     */
    public LineClippingPanel(int xMin, int yMin, int xMax, int yMax)
    {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
        clipper = new LiangBarsky();     
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(Color.blue);
        drawLine(g2d, xMin, 0, xMin, getHeight());
        drawLine(g2d, xMax, 0, xMax, getHeight());
        drawLine(g2d, 0, yMin, getWidth(), yMin);
        drawLine(g2d, 0, yMax, getWidth(), yMax);

        int x0, y0, x1, y1;
        LineSegment line, clippedLine;
        for (int i = 0; i < 10; i++) {
            x0 = (int)(Math.random() * getWidth());
            x1 = (int)(Math.random() * getWidth());
            y0 = (int)(Math.random() * getHeight());
            y1 = (int)(Math.random() * getHeight());
            line = new LineSegment(x0, y0, x1, y1);
            clippedLine = clipper.clip(line);

            System.out.println("Original: " + line);
            System.out.println("Clipped: " + clippedLine);

            if (clippedLine == null) {
                g2d.setColor(Color.red);
                drawLine(g2d, line.x0, line.y0, line.x1, line.y1);
            } else {
                g2d.setColor(Color.red);
                drawLine(g2d, line.x0, line.y0, clippedLine.x0, clippedLine.y0);
                drawLine(g2d, clippedLine.x1, clippedLine.y1, line.x1, line.y1);
                g2d.setColor(Color.green);
                drawLine(g2d, clippedLine.x0, clippedLine.y0, clippedLine.x1, clippedLine.y1);
            }
        }
    }

    private void drawLine(Graphics g, int x1, int y1, int x2, int y2) {
        g.drawLine(x1, getHeight() - y1, x2, getHeight() - y2);
    }

//    public static void main(String[] args) {
//
//        
//
//        JFrame mainFrame = new JFrame("Line Clipping");
//        mainFrame.setSize(800, 600);
//        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        int x0, y0, x1, y1;
//        x0 = y0 = x1 = y1 = -1;
//
//        do {
//            String response = JOptionPane.showInputDialog(mainFrame, "Please insert the coordinates of the lower left and upper right points\nof the rectangle separated by commas.\n"
//                    + "Format: \"xMin,yMin,xMax,yMax\"; xMin < xMax and yMin < yMax\n"
//                    + "(0 <= x <= 800 and 0 <= y <= 600)",
//                                                          "100,100,700,500");
//            if (response == null) System.exit(0);
//            String[] coordinates = response.split(",");
//            try {
//                x0 = Integer.parseInt(coordinates[0]);
//                y0 = Integer.parseInt(coordinates[1]);
//                x1 = Integer.parseInt(coordinates[2]);
//                y1 = Integer.parseInt(coordinates[3]);
//            } catch(NumberFormatException ne) {
//                JOptionPane.showMessageDialog(mainFrame, "All values must be integers");
//            } finally {}
//        } while (0 > x0 || x1 > 800 || 0 > y0 || y1 > 600 || x0 >= x1 || y0 >= y1);
//
//
//
//        String algorithms[] = {"Cohen-Sutherland", "Liam-Barsky"};
//        int choice = JOptionPane.showOptionDialog(mainFrame, "Choose the algorithm to use", "Algoritm selection",
//                                                  JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
//                                                  null, algorithms, algorithms[0]);
//        
//        if (choice == JOptionPane.CLOSED_OPTION) {
//            System.exit(0);
//        }
//
//        mainFrame.add(new LineClippingPanel(x0, y0, x1, y1, choice));
//        mainFrame.setLocationRelativeTo(null);
//        mainFrame.setVisible(true);
//    }

}
