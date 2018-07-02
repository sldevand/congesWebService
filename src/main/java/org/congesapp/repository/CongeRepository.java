package org.congesapp.repository;

import org.congesapp.model.Conge;
import org.congesapp.model.Poste;
import org.springframework.data.repository.CrudRepository;

public interface CongeRepository extends CrudRepository<Conge, String> {

}
