package org.aicl.raytracerchallenge.primitives.shape;

import org.aicl.raytracerchallenge.primitives.*;
import org.aicl.raytracerchallenge.primitives.ray.Ray;
import org.aicl.raytracerchallenge.primitives.ray.RayIntersection;
import org.aicl.raytracerchallenge.transformation.RayTransformer;

public abstract class Shape {
    protected Matrix transform  = Matrix.identity();
    protected Material material = new Material();

    public Shape(){
        material = new Material();
    }

    public RayIntersection intersect(Ray r){
        Ray transformedRay = RayTransformer.transform(r, transform.inverse());
        return localIntersect(transformedRay);
    }


    public Matrix getTransform(){
        return transform;
    }
    public void setTransform(Matrix m){
        this.transform = m;
    }
    public Vector normalAt(Point p) {
        Tuple objectPoint  = transform.inverse().multiply(p);
        Tuple localNormal  = localNormal(objectPoint);
        Tuple worldNormal  = transform.inverse().transpose().multiply(localNormal);
        worldNormal.w = 0;
        return new Vector(worldNormal.normalize());
    }

    public void setMaterial(Material m){
        this.material = m;
    }

    public Material getMaterial(){
        return material;
    }
    public abstract RayIntersection localIntersect(Ray transformedRay);
    public abstract Tuple localNormal(Tuple objectPoint);

    public abstract boolean isSame(Shape input);
    public abstract boolean isSameCharacteristics(Shape shape);
    public abstract String getId();

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Shape){
            return isSame((Shape) obj);
        }

        return false;
    }
}
