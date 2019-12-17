package Routing.models;

public class Point {
    final private static double R_MAJOR = 6378137.0;
    final private static double R_MINOR = 6356752.3142;

    public double longitude;
    public double latitude;
    public double x;
    public double y;
    String Type;

    public Point(double longitude, double latitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Point(int x , int y){
        this.x = x;
        this.y = y;
    }

    public Point(double longitude, double latitude, String Type) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.Type = Type;
        // this.calculateX();
        // this.calculateY();
    }

    public void calculateX() {
        this.x = R_MAJOR * Math.toRadians(this.longitude);
    }

    public void calculateY() {
        if (this.latitude > 89.5) {
            this.latitude = 89.5;
        }
        if (this.latitude < -89.5) {
            this.latitude = -89.5;
        }
        double temp = R_MINOR / R_MAJOR;
        double es = 1.0 - (temp * temp);
        double eccent = Math.sqrt(es);
        double phi = Math.toRadians(this.latitude);
        double sinphi = Math.sin(phi);
        double con = eccent * sinphi;
        double com = 0.5 * eccent;
        con = Math.pow(((1.0 - con) / (1.0 + con)), com);
        double ts = Math.tan(0.5 * ((Math.PI * 0.5) - phi)) / con;
        double y = 0 - R_MAJOR * Math.log(ts);
        this.y = y;
    }

}