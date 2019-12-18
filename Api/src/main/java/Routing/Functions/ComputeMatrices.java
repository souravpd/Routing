package Routing.Functions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import Routing.models.BusStop;

public class ComputeMatrices {
    ArrayList<BusStop> busStopList = new ArrayList<BusStop>();

    public ComputeMatrices(ArrayList<BusStop> b) {
        this.busStopList = b;
    }

    @SuppressWarnings("unchecked")
    public void createMatrixFiles() throws ParseException, IOException {

        JSONObject obj = new JSONObject();
        JSONArray locations = new JSONArray();
        JSONArray depot = new JSONArray();
        depot.add(8.477473);
        depot.add(8.477473);
        locations.add(depot);
        for (int i = 0; i < this.busStopList.size(); i++) {
            JSONArray busStop = new JSONArray();
            busStop.add(this.busStopList.get(i).longitude);
            busStop.add(this.busStopList.get(i).latitude);
            locations.add(busStop);
        }
        obj.put("locations", locations);
        JSONArray metrics = new JSONArray();
        metrics.add("distance");
        metrics.add("duration");
        obj.put("metrics", metrics);
        obj.put("units", "km");
        System.out.println(obj.toJSONString());

        Client client = ClientBuilder.newClient();
        Entity<String> payload = Entity.json(obj.toJSONString());
        Response response = client.target("https://api.openrouteservice.org/v2/matrix/driving-car").request()
                .header("Authorization", "5b3ce3597851110001cf62480ba9fe30e0d343249b2eb6cf328df7b9")
                .header("Accept", "application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8")
                .post(payload);

        String data = response.readEntity(String.class);

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(data);

        File fout = new File("distancesMatrix.txt");
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        JSONArray distances = (JSONArray) json.get("distances");

        for(int i = 0 ; i < distances.size() ; i++){
            JSONArray distance = (JSONArray) distances.get(i);
            int counter = 0;
            Iterator<Double> it = distance.iterator();
            while (it.hasNext()) {
                String line = i + " " + counter + " " + it.next();
                bw.write(line);
                bw.newLine();
                counter++;
            }
        }

        File F = new File("durationsMatrix.txt");
        FileOutputStream FS = new FileOutputStream(F);
        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(FS));

        JSONArray durations = (JSONArray) json.get("durations");

        for(int i = 0 ; i < durations.size() ; i++){
            JSONArray duration = (JSONArray) durations.get(i);
            int counter = 0;
            Iterator<Double> it = duration.iterator();
            while (it.hasNext()) {
                String line = i + " " + counter + " " + it.next();
                br.write(line);
                br.newLine();
                counter++;
            }
        }

    }
}
