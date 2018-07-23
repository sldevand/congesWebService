package org.congesapp.controller;

import org.congesapp.CongesApp;
import org.congesapp.model.Poste;
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
public class PosteControllerTest extends AbstractControllerTest {

    private final static String END_URL = BASE_URL+"jobs/";
    private static String testName;
    private static Long testId;

    private static Poste p0;
    private static Poste p1;
    private static Poste p2;


    @Override
    protected void initialize() {
        p0 = new Poste("Developpeur");
        p1 = new Poste("Scrum Master");
        p2 = new Poste("Product Owner");
    }

    @Test
    public void TestAPostPoste() {
        Poste maResponse = postPoste(p0);
        testName = maResponse.getName();
        testId= maResponse.getId();
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
        ResponseEntity<Poste> response = restTemplate.postForEntity(END_URL, requestBody, Poste.class, headers);
        Assert.isTrue(response.getStatusCodeValue() == 200, "OK");
        return response.getBody();
    }

    @Test
    public void TestCGetPosteById() {

        ResponseEntity<Poste> myResponse = restTemplate.getForEntity(END_URL+ testId, Poste.class, headers);
        Poste poste = myResponse.getBody();
        Assert.isTrue(null != poste, "Fetched poste is null");
        Assert.isTrue(poste.equals(p0), poste.toString() + " != " + p0.toString());
    }

    @Test
    public void TestCGetPosteByName() {

        ResponseEntity<Poste> myResponse = restTemplate.getForEntity(END_URL +"name/"+ testName, Poste.class, headers);
        Poste poste = myResponse.getBody();
        Assert.isTrue(null != poste, "Fetched poste is null");
        Assert.isTrue(poste.equals(p0), poste.toString() + " != " + p0.toString());
    }

    @Test
    public void TestDGetAllPostes() {
        List myResponse = restTemplate.getForObject(END_URL, List.class, headers);
        Assert.isTrue(myResponse.size() == 3, "List size != 3");
    }

    @Test
    public void TestEPutPoste() {

        Optional<Poste> sOpt = posteRepository.findByName(testName);
        Assert.isTrue(sOpt.isPresent(), "Poste " + testName + " not found");

        Poste poste = sOpt.get();
        String newName = "Chaudronnier";
        poste.setName(newName);


        HttpEntity<Poste> requestBody = new HttpEntity<>(poste, headers);

        ResponseEntity<Poste> response = restTemplate.exchange(END_URL, PUT, requestBody, Poste.class, headers);

        Assert.isTrue(response.getStatusCodeValue() == 200, "OK");
        Poste maResponse = response.getBody();

        testName = maResponse.getName();
        testId= maResponse.getId();

        Assert.isTrue(maResponse.getName().equals(newName), "Persisted name != newName");

        p0 = maResponse;
    }


    @Test
    public void TestFGetIfPosteHasBeenUpdated() {

        ResponseEntity<Poste> myResponse = restTemplate.getForEntity(END_URL +"name/"+ testName, Poste.class, headers);
        Poste poste = myResponse.getBody();
        Assert.isTrue(null != poste, "Fetched poste is null");
        Assert.isTrue(poste.equals(p0), poste.toString() + " != " + p0.toString());
    }

    @Test
    public void TestGDeletePosteByName() {

        restTemplate.delete(END_URL +"name/"+ testName, headers);
        ResponseEntity<Poste> myResponse = restTemplate.getForEntity(END_URL +"name/"+ testName, Poste.class, headers);
        Poste poste = myResponse.getBody();
        Assert.isTrue(null == poste, "Fetched poste is not null");
    }


}
