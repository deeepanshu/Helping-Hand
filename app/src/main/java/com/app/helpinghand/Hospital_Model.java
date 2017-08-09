package com.app.helpinghand;

/**
 * Created by Deepanshu on 16/2/2017.
 */

public class Hospital_Model {

    String name;
    String associated;
    String location;

    @Override
    public String toString() {
        return "  ●  "+name.toString()+"\n\t\t\t\t  -  "+associated.toString()+"\n\t\t\t\t  -  "+location.toString();
    }

    public String getAssociated() {
        return associated;
    }

    public void setAssociated(String associated) {
        this.associated = associated;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
