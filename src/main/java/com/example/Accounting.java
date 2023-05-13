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
    private Map<String, Play> mPlays;
    public String statement(Invoice invoice, Map<String, Play> plays) {
        int totalAmount = 0;
        int volumeCredits = 0;
        String result = "Statement for " + invoice.getCustomer() + "\n";
        mPlays = plays;

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);

        for (Performance perf : invoice.getPerformances()) {
            int thisAmount = 0;

            thisAmount = amountFor(perf, playFor(perf));
            
            // ボリューム特典のポイントを加算
            volumeCredits += Math.max(perf.getAudience() - 30, 0);
            // 喜劇のときは10人につき、さらにポイントを加算
            if ("comedy" == playFor(perf).getType()) {
                volumeCredits += (perf.getAudience() / 5);
            }
            // 注文の内訳を出力
            result += "  " + playFor(perf).getName() + ": " + numberFormat.format(thisAmount/100) + " (" + perf.getAudience() + " seats)\n";
            totalAmount += thisAmount;
        }

        result += "Amount owed is " + numberFormat.format(totalAmount/100) + "\n";
        result += "You earned " + volumeCredits + " credits\n";

        return result;
    }

    private Play playFor(Performance perf) {
        return mPlays.get(perf.getPlayID());
    }

    private int amountFor(Performance aPerformance, Play play) {
        int result = 0;
        switch(play.getType()) {
        case "tragedy":
            result = 40000;
            if (aPerformance.getAudience() > 30) {
                result += 1000 * (aPerformance.getAudience() - 30);
            }
            break;
        case "comedy":
            result = 30000;
            if (aPerformance.getAudience() > 20) {
                result += 10000 + 500 * (aPerformance.getAudience() - 20);
            }
            result += 300 * aPerformance.getAudience();
            break;
        default:
            throw new IllegalArgumentException();
        }
        return result;
    }
}
