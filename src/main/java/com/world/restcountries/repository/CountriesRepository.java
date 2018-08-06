package com.world.restcountries.repository;

import com.sun.xml.internal.bind.v2.runtime.output.C14nXmlOutput;
import com.world.restcountries.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountriesRepository extends MongoRepository<Country, String>{

    List<Country> findByNameCommon(String name);
    List<Country> findByRegionAndIndependent(String continent, Boolean independent);
    List<Country> findByNameCommonLike(String name);
    //List<Country>  findByGroupByRegionOrderByAreaDesc();

    List<Country> findAllBy(PageRequest pageRequest);

    @Override
    Page<Country> findAll(Pageable pageable);

    @Override
    List<Country> findAll();

    // for native query use this
//    List<Country> findNative () {
//        mongoTemplate.find(new BasicQuery("{salary: {$gt: 50, $lt: 100}, name: {$regex: '^A', $options: ''}}", TestDocument.class);
//    }

    @Override
    long count();


}
