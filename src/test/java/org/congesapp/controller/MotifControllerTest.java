package org.congesapp.controller;

import org.congesapp.CongesApp;
import org.congesapp.model.Motif;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpMethod.PUT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = CongesApp.class)
@ActiveProfiles({"TEST"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MotifControllerTest extends AbstractControllerTest{

    private final static String END_URL = BASE_URL+"reasons/";
    private static String testNom;
    private static Motif m0;
    private static Motif m1;
    private static Motif m2;
  
    @Override
    protected void initialize() {
        m0 = new Motif("Maladie");
        m1 = new Motif("Maternité");
        m2 = new Motif("Sans solde");
    }

    @Test
    public void TestAPostMotif() {
        Motif maResponse = postMotif(m0);
        testNom = maResponse.getNom();
        Assert.isTrue(m0.equals(maResponse), "Motif not equals to the persisted one");
    }

    @Test
    public void TestBPostMotifs() {
        Motif poste1 = postMotif(m1);
        Assert.isTrue(m1.equals(poste1), "Motif not equals to the persisted one");

        Motif poste2 = postMotif(m2);
        Assert.isTrue(m2.equals(poste2), "Motif not equals to the persisted one");
    }

    private Motif postMotif(Motif motif) {
        HttpEntity<Motif> requestBody = new HttpEntity<>(motif, headers);
        ResponseEntity<Motif> response = restTemplate.postForEntity(END_URL, requestBody, Motif.class, headers);
        Assert.isTrue(response.getStatusCodeValue() == 200, "OK");
        return response.getBody();
    }

    @Test
    public void TestCGetMotifByNom() {

        ResponseEntity<Motif> myResponse = restTemplate.getForEntity(END_URL + testNom, Motif.class, headers);
        Motif motif = myResponse.getBody();
        Assert.isTrue(null != motif, "Fetched motif is null");
        Assert.isTrue(motif.equals(m0), motif.toString() + " != " + m0.toString());
    }

    @Test
    public void TestDGetAllMotifs() {
        List myResponse = restTemplate.getForObject(END_URL, List.class, headers);
        Assert.isTrue(myResponse.size() == 3, "List size != 3");
    }

    @Test
    public void TestEPutMotif() {

        Optional<Motif> sOpt = motifRepository.findByNom(testNom);
        Assert.isTrue(sOpt.isPresent(), "Motif " + testNom + " not found");

        Motif motif = sOpt.get();
        String newIntitule = "Chaudronnier";
        motif.setNom(newIntitule);


        HttpEntity<Motif> requestBody = new HttpEntity<>(motif, headers);

        ResponseEntity<Motif> response = restTemplate.exchange(END_URL, PUT, requestBody, Motif.class, headers);

        Assert.isTrue(response.getStatusCodeValue() == 200, "OK");
        Motif maResponse = response.getBody();

        testNom = maResponse.getNom();

        Assert.isTrue(maResponse.getNom().equals(newIntitule), "Persisted intitule != newIntitule");

        m0 = maResponse;
    }


    @Test
    public void TestFGetIfMotifHasBeenUpdated() {

        ResponseEntity<Motif> myResponse = restTemplate.getForEntity(END_URL + testNom, Motif.class, headers);
        Motif motif = myResponse.getBody();
        Assert.isTrue(null != motif, "Fetched motif is null");
        Assert.isTrue(motif.equals(m0), motif.toString() + " != " + m0.toString());
    }

    @Test
    public void TestGDeleteMotifByNom() {

        restTemplate.delete(END_URL + testNom, headers);

        ResponseEntity<Motif> myResponse = restTemplate.getForEntity(END_URL + testNom, Motif.class, headers);
        Motif motif = myResponse.getBody();
        Assert.isTrue(null == motif, "Fetched motif is not null");


    }


}
