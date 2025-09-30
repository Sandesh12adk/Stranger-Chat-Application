package com.example.userservice.dto;
public class IpRange {
    private long startIp;
    private long endIp;
    private String country;

    public IpRange(String startIp, String endIp, String country) {
        this.startIp = ipToLong(startIp);
        this.endIp = ipToLong(endIp);
        this.country = country;
    }

    public boolean contains(long ip) {
        return ip >= startIp && ip <= endIp;
    }

    public String getCountry() {
        return country;
    }

    private long ipToLong(String ipAddress) {
        String[] parts = ipAddress.split("\\.");
        long result = 0;
        for (int i = 0; i < 4; i++) {
            result = (result << 8) | (Integer.parseInt(parts[i]) & 0xFF);
        }
        return result;
    }
}

