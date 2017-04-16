package team_f.database_wrapper.facade;

import javafx.util.Pair;
import team_f.database_wrapper.database.*;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Facade {
    EntityManager _session;

    public Facade() {
        _session = SessionFactory.getSession();
    }

    public Facade(EntityManager session) {
        _session = session;
    }

    protected EntityManager getCurrentSession(){
        return _session;
    }

    /**
     * Function to add a ModelLogic. Returns the EventDutyId after saving it in database
     * @return EventDutyId      int         returns the primary key of the event
     */

    public Integer addEvent(EventDutyEntity event) {

        EntityManager session = getCurrentSession();
        session.getTransaction().begin();

        EventDutyEntity eventEntity = new EventDutyEntity();

        eventEntity.setName(event.getName());
        eventEntity.setDescription(event.getDescription());
        eventEntity.setStarttime(event.getStarttime());
        eventEntity.setEndtime(event.getEndtime());
        eventEntity.setEventType(event.getEventType());
        eventEntity.setEventStatus(event.getEventStatus());
        eventEntity.setConductor(event.getConductor());
        eventEntity.setLocation(event.getLocation());
        eventEntity.setDefaultPoints(event.getDefaultPoints());
        eventEntity.setInstrumentation(event.getInstrumentation());
        eventEntity.setRehearsalFor(event.getRehearsalFor());

        session.persist(event);

        try {
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }

        return eventEntity.getEventDutyId();
    }

    public EventDutyEntity getEventById(int id) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from EventDutyEntity where eventDutyId = :id");
        query.setParameter("id", id);
        query.setMaxResults(1);

        List<EventDutyEntity> eventDutyEntities = query.getResultList();

        if(eventDutyEntities.size() > 0) {
            return eventDutyEntities.get(0);
        }

        return null;
    }

    public List<EventDutyEntity> getEvents(int month, int year) {
        EntityManager session = getCurrentSession();

        // prevent SQL injections
        Query query = session.createQuery("from EventDutyEntity where MONTH(starttime) = :month and YEAR(starttime) = :year");
        query.setParameter("month", month);
        query.setParameter("year", year);

        List<EventDutyEntity> eventEntities = query.getResultList();
        List<EventDutyEntity> events = new ArrayList<>();
        EventDutyEntity event;

        for(EventDutyEntity eventDuty : eventEntities) {
            event = new EventDutyEntity();
            event.setEventDutyId(eventDuty.getEventDutyId());
            event.setName(eventDuty.getName());
            event.setDescription(eventDuty.getDescription());
            event.setStarttime(eventDuty.getStarttime());
            event.setEndtime(eventDuty.getEndtime());
            event.setEventType(eventDuty.getEventType());
            event.setEventStatus(eventDuty.getEventStatus());
            event.setConductor(eventDuty.getConductor());
            event.setLocation(eventDuty.getLocation());
            event.setDefaultPoints(eventDuty.getDefaultPoints());
            event.setInstrumentation(eventDuty.getInstrumentation());
            event.setRehearsalFor(eventDuty.getRehearsalFor());

            events.add(event);
        }

        return events;
    }

    public List<InstrumentationEntity> getInstrumentations() {
        EntityManager session = getCurrentSession();

        Query query = session.createQuery("from InstrumentationEntity");
        List<InstrumentationEntity> instrumentations= query.getResultList();

        return instrumentations;
    }

    public Integer addInstrumentation(InstrumentationEntity inst) {

        EntityManager session = getCurrentSession();
        session.getTransaction().begin();

        // create new Instrumentation entity
        InstrumentationEntity instrumentation = new InstrumentationEntity();
        StringInstrumentationEntity stringInstrumentation = new StringInstrumentationEntity();
        BrassInstrumentationEntity brassInstrumentation = new BrassInstrumentationEntity();
        WoodInstrumentationEntity woodInstrumentation = new WoodInstrumentationEntity();
        PercussionInstrumentationEntity percussionInstrumentation = new PercussionInstrumentationEntity();

        // temporarily store all child entities in the transaction and get the id
        session.persist(stringInstrumentation);
        session.persist(brassInstrumentation);
        session.persist(woodInstrumentation);
        session.persist(percussionInstrumentation);

        // set the received
        instrumentation.setStringInstrumentation(stringInstrumentation.getStringInstrumentationId());
        instrumentation.setBrassInstrumentation(brassInstrumentation.getBrassInstrumentationId());
        instrumentation.setWoodInstrumentation(woodInstrumentation.getWoodInstrumentationId());
        instrumentation.setPercussionInstrumentation(percussionInstrumentation.getPercussionInstrumentationId());

        session.persist(instrumentation);

        try {
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }

        return instrumentation.getInstrumentationId();
    }

    public List<MusicalWorkEntity> getMusicalWorks(){
        EntityManager session = getCurrentSession();

        // prevent SQL injections
        Query query = session.createQuery("from MusicalWorkEntity");


        List<MusicalWorkEntity> musicalWorkEntities = query.getResultList();
        List<MusicalWorkEntity> musicalworks = new ArrayList<>();
        MusicalWorkEntity musicalWork;

        for (MusicalWorkEntity entity : musicalWorkEntities) {
            musicalWork = new MusicalWorkEntity();

            musicalWork.setInstrumentationId(entity.getInstrumentationId());
            musicalWork.setComposer(entity.getComposer());
            musicalWork.setName(entity.getName());

            musicalworks.add(musicalWork);
        }

        return musicalworks;
    }

    public Integer addMusicalWork(MusicalWorkEntity musicalWork){

        EntityManager session = getCurrentSession();
        session.getTransaction().begin();

        MusicalWorkEntity mwEntity = new MusicalWorkEntity();

        mwEntity.setInstrumentationId(musicalWork.getInstrumentationId());
        mwEntity.setComposer(musicalWork.getComposer());
        mwEntity.setName(musicalWork.getName());

        session.persist(musicalWork);

        try {
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }

        return mwEntity.getMusicalWorkId();
    }
}
