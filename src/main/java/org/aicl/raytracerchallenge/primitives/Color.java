package org.aicl.raytracerchallenge.primitives;

//immutable by design
public class Color {
    private Tuple tuple;

    public Color(double red, double green, double blue){
        tuple = new Tuple(red, green, blue, 0.0);
    }

    public Color(Tuple t){
        tuple = t;
    }

    public void setColor(Color color){
        this.tuple = color.toTuple();
    }

    public double red(){
        return tuple.x;
    }

    public double green(){
        return tuple.y;
    }

    public double blue(){
        return tuple.z;
    }

    public Tuple toTuple(){
        return tuple;
    }

    public Color add(Color color2){
        Tuple result = tuple.add(color2.toTuple());
        return new Color(result);
    }

    public Color subtract(Color color2){
        Tuple result = tuple.subtract(color2.toTuple());
        return new Color(result);
    }

    public Color multiply(double multiplier){
        Tuple result = tuple.multiply(multiplier);
        return new Color(result);
    }

    public Color multiply(Color color){
        return new Color(
                tuple.x * color.toTuple().x,
                tuple.y * color.toTuple().y,
                tuple.z * color.toTuple().z
        );
    }

    public boolean isIdentical(Color color2){
        return tuple.isIdentical(color2.toTuple());
    }
}
