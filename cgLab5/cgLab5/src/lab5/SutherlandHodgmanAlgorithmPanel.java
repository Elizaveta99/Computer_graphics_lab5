/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab5;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author Professional
 */
public class SutherlandHodgmanAlgorithmPanel extends JPanel 
{
 
    public static List<Point2D> subject = new ArrayList<Point2D>(), 
                                clipper = new ArrayList<Point2D>(),
                                result = new ArrayList<Point2D>();
    private double xC = 355, yC = 275;
    //public double[][] subjPoints, clipPoints;
    
    public static void setPoints()
    {
//        double[][] subjPoints = {{50, 150}, {200, 50}, {350, 150}, {350, 300},
//        {250, 300}, {200, 250}, {150, 350}, {100, 250}, {100, 200}};
//
//        double[][] clipPoints = {{100, 100}, {300, 100}, {300, 300}, {100, 300}};

        //subject = new ArrayList<>(Arrays.asList(subjPoints));
        result  = new ArrayList<>(subject);
                System.out.println("res1 = " + result.size());

        //clipper = new ArrayList<>(Arrays.asList(clipPoints));

        clipPolygon();
    }
    
    public SutherlandHodgmanAlgorithmPanel()
    {
       this.repaint();
    }
    
    public static void clipPolygon() 
    {
        int len = clipper.size();
        for (int i = 0; i < len; i++) {

            int len2 = result.size();
            List<Point2D> input = result;
            result = new ArrayList<>(len2);
                    System.out.println("res2 = " + result.size());


            Point2D A = clipper.get((i + len - 1) % len);
            Point2D B = clipper.get(i);

            for (int j = 0; j < len2; j++) {

                Point2D P = input.get((j + len2 - 1) % len2);
                Point2D Q = input.get(j);

                if (isInside(A, B, Q)) {
                    if (!isInside(A, B, P))
                        result.add(intersection(A, B, P, Q));
                    result.add(Q);
                } else if (isInside(A, B, P))
                    result.add(intersection(A, B, P, Q));
            }
        }
    }

    public static boolean isInside(Point2D a, Point2D b, Point2D c) {
        return (a.getX() - c.getX()) * (b.getY() - c.getY()) > (a.getY() - c.getY()) * (b.getX() - c.getX());
    }

    public static Point2D intersection(Point2D a, Point2D b, Point2D p, Point2D q) {
        double A1 = b.getY() - a.getY();
        double B1 = a.getX() - b.getX();
        double C1 = A1 * a.getX() + B1 * a.getY();

        double A2 = q.getY() - p.getY();
        double B2 = p.getX() - q.getX();
        double C2 = A2 * p.getX() + B2 * p.getY();

        double det = A1 * B2 - A2 * B1;
        double x = (B2 * C1 - B1 * C2) / det;
        double y = (A1 * C2 - A2 * C1) / det;

        return new Point2D(x, y);
    }
    
    @Override
    protected void paintComponent(Graphics g) 
    {        
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.translate(0, 0);
        g2.setStroke(new BasicStroke(3));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.BLACK);
        g2.drawLine(0, 275, 730, 275);  // X
        g2.drawLine(355, 500, 355, 100);// Y
        g.drawString("Y", 360, 490);
	g.drawString("Y'", 355, 100);
        g.drawString("X", 0, 275);
	g.drawString("X'", 695, 275);
        g.drawString("(0, 0)", 325, 290);
        
        g.drawString(".", 355, 275); // center
        
        drawPolygon(g2, subject, Color.blue);
        drawPolygon(g2, clipper, Color.red);
        
        System.out.println(result.size());
        
        drawPolygon(g2, result, Color.green);
    }
    
    private void drawPolygon(Graphics2D g2, List<Point2D> points, Color color) {
        g2.setColor(color);
        int len = points.size();
        Line2D line = new Line2D.Double();
        for (int i = 0; i < len; i++) {
            System.out.println(color.toString());
            Point2D p1 = points.get(i);
            Point2D p2 = points.get((i + 1) % len);
            line.setLine(p1.getX() + xC, p1.getY() + yC, p2.getX() + xC, p2.getY() + yC);
            g2.draw(line);
        }
    }
    
}
