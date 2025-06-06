package com.waveghost.users.domain.abstract_services.crud;

public interface IGetById<T, ID> {
    T getById(ID id);
}
