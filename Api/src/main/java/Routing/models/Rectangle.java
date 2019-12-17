package Routing.models;

public class Rectangle {
    final private static double R_MAJOR = 6378137.0;
    final private static double R_MINOR = 6356752.3142;

    double longitude;
    double latitude;
    double x;
    double y;
    double w;
    double h;

    public Rectangle(double longitude, double latitude, double w, double h) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.w = w;
        this.h = h;
        // this.calculateX();
        // this.calculateY();
    }

    public Rectangle(int x , int y , int w , int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
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

    public boolean contains(Point p) {
        return (p.x >= this.x - this.w && p.x <= this.x + this.w && p.y >= this.y - this.h && p.y <= this.y + this.h);
    }

    public boolean intersects(Rectangle range) {
        return !(range.x - range.w > this.x + this.w || range.x + range.w < this.x - this.w
                || range.y - range.h > this.y + this.h || range.y + range.h < this.y - this.h);
    }
}