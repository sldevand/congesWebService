package org.congesapp.controller;

import org.congesapp.CongesApp;
import org.congesapp.repository.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = CongesApp.class)
@ActiveProfiles({"TEST"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class AbstractControllerTest {

    protected final static String BASE_URL = "http://localhost:9001/";

    @Autowired
    protected SalarieRepository salarieRepository;

    @Autowired
    protected ServiceRepository serviceRepository;

    @Autowired
    protected PosteRepository posteRepository;

    @Autowired
    protected MotifRepository motifRepository;

    @Autowired
    protected CongeRepository congeRepository;

    protected static HttpHeaders headers;
    protected RestTemplate restTemplate = new RestTemplate();

    protected static boolean testedOnce = false;

    @BeforeClass
    public static void beforeClass(){
        testedOnce = false;
    }

    @Before
    public void before() {
        headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        if (!testedOnce) {
            deleteAllRepositories();
            initialize();
            testedOnce = true;
        }
    }

    @AfterClass
    public static void tearDown() {
        testedOnce=false;
    }

    protected abstract void initialize();

    protected void deleteAllRepositories(){

        salarieRepository.deleteAll();
        posteRepository.deleteAll();
        serviceRepository.deleteAll();
        congeRepository.deleteAll();
        motifRepository.deleteAll();
    }
}
