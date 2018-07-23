package org.congesapp.controller;

import org.congesapp.model.Conge;
import org.congesapp.repository.CongeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin
@RestController
@RequestMapping(path="/holidays")
public class CongeController {

    @Autowired
    private CongeRepository congeRepository;

    @PostMapping(value = "/",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public Conge create(@RequestBody Conge conge) {
        return congeRepository.save(conge);
    }

    @GetMapping(value = "/reason/{nom}",
            produces = APPLICATION_JSON_VALUE)
    public Conge readByMotif(@PathVariable String nom) {
        Optional<Conge> sOpt = congeRepository.findByMotif(nom);
        return sOpt.orElse(null);
    }

    @GetMapping(value = "/{id}",
            produces = APPLICATION_JSON_VALUE)
    public Conge readById(@PathVariable("id") Long id) {
        Optional<Conge> sOpt = congeRepository.findById(id);
        return sOpt.orElse(null);
    }

    @GetMapping(value = "/",
            produces = APPLICATION_JSON_VALUE)
    public List<Conge> readAll() {
        return (List<Conge>) congeRepository.findAll();
    }

    @PutMapping(value = "/",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public Conge update(@RequestBody Conge conge) {
        return congeRepository.save(conge);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable("id") Long id) {

        Optional<Conge> conge = congeRepository.findById(id);
        conge.ifPresent(m -> congeRepository.delete(m));
    }


}
