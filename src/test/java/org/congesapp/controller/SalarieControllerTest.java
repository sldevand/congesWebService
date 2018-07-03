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
public class SalarieControllerTest extends AbstractControllerTest {

    private final static String END_URL = BASE_URL+"employees/";
    private static String testMatricule;

    private static Salarie s0;
    private static Salarie s1;
    private static Salarie s2;


    @Override
    protected void initialize() {
        addSalaries();
    }

    private void addSalaries(){

        Poste poste = new Poste("dev");
        Service service = new Service("R&D");
        posteRepository.save(poste);
        serviceRepository.save(service);

        Adresse adr0 = new Adresse("Gallion E", "rue des Marins", 66666L, "Tortuga", "Bahamas");
        s0 = new Salarie("Sparrow", "Jack", Tools.randomDate(), adr0, poste , service, DroitEnum.USER);

        Adresse adr1 = new Adresse("Batiment 1", "rue des Peupliers", 69730L, "Genay", "France");
        s1 = new Salarie("Lorrain", "Sébastien", Tools.randomDate(), adr1, poste, service, DroitEnum.ADMIN);

        Adresse adr2 = new Adresse("Les charmettes", "avenue de la glande", 38460L, "Cremieu", "France");
        s2 = new Salarie("Bersh", "Gudrun", Tools.randomDate(), adr2, poste, service, DroitEnum.SUPERADMIN);
    }

    @Test
    public void TestAPostSalarie() {
        Salarie maResponse = postSalarie(s0);
        testMatricule = maResponse.getMatricule();
        Assert.isTrue(s0.equals(maResponse), "Posted Salarie not equals to the persisted one");
    }

    @Test
    public void TestAPostSalaries() {

        Salarie salarie1 = postSalarie(s1);
        Assert.isTrue(s1.equals(salarie1), "Posted Salarie not equals to the persisted one");

        Salarie salarie2 = postSalarie(s2);
        Assert.isTrue(s2.equals(salarie2), "Posted Salarie not equals to the persisted one");
    }

    public Salarie postSalarie(Salarie salarie) {
        HttpEntity<Salarie> requestBody = new HttpEntity<>(salarie, headers);
        ResponseEntity<Salarie> response = restTemplate.postForEntity(END_URL, requestBody, Salarie.class, headers);
        Assert.isTrue(response.getStatusCodeValue() == 200, "OK");
        return response.getBody();
    }

    @Test
    public void TestBGetSalarieByMatricule() {

        ResponseEntity<Salarie> myResponse = restTemplate.getForEntity(END_URL + testMatricule, Salarie.class, headers);
        Salarie salarie = myResponse.getBody();
        Assert.isTrue(null != salarie, "Fetched salarie is null");
        Assert.isTrue(salarie.equals(s0), salarie.toString() + " != " + s0.toString());
    }

    @Test
    public void TestCGetAllSalaries() {
        List myResponse = restTemplate.getForObject(END_URL, List.class, headers);
        Assert.isTrue(myResponse.size() == 3, "List size != 3");
    }

    @Test
    public void TestDPutSalarie() {

        Optional<Salarie> sOpt = salarieRepository.findByMatricule(testMatricule);
        Assert.isTrue(sOpt.isPresent(), "Salarie " + testMatricule + " not found");

        Salarie salarie = sOpt.get();

        String newNom = "Lannister";
        String newPrenom = "Cersei";

        salarie.setNom(newNom);
        salarie.setPrenom(newPrenom);

        HttpEntity<Salarie> requestBody = new HttpEntity<>(salarie, headers);

        ResponseEntity<Salarie> response = restTemplate.exchange(END_URL, PUT, requestBody, Salarie.class, headers);

        Assert.isTrue(response.getStatusCodeValue() == 200, "OK");
        Salarie maResponse = response.getBody();

        testMatricule = maResponse.getMatricule();

        Assert.isTrue(maResponse.getNom().equals(newNom), "Persisted nom != newNom");
        Assert.isTrue(maResponse.getPrenom().equals(newPrenom), "Persisted prenom != newPrenom");
        s0 = maResponse;
    }


    @Test
    public void TestEGetIfSalarieHasBeenUpdated() {

        ResponseEntity<Salarie> myResponse = restTemplate.getForEntity(END_URL + testMatricule, Salarie.class, headers);
        Salarie salarie = myResponse.getBody();
        Assert.isTrue(null != salarie, "Fetched salarie is null");
        Assert.isTrue(salarie.equals(s0), salarie.toString() + " != " + s0.toString());
    }

    @Test
    public void TestFDeleteSalarieByMatricule() {

        restTemplate.delete(END_URL + testMatricule, headers);

        ResponseEntity<Salarie> myResponse = restTemplate.getForEntity(END_URL + testMatricule, Salarie.class, headers);
        Salarie salarie = myResponse.getBody();
        Assert.isTrue(null == salarie, "Fetched salarie is not null");


    }


}
