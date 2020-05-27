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
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author Professional
 */
public class LiangBarskyAlgorithmPanel extends JPanel 
{
    
    
    
    //   как сместить всё на правильные координаты, масштаб?
    //   обрезка не работает. size of result in 1 alg, нет зелёного; нет синего цвета во 2-ом,зелёный неправильно
    
    
    
    public static List<Point4D> subject = new ArrayList<Point4D>(), 
                                result = new ArrayList<Point4D>();
    
    public static List<Point2D> clipper = new ArrayList<Point2D>();

    private double xC = 355, yC = 275;
    
    public LiangBarskyAlgorithmPanel()
    {
       this.repaint();
       System.out.println("repaint");
    }
    
    private static boolean clipTest (double p, double q, double t1, double t2) 
    { 
        double r; 
        boolean retVal = true; 

        //line entry point 
        if (p < 0.0) 
        {     
            r = q / p; 
            // line portion is outside the clipping edge 
            if (r > t2)                          
                retVal = false; 
            else
                if (r > t1) 
                    t1 = r;  
        } 
        else
        //line leaving point 
            if (p > 0.0) 
            {                              
                r = q / p; 
                // line portion is outside      
                if (r < t1)                          
                    retVal = false;     
                else 
                    if (r < t2) 
                        t2 = r; 
            } 

            // p = 0, so line is parallel to this clipping edge  
            else    
            // Line is outside clipping edge  
                if (q < 0.0)                                  
                    retVal = false; 

        return retVal; 
    } 

    private static void clipLine(Point2D winMin, Point2D winMax, Point2D p1, Point2D p2)  
    {  
        double t1 = 0.0;
        double t2 = 1.0;
        double dx = p2.getX() - p1.getX();
        double dy; 

         // inside test wrt left edge 
        if (clipTest (-dx, p1.getX() - winMin.getX(), t1, t2))     

         // inside test wrt right edge  
        if(clipTest (dx, winMax.getX() - p1.getX(), t1, t2))  

        {                 
            dy = p2.getY() - p1.getY(); 

            // inside test wrt bottom edge  
            if(clipTest (-dy, p1.getY() - winMin.getY(), t1, t2)) 

                // inside test wrt top edge  
                if(clipTest (dy, winMax.getY() - p1.getY(), t1, t2)) { 

                if(t2 < 1.0) 
                { 
                    p2.setX(p1.getX() + t2 * dx); 
                    p2.setY(p1.getY() + t2 * dy); 
                } 

                if(t1 > 0.0)
                { 
                    p1.setX(p1.getX() + t1 * dx); 
                    p1.setY(p1.getX() + t1 * dy); 
                } 

                result.add(new Point4D( Math.round((p1.getX())), Math.round((p1.getY())),
                                        Math.round((p2.getX())), Math.round((p2.getY())))); 
                 // to result 
                } 
        } 

    }  
    
    public static void setPoints()
    {
        result  = new ArrayList<>(subject);
        for (int i = 0; i < subject.size(); i++)           
            clipLine(clipper.get(0), clipper.get(2), 
                    new Point2D(subject.get(i).getX1(), subject.get(i).getY1()),
                    new Point2D(subject.get(i).getX2(), subject.get(i).getY2()));
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

        drawLines(g2, subject, Color.blue);
        drawPolygon(g2, clipper, Color.red);
        
        //System.out.println(result.size());
        
        drawLines(g2, result, Color.green);
    }
    
    private void drawLines(Graphics2D g2, List<Point4D> points, Color color) {
        g2.setColor(color);
        int len = points.size();
        Line2D line = new Line2D.Double();
        for (int i = 0; i < len; i++) 
        {
            System.out.println(color.toString());
            Point2D p1 = new Point2D(points.get(i).getX1(), points.get(i).getY1());
            Point2D p2 = new Point2D(points.get((i + 1) % len).getX2(), points.get((i + 1) % len).getY2());
            line.setLine(p1.getX() + xC, p1.getY() + yC, p2.getX() + xC, p2.getY() + yC);
            g2.draw(line);
        }
    }
    
    private void drawPolygon(Graphics2D g2, List<Point2D> points, Color color) {
        g2.setColor(color);
        int len = points.size();
        Line2D line = new Line2D.Double();
        for (int i = 0; i < len; i++) 
        {
            System.out.println(color.toString());
            Point2D p1 = points.get(i);
            Point2D p2 = points.get((i + 1) % len);
            line.setLine(p1.getX() + xC, p1.getY() + yC, p2.getX() + xC, p2.getY() + yC);
            g2.draw(line);
        }
    }
    
}
