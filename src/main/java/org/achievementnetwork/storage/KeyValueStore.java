package org.achievementnetwork.storage;

import javax.lang.model.UnknownEntityException;
import java.io.IOException;

/**
 * An interface for persisting and retrieving data to a NoSQL store.
 */
interface KeyValueStore {
    /**
     * Store the serialised data under the given key.
     *
     * @param key  the identifier for the data
     * @param data the serialised data
     * @throws IOException
     *         if there is an error writing the data
     */
    void store(String key, String data)
        throws IOException;

    /**
     * Fetch the serialised data for the given key.
     *
     * @param key  the identifier for the data
     * @return the serialised data
     * @throws IOException
     *         if there is an error reading the data
     * @throws UnknownEntityException
     *         if no entry with the given key is found
     */
    String fetch(String key)
        throws IOException, UnknownEntityException;
}
