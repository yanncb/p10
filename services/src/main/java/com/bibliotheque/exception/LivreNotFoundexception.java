package com.bibliotheque.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LivreNotFoundexception  extends RuntimeException{

    public LivreNotFoundexception(String message){super(message);}
}
