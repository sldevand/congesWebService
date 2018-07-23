package org.congesapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public abstract class AbstractEntity<ENTITY> implements Serializable {

    protected static final long serialVersionUID = 1L;

    protected Long id;

    @JsonIgnore
    protected transient Class<ENTITY> entityClass;

    public AbstractEntity(Class<ENTITY> clazz) {
        setEntityClass(clazz);
    }

    public Class<ENTITY> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<ENTITY> entityClass) {
        this.entityClass = entityClass;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
