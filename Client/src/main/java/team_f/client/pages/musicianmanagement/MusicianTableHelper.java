package team_f.client.pages.musicianmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import team_f.client.entities.KeyValuePair;
import team_f.jsonconnector.entities.Instrument;
import team_f.jsonconnector.entities.Person;
import team_f.jsonconnector.enums.*;

public class MusicianTableHelper {
    // Returns PersonTestData Id TableColumn
    public static TableColumn<Person, Integer> getIdColumn() {
        TableColumn<Person, Integer> idCol = new TableColumn<>("Id");
        PropertyValueFactory<Person, Integer> idCellValueFactory = new PropertyValueFactory<>("personID");
        idCol.setCellValueFactory(idCellValueFactory);
        return idCol;
    }

    // Returns First Name TableColumn
    public static TableColumn<Person, String> getFirstNameColumn() {
        TableColumn<Person, String> firstNameCol = new TableColumn<>("First Name");
        PropertyValueFactory<Person, String> firstNameCellValueFactory = new PropertyValueFactory<>("firstname");
        firstNameCol.setCellValueFactory(firstNameCellValueFactory);
        return firstNameCol;
    }

    // Returns Last Name TableColumn
    public static TableColumn<Person, String> getLastNameColumn() {
        TableColumn<Person, String> lastNameCol = new TableColumn<>("Last Name");
        PropertyValueFactory<Person, String> lastNameCellValueFactory = new PropertyValueFactory<>("lastname");
        lastNameCol.setCellValueFactory(lastNameCellValueFactory);
        return lastNameCol;
    }

    // Returns Street TableColumn
    public static TableColumn<Person, String> getStreetColumn() {
        TableColumn<Person, String> streetCol = new TableColumn<>("Street");
        PropertyValueFactory<Person, String> streetCellValueFactory = new PropertyValueFactory<>("address");
        streetCol.setCellValueFactory(streetCellValueFactory);
        return streetCol;
    }

    // Returns ZipCode TableColumn
    public static TableColumn<Person, String> getZipCodeColumn() {
        TableColumn<Person, String> zipCodeCol = new TableColumn<>("Email");
        PropertyValueFactory<Person, String> zipCodeCellValueFactory = new PropertyValueFactory<>("email");
        zipCodeCol.setCellValueFactory(zipCodeCellValueFactory);
        return zipCodeCol;
    }

    // Returns City TableColumn
    public static TableColumn<Person, String> getPhonenumberColumn() {
        TableColumn<Person, String> cityCol = new TableColumn<>("Phonenumber");
        PropertyValueFactory<Person, String> cityCellValueFactory = new PropertyValueFactory<>("phoneNumber");
        cityCol.setCellValueFactory(cityCellValueFactory);
        return cityCol;
    }

    // Returns Phone TableColumn
    public static TableColumn<Person, String> getRoleColumn() {
        TableColumn<Person, String> cityCol = new TableColumn<>("Role");
        PropertyValueFactory<Person, String> cityCellValueFactory = new PropertyValueFactory<>("personRole");
        cityCol.setCellValueFactory(cityCellValueFactory);
        return cityCol;
    }

    public static TableColumn<Person, String> getInstrumentColumn() {
        TableColumn<Person, String> countryCol = new TableColumn<>("Instrument Type");
        PropertyValueFactory<Person, String> countryCellValueFactory = new PropertyValueFactory<>("instrumentType");
        countryCol.setCellValueFactory(countryCellValueFactory);
        return countryCol;
    }

    public static ObservableList<KeyValuePair> getPersonRoleList() {
        ObservableList<KeyValuePair> list = FXCollections.observableArrayList(
                new KeyValuePair("Musician", PersonRole.Musician),
                new KeyValuePair("External Musician", PersonRole.External_musician),
                new KeyValuePair("Manager", PersonRole.Manager),
                new KeyValuePair("Musician Librarian", PersonRole.Music_librarian),
                new KeyValuePair("Facility Manager", PersonRole.Orchestral_facility_manager),
                new KeyValuePair("Substitute", PersonRole.Substitute));

        return list;
    }

    public static ObservableList<KeyValuePair> getAccountRoleList() {
        ObservableList<KeyValuePair> list = FXCollections.observableArrayList(
                new KeyValuePair("Musician", AccountRole.Musician),
                new KeyValuePair("Administrator", AccountRole.Administrator),
                new KeyValuePair("Manager", AccountRole.Manager),
                new KeyValuePair("Section Representative", AccountRole.Section_representative),
                new KeyValuePair("Substitute", AccountRole.Substitute));

        return list;
    }

    public static ObservableList<KeyValuePair> getGenderList() {
        ObservableList<KeyValuePair> list = FXCollections.observableArrayList(
                new KeyValuePair("Female", Gender.FEMALE),
                new KeyValuePair("Male", Gender.MALE));

        return list;
    }

    public static ObservableList<KeyValuePair> getInstrumentTypeList(SectionType sectionType) {
        ObservableList<KeyValuePair> list = FXCollections.observableArrayList();

        switch (sectionType) {
            case BRASS:
                list.addAll(
                        new KeyValuePair("Horn", InstrumentType.HORN),
                        new KeyValuePair("Trombone", InstrumentType.TROMBONE),
                        new KeyValuePair("Trumpet", InstrumentType.TRUMPET),
                        new KeyValuePair("Tube", InstrumentType.TUBE));
                break;
            case VIOLA:
                list.addAll(
                        new KeyValuePair("Viola", InstrumentType.VIOLA)
                );
                break;
            case VIOLIN1:
                list.addAll(
                        new KeyValuePair("1. Violin", InstrumentType.FIRSTVIOLIN)
                );
                break;
            case VIOLIN2:
                list.addAll(
                        new KeyValuePair("2. Violin", InstrumentType.SECONDVIOLIN)
                );
                break;
            case WOODWIND:
                list.addAll(
                        new KeyValuePair("Flute", InstrumentType.FLUTE),
                        new KeyValuePair("Oboe", InstrumentType.OBOE),
                        new KeyValuePair("Clarinet", InstrumentType.CLARINET),
                        new KeyValuePair("Bassoon", InstrumentType.BASSOON));

                break;
            case DOUBLEBASS:
                list.addAll(
                        new KeyValuePair("Double Bass", InstrumentType.DOUBLEBASS)
                );

                break;
            case PERCUSSION:
                list.addAll(
                        new KeyValuePair("Percussion", InstrumentType.PERCUSSION),
                        new KeyValuePair("Harp", InstrumentType.HARP),
                        new KeyValuePair("Kettledrum", InstrumentType.KETTLEDRUM)
                );

                break;
            case VIOLINCELLO:
                list.addAll(
                        new KeyValuePair("Violincello", InstrumentType.VIOLONCELLO)
                );

                break;
        }

        return list;
    }

    public static ObservableList<KeyValuePair> getSectionTypeList() {
        ObservableList<KeyValuePair> list = FXCollections.observableArrayList(
                new KeyValuePair("Brass", SectionType.BRASS),
                new KeyValuePair("Doublebass", SectionType.DOUBLEBASS),
                new KeyValuePair("Percussion", SectionType.PERCUSSION),
                new KeyValuePair("Viola", SectionType.VIOLA),
                new KeyValuePair("Violin 1", SectionType.VIOLIN1),
                new KeyValuePair("Violin 2", SectionType.VIOLIN2),
                new KeyValuePair("Violincello", SectionType.VIOLINCELLO),
                new KeyValuePair("Woodwind", SectionType.WOODWIND));

        return list;
    }
}
