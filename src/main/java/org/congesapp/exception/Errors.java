package org.congesapp.exception;

public enum Errors {
    ;

    private Integer num;
    private String name;
    private String message;

    Errors(Integer num, String name, String message) {
        this.num = num;
        this.name = name;
        this.message = message;


    }

    public Integer getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

}
