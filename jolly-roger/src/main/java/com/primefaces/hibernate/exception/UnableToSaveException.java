package com.primefaces.hibernate.exception;

import org.hibernate.HibernateException;

public class UnableToSaveException extends HibernateException {
    
    public UnableToSaveException(String message) {
        super(message);
    }
    
    public UnableToSaveException(Throwable t) {
        super(t);
    }
    
    public UnableToSaveException(String message, Throwable t) {
        super(message, t);
    }
}
