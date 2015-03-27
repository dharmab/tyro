package com.dharmab.tyro.server.requestfactory;

import com.dharmab.tyro.server.database.HasIdAndVersion;
import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.shared.Locator;
import org.hibernate.SessionFactory;

import java.io.Serializable;

/**
 * Locator which implements the various boilerplate locator methods.
 *
 * @param <E> The object's domain type
 * @param <I> The object's ID type
 * @param <V> The object's version type
 * @see com.google.web.bindery.requestfactory.shared.Locator
 */
public abstract class AbstractLocator<E extends HasIdAndVersion<I, V>, I extends Serializable, V extends Serializable> extends Locator<E, I> {
    private final SessionFactory sessionFactory;
    private final Class<E> domainType;
    private final Class<I> idType;

    /**
     * @param domainType     The entity's domain class
     * @param idType         The entity's ID class
     * @param sessionFactory Hibernate session factory used to retrieve entities
     */
    @Inject
    public AbstractLocator(Class<E> domainType, Class<I> idType, SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.domainType = domainType;
        this.idType = idType;
    }

    @Override
    public E create(Class<? extends E> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public E find(Class<? extends E> clazz, I id) {
        @SuppressWarnings("unchecked") E entity = (E) sessionFactory.getCurrentSession().get(clazz, id);
        return entity;
    }

    @Override
    public final Class<E> getDomainType() {
        return domainType;
    }

    @Override
    public final I getId(E domainObject) {
        return domainObject.getId();
    }

    @Override
    public final Class<I> getIdType() {
        return idType;
    }

    @Override
    public final V getVersion(E domainObject) {
        return domainObject.getVersion();
    }
}
