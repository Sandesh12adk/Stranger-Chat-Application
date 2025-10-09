package com.example.userservice.service;
import com.example.userservice.dto.IpRange;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class GeoService {

    private List<IpRange> ipRanges = new ArrayList<>();

    @PostConstruct
    public void init() {
        // ===== South Asia =====
        ipRanges.add(new IpRange("103.1.92.0", "103.1.95.255", "Nepal"));
        ipRanges.add(new IpRange("49.32.0.0", "49.63.255.255", "India"));
        ipRanges.add(new IpRange("39.32.0.0", "39.63.255.255", "Pakistan"));
        ipRanges.add(new IpRange("103.0.0.0", "103.255.255.255", "Bangladesh"));
        ipRanges.add(new IpRange("103.220.0.0", "103.223.255.255", "Sri Lanka"));
        ipRanges.add(new IpRange("103.0.36.0", "103.0.39.255", "Bhutan"));
        ipRanges.add(new IpRange("103.80.0.0", "103.95.255.255", "Maldives"));
        ipRanges.add(new IpRange("103.224.0.0", "103.255.255.255", "Afghanistan"));

        // ===== East Asia =====
        ipRanges.add(new IpRange("27.0.0.0", "27.255.255.255", "Japan"));
        ipRanges.add(new IpRange("58.14.0.0", "58.31.255.255", "China"));
        ipRanges.add(new IpRange("1.0.1.0", "1.0.3.255", "Taiwan"));
        ipRanges.add(new IpRange("27.96.0.0", "27.127.255.255", "South Korea"));
        ipRanges.add(new IpRange("36.0.0.0", "36.255.255.255", "Hong Kong"));
        ipRanges.add(new IpRange("58.30.0.0", "58.63.255.255", "Mongolia"));
        ipRanges.add(new IpRange("27.128.0.0", "27.159.255.255", "North Korea"));

        // ===== Southeast Asia =====
        ipRanges.add(new IpRange("103.0.0.0", "103.255.255.255", "Thailand"));
        ipRanges.add(new IpRange("103.6.0.0", "103.7.255.255", "Vietnam"));
        ipRanges.add(new IpRange("103.8.0.0", "103.11.255.255", "Malaysia"));
        ipRanges.add(new IpRange("103.12.0.0", "103.15.255.255", "Singapore"));
        ipRanges.add(new IpRange("103.16.0.0", "103.19.255.255", "Indonesia"));
        ipRanges.add(new IpRange("103.20.0.0", "103.23.255.255", "Philippines"));
        ipRanges.add(new IpRange("103.24.0.0", "103.27.255.255", "Myanmar"));
        ipRanges.add(new IpRange("103.28.0.0", "103.31.255.255", "Cambodia"));
        ipRanges.add(new IpRange("103.32.0.0", "103.35.255.255", "Laos"));
        ipRanges.add(new IpRange("103.36.0.0", "103.39.255.255", "Brunei"));
        ipRanges.add(new IpRange("103.40.0.0", "103.43.255.255", "East Timor"));
        ipRanges.add(new IpRange("103.44.0.0", "103.47.255.255", "Papua New Guinea"));

        // ===== Middle East =====
        ipRanges.add(new IpRange("5.0.0.0", "5.255.255.255", "Turkey"));
        ipRanges.add(new IpRange("2.16.0.0", "2.31.255.255", "Israel"));
        ipRanges.add(new IpRange("2.32.0.0", "2.47.255.255", "Saudi Arabia"));
        ipRanges.add(new IpRange("5.44.0.0", "5.47.255.255", "UAE"));
        ipRanges.add(new IpRange("5.48.0.0", "5.63.255.255", "Qatar"));
        ipRanges.add(new IpRange("5.64.0.0", "5.79.255.255", "Kuwait"));
        ipRanges.add(new IpRange("5.80.0.0", "5.95.255.255", "Oman"));
        ipRanges.add(new IpRange("5.96.0.0", "5.111.255.255", "Bahrain"));
        ipRanges.add(new IpRange("5.112.0.0", "5.127.255.255", "Jordan"));
        ipRanges.add(new IpRange("5.128.0.0", "5.143.255.255", "Lebanon"));
        ipRanges.add(new IpRange("5.144.0.0", "5.159.255.255", "Iran"));
        ipRanges.add(new IpRange("5.160.0.0", "5.175.255.255", "Iraq"));
        ipRanges.add(new IpRange("5.176.0.0", "5.191.255.255", "Syria"));
        ipRanges.add(new IpRange("5.192.0.0", "5.207.255.255", "Yemen"));
        ipRanges.add(new IpRange("5.208.0.0", "5.223.255.255", "Palestine"));

        // ===== Europe =====
        ipRanges.add(new IpRange("77.0.0.0", "77.255.255.255", "Germany"));
        ipRanges.add(new IpRange("90.0.0.0", "90.255.255.255", "France"));
        ipRanges.add(new IpRange("2.16.0.0", "2.31.255.255", "Italy"));
        ipRanges.add(new IpRange("2.32.0.0", "2.47.255.255", "Spain"));
        ipRanges.add(new IpRange("2.48.0.0", "2.63.255.255", "Switzerland"));
        ipRanges.add(new IpRange("2.64.0.0", "2.79.255.255", "Austria"));
        ipRanges.add(new IpRange("2.80.0.0", "2.95.255.255", "Sweden"));
        ipRanges.add(new IpRange("2.96.0.0", "2.111.255.255", "Norway"));
        ipRanges.add(new IpRange("2.112.0.0", "2.127.255.255", "Denmark"));
        ipRanges.add(new IpRange("2.128.0.0", "2.143.255.255", "Finland"));
        ipRanges.add(new IpRange("2.144.0.0", "2.159.255.255", "Poland"));
        ipRanges.add(new IpRange("2.160.0.0", "2.175.255.255", "Czech Republic"));
        ipRanges.add(new IpRange("2.176.0.0", "2.191.255.255", "Slovakia"));
        ipRanges.add(new IpRange("2.192.0.0", "2.207.255.255", "Hungary"));
        ipRanges.add(new IpRange("2.208.0.0", "2.223.255.255", "Ireland"));
        ipRanges.add(new IpRange("2.224.0.0", "2.239.255.255", "Portugal"));
        ipRanges.add(new IpRange("2.240.0.0", "2.255.255.255", "Greece"));
        ipRanges.add(new IpRange("5.0.0.0", "5.15.255.255", "United Kingdom"));
        ipRanges.add(new IpRange("31.0.0.0", "31.31.255.255", "Netherlands"));
        ipRanges.add(new IpRange("31.32.0.0", "31.47.255.255", "Belgium"));
        ipRanges.add(new IpRange("31.48.0.0", "31.63.255.255", "Luxembourg"));
        ipRanges.add(new IpRange("31.64.0.0", "31.79.255.255", "Romania"));
        ipRanges.add(new IpRange("31.80.0.0", "31.95.255.255", "Bulgaria"));
        ipRanges.add(new IpRange("31.96.0.0", "31.111.255.255", "Serbia"));
        ipRanges.add(new IpRange("31.112.0.0", "31.127.255.255", "Croatia"));
        ipRanges.add(new IpRange("31.128.0.0", "31.143.255.255", "Slovenia"));
        ipRanges.add(new IpRange("31.144.0.0", "31.159.255.255", "Bosnia and Herzegovina"));
        ipRanges.add(new IpRange("31.160.0.0", "31.175.255.255", "North Macedonia"));
        ipRanges.add(new IpRange("31.176.0.0", "31.191.255.255", "Albania"));
        ipRanges.add(new IpRange("31.192.0.0", "31.207.255.255", "Montenegro"));
        ipRanges.add(new IpRange("31.208.0.0", "31.223.255.255", "Kosovo"));
        ipRanges.add(new IpRange("31.224.0.0", "31.239.255.255", "Estonia"));
        ipRanges.add(new IpRange("31.240.0.0", "31.255.255.255", "Latvia"));
        ipRanges.add(new IpRange("37.0.0.0", "37.15.255.255", "Lithuania"));
        ipRanges.add(new IpRange("37.16.0.0", "37.31.255.255", "Ukraine"));
        ipRanges.add(new IpRange("37.32.0.0", "37.47.255.255", "Belarus"));
        ipRanges.add(new IpRange("37.48.0.0", "37.63.255.255", "Moldova"));
        ipRanges.add(new IpRange("37.64.0.0", "37.79.255.255", "Russia"));
        ipRanges.add(new IpRange("37.80.0.0", "37.95.255.255", "Georgia"));
        ipRanges.add(new IpRange("37.96.0.0", "37.111.255.255", "Armenia"));
        ipRanges.add(new IpRange("37.112.0.0", "37.127.255.255", "Azerbaijan"));
        ipRanges.add(new IpRange("37.128.0.0", "37.143.255.255", "Kazakhstan"));
        ipRanges.add(new IpRange("37.144.0.0", "37.159.255.255", "Uzbekistan"));
        ipRanges.add(new IpRange("37.160.0.0", "37.175.255.255", "Turkmenistan"));
        ipRanges.add(new IpRange("37.176.0.0", "37.191.255.255", "Kyrgyzstan"));
        ipRanges.add(new IpRange("37.192.0.0", "37.207.255.255", "Tajikistan"));
        ipRanges.add(new IpRange("85.0.0.0", "85.15.255.255", "Iceland"));
        ipRanges.add(new IpRange("85.16.0.0", "85.31.255.255", "Malta"));
        ipRanges.add(new IpRange("85.32.0.0", "85.47.255.255", "Cyprus"));

        // ===== Americas =====
        ipRanges.add(new IpRange("3.0.0.0", "3.255.255.255", "United States"));
        ipRanges.add(new IpRange("186.0.0.0", "186.255.255.255", "Brazil"));
        ipRanges.add(new IpRange("190.0.0.0", "190.255.255.255", "Argentina"));
        ipRanges.add(new IpRange("200.0.0.0", "200.255.255.255", "Chile"));
        ipRanges.add(new IpRange("201.0.0.0", "201.255.255.255", "Colombia"));
        ipRanges.add(new IpRange("190.128.0.0", "190.255.255.255", "Peru"));
        ipRanges.add(new IpRange("152.0.0.0", "152.255.255.255", "Canada"));
        ipRanges.add(new IpRange("45.0.0.0", "45.255.255.255", "Mexico"));
        ipRanges.add(new IpRange("170.0.0.0", "170.255.255.255", "Venezuela"));
        ipRanges.add(new IpRange("186.64.0.0", "186.127.255.255", "Uruguay"));
        ipRanges.add(new IpRange("186.128.0.0", "186.191.255.255", "Paraguay"));
        ipRanges.add(new IpRange("187.0.0.0", "187.255.255.255", "Bolivia"));
        ipRanges.add(new IpRange("189.0.0.0", "189.255.255.255", "Ecuador"));
        ipRanges.add(new IpRange("201.64.0.0", "201.127.255.255", "Guatemala"));
        ipRanges.add(new IpRange("201.128.0.0", "201.191.255.255", "Honduras"));
        ipRanges.add(new IpRange("201.192.0.0", "201.255.255.255", "El Salvador"));
        ipRanges.add(new IpRange("179.0.0.0", "179.63.255.255", "Nicaragua"));
        ipRanges.add(new IpRange("179.64.0.0", "179.127.255.255", "Costa Rica"));
        ipRanges.add(new IpRange("179.128.0.0", "179.191.255.255", "Panama"));
        ipRanges.add(new IpRange("179.192.0.0", "179.255.255.255", "Dominican Republic"));
        ipRanges.add(new IpRange("66.0.0.0", "66.63.255.255", "Cuba"));
        ipRanges.add(new IpRange("66.64.0.0", "66.127.255.255", "Haiti"));
        ipRanges.add(new IpRange("66.128.0.0", "66.191.255.255", "Jamaica"));
        ipRanges.add(new IpRange("66.192.0.0", "66.255.255.255", "Trinidad and Tobago"));
        ipRanges.add(new IpRange("67.0.0.0", "67.63.255.255", "Bahamas"));
        ipRanges.add(new IpRange("67.64.0.0", "67.127.255.255", "Barbados"));
        ipRanges.add(new IpRange("24.0.0.0", "24.63.255.255", "Puerto Rico"));

        // ===== Africa =====
        ipRanges.add(new IpRange("41.0.0.0", "41.63.255.255", "Egypt"));
        ipRanges.add(new IpRange("41.64.0.0", "41.127.255.255", "South Africa"));
        ipRanges.add(new IpRange("41.128.0.0", "41.191.255.255", "Nigeria"));
        ipRanges.add(new IpRange("41.192.0.0", "41.255.255.255", "Kenya"));
        ipRanges.add(new IpRange("102.0.0.0", "102.63.255.255", "Morocco"));
        ipRanges.add(new IpRange("102.64.0.0", "102.127.255.255", "Algeria"));
        ipRanges.add(new IpRange("102.128.0.0", "102.191.255.255", "Tunisia"));
        ipRanges.add(new IpRange("102.192.0.0", "102.255.255.255", "Libya"));
        ipRanges.add(new IpRange("105.0.0.0", "105.63.255.255", "Ghana"));
        ipRanges.add(new IpRange("105.64.0.0", "105.127.255.255", "Ivory Coast"));
        ipRanges.add(new IpRange("105.128.0.0", "105.191.255.255", "Cameroon"));
        ipRanges.add(new IpRange("105.192.0.0", "105.255.255.255", "Ethiopia"));
        ipRanges.add(new IpRange("154.0.0.0", "154.63.255.255", "Tanzania"));
        ipRanges.add(new IpRange("154.64.0.0", "154.127.255.255", "Uganda"));
        ipRanges.add(new IpRange("154.128.0.0", "154.191.255.255", "Zambia"));
        ipRanges.add(new IpRange("154.192.0.0", "154.255.255.255", "Zimbabwe"));
        ipRanges.add(new IpRange("155.0.0.0", "155.63.255.255", "Senegal"));
        ipRanges.add(new IpRange("155.64.0.0", "155.127.255.255", "Angola"));
        ipRanges.add(new IpRange("155.128.0.0", "155.191.255.255", "Sudan"));
        ipRanges.add(new IpRange("155.192.0.0", "155.255.255.255", "DR Congo"));
        ipRanges.add(new IpRange("156.0.0.0", "156.63.255.255", "Madagascar"));
        ipRanges.add(new IpRange("156.64.0.0", "156.127.255.255", "Mozambique"));
        ipRanges.add(new IpRange("156.128.0.0", "156.191.255.255", "Namibia"));
        ipRanges.add(new IpRange("156.192.0.0", "156.255.255.255", "Botswana"));
        ipRanges.add(new IpRange("197.0.0.0", "197.63.255.255", "Rwanda"));
        ipRanges.add(new IpRange("197.64.0.0", "197.127.255.255", "Burundi"));
        ipRanges.add(new IpRange("197.128.0.0", "197.191.255.255", "Malawi"));
        ipRanges.add(new IpRange("197.192.0.0", "197.255.255.255", "Somalia"));
        ipRanges.add(new IpRange("196.0.0.0", "196.63.255.255", "Mauritius"));
        ipRanges.add(new IpRange("196.64.0.0", "196.127.255.255", "Seychelles"));

        // ===== Oceania =====
        ipRanges.add(new IpRange("103.50.0.0", "103.50.255.255", "New Zealand"));
        ipRanges.add(new IpRange("43.224.0.0", "43.255.255.255", "Australia"));
        ipRanges.add(new IpRange("103.51.0.0", "103.51.255.255", "Fiji"));
        ipRanges.add(new IpRange("103.52.0.0", "103.52.255.255", "Papua New Guinea"));
        ipRanges.add(new IpRange("103.53.0.0", "103.53.255.255", "Solomon Islands"));
        ipRanges.add(new IpRange("103.54.0.0", "103.54.255.255", "Vanuatu"));
        ipRanges.add(new IpRange("103.55.0.0", "103.55.255.255", "Samoa"));
        ipRanges.add(new IpRange("103.56.0.0", "103.56.255.255", "Tonga"));
        ipRanges.add(new IpRange("103.57.0.0", "103.57.255.255", "Kiribati"));
        ipRanges.add(new IpRange("103.58.0.0", "103.58.255.255", "Micronesia"));
        ipRanges.add(new IpRange("103.59.0.0", "103.59.255.255", "Palau"));
        ipRanges.add(new IpRange("103.60.0.0", "103.60.255.255", "Marshall Islands"));

        // ===== Caribbean =====
        ipRanges.add(new IpRange("24.64.0.0", "24.127.255.255", "Dominica"));
        ipRanges.add(new IpRange("24.128.0.0", "24.191.255.255", "Saint Lucia"));
        ipRanges.add(new IpRange("24.192.0.0", "24.255.255.255", "Saint Vincent and the Grenadines"));
        ipRanges.add(new IpRange("67.128.0.0", "67.191.255.255", "Grenada"));
        ipRanges.add(new IpRange("67.192.0.0", "67.255.255.255", "Antigua and Barbuda"));
        ipRanges.add(new IpRange("68.0.0.0", "68.63.255.255", "Saint Kitts and Nevis"));
    }

    public String getCountry(HttpServletRequest request) {
        String ipAddress = extractClientIp(request);
        return getCountry(ipAddress);
    }

    public String getCountry(String ipAddress) {
        long ipNum = ipToLong(ipAddress);
        if (ipNum == -1) return "Unknown"; // invalid IP

        for (IpRange range : ipRanges) {
            if (range.contains(ipNum)) {
                return range.getCountry();
            }
        }
        return "Unknown";
    }


    private long ipToLong(String ipAddress) {
        try {
            String[] parts = ipAddress.split("\\.");
            if (parts.length != 4) return -1; // invalid IPv4
            long result = 0;
            for (int i = 0; i < 4; i++) {
                result = (result << 8) | (Integer.parseInt(parts[i]) & 0xFF);
            }
            return result;
        } catch (NumberFormatException e) {
            return -1; // return invalid
        }
    }


    private String extractClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            ip = ip.split(",")[0].trim(); // first IP in chain
        } else {
            ip = request.getRemoteAddr();
        }

        // Handle localhost IPv6 and IPv4-mapped IPv6
        if ("0:0:0:0:0:0:0:1".equals(ip) || "::1".equals(ip)) {
            ip = "127.0.0.1";
        }
        if (ip.startsWith("::ffff:")) {
            ip = ip.substring(7); // Convert IPv4-mapped IPv6 to IPv4
        }
        return ip;
    }


}