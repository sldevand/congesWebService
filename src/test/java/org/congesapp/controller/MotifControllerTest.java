package org.congesapp.controller;

import org.congesapp.CongesApp;
import org.congesapp.model.Motif;
import org.congesapp.repository.MotifRepository;
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
public class MotifControllerTest {

    private final static String BASE_URL = "http://localhost:9001/reasons/";
    private static String testNom;
    private static HttpHeaders headers;
    private static Motif m0;
    private static Motif m1;
    private static Motif m2;

    private RestTemplate restTemplate = new RestTemplate();

    private static boolean testedOnce = false;

    @Autowired
    private MotifRepository motifRepository;


    @Before
    public void before() {
        headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        if (!testedOnce) {

            motifRepository.deleteAll();
            m0 = new Motif("Maladie");
            m1 = new Motif("Maternité");
            m2 = new Motif("Sans solde");

            testedOnce = true;
        }
    }

    @Test
    public void TestAPostMotif() {
        Motif maResponse = postMotif(m0);
        testNom = maResponse.getNom();
        Assert.isTrue(m0.equals(maResponse), "Motifd Motif not equals to the persisted one");
    }

    @Test
    public void TestBPostMotifs() {


        Motif poste1 = postMotif(m1);
        Assert.isTrue(m1.equals(poste1), "Motifd Motif not equals to the persisted one");

        Motif poste2 = postMotif(m2);
        Assert.isTrue(m2.equals(poste2), "Motifd Motif not equals to the persisted one");
    }

    private Motif postMotif(Motif poste) {
        HttpEntity<Motif> requestBody = new HttpEntity<>(poste, headers);
        ResponseEntity<Motif> response = restTemplate.postForEntity(BASE_URL, requestBody, Motif.class, headers);
        Assert.isTrue(response.getStatusCodeValue() == 200, "OK");
        return response.getBody();
    }

    @Test
    public void TestCGetMotifByNom() {

        ResponseEntity<Motif> myResponse = restTemplate.getForEntity(BASE_URL + testNom, Motif.class, headers);
        Motif poste = myResponse.getBody();
        Assert.isTrue(null != poste, "Fetched poste is null");
        Assert.isTrue(poste.equals(m0), poste.toString() + " != " + m0.toString());
    }

    @Test
    public void TestDGetAllMotifs() {
        List myResponse = restTemplate.getForObject(BASE_URL, List.class, headers);
        Assert.isTrue(myResponse.size() == 3, "List size != 3");
    }

    @Test
    public void TestEPutMotif() {

        Optional<Motif> sOpt = motifRepository.findByNom(testNom);
        Assert.isTrue(sOpt.isPresent(), "Motif " + testNom + " not found");

        Motif poste = sOpt.get();
        String newIntitule = "Chaudronnier";
        poste.setNom(newIntitule);


        HttpEntity<Motif> requestBody = new HttpEntity<>(poste, headers);

        ResponseEntity<Motif> response = restTemplate.exchange(BASE_URL, PUT, requestBody, Motif.class, headers);

        Assert.isTrue(response.getStatusCodeValue() == 200, "OK");
        Motif maResponse = response.getBody();

        testNom = maResponse.getNom();

        Assert.isTrue(maResponse.getNom().equals(newIntitule), "Persisted intitule != newIntitule");

        m0 = maResponse;
    }


    @Test
    public void TestFGetIfMotifHasBeenUpdated() {

        ResponseEntity<Motif> myResponse = restTemplate.getForEntity(BASE_URL + testNom, Motif.class, headers);
        Motif poste = myResponse.getBody();
        Assert.isTrue(null != poste, "Fetched poste is null");
        Assert.isTrue(poste.equals(m0), poste.toString() + " != " + m0.toString());
    }

    @Test
    public void TestGDeleteMotifByNom() {

        restTemplate.delete(BASE_URL + testNom, headers);

        ResponseEntity<Motif> myResponse = restTemplate.getForEntity(BASE_URL + testNom, Motif.class, headers);
        Motif poste = myResponse.getBody();
        Assert.isTrue(null == poste, "Fetched poste is not null");


    }


}
