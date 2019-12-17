package Routing.models;

public class Rectangle {

    public double x;
    public double y;
    public double w;
    public double h;

    public Rectangle(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
    }

    public Rectangle(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public boolean contains(Point p) {
        return (p.x >= this.x - this.w && p.x <= this.x + this.w && p.y >= this.y - this.h && p.y <= this.y + this.h);
    }

    public boolean intersects(Rectangle range) {
        return !(range.x - range.w > this.x + this.w || range.x + range.w < this.x - this.w
                || range.y - range.h > this.y + this.h || range.y + range.h < this.y - this.h);
    }
}