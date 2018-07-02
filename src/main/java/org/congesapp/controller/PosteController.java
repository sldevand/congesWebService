package org.congesapp.controller;

import org.congesapp.model.Poste;
import org.congesapp.repository.PosteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

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

    @GetMapping(value = "/{intitule}",
            produces = APPLICATION_JSON_VALUE)
    public Poste read(@PathVariable String intitule) {
        Optional<Poste> sOpt = posteRepository.findByIntitule(intitule);
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

    @DeleteMapping(value = "/{intitule}")
    public void delete(@PathVariable String intitule) {

        Optional<Poste> poste = posteRepository.findByIntitule(intitule);
        poste.ifPresent(s -> posteRepository.delete(s));
    }


}
