package com.silencedut.router;

/**
 * Created by SilenceDut on 16/8/1.
 */

public class RouterException extends RuntimeException {

    public RouterException(String detailMessage ) {
        super(detailMessage);
    }

    public RouterException(Throwable throwable ) {
        super(throwable);
    }

    public RouterException(String detailMessage, Throwable throwable ) {
        super(detailMessage,throwable);
    }
}
