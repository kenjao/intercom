package com.jaohar.intercom.util;

import com.jaohar.intercom.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class AppUtilTest {


    @Autowired
    AppUtil appUtil;


    @BeforeEach
    void setUp() {
    }

    @Test
    void fetchCustomersFromFile() {
        assertNotNull(appUtil.getCustomersFromFile());
        assertTrue(appUtil.getCustomersFromFile().size() > 0);
    }

    @Test
    void getCustomersFromWeb() {
        assertNotNull(appUtil.getCustomersFromWeb());
    }

    @Test
    void getDistance() {
        assertNotNull(appUtil.getDistance(52.986375, -6.043701));
        assertTrue(appUtil.getDistance(53.339428, -6.257664) == 0);
    }

    @Test
    void getInvitees() {
        List<Customer> invitees = appUtil.getInvitees();

        assertNotNull(invitees);
        assertTrue(invitees.size() <= appUtil.getCustomers().size());
    }
}