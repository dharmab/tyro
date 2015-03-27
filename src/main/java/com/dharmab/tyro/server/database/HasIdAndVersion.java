package com.dharmab.tyro.server.database;

import java.io.Serializable;

/**
 * Implementers of this interface have an ID and version field suitable for use with a database.
 *
 * @param <I> The ID type
 * @param <V> The version type
 */
public interface HasIdAndVersion<I extends Serializable, V extends Serializable> {
    /**
     * @return The database ID of this object's record
     */
    public I getId();

    /**
     * @return The version field of this object's record. This is used to resolve concurrent modification conflicts
     */
    public V getVersion();
}
