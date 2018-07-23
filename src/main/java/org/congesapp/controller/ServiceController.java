package org.congesapp.controller;

import org.congesapp.model.Service;
import org.congesapp.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin
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

    @GetMapping(value = "/{id}",
            produces = APPLICATION_JSON_VALUE)
    public Service read(@PathVariable Long id) {
        Optional<Service> sOpt = serviceRepository.findById(id);
        return sOpt.orElse(null);
    }


    @GetMapping(value = "/name/{name}",
            produces = APPLICATION_JSON_VALUE)
    public Service read(@PathVariable String name) {
        Optional<Service> sOpt = serviceRepository.findByName(name);
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


    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {

        Optional<Service> service = serviceRepository.findById(id);
        service.ifPresent(s -> serviceRepository.delete(s));
    }

    @DeleteMapping(value = "/name/{name}")
    public void delete(@PathVariable String name) {

        Optional<Service> service = serviceRepository.findByName(name);
        service.ifPresent(s -> serviceRepository.delete(s));
    }
}