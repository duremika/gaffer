package ru.duremika.gaffer.filler.impl;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.RequiredArgsConstructor;
import ru.duremika.gaffer.dto.UserState;
import ru.duremika.gaffer.filler.Filler;
import ru.duremika.gaffer.message.impl.MessageFromUser;

@RequiredArgsConstructor
@JsonTypeName("original_text")
public class OriginalText implements Filler {
    @Override
    public Object fill(UserState userState) {
        MessageFromUser messageFromUser = (MessageFromUser) userState.getMessage();
        return messageFromUser.getText();
    }

    @Override
    public String toString() {
        return "OriginalText";
    }
}
