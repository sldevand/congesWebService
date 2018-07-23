package org.congesapp.controller;

import org.congesapp.model.Salarie;
import org.congesapp.repository.PosteRepository;
import org.congesapp.repository.SalarieRepository;
import org.congesapp.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin
@RestController
@RequestMapping(path = "/employees")
public class SalarieController {

    @Autowired
    private SalarieRepository salarieRepository;

    @Autowired
    private PosteRepository posteRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @PostMapping(value = "/",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public Salarie create(@RequestBody Salarie salarie) {
        return salarieRepository.save(salarie);

    }

    @GetMapping(value = "/{id}",
            produces = APPLICATION_JSON_VALUE)
    public Salarie readById(@PathVariable Long id) {
        Optional<Salarie> sOpt = salarieRepository.findById(id);
        if (sOpt.isPresent()) {
            return sOpt.get();
        } else {
            return null;
        }
    }

    @GetMapping(value = "/matricule/{matricule}",
            produces = APPLICATION_JSON_VALUE)
    public Salarie read(@PathVariable String matricule) {
        Optional<Salarie> sOpt = salarieRepository.findByMatricule(matricule);
        if (sOpt.isPresent()) {
            return sOpt.get();
        } else {
            return null;
        }
    }

    @GetMapping(value = "/",
            produces = APPLICATION_JSON_VALUE)
    public List<Salarie> readAll() {
        return (List<Salarie>) salarieRepository.findAll();
    }

    @PutMapping(value = "/",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public Salarie update(@RequestBody Salarie salarie) {
        return salarieRepository.save(salarie);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {

        Optional<Salarie> salarie = salarieRepository.findById(id);
        salarie.ifPresent(s -> salarieRepository.delete(s));
    }

    @DeleteMapping(value = "/matricule/{matricule}")
    public void delete(@PathVariable String matricule) {

        Optional<Salarie> salarie = salarieRepository.findByMatricule(matricule);
        salarie.ifPresent(s -> salarieRepository.delete(s));
    }

}
