package org.aicl.raytracerchallenge.display;

import org.aicl.raytracerchallenge.primitives.Color;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

    public void writeToPpmFile(String fileName) throws IOException {
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
        //header
        writer.println("P3");
        writer.println(width + " " + height);
        writer.println("255");


        for(int row = 0 ; row < height; row++){
            StringBuilder line = new StringBuilder();
            for(int col = 0; col < width; col++){
                Color color = pixelAt(col, row);
                int clampedRed  = clampValue((int)Math.round(color.red() * 255.0), 0, 255);
                int clampedGreen = clampValue((int)Math.round(color.green() * 255.0), 0, 255);
                int clampedBlue  = clampValue((int)Math.round(color.blue() * 255.0), 0, 255);

                String[] colorStr = new String[]{
                        Integer.toString(clampedRed),
                        Integer.toString(clampedGreen),
                        Integer.toString(clampedBlue)
                };

                for(int i = 0 ; i < colorStr.length ; i++){
                    //if the current line plus the color string plus space larger than 70 (max char per line for ppm)
                    //To be safe we use equality (in case the new line is counted as character)
                    if((line.length() + colorStr[i].length() + 1) >= 70){
                        //flush
                        writer.println(line.toString());
                        line = new StringBuilder();
                    }

                    if(line.length() == 0){
                        line.append(colorStr[i]);
                    }
                    else{
                        line.append(" ");
                        line.append(colorStr[i]);
                    }
                }
            }
            writer.println(line);
        }

        writer.close();
    }

    private int clampValue(int value, int min, int max){
        if(value < min)
            return min;
        return Math.min(value, max);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
