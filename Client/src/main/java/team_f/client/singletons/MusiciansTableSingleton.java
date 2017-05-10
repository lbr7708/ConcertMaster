package team_f.client.singletons;

import team_f.client.configuration.Configuration;
import team_f.client.helper.RequestResponseHelper;
import team_f.client.pages.PageAction;
import team_f.client.pages.musicianmanagement.MusicianManagement;
import team_f.client.pages.musicianmanagement.PersonParameter;
import team_f.jsonconnector.common.URIList;
import team_f.jsonconnector.entities.*;
import team_f.jsonconnector.entities.Error;
import team_f.jsonconnector.enums.request.ActionType;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MusiciansTableSingleton {
    private static MusicianManagement _musicianTable;
    private static Configuration _configuration;

    private MusiciansTableSingleton() {
    }

    public static MusicianManagement getInstance(Configuration configuration) {
        if (_musicianTable == null) {
            _configuration = configuration;
            _musicianTable = new MusicianManagement();

            _musicianTable.setOnLoadList(new PageAction<List<Person>, PersonParameter>() {
                @Override
                public List<Person> doAction(PersonParameter value) {
                    if(value != null) {
                        Request request = new Request();
                        request.setActionType(ActionType.GET_ALL);

                        PersonList personList = (PersonList) RequestResponseHelper.writeAndReadJSONObject(getPersonURL(), request, PersonList.class);

                        if(personList != null) {
                            return personList.getPersonList();
                        }
                    }

                    return null;
                }
            });

            _musicianTable.setOnCreate(new PageAction<Person, Person>() {
                @Override
                public Person doAction(Person value) {
                    ErrorList<Person> errorList = (ErrorList) RequestResponseHelper.writeAndReadJSONObject(getRegisterURL(), value, ErrorList.class);

                    if (errorList != null && errorList.getKeyValueList() != null && errorList.getKeyValueList().size() == 1) {
                        Pair<Person, List<Error>> person = errorList.getKeyValueList().get(0);

                        if(person != null && person.getValue() != null && person.getValue().size() == 0) {
                            return person.getKey();
                        }
                    }

                    return null;
                }
            });

            _musicianTable.setOnEdit(new PageAction<Person, Person>() {
                @Override
                public Person doAction(Person value) {
                    if(value != null) {
                        Request request = new Request();
                        request.setActionType(ActionType.UPDATE);

                        Person person = (Person) RequestResponseHelper.writeAndReadJSONObject(getPersonURL(), request, Person.class);

                        if(person != null) {
                            return person;
                        }

                        return person;
                    }

                    return null;
                }
            });

            _musicianTable.setOnDelete(new PageAction<Person, Person>() {
                @Override
                public Person doAction(Person value) {
                    return null;
                }
            });
        }

        return _musicianTable;
    }

    private static URL getRegisterURL() {
        try {
            return new URL(new URL(_configuration.getStartURI()), URIList.register);
        } catch (MalformedURLException e) {
        }

        return null;
    }

    private static URL getPersonURL() {
        try {
            return new URL(new URL(_configuration.getStartURI()), URIList.person);
        } catch (MalformedURLException e) {
        }

        return null;
    }
}


