package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main App
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "main Start" );
        
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
        System.out.println(result);
    }
}
