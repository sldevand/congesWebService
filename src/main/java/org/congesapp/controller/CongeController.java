package org.congesapp.controller;

import org.congesapp.model.Conge;
import org.congesapp.repository.CongeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

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

    @GetMapping(value = "/{nom}",
            produces = APPLICATION_JSON_VALUE)
    public Conge read(@PathVariable String nom) {
        Optional<Conge> sOpt = congeRepository.findByNom(nom);
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

    @DeleteMapping(value = "/{nom}")
    public void delete(@PathVariable String nom) {

        Optional<Conge> conge = congeRepository.findByNom(nom);
        conge.ifPresent(m -> congeRepository.delete(m));
    }


}
