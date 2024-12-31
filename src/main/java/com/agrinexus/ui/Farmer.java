package com.agrinexus.ui;

import java.util.ArrayList;
import java.util.List;

public class Farmer {
    private int farmerId;
    private String name;
    private List<String> farms; // A list to store farm names or IDs

    public Farmer(int farmerId, String name) {
        this.farmerId = farmerId;
        this.name = name;
        this.farms = new ArrayList<>();
    }

    public int getFarmerId() {
        return farmerId;
    }

    public String getName() {
        return name;
    }

    public List<String> getFarms() {
        return new ArrayList<>(farms); // Return a copy of the list to avoid modification
    }

    public void addFarm(String farm) {
        farms.add(farm);
    }
}
