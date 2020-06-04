package org.vaadin.elmot.flow.sensors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.dom.DomEventListener;
import com.vaadin.flow.dom.DomListenerRegistration;
import com.vaadin.flow.shared.Registration;

import elemental.json.JsonObject;
import elemental.json.JsonValue;

@Tag("vaadin-geo-location")
@JsModule("./vaadin-geo-location.js")
public class GeoLocation extends Component implements HasValue<PositionValueChangeEvent, Position> {

    public static final String EVENT_DETAIL_CODE = "event.detail.code";
    public static final String EVENT_DETAIL_MESSAGE = "event.detail.message";
    private static final String ATTR_HIGH_ACCURACY = "high-accuracy";
    private static final String ATTR_TIMEOUT = "timeout";
    private static final String ATTR_MAX_AGE = "max-age";
    private static final String ATTR_WATCH = "watch";
    private static Map<String, BiConsumer<JsonValue, Position>> propertyMappers;

    static {
        propertyMappers = new ConcurrentHashMap<>();
        propertyMappers.put("event.detail.coords.latitude", (v, pos) -> pos.setLatitude(v.asNumber()));
        propertyMappers.put("event.detail.coords.longitude", (v, pos) -> pos.setLongitude(v.asNumber()));
        propertyMappers.put("event.detail.coords.altitude", (v, pos) -> pos.setAltitude(v.asNumber()));
        propertyMappers.put("event.detail.coords.accuracy", (v, pos) -> pos.setAccuracy(v.asNumber()));
        propertyMappers.put("event.detail.coords.altitudeAccuracy", (v, pos) -> pos.setAltitudeAccuracy(v.asNumber()));
        propertyMappers.put("event.detail.coords.heading", (v, pos) -> pos.setHeading(v.asNumber()));
        propertyMappers.put("event.detail.coords.speed", (v, pos) -> pos.setSpeed(v.asNumber()));
        propertyMappers.put("event.detail.timestamp", (v, pos) -> pos.setTimestamp((long) v.asNumber()));

    }

    private List<ValueChangeListener<? super PositionValueChangeEvent>> changeListeners = new ArrayList<>();
    private List<PositionErrorListener> errorListeners = new ArrayList<>();
    private Position lastPosition;
    //todo error

    public GeoLocation() {
        DomListenerRegistration locationRegistration = getElement().addEventListener("location", (DomEventListener) event -> {
            Position position = new Position();
            for (Map.Entry<String, BiConsumer<JsonValue, Position>> entry : propertyMappers.entrySet()) {
                JsonValue jsonValue = event.getEventData().get(entry.getKey());
                if (jsonValue != null) {
                    entry.getValue().accept(jsonValue, position);
                }
            }
            PositionValueChangeEvent e = new PositionValueChangeEvent(lastPosition, position, GeoLocation.this);
            lastPosition = position;
            changeListeners.forEach(listener -> listener.valueChanged(e));
        });
        propertyMappers.keySet().forEach(locationRegistration::addEventData);

        DomListenerRegistration errorRegistration = getElement().addEventListener("error", event -> {
            JsonObject eventData = event.getEventData();
            PositionErrorEvent positionErrorEvent = new PositionErrorEvent(
                    (int) eventData.getNumber(EVENT_DETAIL_CODE), eventData.getString(EVENT_DETAIL_MESSAGE));
            errorListeners.forEach(listener -> listener.onError(positionErrorEvent));
        });
        errorRegistration.addEventData(EVENT_DETAIL_CODE);
        errorRegistration.addEventData(EVENT_DETAIL_MESSAGE);
    }

    public boolean isHighAccuracy() {
        return "true".equalsIgnoreCase(getElement().getProperty(ATTR_HIGH_ACCURACY));
    }

    public void setHighAccuracy(boolean highAccuracy) {
        getElement().setAttribute(ATTR_HIGH_ACCURACY, highAccuracy);
    }

    public int getTimeout() {
        return getElement().getProperty(ATTR_TIMEOUT, 0);
    }

    public void setTimeout(int timeout) {
        getElement().setAttribute(ATTR_TIMEOUT, "" + timeout);
    }

    public int getMaxAge() {
        return getElement().getProperty(ATTR_MAX_AGE, 0);
    }

    public void setMaxAge(int maxAge) {
        getElement().setAttribute(ATTR_MAX_AGE, "" + maxAge);
    }

    public boolean isWatch() {
        return "true".equalsIgnoreCase(getElement().getProperty(ATTR_WATCH));
    }

    public void setWatch(boolean watch) {
        getElement().setAttribute(ATTR_WATCH, watch);
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        if (readOnly) throw new IllegalArgumentException("Always readonly");
    }

    @Override
    public boolean isRequiredIndicatorVisible() {
        return false;
    }

    @Override
    public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
        if (requiredIndicatorVisible)
            throw new IllegalArgumentException("Required Indicator is not supported");
    }

    @Override
    public Registration addValueChangeListener(ValueChangeListener<? super PositionValueChangeEvent> listener) {
        changeListeners.add(listener);
        return () -> changeListeners.remove(listener);
    }

    public Registration addErrorListener(PositionErrorListener listener) {
        errorListeners.add(listener);
        return () -> errorListeners.remove(listener);
    }

    @Override
    public Position getValue() {
        return lastPosition;
    }

    @Override
    public void setValue(Position value) {
        throw new UnsupportedOperationException("Read-only element");
    }

}
