package com.agrinexus.data;

import java.util.ArrayList;

public class Farm {
    ArrayList<Field> fields = new ArrayList<>();

    public void addField(Field field) {
        fields.add(field);
    }

    public ArrayList<Field> getFields() {
        return fields;
    }
}
