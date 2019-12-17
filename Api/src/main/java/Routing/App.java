package Routing;

import java.util.HashMap;

import Routing.models.Employee;
import Routing.models.Point;
import Routing.models.QuadTree;
import Routing.models.Rectangle;

public class App {
    public static void main(String[] args) {

        QuadTree qTree = new QuadTree(new Rectangle(0, 0, 200, 200), 4);

        qTree.insert(new Employee(3, new Point(77.4238889, 12.79694444), "Employee 3", "Address 3"), new Point(13, 14));
        qTree.insert(new Employee(1, new Point(77.4238889, 12.79694444), "Employee 1", "Address 1"), new Point(5, 5));
        qTree.insert(new Employee(2, new Point(77.4238889, 12.79694444), "Employee 2", "Address 2"), new Point(2, 2));
        qTree.insert(new Employee(3, new Point(77.4238889, 12.79694444), "Employee 3", "Address 3"), new Point(4, 4));
        qTree.insert(new Employee(3, new Point(77.4238889, 12.79694444), "Employee 3", "Address 3"), new Point(2, 1));
        qTree.insert(new Employee(3, new Point(77.4238889, 12.79694444), "Employee 3", "Address 3"), new Point(3, 2));
        qTree.insert(new Employee(3, new Point(77.4238889, 12.79694444), "Employee 3", "Address 3"), new Point(90, 100));
        qTree.insert(new Employee(3, new Point(77.4238889, 12.79694444), "Employee 3", "Address 3"), new Point(44, 45));
        qTree.insert(new Employee(3, new Point(77.4238889, 12.79694444), "Employee 3", "Address 3"), new Point(8, 89));
        qTree.insert(new Employee(3, new Point(77.4238889, 12.79694444), "Employee 3", "Address 3"), new Point(13, 23));
        qTree.insert(new Employee(3, new Point(77.4238889, 12.79694444), "Employee 3", "Address 3"), new Point(38, 40));
        qTree.insert(new Employee(3, new Point(77.4238889, 12.79694444), "Employee 3", "Address 3"), new Point(50, 50));
        qTree.insert(new Employee(3, new Point(77.4238889, 12.79694444), "Employee 3", "Address 3"), new Point(74, 2));
        qTree.insert(new Employee(3, new Point(77.4238889, 12.79694444), "Employee 3", "Address 3"), new Point(10, 15));
        qTree.insert(new Employee(3, new Point(77.4238889, 12.79694444), "Employee 3", "Address 3"), new Point(55, 11));
        qTree.insert(new Employee(3, new Point(77.4238889, 12.79694444), "Employee 3", "Address 3"), new Point(88, 5));
        qTree.insert(new Employee(3, new Point(77.4238889, 12.79694444), "Employee 3", "Address 3"), new Point(97, 97));
        qTree.insert(new Employee(3, new Point(77.4238889, 12.79694444), "Employee 3", "Address 3"), new Point(3, 3));
        qTree.insert(new Employee(3, new Point(77.4238889, 12.79694444), "Employee 3", "Address 3"), new Point(15, 35));
        qTree.insert(new Employee(3, new Point(77.4238889, 12.79694444), "Employee 3", "Address 3"), new Point(40, 57));
        qTree.insert(new Employee(3, new Point(77.4238889, 12.79694444), "Employee 3", "Address 3"), new Point(6, 6));

        HashMap<Employee, Point> found = new HashMap<>();
        found = qTree.query(new Rectangle(0, 0, 70, 70));
        for (Employee e : found.keySet()) {
            System.out.println(found.get(e).x + " " + found.get(e).y);
        }

    }
}