package com.muibsols.iptracker;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class DataModel {
    @SerializedName("ip")
    @Expose
    private String ip;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("region_code")
    @Expose
    private String regionCode;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("country_name")
    @Expose
    private String countryName;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("country_code_iso3")
    @Expose
    private String countryCodeIso3;
    @SerializedName("country_capital")
    @Expose
    private String countryCapital;
    @SerializedName("country_tld")
    @Expose
    private String countryTld;
    @SerializedName("continent_code")
    @Expose
    private String continentCode;
    @SerializedName("in_eu")
    @Expose
    private Boolean inEu;
    @SerializedName("postal")
    @Expose
    private String postal;
    @SerializedName("latitude")
    @Expose
    private Float latitude;
    @SerializedName("longitude")
    @Expose
    private Float longitude;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("utc_offset")
    @Expose
    private String utcOffset;
    @SerializedName("country_calling_code")
    @Expose
    private String countryCallingCode;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("currency_name")
    @Expose
    private String currencyName;
    @SerializedName("languages")
    @Expose
    private String languages;
    @SerializedName("country_area")
    @Expose
    private Float countryArea;
    @SerializedName("country_population")
    @Expose
    private String countryPopulation;
    @SerializedName("asn")
    @Expose
    private String asn;
    @SerializedName("org")
    @Expose
    private String org;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCodeIso3() {
        return countryCodeIso3;
    }

    public void setCountryCodeIso3(String countryCodeIso3) {
        this.countryCodeIso3 = countryCodeIso3;
    }

    public String getCountryCapital() {
        return countryCapital;
    }

    public void setCountryCapital(String countryCapital) {
        this.countryCapital = countryCapital;
    }

    public String getCountryTld() {
        return countryTld;
    }

    public void setCountryTld(String countryTld) {
        this.countryTld = countryTld;
    }

    public String getContinentCode() {
        return continentCode;
    }

    public void setContinentCode(String continentCode) {
        this.continentCode = continentCode;
    }

    public Boolean getInEu() {
        return inEu;
    }

    public void setInEu(Boolean inEu) {
        this.inEu = inEu;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getUtcOffset() {
        return utcOffset;
    }

    public void setUtcOffset(String utcOffset) {
        this.utcOffset = utcOffset;
    }

    public String getCountryCallingCode() {
        return countryCallingCode;
    }

    public void setCountryCallingCode(String countryCallingCode) {
        this.countryCallingCode = countryCallingCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public Float getCountryArea() {
        return countryArea;
    }

    public void setCountryArea(Float countryArea) {
        this.countryArea = countryArea;
    }

    public String getCountryPopulation() {
        return countryPopulation;
    }

    public void setCountryPopulation(String countryPopulation) {
        this.countryPopulation = countryPopulation;
    }

    public String getAsn() {
        return asn;
    }

    public void setAsn(String asn) {
        this.asn = asn;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    @Override
    public String toString() {
        return "DataModel{" +
                "ip='" + ip + '\'' +
                ", version='" + version + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", regionCode='" + regionCode + '\'' +
                ", country='" + country + '\'' +
                ", countryName='" + countryName + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", countryCodeIso3='" + countryCodeIso3 + '\'' +
                ", countryCapital='" + countryCapital + '\'' +
                ", countryTld='" + countryTld + '\'' +
                ", continentCode='" + continentCode + '\'' +
                ", inEu=" + inEu +
                ", postal='" + postal + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", timezone='" + timezone + '\'' +
                ", utcOffset='" + utcOffset + '\'' +
                ", countryCallingCode='" + countryCallingCode + '\'' +
                ", currency='" + currency + '\'' +
                ", currencyName='" + currencyName + '\'' +
                ", languages='" + languages + '\'' +
                ", countryArea=" + countryArea +
                ", countryPopulation=" + countryPopulation +
                ", asn='" + asn + '\'' +
                ", org='" + org + '\'' +
                '}';
    }
}
