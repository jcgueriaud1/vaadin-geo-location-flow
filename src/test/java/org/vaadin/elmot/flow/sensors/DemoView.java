package org.vaadin.elmot.flow.sensors;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;

@Route("")
public class DemoView extends Div {

    public DemoView() {
        GeoLocation geoLocation = new GeoLocation();
        geoLocation.setWatch(true);
        geoLocation.setHighAccuracy(true);
        geoLocation.setTimeout(100000);
        geoLocation.setMaxAge(200000);
        Label label = new Label();
        geoLocation.addValueChangeListener( e->label.setText(e.getValue().toString()));
        geoLocation.addErrorListener( e->label.setText(e.toString()));
        add(geoLocation,label);
    }
}
