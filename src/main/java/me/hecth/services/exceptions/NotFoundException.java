package me.hecth.services.exceptions;

public class NotFoundException extends BusinessException{
    public NotFoundException() {
        super("Resource not found");
    }
}

