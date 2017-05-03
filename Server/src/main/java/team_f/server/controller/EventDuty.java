package team_f.server.controller;

import org.json.JSONArray;
import team_f.application.EventApplication;
import team_f.jsonconnector.common.URIList;
import team_f.jsonconnector.entities.EventDutyList;
import team_f.jsonconnector.entities.Pair;
import team_f.jsonconnector.enums.EventStatus;
import team_f.jsonconnector.enums.EventType;
import team_f.jsonconnector.enums.request.EventDutyParameter;
import team_f.jsonconnector.helper.ReadHelper;
import team_f.jsonconnector.helper.WriteHelper;
import team_f.server.helper.response.CommonResponse;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {URIList.event})
public class EventDuty extends HttpServlet {
    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();

        if (MediaType.APPLICATION_JSON.equals(contentType)) {
            CommonResponse.writeJSONObject(resp, new JSONArray());
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();

        if(MediaType.APPLICATION_JSON.equals(contentType)) {
            team_f.jsonconnector.entities.Request request = (team_f.jsonconnector.entities.Request) ReadHelper.readJSONObject(req.getReader(), team_f.jsonconnector.entities.Request.class);

            if(request != null) {
                EventApplication facade = new EventApplication();

                switch (request.getActionType()) {
                    case GET_BY_PARAMETER:
                        int month = -1;
                        int year = -1;

                        for(Pair item : request.getParameterValue()) {
                            if(String.valueOf(EventDutyParameter.MONTH).equals(item.getKey())) {
                                try {
                                    month = Integer.parseInt(item.getValue());
                                } catch (NumberFormatException e) {
                                }
                            } else if(String.valueOf(EventDutyParameter.YEAR).equals(item.getKey())) {
                                try {
                                    year = Integer.parseInt(item.getValue());
                                } catch (NumberFormatException e) {
                                }
                            }
                        }

                        if(month > 0 && year > 0) {
                            List<team_f.domain.entities.EventDuty> eventDuties = facade.getEventsByMonth(month, year);
                            EventDutyList eventDutyList = new EventDutyList();
                            List<team_f.jsonconnector.entities.EventDuty> tmpList = new ArrayList<>(eventDuties.size());
                            team_f.jsonconnector.entities.EventDuty eventDuty;

                            for(team_f.domain.entities.EventDuty item : eventDuties) {
                                eventDuty = new team_f.jsonconnector.entities.EventDuty();
                                eventDuty.setEventDutyID(item.getEventDutyID());
                                eventDuty.setConductor(item.getConductor());
                                eventDuty.setDefaultPoints(item.getDefaultPoints());
                                eventDuty.setDescription(item.getDescription());
                                eventDuty.setEndTime(item.getEndTime());
                                eventDuty.setEventStatus(EventStatus.valueOf(String.valueOf(item.getEventStatus())));
                                // @TODO: complete this
                                //eventDuty.getInstrumentation(item.getInstrumentation());
                                eventDuty.setEventType(EventType.valueOf(String.valueOf(item.getEventType())));
                                eventDuty.setLocation(item.getLocation());
                                eventDuty.setMusicalWorkList(item.getMusicalWorkList());
                                eventDuty.setName(item.getName());
                                // @TODO: complete this
                                //eventDuty.setRehearsalFor(item.getRehearsalFor());
                                eventDuty.setStartTime(item.getStartTime());

                                tmpList.add(eventDuty);
                            }

                            eventDutyList.setEventDutyList(tmpList);

                            resp.setContentType(MediaType.APPLICATION_JSON);
                            WriteHelper.writeJSONObject(resp.getWriter(), eventDutyList);
                        }

                        break;
                    default:
                        break;
                }
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}