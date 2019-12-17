package Routing.models;

import java.util.HashMap;

public class QuadTree {
    public Rectangle boundary;
    public int capacity;
    public HashMap<Employee, Point> hm = new HashMap<Employee, Point>();
    public boolean divided = false;
    public QuadTree northeast, northwest, southeast, southwest;

    public QuadTree(Rectangle boundary, int capacity) {
        this.capacity = capacity;
        this.boundary = boundary;
    }

    public boolean insert(Employee e, Point p) {
        if (!this.boundary.contains(p)) {
            return false;
        }
        if (this.hm.size() < this.capacity) {
            this.hm.put(e, p);
            System.out.println("Added point " + p.x + " "+ p.y + " ");
            return true;
        } else {
            if (!this.divided) {
                this.subdivide();
            }
            if (this.northeast.insert(e, p)) {
                return true;
            } else if (this.northwest.insert(e, p)) {
                return true;
            } else if (this.southeast.insert(e, p)) {
                return true;
            } else if (this.southwest.insert(e, p)) {
                return true;
            }
        }
        return false;
    }

    public void subdivide() {
        double x = this.boundary.x;
        double y = this.boundary.y;
        double w = this.boundary.w;
        double h = this.boundary.h;

        Rectangle ne = new Rectangle(x + w / 2, y - h / 2, w / 2, h / 2);
        this.northeast = new QuadTree(ne, this.capacity);
        Rectangle nw = new Rectangle(x - w / 2, y - h / 2, w / 2, h / 2);
        this.northwest = new QuadTree(nw, this.capacity);
        Rectangle se = new Rectangle(x + w / 2, y + h / 2, w / 2, h / 2);
        this.southeast = new QuadTree(se, this.capacity);
        Rectangle sw = new Rectangle(x - w / 2, y + h / 2, w / 2, h / 2);
        this.southwest = new QuadTree(sw, this.capacity);

        this.divided = true;
    }

    public HashMap<Employee, Point> query(Rectangle range) {
        HashMap<Employee, Point> hm = new HashMap<Employee, Point>();
        this.query(range, hm);
        return hm;
    }

    private void query(Rectangle range, HashMap<Employee, Point> found) {
        if (!this.boundary.intersects(range)) {
            return;
        } else {
            for (Employee e : this.hm.keySet()) {
                if (range.contains(this.hm.get(e))) {
                    found.put(e, this.hm.get(e));
                }
            }

            if (this.divided) {
                this.northeast.query(range, found);
                this.northwest.query(range, found);
                this.southeast.query(range, found);
                this.southwest.query(range, found);
            }
        }
        return;
    }

}