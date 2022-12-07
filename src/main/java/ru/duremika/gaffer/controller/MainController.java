package ru.duremika.gaffer.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.duremika.gaffer.message.Message;
import ru.duremika.gaffer.service.MessageService;

@RestController
@RequestMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
@RequiredArgsConstructor
public class MainController {
    private final MessageService messageService;

    @PostMapping
    public Message main(@RequestBody Message message) {
        return messageService.handleMessageFromUser(message);
    }
}
