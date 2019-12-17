package Routing.models;

import java.util.HashMap;

public class BusStopQuadTree {
    public Rectangle boundary;
    public int capacity;
    public HashMap<BusStop, Point> hm = new HashMap<BusStop, Point>();
    public boolean divided = false;
    public BusStopQuadTree northeast, northwest, southeast, southwest;

    public BusStopQuadTree(Rectangle boundary, int capacity) {
        this.capacity = capacity;
        this.boundary = boundary;
    }

    public boolean insert(BusStop e, Point p) {
        if (!this.boundary.contains(p)) {
            return false;
        }
        if (this.hm.size() < this.capacity) {
            this.hm.put(e, p);
            System.out.println("Added point " + p.x + " "+ p.y + " For the BusStop  " + e.name);
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
        this.northeast = new BusStopQuadTree(ne, this.capacity);
        Rectangle nw = new Rectangle(x - w / 2, y - h / 2, w / 2, h / 2);
        this.northwest = new BusStopQuadTree(nw, this.capacity);
        Rectangle se = new Rectangle(x + w / 2, y + h / 2, w / 2, h / 2);
        this.southeast = new BusStopQuadTree(se, this.capacity);
        Rectangle sw = new Rectangle(x - w / 2, y + h / 2, w / 2, h / 2);
        this.southwest = new BusStopQuadTree(sw, this.capacity);

        this.divided = true;
    }

    public HashMap<BusStop, Point> query(Rectangle range) {
        System.out.println("");
        System.out.println("Perfroming Range Queries For X : " + range.x + " Y : " + range.y + " Bounding : " + range.w);
        HashMap<BusStop, Point> hm = new HashMap<BusStop, Point>();
        this.query(range, hm);
        return hm;
    }

    private void query(Rectangle range, HashMap<BusStop, Point> found) {
        if (!this.boundary.intersects(range)) {
            return;
        } else {
            for (BusStop e : this.hm.keySet()) {
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