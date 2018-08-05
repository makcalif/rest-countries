package com.world.restcountries.repository;

import com.sun.xml.internal.bind.v2.runtime.output.C14nXmlOutput;
import com.world.restcountries.model.Country;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountriesRepository extends MongoRepository<Country, String>{

    List<Country> findByNameCommon(String name);
    List<Country> findByRegionAndIndependent(String continent, Boolean independent);
    List<Country> findByNameCommonLike(String name);
    //Country findTop10ByOrderByAreaDesc();
    List<Country>  findFirst10ByOrderByAreaDesc();

    List<Country> findAllBy(PageRequest pageRequest);
    //List<Country> find();
    //Country getBiggestByArea(


    @Override
    Page<Country> findAll(Pageable pageable);

    @Override
    List<Country> findAll();

    @Override
    long count();


}
