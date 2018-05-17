package org.vaadin.elmot.flow.sensors;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;

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
        geoLocation.addValueChangeListener( e->{
            Position position = e.getValue();
            labelPos.setText(position.toString());
            double dist = EARTH_RADIUS * Math.abs(position.getLatitude() * Math.PI / 180 );
            labelDist.setText(String.format("Your distance from equator is %.2f km (%.2f nm)",dist, dist/1.852));
        });
        geoLocation.addErrorListener( e->labelPos.setText(e.toString()));
        add(geoLocation,labelPos,labelDist);
    }
}
