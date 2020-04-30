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
public class Point2D {
    private double x;
    private double y;
    
    public Point2D(double _x, double _y) {
        x = _x;
        y = _y;
    }
    
    public double getX()
    {
        return x;
    }
    
    public double getY()
    {
        return y;
    }
    
    public void setX(double _x)
    {
        x = _x;
    }
    
    public void setY(double _y)
    {
        y = _y;
    }

}
