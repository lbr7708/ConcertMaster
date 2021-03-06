package team_f.client.controls.slider;

import javafx.scene.transform.Scale;
import team_f.client.pages.PageAction;
import java.net.URL;

public class Slider extends javafx.scene.control.Slider {
    private Scale _scale = null;
    private PageAction<Boolean, Scale> _scaleAction;


    public Slider() {
        final URL Style = ClassLoader.getSystemResource("style/stylesheetSlider.css");
        getStylesheets().add(Style.toString());
        setMaxWidth(100);
        setMin(0.5);
        setMax(2);
        setValue(1);
        setShowTickLabels(true);
        setShowTickMarks(true);
        setMajorTickUnit(0.25);
        setMinorTickCount(1);
        setBlockIncrement(0.025);

        valueProperty().addListener((observable, oldValue, newValue) -> {
            _scale = new Scale(newValue.doubleValue() + 0.018, newValue.doubleValue() + 0.018);
            _scale.setPivotX(0);
            _scale.setPivotY(0);

            scale();
        });
    }

    public void setScaleAction(PageAction<Boolean, Scale> scaleAction) {
        _scaleAction = scaleAction;
    }

    public void refresh() {
        scale();
    }

    private void scale() {
        if(_scaleAction != null) {
            _scaleAction.doAction(_scale);
        }
    }
}
