package org.vaadin.elmot.flow.sensors;

import com.vaadin.flow.component.HasValue;

public class PositionValueChangeEvent implements HasValue.ValueChangeEvent<Position> {

    private final Position oldPosition;
    private final Position position;
    private final GeoLocation src;

    public PositionValueChangeEvent(Position oldPosition, Position position, GeoLocation src) {
        this.oldPosition = oldPosition;
        this.position = position;
        this.src = src;
    }

    @Override
    public HasValue<?, Position> getHasValue() {
        return src;
    }

    @Override
    public boolean isFromClient() {
        return true;
    }

    @Override
    public Position getOldValue() {
        return oldPosition;
    }

    @Override
    public Position getValue() {
        return position;
    }
}
