package team_f.client.pages.musicalwork;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import jfxtras.labs.scene.control.BigDecimalField;
import team_f.client.controls.numberfield.NumberField;
import team_f.client.converter.MusicalWorkConverter;
import team_f.client.entities.KeyValuePair;
import team_f.client.exceptions.NumberRangeException;
import team_f.client.helper.ErrorMessageHelper;
import team_f.client.helper.gui.InstrumentationHelper;
import team_f.client.helper.gui.SpecialInstrumentationEntity;
import team_f.client.pages.BaseTablePage;
import team_f.jsonconnector.entities.*;
import team_f.jsonconnector.entities.Error;
import team_f.jsonconnector.entities.special.errorlist.MusicalWorkErrorList;
import team_f.jsonconnector.enums.SectionGroupType;
import team_f.jsonconnector.enums.properties.MusicalWorkProperty;
import team_f.jsonconnector.interfaces.JSONObjectEntity;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MusicalWorkManagement extends BaseTablePage<MusicalWorkErrorList, MusicalWork, MusicalWork, MusicalWork, MusicalWorkParameter> {
    private TextField _nameField;
    private TextField _composerField;
    private TableView<MusicalWork> _workTable;
    private List<SpecialInstrumentationEntity> _specialInstrumentationEntityList;

    //String
    private NumberField _firstViolinField;
    private NumberField _secondViolinField;
    private NumberField _violaField;
    private NumberField _violoncelloField;
    private NumberField _doublebassField;

    //Wood
    private NumberField _fluteField;
    private NumberField _oboeField;
    private NumberField _clarinetField;
    private NumberField _bassoonField;

    //Brass
    private NumberField _hornField;
    private NumberField _trumpetField;
    private NumberField _tromboneField;
    private NumberField _tubeField;

    //Percussion
    private NumberField _kettledrumField;
    private NumberField _percussionField;
    private NumberField _harpField;
    private HBox _textfields;
    private List<BigDecimalField> _fields;

    //SpecialInstrumentation
    private ScrollPane _specialInstrumentationPane;
    private GridPane _specialInstrumentationContent;
    private ComboBox<KeyValuePair> _specialInstrumentationSectionGroupComboBox;
    private ComboBox<KeyValuePair> _specialInstrumentationInstrumentTypeComboBox;
    private NumberField _specialInstrumentationNumberField;
    private Button _specialInstrumentationButton;

    private Button _addButton;
    private Button _updateButton;
    private Button _editButton;
    private Button _deleteButton;
    private Button _cancelButton;

    private ObservableList<MusicalWork> _masterData = FXCollections.observableArrayList();
    private ObservableList<MusicalWork> _filteredData = FXCollections.observableArrayList();
    private TextField _filterField;


    public MusicalWorkManagement() {
    }

    @Override
    public void initialize() {
        if (_initialize != null) {
            _initialize.doAction(null);
        }
        final URL Style = ClassLoader.getSystemResource("style/stylesheetMusicalWork.css");
        getStylesheets().add(Style.toString());
        _fields = new ArrayList<>();
        _nameField = new TextField();
        _nameField.setMinWidth(200);
        _composerField = new TextField();
        _composerField.setMinWidth(200);
        _specialInstrumentationEntityList = new LinkedList();

        _fields = new ArrayList<>();
        try {
            _firstViolinField = new NumberField(0, 0, Integer.MAX_VALUE);
            _fields.add(_firstViolinField);
            _secondViolinField = new NumberField(0, 0, Integer.MAX_VALUE);
            _fields.add(_secondViolinField);
            _violaField = new NumberField(0, 0, Integer.MAX_VALUE);
            _fields.add(_violaField);
            _violoncelloField = new NumberField(0, 0, Integer.MAX_VALUE);;
            _fields.add(_violoncelloField);
            _doublebassField = new NumberField(0, 0, Integer.MAX_VALUE);
            _fields.add(_doublebassField);

            _fluteField = new NumberField(0, 0, Integer.MAX_VALUE);
            _fields.add(_fluteField);
            _oboeField = new NumberField(0, 0, Integer.MAX_VALUE);
            _fields.add(_oboeField);
            _clarinetField = new NumberField(0, 0, Integer.MAX_VALUE);
            _fields.add(_clarinetField);
            _bassoonField = new NumberField(0, 0, Integer.MAX_VALUE);
            _fields.add(_bassoonField);

            _hornField = new NumberField(0, 0, Integer.MAX_VALUE);
            _fields.add(_hornField);
            _trumpetField = new NumberField(0, 0, Integer.MAX_VALUE);
            _fields.add(_trumpetField);
            _tromboneField = new NumberField(0, 0, Integer.MAX_VALUE);
            _fields.add(_tromboneField);
            _tubeField = new NumberField(0, 0, Integer.MAX_VALUE);
            _fields.add(_tubeField);

            _kettledrumField = new NumberField(0, 0, Integer.MAX_VALUE);
            _fields.add(_kettledrumField);
            _percussionField = new NumberField(0, 0, Integer.MAX_VALUE);
            _fields.add(_percussionField);
            _harpField = new NumberField(0, 0, Integer.MAX_VALUE);
            _fields.add(_harpField);
        } catch (NumberRangeException e) {
        }

        setNumberfieldWidth();

        setNumberfieldWidth();

        _workTable = new TableView<>();
        _workTable.setEditable(false);
        _workTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        _workTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        _workTable.getColumns().addListener((ListChangeListener) change -> {
            change.next();
            if (change.wasReplaced()) {
                update();
            }
        });

        _workTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                _editButton.setDisable(false);
                _deleteButton.setDisable(false);
            } else {
                _editButton.setDisable(true);
                _deleteButton.setDisable(true);
            }
        });

        update();

        _filteredData.addAll(_masterData);
        _workTable.setItems(_filteredData);
        _masterData.addListener((ListChangeListener<MusicalWork>) change -> updateFilteredData());

        _workTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                _editButton.setDisable(false);
                _deleteButton.setDisable(false);
            } else {
                _editButton.setDisable(true);
                _deleteButton.setDisable(true);
            }
        });

        Pane newDataPane = getNewMusicalWorkDataPane();

        _deleteButton = new Button("Delete");
        _deleteButton.setDisable(true);
        _deleteButton.setMinWidth(125);
        _deleteButton.setOnAction(e -> deleteMusicalWork());

        _filterField = new TextField();
        _filterField.setPromptText("Name- or Composer");
        _filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.trim().isEmpty()) {
                updateFilteredData();
            }else{
                updateFilteredData();
            }
        });

        HBox buttonsBox = new HBox(_editButton, _deleteButton, new Label("Search:"),_filterField);
        buttonsBox.setSpacing(10);

        VBox root = new VBox();
        root.getChildren().addAll(newDataPane, _workTable, buttonsBox);
        root.setSpacing(5);
        BorderPane borderPane = new BorderPane();
        borderPane.setId("borderPane");

        borderPane.setCenter(root);
        setCenter(borderPane);
    }

    private Pane getNewMusicalWorkDataPane() {
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);

        pane.getColumnConstraints().addAll(new ColumnConstraints(90), new ColumnConstraints(90), new ColumnConstraints(90),
                new ColumnConstraints(90), new ColumnConstraints(90), new ColumnConstraints(90), new ColumnConstraints(90), new ColumnConstraints(90), new ColumnConstraints(120));

        Label titleMusicalWork = new Label("Add Musical Work");
        titleMusicalWork.setId("titleMusicalWork");
        titleMusicalWork.setMinWidth(200);

        _textfields = new HBox();
        _textfields.setMinWidth(600);
        _textfields.setSpacing(10);
        Label nameLabel = new Label("Name:*");
        nameLabel.setMinWidth(60);
        Label composerLabel = new Label("Composer:*");
        composerLabel.setMinWidth(60);
        _textfields.getChildren().addAll(nameLabel, _nameField, composerLabel, _composerField);
        pane.addRow(0, titleMusicalWork);
        pane.addRow(1, _textfields);

        //Strings
        pane.addRow(4, new Label("1. Violin:"), _firstViolinField);
        pane.addRow(5, new Label("2. Violin:"), _secondViolinField);
        pane.addRow(6, new Label("Viola:"), _violaField);
        pane.addRow(7, new Label("Violoncello:"), _violoncelloField);
        pane.addRow(8, new Label("Doublebass:"), _doublebassField);

        //Wood
        pane.addRow(4, new Label("Flute:"), _fluteField);
        pane.addRow(5, new Label("Oboe:"), _oboeField);
        pane.addRow(6, new Label("Clarinet:"), _clarinetField);
        pane.addRow(7, new Label("Bassoon:"), _bassoonField);

        //Brass
        pane.addRow(4, new Label("Horn:"), _hornField);
        pane.addRow(5, new Label("Trumpet:"), _trumpetField);
        pane.addRow(6, new Label("Trombone:"), _tromboneField);
        pane.addRow(7, new Label("Tube:"), _tubeField);

        //Percussion
        pane.addRow(4, new Label("Kettledrum:"), _kettledrumField);
        pane.addRow(5, new Label("Percussion:"), _percussionField);
        pane.addRow(6, new Label("Harp:"), _harpField);

        List<TextField> textFields = new LinkedList();
        textFields.add(_nameField);
        textFields.add(_composerField);

        List<BigDecimalField> decimalFields = new LinkedList<>();
        decimalFields.add(_firstViolinField);
        decimalFields.add(_secondViolinField);
        decimalFields.add(_violaField);
        decimalFields.add(_violoncelloField);
        decimalFields.add(_doublebassField);

        decimalFields.add(_fluteField);
        decimalFields.add(_oboeField);
        decimalFields.add(_clarinetField);
        decimalFields.add(_bassoonField);

        decimalFields.add(_hornField);
        decimalFields.add(_trumpetField);
        decimalFields.add(_tromboneField);
        decimalFields.add(_tubeField);

        decimalFields.add(_kettledrumField);
        decimalFields.add(_percussionField);
        decimalFields.add(_harpField);


        _addButton = new Button("Add");
        _addButton.setVisible(true);
        _addButton.setMinWidth(100);
        _addButton.setOnAction(e -> {
            if (_nameField.getText().isEmpty() || _composerField.getText().isEmpty() ||
                    _firstViolinField.getText().isEmpty() || _secondViolinField.getText().isEmpty() || _violaField.getText().isEmpty() || _violoncelloField.getText().isEmpty() ||
                    _doublebassField.getText().isEmpty() || _fluteField.getText().isEmpty() || _oboeField.getText().isEmpty() || _clarinetField.getText().isEmpty() ||
                    _bassoonField.getText().isEmpty() || _hornField.getText().isEmpty() || _trumpetField.getText().isEmpty() || _tromboneField.getText().isEmpty() || _tubeField.getText().isEmpty()
                    || _kettledrumField.getText().isEmpty() || _percussionField.getText().isEmpty() || _harpField.getText().isEmpty()) {
                validate(textFields);
                validate(decimalFields);
                showValuesMissingMessage();
            } else {
                addMusicalWork();
            }
        });

        _editButton = new Button("Edit");
        _editButton.setDisable(true);
        _editButton.setMinWidth(125);
        _editButton.setOnAction(e -> {
            _workTable.setDisable(true);
            _addButton.setVisible(false);
            _updateButton.setVisible(true);
            _editButton.setDisable(true);
            _deleteButton.setDisable(true);
            _cancelButton.setText("Cancel");
            _filterField.setDisable(true);
            fillFields(_workTable.getSelectionModel().getSelectedItem());
        });

        _updateButton = new Button("Update");
        _updateButton.setMinWidth(100);
        _updateButton.setVisible(false);
        _updateButton.setOnAction(e -> {
            _workTable.setDisable(false);
            editWork();
        });

        _cancelButton = new Button("Clear");
        _cancelButton.setMinWidth(100);
        _cancelButton.setOnAction(e -> {
            _workTable.setDisable(false);
            reset();
        });

        pane.add(new Label("Instrumentation:"), 0, 2);
        pane.add(new Label("String:"), 0, 3);
        pane.add(new Label("Wood:"), 2, 3);
        pane.add(new Label("Brass:"), 4, 3);
        pane.add(new Label("Percussion:"), 6, 3);
        pane.add(new Label("Special Instruments:"), 8, 2);
        pane.add(_addButton, 8, 9);
        pane.add(_updateButton, 8, 9);
        pane.add(_cancelButton, 0, 9);
        Label labelRequired = new Label("*...Required Fields");
        labelRequired.setMinWidth(100);
        pane.add(labelRequired, 0, 10);

        _specialInstrumentationContent = new GridPane();
        _specialInstrumentationSectionGroupComboBox = new ComboBox<>(InstrumentationHelper.getSectionGroupTypeList());
        _specialInstrumentationSectionGroupComboBox.setMaxWidth(80);
        _specialInstrumentationSectionGroupComboBox.getSelectionModel().selectFirst();
        _specialInstrumentationContent.addColumn(0, _specialInstrumentationSectionGroupComboBox);
        _specialInstrumentationInstrumentTypeComboBox = new ComboBox<>(InstrumentationHelper.getInstrumentTypes((SectionGroupType) _specialInstrumentationSectionGroupComboBox.getSelectionModel().getSelectedItem().getValue()));
        _specialInstrumentationInstrumentTypeComboBox.getSelectionModel().selectFirst();
        _specialInstrumentationInstrumentTypeComboBox.setMaxWidth(80);
        _specialInstrumentationContent.addColumn(1, _specialInstrumentationInstrumentTypeComboBox);

        try {
            _specialInstrumentationNumberField = new NumberField(0, 0, Integer.MAX_VALUE);
        } catch (NumberRangeException e) {
        }

        _specialInstrumentationNumberField.setMaxWidth(60);
        _specialInstrumentationContent.addColumn(2, _specialInstrumentationNumberField);
        _specialInstrumentationButton = new Button("+");
        SpecialInstrumentationEntity instrumentationEntityDefault=new SpecialInstrumentationEntity(0, _specialInstrumentationSectionGroupComboBox, _specialInstrumentationInstrumentTypeComboBox,_specialInstrumentationNumberField,_specialInstrumentationContent);
        _specialInstrumentationEntityList.add(instrumentationEntityDefault);

        _specialInstrumentationContent.addColumn(3, _specialInstrumentationButton);
        _specialInstrumentationPane = new ScrollPane(_specialInstrumentationContent);
        _specialInstrumentationPane.setMaxHeight(250);
        _specialInstrumentationPane.setMinWidth(265);

        pane.add(_specialInstrumentationPane, 8, 3);
        pane.setRowSpan(_specialInstrumentationPane, 6);
        pane.setColumnSpan(_specialInstrumentationPane, 4);

        InstrumentationHelper.addListeners(_specialInstrumentationEntityList, _specialInstrumentationContent, _specialInstrumentationSectionGroupComboBox,
                _specialInstrumentationInstrumentTypeComboBox, _specialInstrumentationNumberField, _specialInstrumentationButton);

        return pane;
    }

    public void addMusicalWork() {
        if (_create != null) {
            MusicalWork musicalWork = new MusicalWork();
            setMusicalWork(musicalWork, true);

            MusicalWorkErrorList resultMusicalWorkErrorList = _create.doAction(musicalWork);

            if (resultMusicalWorkErrorList != null && resultMusicalWorkErrorList.getKeyValueList() != null) {
                List<Pair<JSONObjectEntity, List<Error>>> errorList = MusicalWorkConverter.getAbstractList(resultMusicalWorkErrorList.getKeyValueList());
                String tmpErrorText = ErrorMessageHelper.getErrorMessage(errorList);

                if (tmpErrorText.isEmpty() && resultMusicalWorkErrorList.getKeyValueList().size() == 1 && resultMusicalWorkErrorList.getKeyValueList().get(0).getKey() != null && resultMusicalWorkErrorList.getKeyValueList().get(0).getKey().getMusicalWorkID() > 0) {
                    showSuccessMessage("Successful", tmpErrorText);

                   // _workTable.getItems().add(resultMusicalWorkErrorList.getKeyValueList().get(0).getKey());
                    _masterData.add(resultMusicalWorkErrorList.getKeyValueList().get(0).getKey());
                    update();
                    reset();
                } else {
                    showErrorMessage("Error", tmpErrorText);
                    markInvalidFields(errorList);
                }
            } else {
                showTryAgainLaterErrorMessage();
            }
        }

    }

    public void editWork() {
        if (_edit != null) {
            MusicalWork musicalWork = _workTable.getSelectionModel().getSelectedItem();
            setMusicalWork(musicalWork, false);

            MusicalWorkErrorList resultMusicalWorkErrorList = _edit.doAction(musicalWork);

            if (resultMusicalWorkErrorList != null && resultMusicalWorkErrorList.getKeyValueList() != null) {
                List<Pair<JSONObjectEntity, List<Error>>> errorList = MusicalWorkConverter.getAbstractList(resultMusicalWorkErrorList.getKeyValueList());
                String tmpErrorText = ErrorMessageHelper.getErrorMessage(errorList);

                if (tmpErrorText.isEmpty() && resultMusicalWorkErrorList.getKeyValueList().size() == 1) {
                    showSuccessMessage("Successful", tmpErrorText);

                    //_workTable.getItems().remove(musicalWork);
                    //_workTable.getItems().add(resultMusicalWorkErrorList.getKeyValueList().get(0).getKey());
                    _masterData.remove(musicalWork);
                    _masterData.add(resultMusicalWorkErrorList.getKeyValueList().get(0).getKey());
                    markInvalidFields(errorList);
                    update();
                    reset();
                } else {
                    showErrorMessage("Error", tmpErrorText);
                }
            } else {
                showTryAgainLaterErrorMessage();
            }
        }
    }

    public void deleteMusicalWork() {
        if (_delete != null) {
            MusicalWork musicalWork = new MusicalWork();

            MusicalWorkErrorList resultMusicalWorkErrorList = _create.doAction(musicalWork);

            if (resultMusicalWorkErrorList != null && resultMusicalWorkErrorList.getKeyValueList() != null) {
                List<Pair<JSONObjectEntity, List<Error>>> errorList = MusicalWorkConverter.getAbstractList(resultMusicalWorkErrorList.getKeyValueList());
                String tmpErrorText = ErrorMessageHelper.getErrorMessage(errorList);

                if (tmpErrorText.isEmpty() && resultMusicalWorkErrorList.getKeyValueList().size() == 1 && resultMusicalWorkErrorList.getKeyValueList().get(0).getKey() != null && resultMusicalWorkErrorList.getKeyValueList().get(0).getKey().getMusicalWorkID() > 0) {
                    showSuccessMessage("Successful", tmpErrorText);

                   // _workTable.getItems().remove(resultMusicalWorkErrorList.getKeyValueList().get(0).getKey());
                    _masterData.remove(resultMusicalWorkErrorList.getKeyValueList().get(0).getKey());
                    update();
                    return;
                } else {
                    showErrorMessage("Error", tmpErrorText);
                    markInvalidFields(errorList);
                }
            } else {
                showTryAgainLaterErrorMessage();
            }
        }
    }

    private void setMusicalWork(MusicalWork musicalWork, boolean createInstrumentation) {
        musicalWork.setName(_nameField.getText());
        musicalWork.setComposer(_composerField.getText());

        if(createInstrumentation) {
            Instrumentation instrumentation = new Instrumentation();
            musicalWork.setInstrumentation(instrumentation);
        }

        musicalWork.getInstrumentation().setViolin1(Integer.parseInt(_firstViolinField.getText()));
        musicalWork.getInstrumentation().setViolin2(Integer.parseInt(_secondViolinField.getText()));
        musicalWork.getInstrumentation().setViola(Integer.parseInt(_violaField.getText()));
        musicalWork.getInstrumentation().setViolincello(Integer.parseInt(_violoncelloField.getText()));
        musicalWork.getInstrumentation().setDoublebass(Integer.parseInt(_doublebassField.getText()));

        musicalWork.getInstrumentation().setFlute(Integer.parseInt(_fluteField.getText()));
        musicalWork.getInstrumentation().setOboe(Integer.parseInt(_oboeField.getText()));
        musicalWork.getInstrumentation().setClarinet(Integer.parseInt(_clarinetField.getText()));
        musicalWork.getInstrumentation().setBassoon(Integer.parseInt(_bassoonField.getText()));

        musicalWork.getInstrumentation().setHorn(Integer.parseInt(_hornField.getText()));
        musicalWork.getInstrumentation().setTrumpet(Integer.parseInt(_trumpetField.getText()));
        musicalWork.getInstrumentation().setTrombone(Integer.parseInt(_tromboneField.getText()));
        musicalWork.getInstrumentation().setTube(Integer.parseInt(_tubeField.getText()));

        musicalWork.getInstrumentation().setKettledrum(Integer.parseInt(_kettledrumField.getText()));
        musicalWork.getInstrumentation().setPercussion(Integer.parseInt(_percussionField.getText()));
        musicalWork.getInstrumentation().setHarp(Integer.parseInt(_harpField.getText()));

        List<SpecialInstrumentation> specialInstrumentationList = team_f.client.helper.gui.InstrumentationHelper.getSpecialInstrumentation(_specialInstrumentationEntityList);
        musicalWork.getInstrumentation().setSpecialInstrumentation(specialInstrumentationList);
    }

    private void loadList() {
        if (_loadList != null) {
            MusicalWorkParameter musicalWorkParameter = new MusicalWorkParameter();
            List<MusicalWork> musicalWorkList = _loadList.doAction(musicalWorkParameter);

            if (musicalWorkList != null) {
                _masterData.setAll(musicalWorkList);
                update();
            } else {
                showTryAgainLaterErrorMessage();
            }
        }
    }

    @Override
    public void load() {
        if (_load != null) {
        }

        loadList();
    }

    @Override
    public void update() {
        _workTable.getColumns().clear();
        _workTable.getColumns().addAll(MusicalWorkHelper.getIdColumn(), MusicalWorkHelper.getMusicalWorkNameColumn(),
                MusicalWorkHelper.getComposerColumn(), MusicalWorkHelper.getInstrumentationColumn());
    }

    @Override
    public void exit() {
        if (_exit != null) {
            _exit.doAction(null);
        }
    }

    @Override
    public void dispose() {
    }

    public void fillFields(MusicalWork musicalWork) {
        if (musicalWork.getComposer() != null) {
            _composerField.setText(musicalWork.getComposer());
        }
        if (musicalWork.getName() != null) {
            _nameField.setText(musicalWork.getName());
        }
        if (musicalWork.getInstrumentation() != null) {
            Instrumentation instrumentation = musicalWork.getInstrumentation();
            _firstViolinField.setNumber(new BigDecimal(instrumentation.getViolin1()));
            _secondViolinField.setNumber(new BigDecimal(instrumentation.getViolin2()));
            _violaField.setNumber(new BigDecimal(instrumentation.getViola()));
            _violoncelloField.setNumber(new BigDecimal(instrumentation.getViolincello()));
            _doublebassField.setNumber(new BigDecimal(instrumentation.getDoublebass()));

            _fluteField.setNumber(new BigDecimal(instrumentation.getFlute()));
            _oboeField.setNumber(new BigDecimal(instrumentation.getOboe()));
            _clarinetField.setNumber(new BigDecimal(instrumentation.getClarinet()));
            _bassoonField.setNumber(new BigDecimal(instrumentation.getBassoon()));

            _hornField.setNumber(new BigDecimal(instrumentation.getHorn()));
            _trumpetField.setNumber(new BigDecimal(instrumentation.getTrumpet()));
            _tromboneField.setNumber(new BigDecimal(instrumentation.getTrombone()));
            _tubeField.setNumber(new BigDecimal(instrumentation.getTube()));

            _kettledrumField.setNumber(new BigDecimal(instrumentation.getKettledrum()));
            _percussionField.setNumber(new BigDecimal(instrumentation.getPercussion()));
            _harpField.setNumber(new BigDecimal(instrumentation.getHarp()));

            InstrumentationHelper.fillSpecialInstrumentationEntity(_specialInstrumentationEntityList, instrumentation, _specialInstrumentationContent,
                    _specialInstrumentationSectionGroupComboBox, _specialInstrumentationNumberField);
        }
    }

    private void reset() {
        _workTable.getSelectionModel().clearSelection();
        _nameField.setStyle("-fx-border-color: transparent");
        _composerField.setStyle("-fx-border-color: transparent");
        _nameField.clear();
        _composerField.clear();
        _addButton.setVisible(true);
        _editButton.setDisable(true);
        _deleteButton.setDisable(true);
        _updateButton.setVisible(false);
        _cancelButton.setText("Clear");
        _filterField.setDisable(false);

        for (BigDecimalField field : _fields) {
            field.setNumber(new BigDecimal(0));
            field.setStyle("-fx-border-color: transparent");
        }

        InstrumentationHelper.resetSpecialInstrumentation(_specialInstrumentationEntityList, _specialInstrumentationContent);
        SpecialInstrumentationEntity instrumentationEntityDefault = new SpecialInstrumentationEntity(0, _specialInstrumentationSectionGroupComboBox, _specialInstrumentationInstrumentTypeComboBox,_specialInstrumentationNumberField,_specialInstrumentationContent);
        _specialInstrumentationEntityList.add(instrumentationEntityDefault);
    }

    private void markInvalidFields(List<Pair<JSONObjectEntity, List<Error>>> occuredErrors) {
        setBorder();
        String error;
        List<Error> errorList=occuredErrors.get(0).getValue();
        for(int x=0;x<errorList.size();x++) {
            error = errorList.get(x).getKey().toString();
            if (error.equals(MusicalWorkProperty.CONDUCTOR.toString())) {
                _composerField.setStyle("-fx-border-color: red");
            }
            if (error.equals(MusicalWorkProperty.TITLE.toString())) {
                _nameField.setStyle("-fx-border-color: red");
            }
            if (error.equals(MusicalWorkProperty.INSTRUMENTAMENTATION.toString())) {
                for (BigDecimalField field : _fields) {
                    field.setStyle("-fx-border-color: red");
                }
            }
        }
    }

    private void setBorder() {
       _nameField.setStyle("-fx-border-color: green");
        _composerField.setStyle("-fx-border-color: green");

       /* for (BigDecimalField field : _fields) {
                field.setStyle("-fx-border-color: green");
        }*/
    }

    private void setNumberfieldWidth(){
        for(BigDecimalField field:_fields){
            field.setMaxWidth(60);
        }
    }

    private void updateFilteredData() {
        _filteredData.clear();

        for (MusicalWork m : _masterData) {
            if (matchesFilter(m)) {
                _filteredData.add(m);
            }
        }
        _workTable.setItems(_filteredData);
        reapplyTableSortOrder();
    }

    private boolean matchesFilter(MusicalWork musicalWork) {
        String filterString = _filterField.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        if (musicalWork.getName().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (musicalWork.getComposer().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }

        return false;
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<MusicalWork, ?>> sortOrder = new ArrayList<>(_workTable.getSortOrder());
        _workTable.getSortOrder().clear();
        _workTable.getSortOrder().addAll(sortOrder);
    }
}
