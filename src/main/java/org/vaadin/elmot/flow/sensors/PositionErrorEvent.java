package org.vaadin.elmot.flow.sensors;

import java.io.Serializable;

public class PositionErrorEvent implements Serializable {
    public static final int PERMISSION_DENIED = 1;
    public static final int POSITION_UNAVAILABLE = 2;
    public static final int TIMEOUT = 3;
    final private int code;
    final private String message;

    public PositionErrorEvent(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "PositionErrorEvent{" + code +
                ": " + message + '}';
    }
}
