package com.example;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class AccountingTest {
    @Test
    public void testStatement() {
        String customer = "BigCo";
        List<Performance> performances = new ArrayList<>();
        performances.add(new Performance("hamlet", 55));
        performances.add(new Performance("as-like", 35));
        performances.add(new Performance("othello", 40));
        Invoice invoice = new Invoice(customer, performances);

        Map<String, Play> plays = new HashMap<>();
        plays.put("hamlet", new Play("Hamlet", "tragedy"));
        plays.put("as-like", new Play("As You Like It", "comedy"));
        plays.put("othello", new Play("Othello", "tragedy"));
        
        Accounting accounting = new Accounting();
        String result = accounting.statement(invoice, plays);

        String expected = "Statement for BigCo\n" +
        "  Hamlet: $650.00 (55 seats)\n" +
        "  As You Like It: $580.00 (35 seats)\n" +
        "  Othello: $500.00 (40 seats)\n" +
        "Amount owed is $1,730.00\n" +
        "You earned 47 credits\n";
        assertEquals(expected, result);
    }
}
