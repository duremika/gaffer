package ru.duremika.gaffer.controller;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.duremika.gaffer.message.Message;
import ru.duremika.gaffer.message.impl.MessageToUser;

@RestController
@RequestMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public class MainController {
    @PostMapping
    public Message messageFromUser(@RequestBody Message message) {
        final MessageToUser answer = new MessageToUser("answer");
        return answer;
    }
}
