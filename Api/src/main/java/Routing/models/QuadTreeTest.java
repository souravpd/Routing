package Routing.models;

import java.util.HashMap;

public class QuadTreeTest {

    public static void main(String args[]) {
        QuadTree qTree = new QuadTree(new Rectangle(0, 0, 200, 200), 4);

        qTree.insert(new Employee(3, 77.4238889, 12.79694444, "Employee 3", "Address 3"), new Point(13, 14));
        qTree.insert(new Employee(1, 77.4238889, 12.79694444, "Employee 1", "Address 1"), new Point(5, 5));
        qTree.insert(new Employee(2, 77.4238889, 12.79694444, "Employee 2", "Address 2"), new Point(2, 2));
        qTree.insert(new Employee(3, 77.4238889, 12.79694444, "Employee 3", "Address 3"), new Point(4, -4));
        qTree.insert(new Employee(3, 77.4238889, 12.79694444, "Employee 3", "Address 3"), new Point(2, 1));
        qTree.insert(new Employee(3, 77.4238889, 12.79694444, "Employee 3", "Address 3"), new Point(3, 2));
        qTree.insert(new Employee(3, 77.4238889, 12.79694444, "Employee 3", "Address 3"), new Point(90, 100));
        qTree.insert(new Employee(3, 77.4238889, 12.79694444, "Employee 3", "Address 3"), new Point(93, 87));
        qTree.insert(new Employee(3, 77.4238889, 12.79694444, "Employee 3", "Address 3"), new Point(38, -29));
        qTree.insert(new Employee(3, 77.4238889, 12.79694444, "Employee 3", "Address 3"), new Point(-90, -8));
        HashMap<Employee, Point> found = new HashMap<>();
        found = qTree.query(new Rectangle(0, 0, 70, 70));
        for (Employee e : found.keySet()) {
            System.out.println(found.get(e).x + " " + found.get(e).y);
        }
    }
}