package team_f.jsonconnector.entities.list;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import team_f.jsonconnector.interfaces.JSONObjectEntity;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DutyDispositionList implements JSONObjectEntity {
    private List<DutyDispositionList> _eventDutyList;
    private double _points;

    @JsonGetter("event_duties")
    public List<DutyDispositionList> getEventDutyList() {
        return _eventDutyList;
    }

    @JsonGetter("points")
    public double getPoints() {
        return _points;
    }

    @JsonSetter("event_duties")
    public void setEventDutyList(List<DutyDispositionList> eventDutyList) {
        _eventDutyList = eventDutyList;
    }

    @JsonSetter("points")
    public void setPoints(double points) {
        _points = points;
    }

    @Override
    public String getEntityName() {
        return "Duty Dispositions";
    }

    @Override
    public String getDisplayName() {
        return getEntityName();
    }
}
