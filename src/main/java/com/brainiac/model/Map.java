package com.brainiac.model;

import com.brainiac.Skeleton;

import java.util.List;

public class Map {
    private int width;
    private int height;
    private List<Path> paths;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        Skeleton.writeFunctionDetails("Map(int: "+width+", int: "+height+")");
        Skeleton.writeReturnValue("");
    }

    public List<Path> getPaths() {
        Skeleton.writeFunctionDetails("Map.getPaths()");
        Skeleton.writeReturnValue("paths: List<Path>");
        return paths;
    }
}
