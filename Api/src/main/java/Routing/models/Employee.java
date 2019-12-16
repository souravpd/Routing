package Routing.models;

import com.esri.core.geometry.Point;

public class Employee {
    int id;
    String name;
    Point location;
    String address;
    boolean isAssignedBusStop = false;
    BusStop BusStop = null;

    public Employee(int id, Point p, String name, String address) {
        this.id = id;
        this.location = p;
        this.name = name;
        this.address = address;
    }

    public void assignBusStop(BusStop b) {
        this.BusStop = b;
        isAssignedBusStop = true;
    }
}