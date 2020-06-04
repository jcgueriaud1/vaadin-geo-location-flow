package org.vaadin.elmot.flow.sensors;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.Route;

@SuppressWarnings("unused")
@Route("")
public class DemoView extends Div {

    private static final int EARTH_RADIUS = 6371;

    public DemoView() {
        GeoLocation geoLocation = new GeoLocation();
        geoLocation.setWatch(true);
        geoLocation.setHighAccuracy(true);
        geoLocation.setTimeout(100000);
        geoLocation.setMaxAge(200000);
        Div labelPos = new Div();
        Div labelDist = new Div();
        Anchor anchor = new Anchor();
        geoLocation.addValueChangeListener(e -> {
            Position position = e.getValue();
            labelPos.setText(position.toString());
            double dist = EARTH_RADIUS * Math.abs(position.getLatitude() * Math.PI / 180);
            labelDist.setText(String.format("Your distance from equator is %.2f km (%.2f nm)", dist, dist / 1.852));
            anchor.setHref(String.format(
                    "https://www.openstreetmap.org/#map=7/%.2f/%.2f"
                    , position.getLatitude(), position.getLongitude()));
            anchor.setText("View on OpenStreetMap");
        });
        geoLocation.addErrorListener(e -> labelPos.setText(e.toString()));
        add(geoLocation, labelPos, labelDist,
                new Div(new Label("Somewhere here: ")),
                anchor);
    }
}
