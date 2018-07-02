package org.congesapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.congesapp.exception.DataModelException;

import java.io.Serializable;
import java.util.Map;

public abstract class AbstractEntity<ENTITY> implements Serializable, Hydrator {

    protected static final long serialVersionUID = 1L;

    protected Long id;

    @JsonIgnore
    protected transient Class<ENTITY> entityClass;

    public AbstractEntity(Class<ENTITY> clazz) {
        setEntityClass(clazz);
    }

    @Override
    public String getDef(Map<String, String[]> pMap, String key, String def) {
        return pMap.getOrDefault(key, new String[]{def})[0];
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


    @Override
    public abstract void hydrateFromUrlParams(Map<String, String[]> pMap) throws DataModelException;
}
