package org.aicl.raytracerchallenge.parser;

import org.aicl.raytracerchallenge.primitives.Point;
import org.aicl.raytracerchallenge.primitives.Vector;
import org.aicl.raytracerchallenge.primitives.shape.Group;

import java.util.ArrayList;
import java.util.HashMap;

public class WavefrontScene {
    public int ignoredLines = 0;
    public ArrayList<Point> vertices = new ArrayList<>();

    public ArrayList<Vector> normals = new ArrayList<>();

    public Group defaultGroup = new Group();

    public HashMap<String, Group> groups = new HashMap<>();

    public Group convertToSingleGroup(){
        Group result = new Group();
        result.addChild(defaultGroup);
        for (String key : groups.keySet()) {
            result.addChild(groups.get(key));
        }

        return result;
    }
}
