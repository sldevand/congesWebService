package org.congesapp.controller;

import org.congesapp.model.Poste;
import org.congesapp.repository.PosteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin
@RestController
@RequestMapping(path="/jobs")
public class PosteController {

    @Autowired
    private PosteRepository posteRepository;

    @PostMapping(value = "/",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public Poste create(@RequestBody Poste poste) {
        posteRepository.save(poste);
        return poste;
    }


    @GetMapping(value = "/{id}",
            produces = APPLICATION_JSON_VALUE)
    public Poste readById(@PathVariable Long id) {
        Optional<Poste> sOpt = posteRepository.findById(id);
        return sOpt.orElse(null);
    }

    @GetMapping(value = "/name/{name}",
            produces = APPLICATION_JSON_VALUE)
    public Poste read(@PathVariable String name) {
        Optional<Poste> sOpt = posteRepository.findByName(name);
        return sOpt.orElse(null);
    }

    @GetMapping(value = "/",
            produces = APPLICATION_JSON_VALUE)
    public List<Poste> readAll() {
        return (List<Poste>) posteRepository.findAll();
    }

    @PutMapping(value = "/",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public Poste update(@RequestBody Poste poste) {
        return posteRepository.save(poste);
    }



    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable Long id) {

        Optional<Poste> poste = posteRepository.findById(id);
        poste.ifPresent(s -> posteRepository.delete(s));
    }

    @DeleteMapping(value = "/name/{name}")
    public void delete(@PathVariable String name) {

        Optional<Poste> poste = posteRepository.findByName(name);
        poste.ifPresent(s -> posteRepository.delete(s));
    }


}
