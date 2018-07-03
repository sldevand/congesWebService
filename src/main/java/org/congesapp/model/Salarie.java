package org.congesapp.model;

import org.congesapp.exception.DataModelException;
import org.congesapp.tools.Tools;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.ParseException;
import java.util.*;


@Entity
@Table
@XmlRootElement
public class Salarie extends AbstractEntity<Salarie> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(updatable = false, nullable = false, unique = true)
    private String matricule;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    @Temporal(value = TemporalType.DATE)
    private Date naissance;

    @ManyToOne
    private Poste poste;

    @ManyToOne
    private Service service;

    @Column(nullable = false)
    @Temporal(value = TemporalType.DATE)
    private Date entree;

    //@JsonManagedReference
    @OneToMany(mappedBy = "salarie", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Conge> conges = new ArrayList<>();

    @Column
    private double resteConges;

    @OneToOne(cascade = {CascadeType.ALL})
    private Adresse adresse;

    @Column
    private String tel;

    @Column(nullable = false)
    private String email = "";


    @Column
    private String login;


    @Column
    private String pwd;


    @Enumerated(EnumType.ORDINAL)
    private DroitEnum droit;

    public Salarie() {
        super(Salarie.class);
    }

    public Salarie(String nom, String prenom, Date naissance, Adresse adresse, Poste poste, Service service,
                   DroitEnum droit) {
        this(nom, prenom, naissance, adresse, "", nom + "." + prenom + "@" + "congesapp.fr", new Date(0), nom + prenom,
                "P@$$w0R2blabla", 0, poste, service, droit);
    }

    public Salarie(String nom, String prenom, Date naissance, Adresse adresse, String tel, String email, Date entree,
                   String login, String pwd, int resteConges, Poste poste, Service service, DroitEnum droit) {
        super(Salarie.class);
        this.nom = nom;
        this.prenom = prenom;
        this.naissance = naissance;
        this.adresse = adresse;
        this.tel = tel;
        this.email = email;
        this.entree = entree;
        this.login = login;
        this.pwd = pwd;
        this.resteConges = resteConges;
        this.poste = poste;
        this.service = service;
        this.droit = droit;
        generateMatricule();
    }

    public void setEntree(Date entree) {
        this.entree = entree;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getNaissance() {
        return naissance;
    }

    public void setNaissance(Date naissance) {
        this.naissance = naissance;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Date getEntree() {
        return entree;
    }

    public void setDateEntree(Date entree) {
        this.entree = entree;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void addConge(Conge conge) {
        this.conges.add(conge);
    }

    public void removeConge(Conge conge) {
        this.conges.remove(conge);
    }

    public List<Conge> getConges() {
        return conges;
    }

    public void setConges(List<Conge> conges) {
        this.conges = conges;
    }

    public double getResteConges() {
        return resteConges;
    }

    public void setResteConges(double resteConges) {
        this.resteConges = resteConges;
    }

    public Poste getPoste() {
        return this.poste;
    }

    public void setPoste(final Poste poste) {
        this.poste = poste;
    }

    public Service getService() {
        return this.service;
    }

    public void setService(final Service service) {
        this.service = service;
    }

    public DroitEnum getDroit() {
        return this.droit;
    }

    public void setDroit(final DroitEnum droit) {
        this.droit = droit;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Salarie salarie = (Salarie) o;
        return Objects.equals(matricule, salarie.matricule) &&
                Objects.equals(nom, salarie.nom) &&
                Objects.equals(prenom, salarie.prenom);
    }

    @Override
    public int hashCode() {

        return Objects.hash(matricule, nom, prenom);
    }

    @Override
    public String toString() {
        return "Salarie{" +
                "matricule='" + matricule + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }

    @Override
    public void hydrateFromUrlParams(Map<String, String[]> pMap) throws DataModelException {

        this.nom = getDef(pMap, "nom", "John");
        this.prenom = getDef(pMap, "prenom", "Doe");

        try {
            this.naissance = Tools.strToDate(getDef(pMap, "naissance", "1/1/1970"));
        } catch (ParseException e) {
            throw new DataModelException("Impossible de parser la date de naissance : " + this.naissance);
        }

        try {
            this.entree = Tools.strToDate(getDef(pMap, "entree", "1/1/1970"));
        } catch (ParseException e) {
            throw new DataModelException("Impossible de parser la date d'entree : " + this.entree);
        }

        try {
            this.resteConges = Double.parseDouble(getDef(pMap, "resteConges", "0"));
        } catch (NumberFormatException e) {
            throw new DataModelException("Impossible de parser le nombre de congés : " + this.resteConges);
        }

        this.adresse = new Adresse();
        this.adresse.hydrateFromUrlParams(pMap);

        this.tel = getDef(pMap, "tel", "99999");
        this.email = getDef(pMap, "email", "john@doe.com");

        this.login = getDef(pMap, "login", "user");
        this.pwd = getDef(pMap, "pwd", "user");

        try {
            this.droit = DroitEnum.valueOf(getDef(pMap, "droit", "0"));
        } catch (IllegalArgumentException e) {
            throw new DataModelException("Impossible de parser le droit : " + this.droit);
        }

        String matriculeStr = getDef(pMap, "matricule", "");

        if (matriculeStr != null && !matriculeStr.isEmpty() && !matriculeStr.equals("0")) {
            matricule = matriculeStr;
        } else {
            generateMatricule();
        }

    }

    /**
     * Matricule generation with initials+birth day+birth month+a pseudo random
     * number between 1 and 999
     */
    private void generateMatricule() {

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(naissance);
        String day = String.format("%02d", gc.get(GregorianCalendar.DAY_OF_MONTH));
        String month = String.format("%02d", gc.get(GregorianCalendar.MONTH) + 1);
        String random = String.format("%03d", (int) (Math.random() * 999 + 1));
        matricule = nom.substring(0, 1) + prenom.substring(0, 1) + day + month + random;


    }


}
