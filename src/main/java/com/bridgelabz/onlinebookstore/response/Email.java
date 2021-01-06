package com.bridgelabz.onlinebookstore.response;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;

    private String emailTo;
    private String emailFrom;
    private String subject;
    private String message;
}