package org.congesapp.repository;

import org.congesapp.model.Poste;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PosteRepository extends CrudRepository<Poste, Long> {

    Optional<Poste> findByName(String name);
}
