package ru.smaginv.debtmanager.dm.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface HasIdDto {

    String getId();

    void setId(String id);

    @JsonIgnore
    boolean isNew();
}
