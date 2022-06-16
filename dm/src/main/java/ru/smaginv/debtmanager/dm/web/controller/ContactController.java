package ru.smaginv.debtmanager.dm.web.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
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
import java.net.URI;
import java.util.List;

import static ru.smaginv.debtmanager.dm.util.AppUtil.NO_BODY;

@Log4j2
@RestController
@RequestMapping(
        value = "/contacts",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ContactController {

    private final ContactService contactService;
    private final MessageService messageService;

    @Autowired
    public ContactController(ContactService contactService, MessageService messageService) {
        this.contactService = contactService;
        this.messageService = messageService;
    }

    @GetMapping
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

    @GetMapping(
            value = "/by-person"
    )
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
    public ResponseEntity<List<ContactDto>> getAll(@AuthenticationPrincipal AuthUser authUser,
                                                   HttpServletRequest request) {
        log.info("get all contacts");
        List<ContactDto> contacts = contactService.getAll(authUser.getId());
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), NO_BODY, contacts);
        messageService.sendMessage(message);
        return ResponseEntity.ok(contacts);
    }

    @PutMapping
    public ResponseEntity<?> update(@AuthenticationPrincipal AuthUser authUser,
                                    @Valid @RequestBody ContactUpdateDto contactUpdateDto,
                                    HttpServletRequest request) {
        log.info("update contact: {}", contactUpdateDto);
        contactService.update(authUser.getId(), contactUpdateDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), contactUpdateDto, NO_BODY);
        messageService.sendMessage(message);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<ContactDto> create(@AuthenticationPrincipal AuthUser authUser,
                                             @Valid @RequestBody ContactDto contactDto,
                                             HttpServletRequest request) {
        log.info("create contact: {}", contactDto);
        ContactDto created = contactService.create(authUser.getId(), contactDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .build()
                .toUri();
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), contactDto, created);
        messageService.sendMessage(message);
        return ResponseEntity.created(location).body(created);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@AuthenticationPrincipal AuthUser authUser,
                                    @Valid @RequestBody ContactIdDto contactIdDto,
                                    HttpServletRequest request) {
        log.info("delete contact with id: {}", contactIdDto);
        contactService.delete(authUser.getId(), contactIdDto);
        Message message = new Message(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), contactIdDto, NO_BODY);
        messageService.sendMessage(message);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(
            value = "/by-person"
    )
    public ResponseEntity<?> deleteAllByPerson(@AuthenticationPrincipal AuthUser authUser,
                                               @Valid @RequestBody PersonIdDto personIdDto,
                                               HttpServletRequest request) {
        log.info("delete all contacts for person: {}", personIdDto);
        contactService.deleteAllByPerson(authUser.getId(), personIdDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), personIdDto, NO_BODY);
        messageService.sendMessage(message);
        return ResponseEntity.noContent().build();
    }
}
