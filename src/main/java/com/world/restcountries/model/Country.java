package com.world.restcountries.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "countries")
public class Country {
    @Id
    public String id;

    private String region;
    private Integer area;
    private Boolean independent;
    private Name name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Boolean getIndependent() {
        return independent;
    }

    public void setIndependent(Boolean independent) {
        this.independent = independent;
    }
}
