package Routing.models;

import java.util.Comparator;

public class Tuple {
    public Employee e;
    public Point p;

    public Tuple(Employee e, Point p) {
        this.e = e;
        this.p = p;
    }

    public static Comparator<Tuple> TupleComparator = new Comparator<Tuple>() {

        public int compare(Tuple t1, Tuple t2) {
            double x1 = Math.abs(t1.p.x);
            double y1 = Math.abs(t1.p.y);
            double x2 = Math.abs(t2.p.x);
            double y2 = Math.abs(t2.p.y);
            double dist1 = Math.sqrt(x1 * x1 + y1 * y1);
            double dist2 = Math.sqrt(x2 * x2 + y2 * y2);
            if (dist1 > dist2)
                return 1;
            if (dist2 > dist1)
                return -1;
            else
                return 0;
        }
    };
}