package org.congesapp.model;

import org.congesapp.exception.DataModelException;

import java.util.Map;

public interface Hydrator {
    /**
     * Fill object fields with url parameters
     *
     * @param pMap
     * @throws DataModelException
     */
    void hydrateFromUrlParams(Map<String, String[]> pMap) throws DataModelException;

    /**
     * Returns a default parameter value if not found in url parameters map
     *
     * @param pMap
     * @param key
     * @param def
     * @return String
     */
    String getDef(Map<String, String[]> pMap, String key, String def);
}
