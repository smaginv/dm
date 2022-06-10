package ru.smaginv.debtmanager.web.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.smaginv.debtmanager.service.contact.ContactService;
import ru.smaginv.debtmanager.web.dto.contact.ContactDto;
import ru.smaginv.debtmanager.web.dto.contact.ContactIdDto;
import ru.smaginv.debtmanager.web.dto.contact.ContactUpdateDto;
import ru.smaginv.debtmanager.web.dto.person.PersonIdDto;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Log4j2
@RestController
@RequestMapping(
        value = "/contacts",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public ResponseEntity<ContactDto> get(@Valid @RequestBody ContactIdDto contactIdDto) {
        log.info("get contact: {}", contactIdDto);
        return ResponseEntity.ok(contactService.get(contactIdDto));
    }

    @GetMapping(
            value = "/by-person"
    )
    public ResponseEntity<List<ContactDto>> getAllByPerson(@Valid @RequestBody PersonIdDto personIdDto) {
        log.info("get all contacts for person: {}", personIdDto);
        return ResponseEntity.ok(contactService.getAllByPerson(personIdDto));
    }

    @GetMapping(
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<List<ContactDto>> getAll() {
        log.info("get all contacts");
        return ResponseEntity.ok(contactService.getAll());
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody ContactUpdateDto contactUpdateDto) {
        log.info("update contact: {}", contactUpdateDto);
        contactService.update(contactUpdateDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<ContactDto> create(@Valid @RequestBody ContactDto contactDto) {
        log.info("create contact: {}", contactDto);
        ContactDto created = contactService.create(contactDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .build()
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@Valid @RequestBody ContactIdDto contactIdDto) {
        log.info("delete contact with id: {}", contactIdDto);
        contactService.delete(contactIdDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(
            value = "/by-person"
    )
    public ResponseEntity<?> deleteAllByPerson(@Valid @RequestBody PersonIdDto personIdDto) {
        log.info("delete all contacts for person: {}", personIdDto);
        contactService.deleteAllByPerson(personIdDto);
        return ResponseEntity.noContent().build();
    }
}
