package team_f.database_wrapper.facade;

import team_f.database_wrapper.entities.*;
import team_f.domain.entities.Instrument;
import team_f.domain.enums.InstrumentType;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class InstrumentFacade extends BaseDatabaseFacade {
    public InstrumentFacade() {
        super();
    }

    public InstrumentFacade(EntityManager session) {
        super(session);
    }

    /**
     * Function to get all Instruments. Returns a List of Instruments
     *
     * @return instruments      List<Instrument>         returns a list of instruments
     */
    public List<Instrument> getAllInstruments() {
        EntityManager session = getCurrentSession();

        Query query = session.createQuery("from InstrumentEntity ");

        List<InstrumentEntity> instrumentEntities = query.getResultList();
        List<Instrument> instruments = new ArrayList<>(instrumentEntities.size());
        Instrument instrument;

        for (InstrumentEntity entity : instrumentEntities) {
            instrument = convertToInstrument(entity);
            instruments.add(instrument);
        }

        return instruments;
    }

    public Instrument getInstrumentById(int id) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from InstrumentEntity where instrumentId = :id");
        query.setParameter("id", id);
        query.setMaxResults(1);

        List<InstrumentEntity> instrumentEntities = query.getResultList();
        Instrument instrument = new Instrument();

        if (instrumentEntities.size() > 0) {
            InstrumentEntity entity = instrumentEntities.get(0);
            instrument = convertToInstrument(entity);
        }

        return instrument;
    }

    /**
     * Function to convert a InstrumentEntity object to a Instrument. Returns the Instrument after creating and setting information from InstrumentEntity object.
     * @return instrument      Instrument        returns a instrument object
     */
    protected Instrument convertToInstrument(InstrumentEntity instrumentEntity) {
        Instrument instrument = new Instrument();

        instrument.setInstrumentID(instrumentEntity.getInstrumentId());
        instrument.setBrand(instrumentEntity.getBrand());
        instrument.setModel(instrumentEntity.getModel());

        InstrumentTypeFacade instrumentTypeFacade = new InstrumentTypeFacade(getCurrentSession());
        InstrumentType instrumentType = instrumentTypeFacade.getInstrumentTypeById(instrumentEntity.getInstrumentType());
        instrument.setInstrumentType(instrumentType);

        return instrument;
    }
}