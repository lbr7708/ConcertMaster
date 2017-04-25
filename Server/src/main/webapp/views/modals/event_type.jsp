<%@ page import="team_f.domain.enums.EventDutyProperty" %>
<%@ page import="team_f.domain.helper.EnumHelper" %>
<%@ page import="team_f.domain.enums.EventType" %>
<%@ page import="team_f.domain.enums.EventStatus" %>
<%@ page import="team_f.domain.entities.EventDuty" %>
<%@ page import="team_f.application.helper.DomainEntityHelper" %>
<%@ page import="team_f.domain.entities.MusicalWork" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<form id="modal-form" action="${IS_DATE ? "/Date" : ""} ${IS_REHEARSAL_FOR ? "/RehearsalFor" : ""}" class="form-group">
    <div class="modal-header">
        <h4 class="modal-title">
            <c:if test="${IS_DATE}">Event</c:if>
            <c:if test="${IS_REHEARSAL_FOR}">Rehearsal</c:if>
        </h4>
    </div>
    <div class="modal-body container-fluid">
        <input type="number" name="${EventDutyProperty.ID}" value="${eventDuty.eventDutyId}" class="hidden">

        <div class="control-group row">
            <c:if test="${IS_REHEARSAL_FOR}">
                <div class="form-group col-xs-8 col-sm-6">
                    <t:ErrorMessage errorMessage="${PROBLEM_REHEARSAL_FOR}">
                        <label for="${EventDutyProperty.REHEARSAL_FOR}" class="control-label">For Event</label><br>
                        <input type="number" id="${EventDutyProperty.REHEARSAL_FOR}_" class="hidden" name="${EventDutyProperty.REHEARSAL_FOR}" value="${eventDuty.rehearsalFor.eventDutyId}">
                        <label id="${EventDutyProperty.REHEARSAL_FOR}" class="form-control">${eventDuty.rehearsalFor.name}</label>
                        <%--<select class="selectpicker form-control" data-live-search="true" name="${EventDutyProperty.REHEARSAL_FOR}" disabled>
                            <option value="${eventDuty.rehearsalFor.eventDutyId}" selected disabled>${eventDuty.rehearsalFor.name}</option>
                        </select>--%>
                    </t:ErrorMessage>
                </div>
            </c:if>
            <div class="form-group col-xs-8 col-sm-6">
                <c:if test="${IS_DATE}">
                    <t:ErrorMessage errorMessage="${PROBLEM_EVENT_TYPE}">
                        <label for="${EventDutyProperty.EVENT_TYPE}" class="control-label">Eventtype</label><br>
                        <select class="selectpicker form-control" data-live-search="true" name="${EventDutyProperty.EVENT_TYPE}">
                            <option value="">Select Eventtype</option>
                            <c:forEach var="item" items="${EnumHelper.getBasicEventTypeList()}">
                                <option data-divider="true"></option>
                                <option value="${item}" ${item eq eventDuty.eventType ? "selected" : ""}>${item}</option>
                            </c:forEach>
                        </select>
                    </t:ErrorMessage>
                </c:if>
                <c:if test="${IS_REHEARSAL_FOR}">
                    <label for="${EventDutyProperty.EVENT_TYPE}_" class="control-label">Eventtype</label><br>
                    <label id="${EventDutyProperty.EVENT_TYPE}_" class="form-control">${eventDuty.rehearsalFor.eventStatus}</label>
                </c:if>
            </div>
        </div>

        <c:if test="${IS_REHEARSAL_FOR}">
            <div class="control-group row">
                <div class="form-group col-xs-8 col-sm-6">
                    <label for="${EventDutyProperty.START_TIME}_" class="control-label">Starttime</label><br>
                    <label id="${EventDutyProperty.START_TIME}_" class="form-control">${eventDuty.rehearsalFor.getStartDate("MM/dd/yyyy")} ${eventDuty.rehearsalFor.getStartTime("HH:mm")}</label>
                </div>

                <div class="form-group col-xs-8 col-sm-6">
                    <label for="${EventDutyProperty.END_TIME}_" class="control-label">Endtime</label><br>
                    <label id="${EventDutyProperty.END_TIME}_" class="form-control">${eventDuty.rehearsalFor.getEndDate("MM/dd/yyyy")} ${eventDuty.rehearsalFor.getEndTime("HH:mm")}</label>
                </div>
            </div>
            <hr />
        </c:if>

        <div class="control-group row">
            <div class="form-group col-xs-8 col-sm-6">
                <t:ErrorMessage errorMessage="${PROBLEM_START_DATE}">
                    <t:DatePicker>
                        <jsp:attribute name="label">${"Startdate"}</jsp:attribute>
                        <jsp:attribute name="id">${EventDutyProperty.START_DATE}</jsp:attribute>
                        <jsp:attribute name="inputName">${EventDutyProperty.START_DATE}</jsp:attribute>
                        <jsp:attribute name="inputRequired">${true}</jsp:attribute>
                        <jsp:attribute name="inputDateFormat">${"mm/dd/yyyy"}</jsp:attribute>
                        <jsp:attribute name="inputValue">${eventDuty.getStartDate("MM/dd/yyyy")}</jsp:attribute>
                    </t:DatePicker>
                </t:ErrorMessage>
            </div>

            <div class="form-group col-xs-8 col-sm-6">
                <t:ErrorMessage errorMessage="${PROBLEM_END_DATE}">
                    <t:DatePicker>
                        <jsp:attribute name="label">${"Enddate"}</jsp:attribute>
                        <jsp:attribute name="id">${EventDutyProperty.END_DATE}</jsp:attribute>
                        <jsp:attribute name="inputName">${EventDutyProperty.END_DATE}</jsp:attribute>
                        <jsp:attribute name="inputRequired">${true}</jsp:attribute>
                        <jsp:attribute name="inputDateFormat">${"mm/dd/yyyy"}</jsp:attribute>
                        <jsp:attribute name="inputValue">${eventDuty.getEndDate("MM/dd/yyyy")}</jsp:attribute>
                    </t:DatePicker>
                </t:ErrorMessage>
            </div>
        </div>

        <div class="control-group row">
            <div class="form-group col-xs-8 col-sm-6 form-inline">
                <t:ErrorMessage errorMessage="${PROBLEM_START_TIME}">
                    <t:ClockPicker>
                        <jsp:attribute name="label">${"Starttime"}</jsp:attribute>
                        <jsp:attribute name="id">${EventDutyProperty.START_TIME}</jsp:attribute>
                        <jsp:attribute name="inputName">${EventDutyProperty.START_TIME}</jsp:attribute>
                        <jsp:attribute name="inputRequired">${true}</jsp:attribute>
                        <jsp:attribute name="inputClockFormat">${"HH:mm"}</jsp:attribute>
                        <jsp:attribute name="inputValue">${eventDuty.getStartTime("HH:mm")}</jsp:attribute>
                    </t:ClockPicker>
                </t:ErrorMessage>
            </div>

            <div class="form-group col-xs-8 col-sm-6 form-inline">
                <t:ErrorMessage errorMessage="${PROBLEM_END_TIME}">
                    <t:ClockPicker>
                        <jsp:attribute name="label">${"Endtime"}</jsp:attribute>
                        <jsp:attribute name="id">${EventDutyProperty.END_TIME}</jsp:attribute>
                        <jsp:attribute name="inputName">${EventDutyProperty.END_TIME}</jsp:attribute>
                        <jsp:attribute name="inputRequired">${true}</jsp:attribute>
                        <jsp:attribute name="inputClockFormat">${"HH:mm"}</jsp:attribute>
                        <jsp:attribute name="inputValue">${eventDuty.getEndTime("HH:mm")}</jsp:attribute>
                    </t:ClockPicker>
                </t:ErrorMessage>
            </div>
        </div>

        <div class="control-group row">
            <div class="form-group col-xs-8 col-sm-6">
                <t:ErrorMessage errorMessage="${PROBLEM_NAME}">
                    <label for="${EventDutyProperty.NAME}" class="control-label">Name</label><br>
                    <input id="${EventDutyProperty.NAME}" type="text" value="${eventDuty.name}" name="${EventDutyProperty.NAME}" placeholder="Name" class="form-control" required><br>
                </t:ErrorMessage>
            </div>
            <div class="form-group col-xs-8 col-sm-6">
                <t:ErrorMessage errorMessage="${PROBLEM_LOCATION}">
                    <label for="${EventDutyProperty.LOCATION}" class="control-label">Location</label><br>
                    <input id="${EventDutyProperty.LOCATION}" type="text" value="${eventDuty.location}" name="${EventDutyProperty.LOCATION}" placeholder="Location" class="form-control" required><br>
                </t:ErrorMessage>
            </div>
        </div>

        <div class="control-group row">
            <div class="form-group col-xs-8 col-sm-6">
                <t:ErrorMessage errorMessage="${PROBLEM_CONDUCTOR}">
                    <label for="${EventDutyProperty.CONDUCTOR}" class="control-label">Conductor</label><br>
                    <input id="${EventDutyProperty.CONDUCTOR}" type="text" value="${eventDuty.conductor}" name="${EventDutyProperty.CONDUCTOR}" placeholder="Conductor" class="form-control" required><br>
                </t:ErrorMessage>
            </div>
            <div class="form-group col-xs-8 col-sm-6">
                <t:ErrorMessage errorMessage="${PROBLEM_DEFAULT_POINTS}">
                    <label for="${EventDutyProperty.DEFAULT_POINTS}" class="control-label">Standard Points</label><br>
                    <input id="${EventDutyProperty.DEFAULT_POINTS}" type="number" value="${empty eventDuty.defaultPoints ? 0 : eventDuty.defaultPoints}" name="${EventDutyProperty.DEFAULT_POINTS}" placeholder="Standard Points" value="0" class="form-control" min="0" required><br>
                </t:ErrorMessage>
            </div>
        </div>

        <div class="control-group row">
            <div class="form-group col-xs-8 col-sm-6">
                <t:ErrorMessage errorMessage="${PROBLEM_DESCRIPTION}">
                    <label for="${EventDutyProperty.DESCRIPTION}" class="control-label">Description</label><br>
                    <input id="${EventDutyProperty.DESCRIPTION}" type="text" value="${eventDuty.description}" name="${EventDutyProperty.DESCRIPTION}" placeholder="Description" class="form-control"><br>
                </t:ErrorMessage>
            </div>
            <div class="form-group col-xs-8 col-sm-6">
                <c:choose>
                    <c:when test="${not empty eventDuty.eventStatus}">
                        <t:ErrorMessage errorMessage="${PROBLEM_STATUS}">
                            <label for="${EventDutyProperty.EVENT_STATUS}" class="control-label">Eventstatus</label><br>
                            <label id="${EventDutyProperty.EVENT_STATUS}" class="form-control">${eventDuty.eventStatus}</label>
                            <%--<select class="selectpicker form-control" data-live-search="true" name="${EventDutyProperty.EVENT_STATUS}">
                                <option value="">Select Eventstatus</option>
                                <c:forEach var="item" items="${EnumHelper.getEventStatusList()}">
                                    <option data-divider="true"></option>
                                    <option value="${item}" ${item eq eventDuty.eventStatus ? "selected" : ""}>${item}</option>
                                </c:forEach>
                            </select>--%>
                        </t:ErrorMessage>
                    </c:when>
                </c:choose>
            </div>
        </div>

        <div class="control-group row">
            <c:if test="${IS_DATE}">
                <div class="form-group col-xs-8 col-sm-6">
                    <t:ErrorMessage errorMessage="${PROBLEM_MUSICAL_WORK_LIST}">
                        <label for="${EventDutyProperty.MUSICAL_WORK_LIST}" class="control-label">Musical Work</label><br>
                        <select class="selectpicker form-control" data-live-search="true" name="${EventDutyProperty.MUSICAL_WORK_LIST}">
                            <option value="">Select Musical Work</option>
                            <c:forEach var="item" items="${DomainEntityHelper.getMusicalWorkList()}">
                                <option data-divider="true"></option>
                                <option value="${item.musicalWorkID}"
                                        <c:forEach var="musicalWork" items="${eventDuty.musicalWorkList}">
                                            <c:if test="${musicalWork.musicalWorkID eq item.musicalWorkID}">
                                                selected
                                            </c:if>
                                        </c:forEach>
                                        data-subtext="${item.composer}">${item.name}</option>
                            </c:forEach>
                        </select>
                        <input type="text" name="${EventDutyProperty.ALTERNATIVE_INSTRUMENTATION_LIST}" value="" class="hidden">
                        <!-- add a new popup window -->
                    </t:ErrorMessage>
                </div>
                <div class="form-group col-xs-8 col-sm-6">
                    <t:ErrorMessage errorMessage="${PROBLEM_INSTRUMENTATION}">
                        <label for="${EventDutyProperty.INSTRUMENTATION}" class="control-label">Instrumentation</label><br>
                        <select class="selectpicker form-control" data-live-search="true" name="${EventDutyProperty.INSTRUMENTATION}">
                            <option value="">Select Instrumentation</option>
                            <c:forEach var="item" items="${DomainEntityHelper.getInstrumentationList()}">
                                <option data-divider="true"></option>
                                <option value="${item.instrumentationID}" ${item.instrumentationID eq eventDuty.instrumentation.instrumentationID ? "selected" : ""}>Flute: ${item.flute}, Oboe: ${item.oboe}, Clarinet: ${item.clarinet}, Bassoon: ${item.bassoon}, Violin 1: ${item.violin1}, Violin 2: ${item.violin2}, Viola: ${item.viola}, Violincello: ${item.violincello}, Doublebass: ${item.doublebass}, Horn: ${item.horn}, Trumpet: ${item.trumpet}, Trombone: ${item.trombone}, Tube: ${item.tube}, Kettledrum: ${item.kettledrum}, Percussion: ${item.percussion}, Harp: ${item.harp}</option>
                            </c:forEach>
                        </select>
                    </t:ErrorMessage>
                </div>
            </c:if>
        </div>
    </div>
    <div class="modal-footer">
        <button id="modal-cancel" type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button><!--data-dismiss="modal"-->
        <button id="modal-edit" type="button" class="btn btn-default">Edit</button>
        <button id="modal-save" type="submit" class="btn btn-info pull-right">Save</button>
    </div>
</form>