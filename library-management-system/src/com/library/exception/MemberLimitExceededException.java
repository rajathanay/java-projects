package com.library.exception;

public class MemberLimitExceededException
        extends Exception {

    public MemberLimitExceededException(
            String message
    ) {

        super(message);
    }
}