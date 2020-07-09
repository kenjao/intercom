package com.jaohar.intercom.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer implements Comparable {

    private double latitude;
    private double longitude;
    private int user_id;
    private String name;

    @Override
    public boolean equals(Object obj) {
        Customer cust = (Customer)obj;
        return getUser_id() == cust.getUser_id();
    }

    @Override
    public int compareTo(Object o) {
        Customer cust = (Customer)o;

        if(getUser_id() < cust.getUser_id()) {
            return -1;
        }

        if(getUser_id() > cust.getUser_id()) {
            return 1;
        }

        return 0;
    }
}
