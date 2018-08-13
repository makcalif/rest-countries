package com.world.restcountries;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import junit.framework.TestCase;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.logging.Logger;

@RunWith(SpringRunner.class)
@SpringBootTest (classes = RestCountriesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CountriesIT extends TestCase {

    private static final Logger LOGGER = Logger.getLogger(CountriesIT.class.getName());
    private static final MongodStarter starter = MongodStarter.getDefaultInstance();
    private MongodExecutable _mongodExe;
    private MongodProcess _mongod;

    private MongoClient _mongo;

    @LocalServerPort
    private int port;

    @Value("${mongo.port}")
    private int mongoPort;

    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    @Override
    protected void setUp() throws Exception {
        _mongodExe = starter.prepare(new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net("localhost", mongoPort, Network.localhostIsIPv6()))
                .build());
        _mongod = _mongodExe.start();

        super.setUp();

        _mongo = new MongoClient("localhost", mongoPort);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

        _mongod.stop();
        _mongodExe.stop();
    }

    public Mongo getMongo() {
        return _mongo;
    }

    //@Test
    public void first() throws IOException {
        MongodStarter starter = MongodStarter.getDefaultInstance();

        String bindIp = "localhost";
        int port = 12345;
        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(bindIp, port, Network.localhostIsIPv6()))
                .build();

//        MongodExecutable mongodExecutable = null;
//        try {
//            mongodExecutable = starter.prepare(mongodConfig);
//            MongodProcess mongod = mongodExecutable.start();
//
//            MongoClient mongo = new MongoClient(bindIp, port);
//            DB db = mongo.getDB("test");
//            DBCollection col = db.createCollection("testCol", new BasicDBObject());
//            col.save(new BasicDBObject("testDoc", new Date()));
//
//        } finally {
//            if (mongodExecutable != null)
//                mongodExecutable.stop();
//        }
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    public void second() throws JSONException{
        LOGGER.info("starting test");
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/Pakistan"),
                HttpMethod.GET, entity, String.class);

        String expected = "[\n" +
                "    {\n" +
                "        \"id\": \"5b6525ad2fe20ec68731f759\",\n" +
                "        \"region\": \"Asia\",\n" +
                "        \"area\": 881912,\n" +
                "        \"independent\": true,\n" +
                "        \"name\": {\n" +
                "            \"common\": \"Pakistan\",\n" +
                "            \"official\": \"Islamic Republic of Pakistan\"\n" +
                "        }\n" +
                "    }\n" +
                "]";

        JSONAssert.assertEquals(expected, response.getBody(), false);

    }
}
