/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab5;

/**
 *
 * @author Professional
 */
public class Point4D {
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    
    public Point4D(double _x1, double _y1, double _x2, double _y2) {
        x1 = _x1;
        y1 = _y1;
        x2 = _x2;
        y2 = _y2;
    }
    
    public double getX1()
    {
        return x1;
    }
    
    public double getY1()
    {
        return y1;
    }
    
    public double getX2()
    {
        return x2;
    }
    
    public double getY2()
    {
        return y2;
    }
    
}