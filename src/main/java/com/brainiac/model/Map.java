package com.brainiac.model;

import java.util.List;

public class Map {
    private int width;
    private int height;
    private List<Path> paths;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public List<Path> getPaths() {
        return paths;
    }
}
