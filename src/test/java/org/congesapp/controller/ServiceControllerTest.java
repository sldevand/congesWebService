package org.congesapp.controller;

import org.congesapp.CongesApp;
import org.congesapp.model.Service;
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
public class ServiceControllerTest extends AbstractControllerTest {

    private final static String END_URL = BASE_URL+"services/";
    private static String testNom;
    private static Service s0;
    private static Service s1;
    private static Service s2;

    @Override
    protected void initialize() {
        s0 = new Service("Commercial");
        s1 = new Service("Informatique");
        s2 = new Service("Direction");
    }

    @Test
    public void testAPostService() {
        Service maResponse = postService(s0);
        testNom = maResponse.getNom();
        Assert.isTrue(s0.equals(maResponse), "Serviced Service not equals to the persisted one");
    }

    @Test
    public void testBPostServices() {


        Service service1 = postService(s1);
        Assert.isTrue(s1.equals(service1), "Serviced Service not equals to the persisted one");

        Service service2 = postService(s2);
        Assert.isTrue(s2.equals(service2), "Serviced Service not equals to the persisted one");
    }

    private Service postService(Service service) {
        HttpEntity<Service> requestBody = new HttpEntity<>(service, headers);
        ResponseEntity<Service> response = restTemplate.postForEntity(END_URL, requestBody, Service.class, headers);
        Assert.isTrue(response.getStatusCodeValue() == 200, "OK");
        return response.getBody();
    }

    @Test
    public void testCGetServiceByNom() {

        ResponseEntity<Service> myResponse = restTemplate.getForEntity(END_URL + testNom, Service.class, headers);
        Service service = myResponse.getBody();
        Assert.isTrue(null != service, "Fetched service is null");
        Assert.isTrue(service.equals(s0), service.toString() + " != " + s0.toString());
    }

    @Test
    public void testDGetAllServices() {
        List myResponse = restTemplate.getForObject(END_URL, List.class, headers);
        Assert.isTrue(myResponse.size() == 3, "List size != 3");
    }

    @Test
    public void testEPutService() {

        Optional<Service> sOpt = serviceRepository.findByNom(testNom);
        Assert.isTrue(sOpt.isPresent(), "Service " + testNom + " not found");

        Service service = sOpt.get();
        String newNom = "Chaudronnier";
        service.setNom(newNom);


        HttpEntity<Service> requestBody = new HttpEntity<>(service, headers);

        ResponseEntity<Service> response = restTemplate.exchange(END_URL, PUT, requestBody, Service.class, headers);

        Assert.isTrue(response.getStatusCodeValue() == 200, "OK");
        Service maResponse = response.getBody();

        testNom = maResponse.getNom();

        Assert.isTrue(maResponse.getNom().equals(newNom), "Persisted intitule != newNom");

        s0 = maResponse;
    }


    @Test
    public void testFGetIfServiceHasBeenUpdated() {

        ResponseEntity<Service> myResponse = restTemplate.getForEntity(END_URL + testNom, Service.class, headers);
        Service service = myResponse.getBody();
        Assert.isTrue(null != service, "Fetched service is null");
        Assert.isTrue(service.equals(s0), service.toString() + " != " + s0.toString());
    }

    @Test
    public void testGDeleteServiceByNom() {

        restTemplate.delete(END_URL + testNom, headers);

        ResponseEntity<Service> myResponse = restTemplate.getForEntity(END_URL + testNom, Service.class, headers);
        Service service = myResponse.getBody();
        Assert.isTrue(null == service, "Fetched service is not null");


    }


}
