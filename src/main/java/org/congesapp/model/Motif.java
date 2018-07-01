package org.congesapp.model;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.congesapp.exception.DataModelException;

@Entity
@Table
@XmlRootElement
public class Motif extends AbstractEntity<Motif> {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Column(nullable = false, unique = true)
	private String nom;

	public Motif() {
		super(Motif.class);
	}

	public Motif(String nom) {
		super(Motif.class);
		this.nom = nom;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Motif other = (Motif) obj;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		return true;
	}

	
	@Override
	public String toString() {
		return nom;
	}
	
	
	
	
	@Override
	public void hydrateFromUrlParams(Map<String, String[]> pMap) throws DataModelException {
		this.nom = getDef(pMap, "nom", " ");
	}

}
