package com.example.treasure.model;

public class TimeZoneResponse {
    private String status;
    private String message;
    private String countryCode;
    private String countryName;
    private String regionName;
    private String cityName;
    private String zoneName;
    private String abbreviation;
    private int gmtOffset;
    private String dst;
    private long zoneStart;
    private long zoneEnd;
    private String nextAbbreviation;
    private long timestamp;
    private String formatted;

    // Getters e Setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }

    public String getCountryName() { return countryName; }
    public void setCountryName(String countryName) { this.countryName = countryName; }

    public String getRegionName() { return regionName; }
    public void setRegionName(String regionName) { this.regionName = regionName; }

    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }

    public String getZoneName() { return zoneName; }
    public void setZoneName(String zoneName) { this.zoneName = zoneName; }

    public String getAbbreviation() { return abbreviation; }
    public void setAbbreviation(String abbreviation) { this.abbreviation = abbreviation; }

    public int getGmtOffset() { return gmtOffset; }
    public void setGmtOffset(int gmtOffset) { this.gmtOffset = gmtOffset; }

    public String getDst() { return dst; }
    public void setDst(String dst) { this.dst = dst; }

    public long getZoneStart() { return zoneStart; }
    public void setZoneStart(long zoneStart) { this.zoneStart = zoneStart; }

    public long getZoneEnd() { return zoneEnd; }
    public void setZoneEnd(long zoneEnd) { this.zoneEnd = zoneEnd; }

    public String getNextAbbreviation() { return nextAbbreviation; }
    public void setNextAbbreviation(String nextAbbreviation) { this.nextAbbreviation = nextAbbreviation; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public String getFormatted() { return formatted; }
    public void setFormatted(String formatted) { this.formatted = formatted; }
}