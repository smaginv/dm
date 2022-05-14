package ru.smaginv.debtmanager.entity;

public interface HasId<T> {

    T getId();

    boolean isNew();
}
