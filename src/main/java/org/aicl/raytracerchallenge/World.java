package org.aicl.raytracerchallenge;

import org.aicl.raytracerchallenge.primitives.*;
import org.aicl.raytracerchallenge.primitives.light.LightSampler;
import org.aicl.raytracerchallenge.primitives.light.PointLight;
import org.aicl.raytracerchallenge.primitives.ray.Intersection;
import org.aicl.raytracerchallenge.primitives.ray.PrecomputedIntersectionData;
import org.aicl.raytracerchallenge.primitives.ray.Ray;
import org.aicl.raytracerchallenge.primitives.ray.RayIntersection;
import org.aicl.raytracerchallenge.primitives.shape.Shape;
import org.aicl.raytracerchallenge.primitives.shape.Sphere;
import org.aicl.raytracerchallenge.transformation.TransformMatrixGenerator;
import org.aicl.raytracerchallenge.utilities.FloatEquality;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class World {
    public ArrayList<Shape> shapes      = new ArrayList<>();
    public ArrayList<PointLight> lights = new ArrayList<>();

    public World(){}

    public World(List<Shape> shapes, List<PointLight> lights){
        this.shapes.addAll(shapes);
        this.lights.addAll(lights);
    }

    public RayIntersection intersect(Ray ray){
        RayIntersection intersections = new RayIntersection(0, List.of());
        for(int i = 0; i < shapes.size() ; i++){
            RayIntersection spaheIntersect = shapes.get(i).intersect(ray);
            intersections.concat(spaheIntersect);
        }

        intersections.sort();
        return intersections;
    }

    public boolean containsLight(PointLight light){
        for(int i = 0; i < lights.size() ; i++){
            if(lights.get(i).isIdentical(light))
                return true;
        }
        return false;
    }

    public boolean containsObject(Shape s){
        for(int i = 0; i < shapes.size() ; i++){
            if(shapes.get(i).isSameCharacteristics(s))
                return true;
        }
        return false;
    }

    public Color shadeHit(PrecomputedIntersectionData data, int remaining){
        Color surfaceColor = new Color(0, 0, 0);
        for(int i = 0 ; i < lights.size() ; i++){
            boolean isInShadow = isInShadow(data.overPoint);
            surfaceColor = surfaceColor.add(LightSampler.lighting(data.intersectedObj.getMaterial(), data.intersectedObj,
                    lights.get(i), data.overPoint, data.eyev, data.normal, isInShadow));
        }
        Color reflectedColor = reflectedColor(data, remaining);
        return surfaceColor.add(reflectedColor);
    }

    public Color worldColorAtRay(Ray ray, int remaining){
        RayIntersection intersections = new RayIntersection(0, List.of());
        for(int i = 0 ; i < shapes.size() ; i++){
            RayIntersection shapeIntersection = shapes.get(i).intersect(ray);
            intersections.concat(shapeIntersection);
        }
        Intersection hit = intersections.hit();
        if(hit == null)
            return new Color(0, 0, 0);

        PrecomputedIntersectionData data = new PrecomputedIntersectionData();
        data.compute(hit, ray, new RayIntersection(0, List.of()));
        return shadeHit(data, remaining);
    }

    public Shape getObject(int index){
        return shapes.get(index);
    }

    public void addObject(Shape obj){
        this.shapes.add(obj);
    }

    public void addLight(PointLight light){
        lights.add(light);
    }

    public void setLight(PointLight light, int index){
        lights.set(index, light);
    }

    public boolean isInShadow(Point p){
        for(int i = 0; i < lights.size() ; i++){
            Tuple direction = lights.get(i).position.subtract(p);
            double distance = direction.magnitude();
            //normalized
            direction = direction.normalize();

            Ray r = new Ray(p, new Vector(direction));
            RayIntersection intersections = this.intersect(r);

            Intersection hit = intersections.hit();
            if(hit == null || hit.time >= distance)
                return false;
        }

        return true;
    }

    public Color reflectedColor(PrecomputedIntersectionData data, int remaining){
        if(FloatEquality.isEqual(data.intersectedObj.getMaterial().reflective, 0)){
            return new Color(0, 0, 0);
        }
        if(remaining <= 0)
            return new Color(0, 0, 0);

        Ray reflectedRay = new Ray(data.overPoint, data.reflectv);
        Color colorAt = worldColorAtRay(reflectedRay, remaining - 1); //get the color made by intersection of the reflected ray

        return colorAt.multiply(data.intersectedObj.getMaterial().reflective); // multiply with how reflective the material is
    }

    public static World createDefault(){
        PointLight light = new PointLight(new Point(-10, 10, -10), new Color(1, 1, 1));
        Sphere s1  = new Sphere();
        Material m = new Material();
        m.color    = new Color(0.8, 1.0, 0.6);
        m.diffuse  = 0.7;
        m.specular = 0.2;
        s1.setMaterial(m);

        TransformMatrixGenerator generator = new TransformMatrixGenerator();
        Sphere s2 = new Sphere();
        s2.setTransform(generator.scale(0.5, 0.5, 0.5));

        return new World(Arrays.asList(s1, s2), List.of(light));
    }
}
