package com.garrow.coffeemachine.services;

import com.garrow.coffeemachine.utils.enums.ExceptionMessages;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class ExceptionMessagesService {

    private final MessageSource messages;

    public ExceptionMessagesService(@Qualifier("messageSource") MessageSource messages) {
        this.messages = messages;
    }

    public String getMessage(ExceptionMessages method, String... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messages.getMessage(method.getValue(), args, locale);
    }

    public String getMessage(ExceptionMessages method) {
        Locale locale = LocaleContextHolder.getLocale();
        return messages.getMessage(method.getValue(), null, locale);
    }
}