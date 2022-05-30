package ru.smaginv.debtmanager.web.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.smaginv.debtmanager.service.contact.ContactService;
import ru.smaginv.debtmanager.util.validation.ValidationUtil;
import ru.smaginv.debtmanager.web.dto.contact.ContactDto;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Log4j2
@RestController
@RequestMapping(
        value = "/people",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ContactController {

    private final ContactService contactService;
    private final ValidationUtil validationUtil;

    @Autowired
    public ContactController(ContactService contactService, ValidationUtil validationUtil) {
        this.contactService = contactService;
        this.validationUtil = validationUtil;
    }

    @GetMapping(
            value = "/person/{personId}/contacts/{contactId}",
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<ContactDto> get(@PathVariable Long personId,
                                          @PathVariable Long contactId) {
        log.info("get contact by id: {} for person: {}", contactId, personId);
        return ResponseEntity.ok(contactService.get(personId, contactId));
    }

    @GetMapping(
            value = "/person/{personId}/contacts",
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<List<ContactDto>> getAllByPerson(@PathVariable Long personId) {
        log.info("get all contacts for person: {}", personId);
        return ResponseEntity.ok(contactService.getAllByPerson(personId));
    }

    @GetMapping(
            value = "/contacts",
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<List<ContactDto>> getAll() {
        log.info("get all contacts");
        return ResponseEntity.ok(contactService.getAll());
    }

    @PutMapping(
            value = "/person/{personId}/contacts/{contactId}"
    )
    public ResponseEntity<?> update(@PathVariable Long personId,
                                    @PathVariable Long contactId,
                                    @Valid @RequestBody ContactDto contactDto) {
        validationUtil.assureIdConsistent(contactDto, String.valueOf(contactId));
        log.info("update contact: {}, with id: {}", contactDto, contactId);
        contactService.update(personId, contactDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(
            value = "/person/{personId}/contacts"
    )
    public ResponseEntity<ContactDto> create(@PathVariable Long personId,
                                             @Valid @RequestBody ContactDto contactDto) {
        validationUtil.checkIsNew(contactDto);
        log.info("create contact: {}, for person: {}", contactDto, personId);
        ContactDto created = contactService.create(personId, contactDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{contactId}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @DeleteMapping(
            value = "/person/{personId}/contacts/{contactId}",
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<?> delete(@PathVariable Long personId,
                                    @PathVariable Long contactId) {
        log.info("delete contact with id: {}, for person: {}", contactId, personId);
        contactService.delete(personId, contactId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(
            value = "/person/{personId}/contacts",
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<?> deleteAllByPerson(@PathVariable Long personId) {
        log.info("delete all contacts for person: {}", personId);
        contactService.deleteAllByPerson(personId);
        return ResponseEntity.noContent().build();
    }
}
