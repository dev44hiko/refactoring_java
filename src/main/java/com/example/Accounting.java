package com.example;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

class Play {
    private String mName;
    private String mType;
    public Play(String name, String type) {
        mName = name;
        mType = type;
    }

    public String getName() {
        return mName;
    }

    public String getType() {
        return mType;
    }
}

class Performance {
    private String mPlayID;
    private int mAudience;
    public Performance(String id, int audience) {
        mPlayID = id;
        mAudience = audience;
    }
    
    public String getPlayID() {
        return mPlayID;
    }

    public int getAudience() {
        return mAudience;
    }

}

class Invoice {
    private String mCustomer;
    private List<Performance> mPerformances;

    public Invoice(String customer, List<Performance> performances) {
        mCustomer = customer;
        mPerformances = performances;
    }

    public String getCustomer() {
        return mCustomer;
    }

    public List<Performance> getPerformances() {
        return mPerformances;
    }
}

public class Accounting {
    public String statement(Invoice invoice, Map<String, Play> plays) {
        int totalAmount = 0;
        int volumeCredits = 0;
        String result = "Statement for " + invoice.getCustomer() + "\n";

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);

        for (Performance perf : invoice.getPerformances()) {
            Play play = plays.get(perf.getPlayID());
            int thisAmount = 0;

            thisAmount = amountFor(perf, play);
            
            // ボリューム特典のポイントを加算
            volumeCredits += Math.max(perf.getAudience() - 30, 0);
            // 喜劇のときは10人につき、さらにポイントを加算
            if ("comedy" == play.getType()) {
                volumeCredits += (perf.getAudience() / 5);
            }
            // 注文の内訳を出力
            result += "  " + play.getName() + ": " + numberFormat.format(thisAmount/100) + " (" + perf.getAudience() + " seats)\n";
            totalAmount += thisAmount;
        }

        result += "Amount owed is " + numberFormat.format(totalAmount/100) + "\n";
        result += "You earned " + volumeCredits + " credits\n";

        return result;
    }

    private int amountFor(Performance perf, Play play) {
        int thisAmount = 0;
        switch(play.getType()) {
        case "tragedy":
            thisAmount = 40000;
            if (perf.getAudience() > 30) {
                thisAmount += 1000 * (perf.getAudience() - 30);
            }
            break;
        case "comedy":
            thisAmount = 30000;
            if (perf.getAudience() > 20) {
                thisAmount += 10000 + 500 * (perf.getAudience() - 20);
            }
            thisAmount += 300 * perf.getAudience();
            break;
        default:
            throw new IllegalArgumentException();
        }
        return thisAmount;
    }
}
