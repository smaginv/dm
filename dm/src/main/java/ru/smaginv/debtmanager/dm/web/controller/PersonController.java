package ru.smaginv.debtmanager.dm.web.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.smaginv.debtmanager.dm.service.person.PersonService;
import ru.smaginv.debtmanager.dm.web.AuthUser;
import ru.smaginv.debtmanager.dm.web.dto.contact.ContactSearchDto;
import ru.smaginv.debtmanager.dm.web.dto.person.*;

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

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<PersonInfoDto> get(@AuthenticationPrincipal AuthUser authUser,
                                             @Valid @RequestBody PersonIdDto personIdDto) {
        log.info("get person by id: {}", personIdDto);
        return ResponseEntity.ok(personService.get(authUser.getId(), personIdDto));
    }

    @GetMapping(
            value = "/by-contact"
    )
    public ResponseEntity<PersonInfoDto> getByContact(@AuthenticationPrincipal AuthUser authUser,
                                                      @Valid @RequestBody ContactSearchDto contactSearchDto) {
        log.info("get person by contact {}", contactSearchDto);
        return ResponseEntity.ok(personService.getByContact(authUser.getId(), contactSearchDto));
    }

    @GetMapping(
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<List<PersonDto>> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get all people");
        return ResponseEntity.ok(personService.getAll(authUser.getId()));
    }

    @GetMapping(
            value = "/find"
    )
    public ResponseEntity<List<PersonDto>> find(@AuthenticationPrincipal AuthUser authUser,
                                                @RequestBody PersonSearchDto personSearchDto) {
        log.info("find people by {}", personSearchDto);
        return ResponseEntity.ok(personService.find(authUser.getId(), personSearchDto));
    }

    @PutMapping
    public ResponseEntity<?> update(@AuthenticationPrincipal AuthUser authUser,
                                    @Valid @RequestBody PersonUpdateDto personUpdateDto) {
        log.info("update person: {}", personUpdateDto);
        personService.update(authUser.getId(), personUpdateDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<PersonDto> create(@AuthenticationPrincipal AuthUser authUser,
                                            @Valid @RequestBody PersonDto personDto) {
        log.info("create person: {}", personDto);
        PersonDto created = personService.create(authUser.getId(), personDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .build()
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@AuthenticationPrincipal AuthUser authUser,
                                    @Valid @RequestBody PersonIdDto personIdDto) {
        log.info("delete person: {}", personIdDto);
        personService.delete(authUser.getId(), personIdDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(
            value = "/by-contact"
    )
    public ResponseEntity<?> deleteByContact(@AuthenticationPrincipal AuthUser authUser,
                                             @Valid @RequestBody ContactSearchDto contactSearchDto) {
        log.info("delete person by contact: {}", contactSearchDto);
        personService.deleteByContact(authUser.getId(), contactSearchDto);
        return ResponseEntity.noContent().build();
    }
}
