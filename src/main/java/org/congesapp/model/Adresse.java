package org.congesapp.model;

import org.congesapp.exception.DataModelException;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

@Entity
@Table
@XmlRootElement
public class Adresse extends AbstractEntity<Adresse> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(nullable = true)
    private String libelle;

    @Column(nullable = true)
    private String complement;

    @Column(nullable = true)
    private Long codePostal;

    @Column(nullable = true)
    private String ville;

    @Column(nullable = true)
    private String pays;

    public Adresse() {
        super(Adresse.class);
    }

    public Adresse(String complement, String libelle, Long codePostal, String ville, String pays) {
        super(Adresse.class);
        this.libelle = libelle;
        this.complement = complement;
        this.codePostal = codePostal;
        this.ville = ville;
        this.pays = pays;
    }

    public boolean isValid() throws DataModelException {

        if (libelle.isEmpty())
            throw new DataModelException("Le libellé  est vide !");
        if (codePostal < 0 || codePostal > 100000)
            throw new DataModelException("Le code postal est hors limites !");
        if (ville.isEmpty())
            throw new DataModelException("La ville est vide !");
        if (pays.isEmpty())
            throw new DataModelException("Le pays est vide !");
        return true;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public Long getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(Long codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codePostal == null) ? 0 : codePostal.hashCode());
        result = prime * result + ((complement == null) ? 0 : complement.hashCode());
        result = prime * result + ((libelle == null) ? 0 : libelle.hashCode());
        result = prime * result + ((pays == null) ? 0 : pays.hashCode());
        result = prime * result + ((ville == null) ? 0 : ville.hashCode());
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
        Adresse other = (Adresse) obj;
        if (codePostal == null) {
            if (other.codePostal != null)
                return false;
        } else if (!codePostal.equals(other.codePostal))
            return false;
        if (complement == null) {
            if (other.complement != null)
                return false;
        } else if (!complement.equals(other.complement))
            return false;
        if (libelle == null) {
            if (other.libelle != null)
                return false;
        } else if (!libelle.equals(other.libelle))
            return false;
        if (pays == null) {
            if (other.pays != null)
                return false;
        } else if (!pays.equals(other.pays))
            return false;
        if (ville == null) {
            if (other.ville != null)
                return false;
        } else if (!ville.equals(other.ville))
            return false;
        return true;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        return builder.append(libelle).append("\n").append(codePostal).append(" ").append(ville).append("\n")
                .append(pays).toString();

    }

    @Override
    public void hydrateFromUrlParams(Map<String, String[]> pMap) throws DataModelException {
        String idStr = getDef(pMap, "adresseId", "0");
        try {

            if (idStr != null && idStr != "0" && !idStr.isEmpty()) {
                this.id = Long.parseLong(getDef(pMap, "adresseId", "0"));
            }
        } catch (NumberFormatException e) {
            throw new DataModelException("Adresse : Impossible de parser l'Id : " + id + " à partir de " + idStr);
        }

        this.complement = getDef(pMap, "complement", " ");
        this.libelle = getDef(pMap, "libelle", " ");

        try {
            this.codePostal = Long.parseLong(getDef(pMap, "codePostal", "99999"));
        } catch (IllegalArgumentException e) {
            throw new DataModelException(
                    "Impossible de parser le code Postal : " + getDef(pMap, "codePostal", "99999"));
        }

        this.ville = getDef(pMap, "ville", " ");
        this.pays = getDef(pMap, "pays", " ");

    }

}
