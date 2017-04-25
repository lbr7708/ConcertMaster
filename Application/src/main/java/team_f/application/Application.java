package team_f.application;

import javafx.util.Pair;
import team_f.database_wrapper.database.*;
import team_f.database_wrapper.facade.Facade;
import team_f.domain.entities.EventDuty;
import team_f.domain.entities.Instrumentation;
import team_f.domain.entities.MusicalWork;
import team_f.domain.enums.EntityType;
import team_f.domain.enums.EventStatus;
import team_f.domain.enums.EventType;
import team_f.domain.logic.DomainEntityManager;
import team_f.domain.logic.EventDutyLogic;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Application {

    public Application (){}

    private Facade facade = new Facade();

    public void closeSession() {
        facade.closeSession();
    }

    public Pair<EventDuty, List<Pair<String, String>>> addEvent(int id, String name, String description, String location, LocalDateTime startTime,
                                                                LocalDateTime endTime, String conductor, EventType type, int rehearsalForId,
                                                                Double standardPoints, int instrumentationId, int[] musicalWorkIdList,
                                                                int[] alternativeInstrumentationIdList) {

        // transform the parameters in an domain object
        EventDuty eventDuty = new EventDuty();
        eventDuty.setEventDutyId(id);
        eventDuty.setName(name);
        eventDuty.setDescription(description);
        eventDuty.setLocation(location);
        eventDuty.setStarttime(startTime);
        eventDuty.setEndtime(endTime);
        eventDuty.setConductor(conductor);
        eventDuty.setEventType(type);
        eventDuty.setRehearsalFor(rehearsalForId);
        eventDuty.setDefaultPoints(standardPoints);
        eventDuty.setInstrumentationId(instrumentationId);

        MusicalWork tmpMusicalWork;
        Instrumentation tmpInstrumentation;

        if(musicalWorkIdList != null && alternativeInstrumentationIdList != null) {
            for(int i = 0; i < musicalWorkIdList.length && i < alternativeInstrumentationIdList.length; i++) {
                tmpMusicalWork = new MusicalWork();
                tmpMusicalWork.setMusicalWorkID(musicalWorkIdList[i]);

                tmpInstrumentation = new Instrumentation();
                tmpInstrumentation.setInstrumentationID(alternativeInstrumentationIdList[i]);

                eventDuty.addMusicalWork(tmpMusicalWork, tmpInstrumentation);
            }
        }

        // check for errors
        EventDutyLogic eventDutyLogic = (EventDutyLogic) DomainEntityManager.getLogic(EntityType.EVENT_DUTY);
        List<Pair<String, String>> errorList = eventDutyLogic.validate(eventDuty);

        // return the errorList when the validation is not successful
        if(errorList.size() > 0) {
            return new Pair<>(eventDuty, errorList);
        }

        // create the database entity
        EventDutyEntity event = new EventDutyEntity();
        List<MusicalWorkEntity> eventMusicalList = new ArrayList<>();

        for (MusicalWork musicalWork : eventDuty.getMusicalWorkList()) {
            MusicalWorkEntity mE = new MusicalWorkEntity();
            mE.setComposer(musicalWork.getComposer());
            mE.setInstrumentationId(musicalWork.getInstrumentationID());
            mE.setName(musicalWork.getName());
            eventMusicalList.add(mE);
        }

        event.setEventDutyId(eventDuty.getEventDutyId());
        event.setName(eventDuty.getName());
        event.setDescription(eventDuty.getDescription());
        event.setStarttime(eventDuty.getStarttime());
        event.setEndtime(eventDuty.getEndtime());

        event.setEventType(team_f.database_wrapper.entities.EventType.valueOf(String.valueOf(eventDuty.getEventType())));
        event.setEventStatus(team_f.database_wrapper.entities.EventStatus.valueOf(String.valueOf(eventDuty.getEventStatus())));

        event.setConductor(eventDuty.getConductor());
        event.setLocation(eventDuty.getLocation());
        event.setDefaultPoints(eventDuty.getDefaultPoints());
        event.setInstrumentation(eventDuty.getInstrumentationId());
        event.setRehearsalFor(eventDuty.getRehearsalFor());


        // @TODO: musical works have to be saved in this stage

        facade.addEvent(event, eventMusicalList);

        return new Pair<>(eventDuty, new LinkedList<>());
    }

    public EventDuty getEventById(int id) {
        EventDutyEntity entity = facade.getEventById(id);

        if(entity != null) {
            return transformEventDutyEntity(entity);
        }

        return null;
    }

    public List<EventDuty> getEvents(int month, int year) {
        List<EventDutyEntity> entities = facade.getEvents(month, year);
        List<EventDuty> eventDuties = new ArrayList<>(entities.size());
        EventDuty event;

        for(EventDutyEntity eventDuty : entities) {
            event = transformEventDutyEntity(eventDuty);
            eventDuties.add(event);
        }

        return eventDuties;
    }

    public List<MusicalWork> getMusicalWorkList() {
        List<MusicalWorkEntity> musicalWorkEntities = facade.getMusicalWorks();
        List<MusicalWork> musicalWorks = new ArrayList<>(musicalWorkEntities.size());
        MusicalWork musicalWork;

        for(MusicalWorkEntity item : musicalWorkEntities) {
            musicalWork = transformMusicalWorkEntity(item);
            musicalWorks.add(musicalWork);
        }

        return musicalWorks;
    }

    public List<Instrumentation> getInstrumentationList() {
        List<InstrumentationEntity> instrumentationEntities = facade.getInstrumentations();
        List<Instrumentation> instrumentations = new ArrayList<>(instrumentationEntities.size());
        Instrumentation instrumentation;

        for(InstrumentationEntity item : instrumentationEntities) {
            instrumentation = transformInstrumenationEntity(item);
            instrumentations.add(instrumentation);
        }

        return instrumentations;
    }

    public List<Pair<MusicalWork, Instrumentation>> getMusicalWorkInstrumentationList() {
        List<Pair<MusicalWork, Instrumentation>> list = new LinkedList<>();

        // @TODO: this method will be useful when we have to map a musicalWork to an alternativeInstrumenation in the view

        return list;
    }

    private EventDuty transformEventDutyEntity(EventDutyEntity eventDutyEntity) {
        EventDuty event = new EventDuty();
        event.setConductor(eventDutyEntity.getConductor());
        event.setDefaultPoints(eventDutyEntity.getDefaultPoints());
        event.setDescription(eventDutyEntity.getDescription());
        event.setEndtime(eventDutyEntity.getEndtime());
        event.setStarttime(eventDutyEntity.getStarttime());
        event.setEventDutyId(eventDutyEntity.getEventDutyId());
        event.setRehearsalFor(eventDutyEntity.getRehearsalFor());
        event.setLocation(eventDutyEntity.getLocation());
        event.setInstrumentationId(eventDutyEntity.getInstrumentation());
        event.setName(eventDutyEntity.getName());

        event.setEventType(EventType.valueOf(String.valueOf(eventDutyEntity.getEventType())));
        event.setEventStatus(EventStatus.valueOf(String.valueOf(eventDutyEntity.getEventStatus())));

        MusicalWorkEntity musicalWorkEntity;
        MusicalWork musicalWork;
        InstrumentationEntity instrumentationEntity;
        Instrumentation instrumentation;

        Collection<EventDutyMusicalWorkEntity> eventDutyMusicalWorkEntityList = eventDutyEntity.getEventDutyMusicalWorksByEventDutyId(); //=  facade.getMusicalWorksByEventId(int eventId);

        if(eventDutyMusicalWorkEntityList != null) {
            for (EventDutyMusicalWorkEntity item : eventDutyEntity.getEventDutyMusicalWorksByEventDutyId()) {
                musicalWorkEntity = item.getMusicalWorkByMusicalWork();
                instrumentationEntity = item.getInstrumentationByAlternativeInstrumentation();

                musicalWork = null;

                if (musicalWorkEntity != null) {
                    musicalWork = transformMusicalWorkEntity(musicalWorkEntity);
                }

                instrumentation = null;

                if (instrumentationEntity != null) {
                    instrumentation = transformInstrumenationEntity(instrumentationEntity);
                }

                event.addMusicalWork(musicalWork, instrumentation);
            }
        }

        return event;
    }

    private MusicalWork transformMusicalWorkEntity(MusicalWorkEntity musicalWorkEntity) {
        MusicalWork musicalWork = new MusicalWork();
        musicalWork.setComposer(musicalWorkEntity.getComposer());
        musicalWork.setInstrumentationID(musicalWorkEntity.getInstrumentationId());
        musicalWork.setMusicalWorkID(musicalWorkEntity.getMusicalWorkId());
        musicalWork.setName(musicalWorkEntity.getName());

        return musicalWork;
    }

    private Instrumentation transformInstrumenationEntity(InstrumentationEntity instrumentationEntity) {
        Instrumentation instrumentation = new Instrumentation();

        instrumentation.setInstrumentationID(instrumentationEntity.getInstrumentationId());

        // @TODO: use Instrumentation Objects instead of ids and check in the validation the id, ...
        BrassInstrumentationEntity brassInstrumentationEntity = instrumentationEntity.getBrassInstrumentationByBrassInstrumentation();
        PercussionInstrumentationEntity percussionInstrumentationEntity = instrumentationEntity.getPercussionInstrumentationByPercussionInstrumentation();
        StringInstrumentationEntity stringInstrumentationEntity = instrumentationEntity.getStringInstrumentationByStringInstrumentation();
        WoodInstrumentationEntity woodInstrumentationEntity = instrumentationEntity.getWoodInstrumentationByWoodInstrumentation();

        instrumentation.setHorn(brassInstrumentationEntity.getHorn());
        instrumentation.setTrombone(brassInstrumentationEntity.getTrombone());
        instrumentation.setTrumpet(brassInstrumentationEntity.getTrumpet());
        instrumentation.setTube(brassInstrumentationEntity.getTube());

        instrumentation.setPercussion(percussionInstrumentationEntity.getPercussion());
        instrumentation.setKettledrum(percussionInstrumentationEntity.getKettledrum());
        instrumentation.setHarp(percussionInstrumentationEntity.getHarp());

        instrumentation.setViola(stringInstrumentationEntity.getViola());
        instrumentation.setViolin1(stringInstrumentationEntity.getViolin1());
        instrumentation.setViolin2(stringInstrumentationEntity.getViolin2());
        instrumentation.setViolincello(stringInstrumentationEntity.getViolincello());
        instrumentation.setDoublebass(stringInstrumentationEntity.getDoublebass());

        instrumentation.setBassoon(woodInstrumentationEntity.getBassoon());
        instrumentation.setClarinet(woodInstrumentationEntity.getClarinet());
        instrumentation.setOboe(woodInstrumentationEntity.getOboe());
        instrumentation.setFlute(woodInstrumentationEntity.getFlute());

        return instrumentation;
    }
}

