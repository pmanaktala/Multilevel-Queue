package com.pmanaktala;

/**
 * The type Multi level queue exception.
 */
public class MultiLevelQueueException extends RuntimeException{

    /**
     * Instantiates a new Multi level queue exception.
     *
     * @param message the message
     */
    public MultiLevelQueueException(String message) {
        super(message);
    }


    /**
     * Instantiates a new Multi level queue exception.
     *
     * @param cause the cause
     */
    public MultiLevelQueueException(Throwable cause) {
        super(cause);
    }

}
