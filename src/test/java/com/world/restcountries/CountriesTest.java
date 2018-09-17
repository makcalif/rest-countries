package com.world.restcountries;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static org.junit.Assert.assertThat;

@DataMongoTest
//@ExtendWith
public class CountriesTest {

    @Test
    public void test1 (@Autowired MongoTemplate mongoTemplate) {
        // given
        DBObject objectToSave = BasicDBObjectBuilder.start()
                .add("key", "value")
                .get();

        // when
        mongoTemplate.save(objectToSave, "collection");

        // then

        List list = mongoTemplate.findAll(DBObject.class, "collection");

//        assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("key")
//                .containsOnly("value");
    }
}
