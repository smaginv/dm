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
import ru.smaginv.debtmanager.dm.service.contact.ContactService;
import ru.smaginv.debtmanager.dm.web.AuthUser;
import ru.smaginv.debtmanager.dm.web.dto.contact.ContactDto;
import ru.smaginv.debtmanager.dm.web.dto.contact.ContactIdDto;
import ru.smaginv.debtmanager.dm.web.dto.contact.ContactUpdateDto;
import ru.smaginv.debtmanager.dm.web.dto.person.PersonIdDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Log4j2
@RestController
@RequestMapping(
        value = "/contacts",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@Tag(name = "Contact controller")
public class ContactController {

    private final ContactService contactService;
    private final MessageService messageService;

    @Autowired
    public ContactController(ContactService contactService, MessageService messageService) {
        this.contactService = contactService;
        this.messageService = messageService;
    }

    @PostMapping(
            value = "/by-id/get"
    )
    @Operation(summary = "Get contact by id")
    public ResponseEntity<ContactDto> get(@AuthenticationPrincipal AuthUser authUser,
                                          @Valid @RequestBody ContactIdDto contactIdDto,
                                          HttpServletRequest request) {
        log.info("get contact: {}", contactIdDto);
        ContactDto contact = contactService.get(authUser.getId(), contactIdDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), contactIdDto, contact);
        messageService.sendMessage(message);
        return ResponseEntity.ok(contact);
    }

    @PostMapping(
            value = "/by-person/get"
    )
    @Operation(summary = "Get all contacts by person")
    public ResponseEntity<List<ContactDto>> getAllByPerson(@AuthenticationPrincipal AuthUser authUser,
                                                           @Valid @RequestBody PersonIdDto personIdDto,
                                                           HttpServletRequest request) {
        log.info("get all contacts for person: {}", personIdDto);
        List<ContactDto> contacts = contactService.getAllByPerson(authUser.getId(), personIdDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), personIdDto, contacts);
        messageService.sendMessage(message);
        return ResponseEntity.ok(contacts);
    }

    @GetMapping(
            consumes = MediaType.ALL_VALUE
    )
    @Operation(summary = "Get all contacts")
    public ResponseEntity<List<ContactDto>> getAll(@AuthenticationPrincipal AuthUser authUser,
                                                   HttpServletRequest request) {
        log.info("get all contacts");
        List<ContactDto> contacts = contactService.getAll(authUser.getId());
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), RequestEntity.EMPTY, contacts);
        messageService.sendMessage(message);
        return ResponseEntity.ok(contacts);
    }

    @PutMapping
    @Operation(summary = "Update contact")
    public ResponseEntity<?> update(@AuthenticationPrincipal AuthUser authUser,
                                    @Valid @RequestBody ContactUpdateDto contactUpdateDto,
                                    HttpServletRequest request) {
        log.info("update contact: {}", contactUpdateDto);
        contactService.update(authUser.getId(), contactUpdateDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), contactUpdateDto, ResponseEntity.EMPTY);
        messageService.sendMessage(message);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @Operation(summary = "Create contact")
    public ResponseEntity<ContactDto> create(@AuthenticationPrincipal AuthUser authUser,
                                             @Valid @RequestBody ContactDto contactDto,
                                             HttpServletRequest request) {
        log.info("create contact: {}", contactDto);
        ContactDto created = contactService.create(authUser.getId(), contactDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), contactDto, created);
        messageService.sendMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping(
            value = "/by-id/delete"
    )
    @Operation(summary = "Delete contact by id")
    public ResponseEntity<?> delete(@AuthenticationPrincipal AuthUser authUser,
                                    @Valid @RequestBody ContactIdDto contactIdDto,
                                    HttpServletRequest request) {
        log.info("delete contact with id: {}", contactIdDto);
        contactService.delete(authUser.getId(), contactIdDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), contactIdDto, ResponseEntity.EMPTY);
        messageService.sendMessage(message);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(
            value = "/by-person/delete"
    )
    @Operation(summary = "Delete all contacts by person")
    public ResponseEntity<?> deleteAllByPerson(@AuthenticationPrincipal AuthUser authUser,
                                               @Valid @RequestBody PersonIdDto personIdDto,
                                               HttpServletRequest request) {
        log.info("delete all contacts for person: {}", personIdDto);
        contactService.deleteAllByPerson(authUser.getId(), personIdDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), personIdDto, ResponseEntity.EMPTY);
        messageService.sendMessage(message);
        return ResponseEntity.noContent().build();
    }
}
