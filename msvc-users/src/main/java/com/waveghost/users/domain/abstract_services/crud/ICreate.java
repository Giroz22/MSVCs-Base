package com.waveghost.users.domain.abstract_services.crud;

public interface ICreate<T> {
    T create(T entity);
}
