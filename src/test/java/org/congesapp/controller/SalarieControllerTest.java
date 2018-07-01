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

/**
 * @author Josh Long
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,classes = CongesApp.class)
@ActiveProfiles({"TEST"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SalarieControllerTest {

    private final static String BASE_URL="http://localhost:9001/employees/";
    private static String testMatricule;
    private static HttpHeaders headers;
    private static Salarie s0;
    private static Salarie s1;
    private static Salarie s2;

    private RestTemplate restTemplate = new RestTemplate();

    private static boolean  testedOnce = false;


    @Autowired
    private SalarieRepository salarieRepository;

    @Autowired
    private  ServiceRepository serviceRepository;

    @Autowired
    private PosteRepository posteRepository;


    @Before
    public void before(){
        headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Accept",MediaType.APPLICATION_JSON_VALUE);

        if(!testedOnce) {
            Poste poste = new Poste("dev");
            Service service = new Service("R&D");
            posteRepository.save(poste);
            serviceRepository.save(service);
            List<Poste> postes = (List<Poste>) posteRepository.findAll();
            List<Service> services = (List<Service>) serviceRepository.findAll();
            Adresse adr0 = new Adresse("Gallion E", "rue des Marins", 66666L, "Tortuga", "Bahamas");
            s0 = new Salarie("Sparrow", "Jack", Tools.randomDate(), adr0, postes.get(0), services.get(0),DroitEnum.USER);

            Adresse adr1 = new Adresse("Batiment 1", "rue des Peupliers", 69730L, "Genay", "France");
            s1 = new Salarie("Lorrain", "Sébastien", Tools.randomDate(), adr1, postes.get(0), services.get(0),DroitEnum.ADMIN);

            Adresse adr2 = new Adresse("Les charmettes", "avenue de la glande", 38460L, "Cremieu", "France");
            s2 = new Salarie("Prrrt", "Gudrun", Tools.randomDate(), adr2, postes.get(0), services.get(0),DroitEnum.SUPERADMIN);

            testedOnce=true;
        }
    }

    @Test
    public void TestAPostSalarie(){
        Salarie maResponse = postSalarie(s0);
        testMatricule = maResponse.getMatricule();
        Assert.isTrue(s0.equals(maResponse),"Posted Salarie not equals to the persisted one");
    }

    @Test
    public void TestAPostSalaries(){

        Salarie salarie1 = postSalarie(s1);
        Assert.isTrue(s1.equals(salarie1),"Posted Salarie not equals to the persisted one");

        Salarie salarie2 = postSalarie(s2);
        Assert.isTrue(s2.equals(salarie2),"Posted Salarie not equals to the persisted one");
    }

    public Salarie postSalarie(Salarie salarie){
        HttpEntity<Salarie> requestBody = new HttpEntity<>(salarie,headers);
        ResponseEntity<Salarie> response  = restTemplate.postForEntity(BASE_URL, requestBody, Salarie.class,headers);
        Assert.isTrue(response.getStatusCodeValue() == 200, "OK");
        return response.getBody();
    }

    @Test
    public void TestBGetSalarieByMatricule(){

        ResponseEntity<Salarie> myResponse = restTemplate.getForEntity(BASE_URL + testMatricule, Salarie.class, headers);
        Salarie salarie = myResponse.getBody();
        Assert.isTrue(null != salarie, "Fetched salarie is null");
        Assert.isTrue(salarie.equals(s0),salarie.toString() +" != "+ s0.toString());
    }

    @Test
    public void TestCGetAllSalaries() {
        List myResponse = restTemplate.getForObject(BASE_URL, List.class, headers);
        Assert.isTrue(myResponse.size()==3,"List size != 3");
    }

    @Test
    public void TestDPutSalarie(){

        Optional<Salarie> sOpt = salarieRepository.findByMatricule(testMatricule);
        Assert.isTrue(sOpt.isPresent(),"Salarie "+ testMatricule +" not found");

        Salarie salarie = sOpt.get();

        String newNom = "Lannister";
        String newPrenom = "Cersei";

        salarie.setNom(newNom);
        salarie.setPrenom(newPrenom);

        HttpEntity<Salarie> requestBody = new HttpEntity<>(salarie,headers);

        ResponseEntity<Salarie> response = restTemplate.exchange(BASE_URL,PUT, requestBody,Salarie.class,headers);

        Assert.isTrue(response.getStatusCodeValue() == 200, "OK");
        Salarie maResponse = response.getBody();

        testMatricule = maResponse.getMatricule();

        Assert.isTrue(maResponse.getNom().equals(newNom),"Persisted nom != newNom");
        Assert.isTrue(maResponse.getPrenom().equals(newPrenom),"Persisted prenom != newPrenom");
        s0=maResponse;
    }


    @Test
    public void TestEGetIfSalarieHasBeenUpdated(){

        ResponseEntity<Salarie> myResponse = restTemplate.getForEntity(BASE_URL + testMatricule, Salarie.class, headers);
        Salarie salarie = myResponse.getBody();
        Assert.isTrue(null != salarie, "Fetched salarie is null");
        Assert.isTrue(salarie.equals(s0),salarie.toString() +" != "+ s0.toString());
    }

    @Test
    public void TestFDeleteSalarieByMatricule(){

        restTemplate.delete( BASE_URL + testMatricule,headers);

        ResponseEntity<Salarie> myResponse = restTemplate.getForEntity(BASE_URL + testMatricule, Salarie.class, headers);
        Salarie salarie = myResponse.getBody();
        Assert.isTrue(null == salarie, "Fetched salarie is not null");


    }










}
