package team_f.client.pages;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import team_f.client.helper.AlertHelper;
import team_f.client.pages.interfaces.BasePageControl;
import javax.lang.model.type.NullType;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public abstract class BasePage<R, V, L, S> extends BorderPane implements BasePageControl {
    /**
     * Extends the behaviour of the page without loosing the base functionality and without inheriting from BasePage.
     * It also separates the the action behaviour from the GUI components and does not allow to access the GUI components
     * so easily.
     *
     * Can be implemented by the page which inherits from it, but it's not necessary
     * e.g. NotImplemented can be thrown (not recommend nor necessary).
     * The standard behaviour doesn't call the attached event handler when it's not implemented.
     * (implemented actions should be documented in JavaDoc)
     */
    protected PageAction<Void, NullType> _initialize;
    protected PageAction<List<L>, S> _load;
    protected PageAction<R, V> _create;
    protected PageAction<R, V> _edit;
    protected PageAction<R, V> _update;
    protected PageAction<R, V> _delete;
    protected PageAction<R, V> _save;
    protected PageAction<Boolean, V> _exit;

    /**
     * sets the event handler for initializing the component (is only called once)
     * @param action event handler
     */
    public void setOnInitialize(PageAction<Void, NullType> action) {
        _initialize = action;
    }

    /**
     * sets the event handler for loading the component (is called on every page reload)
     * @param action event handler
     */
    public void setOnLoad(PageAction<List<L>, S> action) {
        _load = action;
    }

    /**
     * sets the event handler for creating an object in the component
     * @param action event handler
     */
    public void setOnCreate(PageAction<R, V> action) {
        _create = action;
    }

    /**
     * sets the event handler for editing an object in the component
     * @param action event handler
     */
    public void setOnEdit(PageAction<R, V> action) {
        _edit = action;
    }

    /**
     * sets the event handler for updating an object in the component
     * @param action event handler
     */
    public void setOnUpdate(PageAction<R,V> action) {
        _update = action;
    }

    /**
     * sets the event handler for deleting an object in the component
     * @param action event handler  return null for R when the object was deleted
     */
    public void setOnDelete(PageAction<R, V> action) {
        _delete = action;
    }

    /**
     * sets the event handler for saving an object in the component
     * @param action event handler
     */
    public void setOnSave(PageAction<R, V> action) {
        _save = action;
    }

    /**
     * sets the event handler for exiting the component
     * @param action event handler
     */
    public void setOnExit(PageAction<Boolean, V> action) {
        _exit = action;
    }

    protected void validate(ArrayList<TextField> fields) {
        for (TextField textField : fields) {
            if (textField.getText().isEmpty()) {
                textField.setStyle("-fx-border-color: red");
            } else {
                textField.setStyle("-fx-border-color: green");
            }
        }
    }

    public void showSuccessMessage(String headerText, String contentText) {
        showSuccessMessage(headerText, contentText);
    }

    public void showErrorMessage(String headerText, String contentText) {
        AlertHelper.showErrorMessage(headerText, contentText, this);
    }

    public Boolean showWarningMessage(String headerText, String contentText, String okButtonLabel) {
        return AlertHelper.showWarningMessage(headerText, contentText, okButtonLabel, this);
    }
}