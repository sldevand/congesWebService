package org.congesapp.repository;

import org.congesapp.model.Conge;
import org.congesapp.model.Poste;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CongeRepository extends CrudRepository<Conge, Long> {

    @Query("SELECT c FROM Conge c WHERE c.motif.nom = :nom")
    Optional<Conge> findByMotif(@Param("nom") String nom);


}
