package org.congesapp.controller;

import org.congesapp.CongesApp;
import org.congesapp.model.Service;
import org.congesapp.repository.ServiceRepository;
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
public class ServiceControllerTest {

    private final static String BASE_URL = "http://localhost:9001/services/";
    private static String testNom;
    private static HttpHeaders headers;
    private static Service s0;
    private static Service s1;
    private static Service s2;

    private RestTemplate restTemplate = new RestTemplate();

    private static boolean testedOnce = false;

    @Autowired
    private ServiceRepository serviceRepository;


    @Before
    public void before() {
        headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        if (!testedOnce) {

            serviceRepository.deleteAll();
            s0 = new Service("Commercial");
            s1 = new Service("Informatique");
            s2 = new Service("Direction");

            testedOnce = true;
        }
    }

    @Test
    public void TestAPostService() {
        Service maResponse = postService(s0);
        testNom = maResponse.getNom();
        Assert.isTrue(s0.equals(maResponse), "Serviced Service not equals to the persisted one");
    }

    @Test
    public void TestBPostServices() {


        Service service1 = postService(s1);
        Assert.isTrue(s1.equals(service1), "Serviced Service not equals to the persisted one");

        Service service2 = postService(s2);
        Assert.isTrue(s2.equals(service2), "Serviced Service not equals to the persisted one");
    }

    private Service postService(Service service) {
        HttpEntity<Service> requestBody = new HttpEntity<>(service, headers);
        ResponseEntity<Service> response = restTemplate.postForEntity(BASE_URL, requestBody, Service.class, headers);
        Assert.isTrue(response.getStatusCodeValue() == 200, "OK");
        return response.getBody();
    }

    @Test
    public void TestCGetServiceByNom() {

        ResponseEntity<Service> myResponse = restTemplate.getForEntity(BASE_URL + testNom, Service.class, headers);
        Service service = myResponse.getBody();
        Assert.isTrue(null != service, "Fetched service is null");
        Assert.isTrue(service.equals(s0), service.toString() + " != " + s0.toString());
    }

    @Test
    public void TestDGetAllServices() {
        List myResponse = restTemplate.getForObject(BASE_URL, List.class, headers);
        Assert.isTrue(myResponse.size() == 3, "List size != 3");
    }

    @Test
    public void TestEPutService() {

        Optional<Service> sOpt = serviceRepository.findByNom(testNom);
        Assert.isTrue(sOpt.isPresent(), "Service " + testNom + " not found");

        Service service = sOpt.get();
        String newNom = "Chaudronnier";
        service.setNom(newNom);


        HttpEntity<Service> requestBody = new HttpEntity<>(service, headers);

        ResponseEntity<Service> response = restTemplate.exchange(BASE_URL, PUT, requestBody, Service.class, headers);

        Assert.isTrue(response.getStatusCodeValue() == 200, "OK");
        Service maResponse = response.getBody();

        testNom = maResponse.getNom();

        Assert.isTrue(maResponse.getNom().equals(newNom), "Persisted intitule != newNom");

        s0 = maResponse;
    }


    @Test
    public void TestFGetIfServiceHasBeenUpdated() {

        ResponseEntity<Service> myResponse = restTemplate.getForEntity(BASE_URL + testNom, Service.class, headers);
        Service service = myResponse.getBody();
        Assert.isTrue(null != service, "Fetched service is null");
        Assert.isTrue(service.equals(s0), service.toString() + " != " + s0.toString());
    }

    @Test
    public void TestGDeleteServiceByNom() {

        restTemplate.delete(BASE_URL + testNom, headers);

        ResponseEntity<Service> myResponse = restTemplate.getForEntity(BASE_URL + testNom, Service.class, headers);
        Service service = myResponse.getBody();
        Assert.isTrue(null == service, "Fetched service is not null");


    }


}
