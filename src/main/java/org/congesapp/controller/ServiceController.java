package org.congesapp.controller;

import org.congesapp.model.Service;
import org.congesapp.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path="/services")
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;

    @PostMapping(value = "/",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public Service create(@RequestBody Service service) {
        serviceRepository.save(service);
        return service;
    }

    @GetMapping(value = "/{nom}",
            produces = APPLICATION_JSON_VALUE)
    public Service read(@PathVariable String nom) {
        Optional<Service> sOpt = serviceRepository.findByNom(nom);
        return sOpt.orElse(null);
    }

    @GetMapping(value = "/",
            produces = APPLICATION_JSON_VALUE)
    public List<Service> readAll() {
        return (List<Service>) serviceRepository.findAll();
    }

    @PutMapping(value = "/",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public Service update(@RequestBody Service service) {
        return serviceRepository.save(service);
    }

    @DeleteMapping(value = "/{nom}")
    public void delete(@PathVariable String nom) {

        Optional<Service> service = serviceRepository.findByNom(nom);
        service.ifPresent(s -> serviceRepository.delete(s));
    }
}