package Routing.models;

import com.esri.core.geometry.Point;

public class Employee {
    public int id;
    public String name;
    public Point location;
    public String address;
    public boolean isAssignedBusStop = false;
    public BusStop BusStop = null;

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