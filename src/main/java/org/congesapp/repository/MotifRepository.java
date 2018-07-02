package org.congesapp.repository;

import org.congesapp.model.Motif;
import org.congesapp.model.Poste;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MotifRepository extends CrudRepository<Motif, String> {

    Optional<Motif> findByNom(String nom);
}
