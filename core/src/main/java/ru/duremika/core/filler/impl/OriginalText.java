package ru.duremika.core.filler.impl;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.RequiredArgsConstructor;
import ru.duremika.core.dto.UserState;
import ru.duremika.core.filler.Filler;
import ru.duremika.core.message.impl.MessageFromUser;

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
