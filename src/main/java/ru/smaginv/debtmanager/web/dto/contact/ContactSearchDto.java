package ru.smaginv.debtmanager.web.dto.contact;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.ToString;

@ToString(callSuper = true)
@JsonPropertyOrder({"type", "value"})
public class ContactSearchDto extends AbstractContactDto {
}
