package team_f.client.helper.converter;

import team_f.jsonconnector.entities.DutyDisposition;
import team_f.jsonconnector.enums.DutyDispositionStatus;

public class DutyDispositionConverter {
    public static DutyDisposition convertToJSON(team_f.domain.entities.DutyDisposition dutyDisposition) {
        DutyDisposition result = new DutyDisposition();
        result.setDescription(dutyDisposition.getDescription());
        result.setStatus(DutyDispositionStatus.valueOf(String.valueOf(dutyDisposition.getDutyDispositionStatus())));
        result.setPoints(dutyDisposition.getPoints());

        if(dutyDisposition.getEventDuty() != null) {
            result.setEventDuty(EventDutyConverter.convertToJSON(dutyDisposition.getEventDuty()));
        }

        if(dutyDisposition.getMusician() != null) {
            result.setPerson(PersonConverter.convertToJSON(dutyDisposition.getMusician()));
        }

        return result;
    }
}
