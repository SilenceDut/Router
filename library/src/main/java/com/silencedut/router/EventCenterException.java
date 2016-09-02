package com.silencedut.router;

/**
 * Created by SilenceDut on 16/8/31.
 */

public class EventCenterException extends RuntimeException {

    public EventCenterException(String detailMessage ) {
        super(detailMessage);
    }

    public EventCenterException( Throwable throwable ) {
        super(throwable);
    }

    public EventCenterException(String detailMessage, Throwable throwable ) {
        super(detailMessage,throwable);
    }
}
