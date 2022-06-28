package org.aicl.raytracerchallenge;

import org.aicl.raytracerchallenge.primitives.Matrix;
import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.Tuple;
import org.aicl.raytracerchallenge.primitives.Vector;
import org.aicl.raytracerchallenge.primitives.ray.Ray;

public class Camera {
    public int hsize;
    public int vsize;
    public double fov;
    public Matrix transform = Matrix.identity();

    public double pixelSize = 0;

    private double halfWidth;
    private double halfHeight;

    public Camera(int hsize, int vsize, double fov){
        this.hsize = hsize;
        this.vsize = vsize;
        this.fov = fov;

        double halfView = Math.tan(fov/2.0);
        double aspect = (double)hsize/vsize;

        if(aspect >= 1){
            halfWidth  = halfView;
            halfHeight = halfView / aspect;
        }
        else{
            halfWidth  = halfView * aspect;
            halfHeight = halfView;
        }

        pixelSize = (halfWidth * 2)/(double)hsize;
    }

    public Ray rayForPixel(int px, int py){
        double xoffset = (px + 0.5) * pixelSize;
        double yoffset = (py + 0.5) * pixelSize;

        double worldX = halfWidth - xoffset;
        double worldY = halfHeight - yoffset;

        Matrix inverseTr = transform.inverse();
        Tuple pxLocation = inverseTr.multiply(new Point(worldX, worldY, -1));
        Tuple origin     = inverseTr.multiply(new Point(0, 0, 0));
        Vector direction = new Vector(pxLocation.subtract(origin).normalize());

        return new Ray(new Point(origin), direction);
    }
}
