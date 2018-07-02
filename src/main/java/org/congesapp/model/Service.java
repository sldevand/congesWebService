package org.congesapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.congesapp.exception.DataModelException;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table
@XmlRootElement
public class Service extends AbstractEntity<Service> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nom;

    @JsonBackReference
    @OneToMany(mappedBy = "service", fetch = FetchType.EAGER)
    private Set<Salarie> salarie = new HashSet<Salarie>();

    public Service() {
        super(Service.class);
    }

    public Service(String nom) {
        super(Service.class);
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

    public Set<Salarie> getSalarie() {
        return this.salarie;
    }

    public void setSalarie(final Set<Salarie> salarie) {
        this.salarie = salarie;
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
        Service other = (Service) obj;
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
