package com.waveghost.users.domain.abstract_services.crud;

public interface IUpdate<T, ID> {
    T update(ID id, T entity);
}
