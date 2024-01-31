package com.korea.MOVIEBOOK.customerSupport;

import lombok.Getter;

@Getter
public enum Category {
    QUESTION("QUESTION"),
    NOTICE("NOTICE"),
    FAQ("FAQ"),
    MYQUESTION("MYQUESTION");

    private String value;

    Category(String value) {
        this.value = value;
    }
}
