package org.aicl.raytracerchallenge.parser;

import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.Vector;
import org.aicl.raytracerchallenge.primitives.shape.Group;
import org.aicl.raytracerchallenge.primitives.shape.SmoothTriangle;
import org.aicl.raytracerchallenge.primitives.shape.Triangle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WavefrontObjParser {

    public WavefrontScene parse(String filePath) throws IOException {
        WavefrontScene result = new WavefrontScene();

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        String lastReferencedGroup = null;
        while((line = br.readLine()) != null){
            String[] components = line.trim().split(" ");
            String command = components[0];
            if(command.equals("v")){
                if(components.length != 4)
                    throw new IllegalArgumentException("Invalid line : " + line);

                result.vertices.add(new Point(
                        Double.parseDouble(components[1]),  Double.parseDouble(components[2]),  Double.parseDouble(components[3])
                ));
            }
            else if(command.equals("f")){
                boolean isSmoothTriangle         = false;
                ArrayList<Integer> normalIndices = null;

                if(components[1].contains("/")){
                    isSmoothTriangle = true;
                    normalIndices = new ArrayList<>();
                }

                ArrayList<Integer> verticeIdx = new ArrayList<>();
                for(int i = 1; i < components.length ; i++){
                    if(isSmoothTriangle){
                        String[] faceComponents = components[i].split("/");
                        verticeIdx.add(Integer.parseInt(faceComponents[0]) - 1);
                        normalIndices.add(Integer.parseInt(faceComponents[2]) - 1);
                    }
                    else{
                        verticeIdx.add(Integer.parseInt(components[i]) - 1);
                    }
                }
                ArrayList<Triangle> triangles = triangulate(result, verticeIdx, isSmoothTriangle, normalIndices);
                for(int i = 0; i < triangles.size(); i++){
                    if(lastReferencedGroup == null){
                        result.defaultGroup.addChild(triangles.get(i));
                    }
                    else{
                        Group g = result.groups.get(lastReferencedGroup);
                        if(g == null) {
                            g = new Group();
                            result.groups.put(lastReferencedGroup, g);
                        }
                        g.addChild(triangles.get(i));
                    }
                }
            }
            else if(command.equals("g")){
                if(components.length != 2)
                    throw new IllegalArgumentException("Invalid line : " + line);
                lastReferencedGroup = components[1];
            }
            else if(command.equals("vn")){
                if(components.length != 4)
                    throw new IllegalArgumentException("Invalid line : " + line);
                result.normals.add(new Vector(
                                Double.parseDouble(components[1]),  Double.parseDouble(components[2]),  Double.parseDouble(components[3])
                        ));
            }
            else{
                result.ignoredLines++;
            }
        }

        return result;
    }

    private ArrayList<Triangle> triangulate(WavefrontScene scene, ArrayList<Integer> vertexIndices
            , boolean isSmoothTriangle, ArrayList<Integer> normalsIndices){
        ArrayList<Triangle> result = new ArrayList<>();
        for(int i = 1 ; i < vertexIndices.size() - 1; i++){
            Point v1 = scene.vertices.get(vertexIndices.get(0));
            Point v2 = scene.vertices.get(vertexIndices.get(i));
            Point v3 = scene.vertices.get(vertexIndices.get(i + 1));
            if(isSmoothTriangle){
                Vector n1 = scene.normals.get(normalsIndices.get(0));
                Vector n2 = scene.normals.get(normalsIndices.get(i));
                Vector n3 = scene.normals.get(normalsIndices.get(i + 1));
                result.add(new SmoothTriangle(v1, v2, v3, n1, n2, n3));
            }
            else{
                result.add(new Triangle(v1, v2, v3));
            }
        }

        return result;
    }
}
