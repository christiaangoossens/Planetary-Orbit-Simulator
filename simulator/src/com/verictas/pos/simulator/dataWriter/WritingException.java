package com.verictas.pos.simulator.dataWriter;

public class WritingException extends Exception {
    public WritingException() { super(); }
    public WritingException(String message) { super(message); }
    public WritingException(String message, Throwable cause) { super(message, cause); }
    public WritingException(Throwable cause) { super(cause); }
}
