package com.agrinexus.data;
import com.agrinexus.ui.GUI;

import java.util.ArrayList;

public class Farmer {
    private String name;
    private ArrayList<Farm> farms;

    public Farmer() {
        this.farms = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addFarm(Farm farm) {
        farms.add(farm);
    }
    
    public void setNameFromGUI(GUI gui) {
        this.name = gui.getNameInput();
    }

    public ArrayList<Farm> getFarms() {
        return farms;
    }
}
