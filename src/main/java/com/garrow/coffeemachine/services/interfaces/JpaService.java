package com.garrow.coffeemachine.services.interfaces;

import com.garrow.coffeemachine.utils.exceptions.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

public interface JpaService<E, ID> {
    E findById(ID id) throws NotFoundException;

    void existsById(ID id) throws NotFoundException;

    @Transactional
    E create(E entity);

    @Transactional
    E update(E entity);

    @Transactional
    void deleteById(ID id);
}
