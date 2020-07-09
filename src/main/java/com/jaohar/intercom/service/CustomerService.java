package com.jaohar.intercom.service;

import com.google.gson.Gson;
import com.jaohar.intercom.entity.Customer;
import com.jaohar.intercom.util.AppUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    public static Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private static Gson gson = new Gson();

    //Using google gson to convert to and from json

    @Autowired
    AppUtil appUtil;


    /**
     * After an instance of CustomerService is created,
     * it should trigger the method to get the customers that should be invited
     */
    @PostConstruct
    public void inviteToOffice(){
        List<Customer> invitees = appUtil.getInvitees();
        logger.info(invitees.toString());
        try(FileWriter fileWriter = new FileWriter("output.txt")){
            invitees.forEach(customer ->  write(fileWriter, customer) );
        }catch (IOException ex){
            logger.error("Error writing file", ex);
        }
    }

    /**
     * Utility method to write customer as json using the fileWriter
     * @param writer
     * @param customer
     */
    private void write(FileWriter writer, Customer customer){
        try {
            writer.write(gson.toJson(customer) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
