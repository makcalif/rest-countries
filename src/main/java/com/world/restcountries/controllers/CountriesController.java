package com.world.restcountries.controllers;

import com.world.restcountries.model.Country;
import com.world.restcountries.repository.CountriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountriesController {

    @Autowired
    CountriesRepository countriesRepository;

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    MongoTemplate mongoTemplate;

    @GetMapping (value = "/{name}")
    public List<Country> getCountryByName(@PathVariable String name) {
        return countriesRepository.findByNameCommon(name);
    }

    @GetMapping(value = "/dependentByContinent/{continent}")
    public List<Country> dependentByContinent (@PathVariable String continent) {
        return countriesRepository.findByRegionAndIndependent(continent, false);
    }

    @GetMapping (value = "/getBiggestByArea/{size}/{page}")
    public Page<Country> getBiggestByArea (@PathVariable Integer size, @PathVariable Integer page) {

//        Aggregation aggregation = Aggregation.newAggregation(Aggregation.group("region").max("area") .as("mktotal"));
//        AggregationResults<Object> count = mongoTemplate.aggregate(aggregation, Country.class, Object.class);
//        return count.getMappedResults().toString();
        //PageRequest pageRequest = PageRequest.of(page, size, new Sort(Sort.Direction.DESC,  "area"));  //new PageRequest(0, 1, new Sort(Sort.Direction.DESC, "area"));
        Pageable pageRequest =  PageRequest.of(page, size, new Sort(Sort.Direction.DESC,  "area"));

        Page<Country> countries = countriesRepository.findAll(pageRequest);
        //Country country = countriesRepository.find();
        return countries;
    }


    @GetMapping (value = "/nativeQuery")
    public String getNativeQuery () {

        Aggregation aggregation = Aggregation.newAggregation(Aggregation.group("region").sum("area") .as("mktotal"));
        AggregationResults<Object> count = mongoTemplate.aggregate(aggregation, Country.class, Object.class);
        return count.getMappedResults().toString();
    }

    @GetMapping(value = "/nameLike/{name}")
    public List<Country> nameLike(@PathVariable String name) {
        List<Country> countries = countriesRepository.findByNameCommonLike(name);
        return countries;
    }

    @GetMapping (value = "/getByNameUsingQuery")
    public Country getByNameUsingQuery () {
        Query query = new BasicQuery("{'name.common' : 'Pakistan'}");
        Country country = mongoOperations.findOne(query, Country.class);
        return country;
    }

    @GetMapping (value="/count")
    public String getCount() {
        return new Long(countriesRepository.count()).toString();
    }


}