package org.congesapp.repository;

import org.congesapp.model.Salarie;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SalarieRepository extends CrudRepository<Salarie, String> {
    Optional<Salarie> findByMatricule(String matricule);

    Salarie deleteByMatricule(String matricule);


}

