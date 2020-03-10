package org.achievementnetwork.storage;

import domain.Frobnicator;

import java.io.IOException;

/**
 * Serialises a Frobnicator for storage
 */
interface Serialiser {
    /**
     * Serialises a Frobnicator.
     *
     * @param frob a Frobnicator to serialise
     * @return a serialised frob
     * @throws IOException
     *         if there is an error serialising the frob
     */
    String dehydrate(Frobnicator frob)
        throws IOException;

    /**
     * Deserialises a Frobnicator.
     *
     * @param frob a serialised Frobnicator
     * @return a Frobnicator
     * @throws IOException
     *         if there is an error during deserialisation
     * @throws IllegalArgumentException
     *         if the argument is not a valid serialised frob
     */
    Frobnicator hydrate(String frob)
        throws IOException, IllegalArgumentException;
}
