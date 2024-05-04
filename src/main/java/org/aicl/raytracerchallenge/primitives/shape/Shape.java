package org.aicl.raytracerchallenge.primitives.shape;

import org.aicl.raytracerchallenge.primitives.*;
import org.aicl.raytracerchallenge.primitives.ray.Intersection;
import org.aicl.raytracerchallenge.primitives.ray.Ray;
import org.aicl.raytracerchallenge.primitives.ray.RayIntersection;
import org.aicl.raytracerchallenge.transformation.RayTransformer;

public abstract class Shape {
    protected Matrix transform  = Matrix.identity();
    protected Material material = new Material();
    protected Shape parent = null;

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
    public Vector normalAt(Point p, Intersection hit) {
        Tuple objectPoint  = worldPointToObjectPoint(p);
        Tuple localNormal  = localNormal(objectPoint, hit);
        Tuple worldNormal  = objectNormalToWorldNormal(localNormal);
        worldNormal.w = 0;
        return new Vector(worldNormal.normalize());
    }

    public Tuple worldPointToObjectPoint(Point p){
        Tuple tempPoint = p;
        if(parent != null)
            tempPoint = parent.worldPointToObjectPoint(p);

        return transform.inverse().multiply(tempPoint);
    }

    public Tuple objectNormalToWorldNormal(Tuple localNormal){
        Tuple normal = transform.inverse().transpose().multiply(localNormal);
        normal.w = 0;
        normal = normal.normalize();

        if(parent != null)
            normal = parent.objectNormalToWorldNormal(normal);

        return normal;
    }

    public void setMaterial(Material m){
        this.material = m;
    }

    public Material getMaterial(){
        return material;
    }
    public abstract RayIntersection localIntersect(Ray transformedRay);
    public abstract Tuple localNormal(Tuple objectPoint, Intersection hit);

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

    public Shape getParent() {
        return parent;
    }

    public void setParent(Shape group){
        this.parent = group;
    }
}
