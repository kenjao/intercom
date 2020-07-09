package com.jaohar.intercom.util;

import com.google.gson.Gson;
import com.jaohar.intercom.entity.Customer;
import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicLine;
import net.sf.geographiclib.GeodesicMask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppUtil {

    private static Logger logger = LoggerFactory.getLogger(AppUtil.class);
    private static Gson gson = new Gson();
    private static Geodesic geod = Geodesic.WGS84;
    // This matches EPSG4326, which is the coordinate system used by Geolake
    //Made use of the existing geo library


    @Value("${customer.fetch.url:https://s3.amazonaws.com/intercom-take-home-test/customers.txt}")
    private String url;

    @Value("${dublin.latitude:53.339428}")
    private double dublinLatitude;

    @Value("${dublin.longitude:-6.257664}")
    private double dublinLongitude;


    /**
     * Get the distance between two points in kilometers.
     * @param latitude First point's latitude
     * @param longitude First point's longitude
     * @return Distance between the first and the second point in meters
     */
    public double getDistance(double latitude, double longitude) {
        GeodesicLine line = geod.InverseLine(latitude, longitude, dublinLatitude, dublinLongitude, GeodesicMask.DISTANCE_IN | GeodesicMask.LATITUDE | GeodesicMask.LONGITUDE);
        return line.Distance()/1000;
    }


    /**
     * Gets customers from the customers.txt file in resource folder
     * @return List of customers
     */
    protected List<Customer> getCustomersFromFile(){
        Path customerFilePath = Paths.get(URI.create(AppUtil.class.getClassLoader().getResource("customers.txt").toString()));
        if(Files.notExists(customerFilePath)){
            logger.error("File not found!!!");
            return null;
        }

        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(customerFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }


        List<Customer> customers =  lines.stream().map( customer -> gson.fromJson(customer, Customer.class)).collect(Collectors.toList());

        return customers;
    }

    /**
     *
     * @return List of customers
     */
    public List<Customer> getCustomers(){
        List<Customer> customers = getCustomersFromWeb();
        return customers != null ? customers: getCustomersFromFile();
    }

    /**
     * Gets the customers from the web url
     * @return a list of customers
     */
    protected List<Customer> getCustomersFromWeb()  {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        List<String> lines = Arrays.asList(response.split("\\n"));
        List<Customer> customers = lines.stream().map((customer)-> gson.fromJson(customer, Customer.class)).collect(Collectors.toList());

        return customers;
    }

    /**
     * Filters customers that are within 100km to the dublin office
     * @return a list of customers in ascending order by customer id
     */
    public List<Customer> getInvitees(){
        List<Customer> invitees = getCustomers().stream().filter(customer -> {
            return getDistance(customer.getLatitude(), customer.getLongitude()) < 100;
        }).collect(Collectors.toList());

        Customer[] custArray = invitees.toArray(new Customer[0]);
        Arrays.sort(custArray);

        return Arrays.asList(custArray);
    }




}
