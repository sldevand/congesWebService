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
public class Poste extends AbstractEntity<Poste> {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Column(nullable = false, unique = true)
	private String intitule;

	public Poste() {
		super(Poste.class);
	}

	public Poste(String intitule) {
		super(Poste.class);
		this.intitule = intitule;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((intitule == null) ? 0 : intitule.hashCode());
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
		Poste other = (Poste) obj;
		if (intitule == null) {
			if (other.intitule != null)
				return false;
		} else if (!intitule.equals(other.intitule))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return intitule;
	}

	@Override
	public void hydrateFromUrlParams(Map<String, String[]> pMap) throws DataModelException {
		this.intitule = getDef(pMap, "intitule", " ");
		
	}
}
