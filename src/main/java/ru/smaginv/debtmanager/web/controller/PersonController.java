package ru.smaginv.debtmanager.web.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.smaginv.debtmanager.service.person.PersonService;
import ru.smaginv.debtmanager.util.validation.ValidationUtil;
import ru.smaginv.debtmanager.web.dto.contact.ContactSearchDto;
import ru.smaginv.debtmanager.web.dto.person.PersonDto;
import ru.smaginv.debtmanager.web.dto.person.PersonInfoDto;
import ru.smaginv.debtmanager.web.dto.person.PersonSearchDto;

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
public class PersonController {

    private final PersonService personService;
    private final ValidationUtil validationUtil;

    @Autowired
    public PersonController(PersonService personService, ValidationUtil validationUtil) {
        this.personService = personService;
        this.validationUtil = validationUtil;
    }

    @GetMapping(value = "/person/{personId}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<PersonInfoDto> get(@PathVariable Long personId) {
        log.info("get person by id: {}", personId);
        return ResponseEntity.ok(personService.get(personId));
    }

    @GetMapping(value = "/person/by-contact")
    public ResponseEntity<PersonInfoDto> getByContact(@Valid @RequestBody ContactSearchDto contactSearchDto) {
        validationUtil.validateContact(contactSearchDto);
        log.info("get person by contact {}", contactSearchDto);
        return ResponseEntity.ok(personService.getByContact(contactSearchDto));
    }

    @GetMapping(consumes = MediaType.ALL_VALUE)
    public ResponseEntity<List<PersonDto>> getAll() {
        log.info("get all people");
        return ResponseEntity.ok(personService.getAll());
    }

    @GetMapping(value = "/person/find")
    public ResponseEntity<List<PersonDto>> find(@RequestBody PersonSearchDto personSearchDto) {
        log.info("find people by {}", personSearchDto);
        return ResponseEntity.ok(personService.find(personSearchDto));
    }

    @PatchMapping(value = "/person/{personId}")
    public ResponseEntity<?> update(@Valid @RequestBody PersonDto personDto, @PathVariable String personId) {
        validationUtil.assureIdConsistent(personDto, personId);
        log.info("update person: {}, with id: {}", personDto, personId);
        personService.update(personDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/person")
    public ResponseEntity<PersonDto> create(@Valid @RequestBody PersonDto personDto) {
        validationUtil.checkIsNew(personDto);
        log.info("create person: {}", personDto);
        PersonDto created = personService.create(personDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{personId}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @DeleteMapping(value = "/person/{personId}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long personId) {
        log.info("delete person by id: {}", personId);
        personService.delete(personId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/person/by-contact")
    public ResponseEntity<?> deleteByContact(@Valid @RequestBody ContactSearchDto contactSearchDto) {
        validationUtil.validateContact(contactSearchDto);
        log.info("delete person by contact: {}", contactSearchDto);
        personService.deleteByContact(contactSearchDto);
        return ResponseEntity.noContent().build();
    }
}
