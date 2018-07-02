package org.congesapp.controller;

import org.congesapp.model.Motif;
import org.congesapp.repository.MotifRepository;
import org.congesapp.repository.MotifRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path="/patterns")
public class MotifController {

    @Autowired
    private MotifRepository motifRepository;

    @PostMapping(value = "/",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public Motif create(@RequestBody Motif motif) {
        motifRepository.save(motif);
        return motif;
    }

    @GetMapping(value = "/{nom}",
            produces = APPLICATION_JSON_VALUE)
    public Motif read(@PathVariable String nom) {
        Optional<Motif> sOpt = motifRepository.findByNom(nom);
        return sOpt.orElse(null);
    }

    @GetMapping(value = "/",
            produces = APPLICATION_JSON_VALUE)
    public List<Motif> readAll() {
        return (List<Motif>) motifRepository.findAll();
    }

    @PutMapping(value = "/",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public Motif update(@RequestBody Motif motif) {
        return motifRepository.save(motif);
    }

    @DeleteMapping(value = "/{nom}")
    public void delete(@PathVariable String nom) {

        Optional<Motif> motif = motifRepository.findByNom(nom);
        motif.ifPresent(m -> motifRepository.delete(m));
    }


}
