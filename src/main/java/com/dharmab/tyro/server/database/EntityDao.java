package com.dharmab.tyro.server.database;

import com.google.common.base.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import java.io.Serializable;
import java.util.List;

/**
 * Generic class for database access objects.
 *
 * @param <T> The type of entity this DAO will manage. Must be mapped with JPA/Hibernate.
 * @param <I> The type of the entity's id property.
 * @param <V> The type of the entity's version property.
 */
public class EntityDao<T extends HasIdAndVersion<I, V>, I extends Serializable, V extends Serializable> {
    private final SessionFactory sessionFactory;
    private final Class<T> domainType;

    /**
     * @param domainType     The class that this EntityDao should manage
     * @param sessionFactory The Hibernate session factory
     */
    public EntityDao(Class<T> domainType, SessionFactory sessionFactory) {
        // Type information has be be specified twice (once in the generic and once in the constructor) because
        // Generics in java are subject to type erasure, preventing us from determining the type at runtime.
        this.domainType = domainType;
        this.sessionFactory = sessionFactory;
    }

    /**
     * If the given entity exists in the database, update the record matching the given entity.
     * Otherwise, persist the given entity as a new record.
     *
     * @param entity entity to persist, identified by ID.
     */
    public void persist(T entity) {
        getCurrentSession().persist(entity);
    }

    /**
     * Delete the record matching the given entity from the database.
     *
     * @param entity entity to delete, identified by ID.
     */
    public void delete(T entity) {
        getCurrentSession().delete(entity);
    }

    /**
     * Delete the record matching the given entity ID from the database.
     *
     * @param id ID of entity to delete.
     */
    public void delete(I id) {
        Optional<T> maybeEntity = get(id);
        if (maybeEntity.isPresent()) {
            delete(maybeEntity.get());
        }
    }

    /**
     * Retrieve the entity matching the given ID from the database.
     *
     * @param id ID of entity to retrieve.
     * @return entity identified by the given ID
     */
    public Optional<T> get(I id) {
        // Converting a database type to a Java type is inherently unchecked
        @SuppressWarnings("unchecked") T entity = (T) getCurrentSession().get(domainType, id);
        return Optional.fromNullable(entity);
    }

    /**
     * Retrieve a list of entities by ID
     *
     * @param start     The ID of the first entity to retrieve from the database.
     * @param maxLength The maximum number of entities to retrieve from the database.
     * @return A list containing up to the maximum specified number of entities, starting from
     * the given starting ID and ordered by ID ascending.
     */
    public List<T> get(I start, int maxLength) {
        @SuppressWarnings("unchecked") List<T> results = getCurrentSession()
                .createCriteria(domainType)
                .setFirstResult((Integer) start)
                .addOrder(Order.asc("id"))
                .setMaxResults(maxLength)
                .list();
        return results;
    }

    /**
     * @return The number of entities in the database
     */
    public Number count() {
        return (Number) getCurrentSession()
                .createCriteria(domainType)
                .setProjection(Projections.rowCount())
                .uniqueResult();
    }

    /**
     * @return The Hibernate session bound to the current thread.
     */
    protected final Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
