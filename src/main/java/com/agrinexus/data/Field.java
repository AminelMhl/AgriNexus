package com.agrinexus.data;

import java.util.HashMap;
import java.util.Map;

public class Field {
    private int size;
    private String cropType;
    private Map<Integer,Integer> map = new HashMap<>();

    //constructor
    public Field(int size, String cropType) {
        this.size = size;
        this.cropType = cropType;
    }
}
