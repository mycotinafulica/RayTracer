package org.aicl.raytracerchallenge.display;

import org.aicl.raytracerchallenge.primitives.Color;

import java.util.ArrayList;
import java.util.List;

public class Canvas {
    private final int width;
    private final int height;

    //color value of pixel in 2d array pixels[row].[column]
    private final ArrayList<ArrayList<Color>> pixels = new ArrayList<>();

    public Canvas(int width, int height){
        this.width  = width;
        this.height = height;

        for(int row = 0 ; row < height ; row++){
            ArrayList<Color> rowPixels = new ArrayList<>();
            for(int col = 0 ; col < width ; col++){
                rowPixels.add(new Color(0, 0, 0));
            }
            pixels.add(rowPixels);
        }
    }

    public void writePixel(int col, int row, Color color){
        pixels.get(row).get(col).setColor(color);
    }

    public Color pixelAt(int col, int row){
        return pixels.get(row).get(col);
    }

    public List<Color> getAllPixelsAsList(){
        ArrayList<Color> result = new ArrayList<>();
        for(int row = 0 ; row < height ; row++){
            for(int col = 0 ; col < width ; col++){
                result.add(pixelAt(col, row));
            }
        }
        return result;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
