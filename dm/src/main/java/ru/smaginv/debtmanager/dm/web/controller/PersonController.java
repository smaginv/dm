package ru.smaginv.debtmanager.dm.web.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.smaginv.debtmanager.dm.message.Message;
import ru.smaginv.debtmanager.dm.message.MessageService;
import ru.smaginv.debtmanager.dm.service.person.PersonService;
import ru.smaginv.debtmanager.dm.web.AuthUser;
import ru.smaginv.debtmanager.dm.web.dto.contact.ContactSearchDto;
import ru.smaginv.debtmanager.dm.web.dto.person.*;

import javax.servlet.http.HttpServletRequest;
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
    private final MessageService messageService;

    @Autowired
    public PersonController(PersonService personService, MessageService messageService) {
        this.personService = personService;
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<PersonInfoDto> get(@AuthenticationPrincipal AuthUser authUser,
                                             @Valid @RequestBody PersonIdDto personIdDto,
                                             HttpServletRequest request) {
        log.info("get person by id: {}", personIdDto);
        PersonInfoDto personInfo = personService.get(authUser.getId(), personIdDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), personIdDto, personInfo);
        messageService.sendMessage(message);
        return ResponseEntity.ok(personInfo);
    }

    @GetMapping(
            value = "/by-contact"
    )
    public ResponseEntity<PersonInfoDto> getByContact(@AuthenticationPrincipal AuthUser authUser,
                                                      @Valid @RequestBody ContactSearchDto contactSearchDto,
                                                      HttpServletRequest request) {
        log.info("get person by contact {}", contactSearchDto);
        PersonInfoDto personInfo = personService.getByContact(authUser.getId(), contactSearchDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), contactSearchDto, personInfo);
        messageService.sendMessage(message);
        return ResponseEntity.ok(personInfo);
    }

    @GetMapping(
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<List<PersonDto>> getAll(@AuthenticationPrincipal AuthUser authUser,
                                                  HttpServletRequest request) {
        log.info("get all people");
        List<PersonDto> people = personService.getAll(authUser.getId());
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), RequestEntity.EMPTY, people);
        messageService.sendMessage(message);
        return ResponseEntity.ok(people);
    }

    @GetMapping(
            value = "/find"
    )
    public ResponseEntity<List<PersonDto>> find(@AuthenticationPrincipal AuthUser authUser,
                                                @RequestBody PersonSearchDto personSearchDto,
                                                HttpServletRequest request) {
        log.info("find people by {}", personSearchDto);
        List<PersonDto> people = personService.find(authUser.getId(), personSearchDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), personSearchDto, people);
        messageService.sendMessage(message);
        return ResponseEntity.ok(people);
    }

    @PutMapping
    public ResponseEntity<?> update(@AuthenticationPrincipal AuthUser authUser,
                                    @Valid @RequestBody PersonUpdateDto personUpdateDto,
                                    HttpServletRequest request) {
        log.info("update person: {}", personUpdateDto);
        personService.update(authUser.getId(), personUpdateDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), personUpdateDto, ResponseEntity.EMPTY);
        messageService.sendMessage(message);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<PersonDto> create(@AuthenticationPrincipal AuthUser authUser,
                                            @Valid @RequestBody PersonDto personDto,
                                            HttpServletRequest request) {
        log.info("create person: {}", personDto);
        PersonDto created = personService.create(authUser.getId(), personDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .build()
                .toUri();
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), personDto, created);
        messageService.sendMessage(message);
        return ResponseEntity.created(location).body(created);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@AuthenticationPrincipal AuthUser authUser,
                                    @Valid @RequestBody PersonIdDto personIdDto,
                                    HttpServletRequest request) {
        log.info("delete person: {}", personIdDto);
        personService.delete(authUser.getId(), personIdDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), personIdDto, ResponseEntity.EMPTY);
        messageService.sendMessage(message);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(
            value = "/by-contact"
    )
    public ResponseEntity<?> deleteByContact(@AuthenticationPrincipal AuthUser authUser,
                                             @Valid @RequestBody ContactSearchDto contactSearchDto,
                                             HttpServletRequest request) {
        log.info("delete person by contact: {}", contactSearchDto);
        personService.deleteByContact(authUser.getId(), contactSearchDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), contactSearchDto, ResponseEntity.EMPTY);
        messageService.sendMessage(message);
        return ResponseEntity.noContent().build();
    }
}
