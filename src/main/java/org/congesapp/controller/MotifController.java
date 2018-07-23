package org.congesapp.controller;

import org.congesapp.model.Motif;
import org.congesapp.repository.MotifRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin
@RestController
@RequestMapping(path="/reasons")
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

    @GetMapping(value = "/{id}",
            produces = APPLICATION_JSON_VALUE)
    public Motif readById(@PathVariable Long id) {
        Optional<Motif> sOpt = motifRepository.findById(id);
        return sOpt.orElse(null);
    }

    @GetMapping(value = "/name/{name}",
            produces = APPLICATION_JSON_VALUE)
    public Motif read(@PathVariable String name) {
        Optional<Motif> sOpt = motifRepository.findByName(name);
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

    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable Long id) {

        Optional<Motif> motif = motifRepository.findById(id);
        motif.ifPresent(m -> motifRepository.delete(m));
    }


    @DeleteMapping(value = "/name/{name}")
    public void delete(@PathVariable String name) {

        Optional<Motif> motif = motifRepository.findByName(name);
        motif.ifPresent(m -> motifRepository.delete(m));
    }


}
