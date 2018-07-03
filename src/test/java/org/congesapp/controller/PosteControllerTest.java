package org.congesapp.controller;

import org.congesapp.CongesApp;
import org.congesapp.model.*;
import org.congesapp.repository.PosteRepository;
import org.congesapp.repository.SalarieRepository;
import org.congesapp.repository.ServiceRepository;
import org.congesapp.tools.Tools;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpMethod.PUT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = CongesApp.class)
@ActiveProfiles({"TEST"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PosteControllerTest {

    private final static String BASE_URL = "http://localhost:9001/jobs/";
    private static String testIntitule;
    private static HttpHeaders headers;
    private static Poste p0;
    private static Poste p1;
    private static Poste p2;

    private RestTemplate restTemplate = new RestTemplate();

    private static boolean testedOnce = false;

    @Autowired
    private PosteRepository posteRepository;


    @Before
    public void before() {
        headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        if (!testedOnce) {

            posteRepository.deleteAll();
            p0 = new Poste("Developpeur");
            p1 = new Poste("Scrum Master");
            p2 = new Poste("Product Owner");

            testedOnce = true;
        }
    }

    @Test
    public void TestAPostPoste() {
        Poste maResponse = postPoste(p0);
        testIntitule = maResponse.getIntitule();
        Assert.isTrue(p0.equals(maResponse), "Posted Poste not equals to the persisted one");
    }

    @Test
    public void TestBPostPostes() {


        Poste poste1 = postPoste(p1);
        Assert.isTrue(p1.equals(poste1), "Posted Poste not equals to the persisted one");

        Poste poste2 = postPoste(p2);
        Assert.isTrue(p2.equals(poste2), "Posted Poste not equals to the persisted one");
    }

    private Poste postPoste(Poste poste) {
        HttpEntity<Poste> requestBody = new HttpEntity<>(poste, headers);
        ResponseEntity<Poste> response = restTemplate.postForEntity(BASE_URL, requestBody, Poste.class, headers);
        Assert.isTrue(response.getStatusCodeValue() == 200, "OK");
        return response.getBody();
    }

    @Test
    public void TestCGetPosteByIntitule() {

        ResponseEntity<Poste> myResponse = restTemplate.getForEntity(BASE_URL + testIntitule, Poste.class, headers);
        Poste poste = myResponse.getBody();
        Assert.isTrue(null != poste, "Fetched poste is null");
        Assert.isTrue(poste.equals(p0), poste.toString() + " != " + p0.toString());
    }

    @Test
    public void TestDGetAllPostes() {
        List myResponse = restTemplate.getForObject(BASE_URL, List.class, headers);
        Assert.isTrue(myResponse.size() == 3, "List size != 3");
    }

    @Test
    public void TestEPutPoste() {

        Optional<Poste> sOpt = posteRepository.findByIntitule(testIntitule);
        Assert.isTrue(sOpt.isPresent(), "Poste " + testIntitule + " not found");

        Poste poste = sOpt.get();
        String newIntitule = "Chaudronnier";
        poste.setIntitule(newIntitule);


        HttpEntity<Poste> requestBody = new HttpEntity<>(poste, headers);

        ResponseEntity<Poste> response = restTemplate.exchange(BASE_URL, PUT, requestBody, Poste.class, headers);

        Assert.isTrue(response.getStatusCodeValue() == 200, "OK");
        Poste maResponse = response.getBody();

        testIntitule = maResponse.getIntitule();

        Assert.isTrue(maResponse.getIntitule().equals(newIntitule), "Persisted intitule != newIntitule");

        p0 = maResponse;
    }


    @Test
    public void TestFGetIfPosteHasBeenUpdated() {

        ResponseEntity<Poste> myResponse = restTemplate.getForEntity(BASE_URL + testIntitule, Poste.class, headers);
        Poste poste = myResponse.getBody();
        Assert.isTrue(null != poste, "Fetched poste is null");
        Assert.isTrue(poste.equals(p0), poste.toString() + " != " + p0.toString());
    }

    @Test
    public void TestGDeletePosteByIntitule() {

        restTemplate.delete(BASE_URL + testIntitule, headers);

        ResponseEntity<Poste> myResponse = restTemplate.getForEntity(BASE_URL + testIntitule, Poste.class, headers);
        Poste poste = myResponse.getBody();
        Assert.isTrue(null == poste, "Fetched poste is not null");


    }


}
