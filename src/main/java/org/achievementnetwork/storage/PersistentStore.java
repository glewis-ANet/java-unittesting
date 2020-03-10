package org.achievementnetwork.storage;

import org.achievementnetwork.domain.Frobnicator;

import javax.lang.model.UnknownEntityException;
import java.io.IOException;

import lombok.RequiredArgsConstructor;
import lombok.NonNull;

/**
 * Persistence store of frobnicators
 */
@RequiredArgsConstructor
public
class PersistentStore {
    private final KeyValueStore store;
    private final Serialiser serialiser;

    /**
     * Stores a Frobnicator
     *
     * @param frob the frobnicator to store
     * @throws IOException
     *         if there was an error writing to the store
     * @throws IllegalArgumentException
     *         if the frobnicator is not valid for storage
     */
    public
    void store(@NonNull Frobnicator frob)
        throws IOException, IllegalArgumentException {

        // Validate
        validate(frob);

        // Serialise
        String serialFrob = serialiser.dehydrate(frob);

        // Store
        store.store(frob.getId(), serialFrob);
    }

    /**
     * Retrieves a Frobnicator from storage
     *
     * @param id the id for the frobnicator
     * @return the frobnicator with the given id
     * @throws IOException
     *         if there was an error reading from the store
     * @throws UnknownEntityException
     *         if no such frobnicator was found
     */
    public
    Frobnicator fetch(@NonNull String id)
        throws IOException, UnknownEntityException {

        // Fetch
        String serialFrob = store.fetch(id);

        // Deserialise
        return serialiser.hydrate(serialFrob);
    }

    /**
     * Validates that a frobnicator is ready for storage
     *
     * @param frob
     * @throws IllegalArgumentException
     *         if the frobnicator has no identifier
     */
    private
    void validate(Frobnicator frob) {
        if (frob.getId() == null) {
            throw new IllegalArgumentException(
                "Can't store a frob without an id");
        }
    }
}
