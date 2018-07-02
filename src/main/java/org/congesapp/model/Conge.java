package org.congesapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.congesapp.exception.DataModelException;
import org.congesapp.tools.Tools;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

@Entity
@Table
@XmlRootElement
public class Conge extends AbstractEntity<Conge> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @JsonBackReference
    @ManyToOne
    private Salarie salarie;

    @Column(nullable = false)
    @Temporal(value = TemporalType.DATE)
    private Date debut;

    @Column(nullable = false)
    @Temporal(value = TemporalType.DATE)
    private Date fin;

    @ManyToOne
    private Motif motif;

    public Conge() {
        super(Conge.class);
    }

    public Conge(Motif motif, Date debut, Date fin) {
        super(Conge.class);
        this.motif = motif;
        this.debut = debut;
        this.fin = fin;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Motif getMotif() {
        return this.motif;
    }

    public void setMotif(final Motif motif) {
        this.motif = motif;
    }

    public Date getDebut() {
        return debut;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public Salarie getSalarie() {
        return salarie;
    }

    public void setSalarie(Salarie salarie) {
        this.salarie = salarie;
    }

    @Override
    public void hydrateFromUrlParams(Map<String, String[]> pMap) throws DataModelException {

        try {
            this.debut = Tools.strToDate(getDef(pMap, "debut", "01/01/1970"));
        } catch (ParseException e1) {
            throw new DataModelException("Impossible de parser la date de debut : " + this.debut);

        }

        try {
            this.fin = Tools.strToDate(getDef(pMap, "fin", "01/01/1970"));
        } catch (ParseException e) {
            throw new DataModelException("Impossible de parser la date de fin : " + this.fin);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((debut == null) ? 0 : debut.hashCode());
        result = prime * result + ((fin == null) ? 0 : fin.hashCode());
        result = prime * result + ((motif == null) ? 0 : motif.hashCode());
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
        Conge other = (Conge) obj;
        if (debut == null) {
            if (other.debut != null)
                return false;
        } else if (!debut.equals(other.debut))
            return false;
        if (fin == null) {
            if (other.fin != null)
                return false;
        } else if (!fin.equals(other.fin))
            return false;
        if (motif == null) {
            if (other.motif != null)
                return false;
        } else if (!motif.equals(other.motif))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Du " + debut + " au " + fin + ", " + motif;
    }

}