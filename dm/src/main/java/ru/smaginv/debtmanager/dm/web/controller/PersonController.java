package ru.smaginv.debtmanager.dm.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.smaginv.debtmanager.dm.message.Message;
import ru.smaginv.debtmanager.dm.message.MessageService;
import ru.smaginv.debtmanager.dm.service.person.PersonService;
import ru.smaginv.debtmanager.dm.web.AuthUser;
import ru.smaginv.debtmanager.dm.web.dto.contact.ContactSearchDto;
import ru.smaginv.debtmanager.dm.web.dto.person.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Log4j2
@RestController
@RequestMapping(
        value = "/people",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@Tag(name = "Person controller")
public class PersonController {

    private final PersonService personService;
    private final MessageService messageService;

    @Autowired
    public PersonController(PersonService personService, MessageService messageService) {
        this.personService = personService;
        this.messageService = messageService;
    }

    @PostMapping(
            value = "/by-id/get"
    )
    @Operation(summary = "Get person by id")
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

    @PostMapping(
            value = "/by-contact/get"
    )
    @Operation(summary = "Get person by contact")
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
    @Operation(summary = "Get all people")
    public ResponseEntity<List<PersonDto>> getAll(@AuthenticationPrincipal AuthUser authUser,
                                                  HttpServletRequest request) {
        log.info("get all people");
        List<PersonDto> people = personService.getAll(authUser.getId());
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), RequestEntity.EMPTY, people);
        messageService.sendMessage(message);
        return ResponseEntity.ok(people);
    }

    @PostMapping(
            value = "/find"
    )
    @Operation(summary = "Find people")
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
    @Operation(summary = "Update person")
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
    @Operation(summary = "Create person")
    public ResponseEntity<PersonDto> create(@AuthenticationPrincipal AuthUser authUser,
                                            @Valid @RequestBody PersonDto personDto,
                                            HttpServletRequest request) {
        log.info("create person: {}", personDto);
        PersonDto created = personService.create(authUser.getId(), personDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), personDto, created);
        messageService.sendMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping(
            value = "/by-id/delete"
    )
    @Operation(summary = "Delete person by id")
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

    @PostMapping(
            value = "/by-contact/delete"
    )
    @Operation(summary = "Delete person by contact")
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
