package com.garrow.coffeemachine.services.interfaces;

import com.garrow.coffeemachine.utils.exceptions.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

/**
 * The JpaService interface defines common CRUD operations that can be performed
 * on JPA entities, with transactional support.
 *
 * @param <E>  the entity type managed by the service
 * @param <ID> the type of the entity's identifier
 */
@Transactional(readOnly = true)
public interface JpaService<E, ID> {

    /**
     * Finds an entity by its identifier.
     *
     * @param id the identifier of the entity to find
     * @return the entity if found
     * @throws NotFoundException if the entity with the given ID is not found
     */
    E findById(ID id) throws NotFoundException;

    /**
     * Checks if an entity with the given identifier exists.
     *
     * @param id the identifier of the entity to check
     * @throws NotFoundException if the entity with the given ID is not found
     */
    void existsById(ID id) throws NotFoundException;

    /**
     * Creates a new entity.
     *
     * @param entity the entity to create
     * @return the created entity
     */
    @Transactional
    E create(E entity);

    /**
     * Updates an existing entity identified by the given ID.
     *
     * @param id the identifier of the entity to update
     * @param entity the updated entity object
     * @return the updated entity
     * @throws NotFoundException if the entity with the given ID is not found
     */
    @Transactional
    E update(ID id, E entity) throws NotFoundException;

    /**
     * Deletes an entity by its identifier.
     *
     * @param id the identifier of the entity to delete
     * @throws NotFoundException if the entity with the given ID is not found
     */
    @Transactional
    void deleteById(ID id) throws NotFoundException;
}
