package Routing.Functions;

import java.util.Arrays;
import java.util.Iterator;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Routing.models.BusStop;
import Routing.models.Employee;

public class DistanceCalculator {

    @SuppressWarnings("unchecked")

    public static double[] getRouteDistance(double long1, double lat1, double long2, double lat2)
            throws ParseException {

        try {
            System.out.println("Sleeping For 3 Seconds");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject obj = new JSONObject();
        JSONArray locations = new JSONArray();
        JSONArray loc = new JSONArray();
        loc.add(long1);
        loc.add(lat1);
        JSONArray loc2 = new JSONArray();
        loc2.add(long2);
        loc2.add(lat2);
        locations.add(loc);
        locations.add(loc2);
        obj.put("locations", locations);
        JSONArray metrics = new JSONArray();
        metrics.add("distance");
        metrics.add("duration");
        obj.put("metrics", metrics);
        obj.put("units", "km");
        System.out.println(obj);
        Client client = ClientBuilder.newClient();
        Entity<String> payload = Entity.json(obj.toJSONString());
        Response response = client.target("https://api.openrouteservice.org/v2/matrix/driving-car").request()
                .header("Authorization", "5b3ce3597851110001cf62485b455df7934944439d4e88f2dae157a3")
                .header("Accept", "application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8")
                .post(payload);

        String data = response.readEntity(String.class);
        System.out.println(data);
        double arr[] = new double[4];
        Arrays.fill(arr, -1.0);
        JSONObject json;

        try {
            JSONParser parser = new JSONParser();
            json = (JSONObject) parser.parse(data);

        } catch (Exception e) {
            return arr;
        }

        int counter = 0;
        JSONArray distances = (JSONArray) json.get("distances");
        if (distances == null) {
            return arr;
        }
        if (distances.size() == 0) {
            return arr;
        }
        for (int i = 0; i < distances.size(); i++) {
            JSONArray distance = (JSONArray) distances.get(i);
            Iterator<Double> it = distance.iterator();
            while (it.hasNext()) {
                arr[counter++] = it.next();
            }
        }
        return arr;

    }

    public static double getRouteDistance(Employee e, BusStop b) throws ParseException {
        double long1 = e.longitude;
        double lat1 = e.latitude;
        double long2 = b.longitude;
        double lat2 = b.latitude;
        double arr[] = new double[4];
        arr = DistanceCalculator.getRouteDistance(long1, lat1, long2, lat2);
        if (arr[1] == -1) {
            System.out.println("Using Carteisan for Employee" + e.name + " And Bus Stop " + b.name);
            return Math.sqrt((e.x - b.x) * (e.x - b.x) + (e.y - b.y) * (e.y - b.y));
        }
        return arr[1];
    }

    public static double getRouteDistance(BusStop b1, BusStop b2) throws ParseException {
        double long1 = b1.longitude;
        double lat1 = b1.latitude;
        double lat2 = b2.latitude;
        double long2 = b2.longitude;
        double arr[] = new double[4];
        arr = DistanceCalculator.getRouteDistance(long1, lat1, long2, lat2);
        if (arr[1] == -1) {
            System.out.println("Using Carteisan for BusStop" + b1.name + " And Bus Stop " + b2.name);
            return Math.sqrt((b1.x - b2.x) * (b1.x - b2.x) + (b1.y - b2.y) * (b1.y - b2.y));
        }
        return arr[1];
    }
}