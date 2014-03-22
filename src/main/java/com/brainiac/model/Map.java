package com.brainiac.model;

import com.brainiac.controller.Skeleton;

import java.util.List;

public class Map {
    private int width;
    private int height;
    private List<Path> paths;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        Skeleton.writeFunctionDetails("Map(int: "+width+", int: "+height+")");
        Skeleton.writeReturnValue("void");
    }

    public List<Path> getPaths() {
        return paths;
    }
}
