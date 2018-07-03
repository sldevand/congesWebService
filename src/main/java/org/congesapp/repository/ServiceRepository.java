package org.congesapp.repository;

import org.congesapp.model.Motif;
import org.congesapp.model.Service;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ServiceRepository extends CrudRepository<Service, String> {
    Optional<Service> findByNom(String nom);
}
