package org.congesapp.controller;

import org.congesapp.CongesApp;
import org.congesapp.model.*;
import org.congesapp.tools.Tools;
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
public class CongeControllerTest extends AbstractControllerTest{

    private final static String END_URL = BASE_URL+"holidays/";

    private static Long testId;
    private static String testNom;
    private static Conge c0;
    private static Conge c1;
    private static Conge c2;
    private static Motif m0;
    private static Motif m1;
    private static Motif m2;
    private static Salarie s0;
    private static Salarie s1;
    private static Salarie s2;

    @Override
    protected void initialize() {
        addMotifs();
        addSalaries();

        List<Salarie> salaries = (List<Salarie>) salarieRepository.findAll();


        c0 = new Conge(m0,Tools.randomDate(),Tools.randomDate());
        c0.setSalarie(salaries.get(0));

        c1 = new Conge(m1,Tools.randomDate(),Tools.randomDate());
        c1.setSalarie(salaries.get(1));

        c2 = new Conge(m2,Tools.randomDate(),Tools.randomDate());
        c2.setSalarie(salaries.get(2));
    }

    private void addMotifs(){
        m0 = new Motif("Maladie");
        m1 = new Motif("Maternité");
        m2 = new Motif("Sans solde");

        motifRepository.save(m0);
        motifRepository.save(m1);
        motifRepository.save(m2);
    }

    private void addSalaries(){

        Poste poste = new Poste("dev");
        Service service = new Service("R&D");
        posteRepository.save(poste);
        serviceRepository.save(service);


        Adresse adr0 = new Adresse("Gallion E", "rue des Marins", 66666L, "Tortuga", "Bahamas");
        s0 = new Salarie("Sparrow", "Jack", Tools.randomDate(), adr0, poste , service, DroitEnum.USER);
        s0.addConge(c0);

        Adresse adr1 = new Adresse("Batiment 1", "rue des Peupliers", 69001L, "Lyon", "France");
        s1 = new Salarie("Doe", "John", Tools.randomDate(), adr1, poste, service, DroitEnum.ADMIN);
        s1.addConge(c1);

        Adresse adr2 = new Adresse("Les charmettes", "avenue de la glande", 38460L, "Cremieu", "France");
        s2 = new Salarie("Bersh", "Gudrun", Tools.randomDate(), adr2, poste, service, DroitEnum.SUPERADMIN);
        s2.addConge(c2);

        salarieRepository.save(s0);
        salarieRepository.save(s1);
        salarieRepository.save(s2);
    }


    @Test
    public void testAPostConge() {
        Conge maResponse = postConge(c0);
        testId = maResponse.getId();
        testNom = maResponse.getMotif().getNom();
        Assert.isTrue(c0.equals(maResponse), "Conge not equals to the persisted one");
    }

    @Test
    public void testBPostConges() {

        Conge poste1 = postConge(c1);
        Assert.isTrue(c1.equals(poste1), "Conged Conge not equals to the persisted one");

        Conge poste2 = postConge(c2);
        Assert.isTrue(c2.equals(poste2), "Conged Conge not equals to the persisted one");
    }

    private Conge postConge(Conge conge) {
        HttpEntity<Conge> requestBody = new HttpEntity<>(conge, headers);
        ResponseEntity<Conge> response = restTemplate.postForEntity(END_URL, requestBody, Conge.class, headers);
        Assert.isTrue(response.getStatusCodeValue() == 200, "OK");
        return response.getBody();
    }

    @Test
    public void testCGetCongeById() {

        ResponseEntity<Conge> myResponse = restTemplate.getForEntity(END_URL +testId, Conge.class, headers);
        Conge conge = myResponse.getBody();
        Assert.isTrue(null != conge, "Fetched conge is null");
        Assert.isTrue(conge.getMotif().equals(c0.getMotif()), conge.toString() + " != " + c0.toString());
    }

    @Test
    public void testCGetCongeByMotif() {

        ResponseEntity<Conge> myResponse = restTemplate.getForEntity(END_URL +"reason/"+ testNom, Conge.class, headers);
        Conge conge = myResponse.getBody();
        Assert.isTrue(null != conge, "Fetched conge is null");
        Assert.isTrue(conge.getMotif().equals(c0.getMotif()), conge.toString() + " != " + c0.toString());
    }

    @Test
    public void testDGetAllConges() {
        List myResponse = restTemplate.getForObject(END_URL, List.class, headers);
        Assert.isTrue(myResponse.size() == 3, "List size != 3");
    }

    @Test
    public void testEPutConge() {
        Optional<Conge> cOpt = congeRepository.findById(testId);
        Assert.isTrue(cOpt.isPresent(), "Conge " + testNom + " not found");
        Conge conge = cOpt.get();
        conge.setMotif(m1);
        conge.setSalarie(s1);

        HttpEntity<Conge> requestBody = new HttpEntity<>(conge, headers);
        ResponseEntity<Conge> response = restTemplate.exchange(END_URL, PUT, requestBody, Conge.class, headers);
        Assert.isTrue(response.getStatusCodeValue() == 200, "OK");

        Conge maResponse = response.getBody();
        testId = maResponse.getId();
        Assert.isTrue(maResponse.getMotif().equals(m1), "Persisted conge != conge");
        Assert.isTrue(maResponse.getSalarie().equals(s1), "Persisted conge != conge");
        c0 = maResponse;
    }

    @Test
    public void testFGetIfCongeHasBeenUpdated() {

        ResponseEntity<Conge> myResponse = restTemplate.getForEntity(END_URL + testId, Conge.class, headers);
        Conge conge = myResponse.getBody();
        Assert.isTrue(null != conge, "Fetched conge is null");
        Assert.isTrue(conge.getMotif().equals(m1), "Persisted conge != conge");
        Assert.isTrue(conge.getSalarie().equals(s1), "Persisted conge != conge");
    }

    @Test
    public void testGDeleteCongeById() {
        restTemplate.delete(END_URL + testId, headers);
        ResponseEntity<Conge> myResponse = restTemplate.getForEntity(END_URL + testId, Conge.class, headers);
        Conge conge = myResponse.getBody();
        Assert.isTrue(null == conge, "Fetched conge is not null");
    }
}
