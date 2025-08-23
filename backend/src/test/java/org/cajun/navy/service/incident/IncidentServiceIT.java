package org.cajun.navy.service.incident;

import java.math.BigDecimal;
import java.util.List;
import jakarta.inject.Inject;

import org.cajun.navy.exception.NoResponderAvailableException;
import org.cajun.navy.model.incident.IncidentStatus;
import org.cajun.navy.service.AbstractTestBase;
import org.cajun.navy.service.IncidentService;
import org.cajun.navy.service.model.Incident;
import org.junit.Assert;
import org.junit.Test;

public class IncidentServiceIT extends AbstractTestBase {
    @Inject
    private IncidentService service;

    @Test
    public void foo() {
        List<Incident> incidents = service.findAll();
        Assert.assertNotNull(incidents);
        incidents.forEach(System.out::println);
    }

    @Test
    public void testIncidentById() throws NoResponderAvailableException {
        String incidentId = "incidentId";
        Incident incident = new Incident();
        incident.setId(incidentId);
        incident.setLat(BigDecimal.valueOf(34.214745));
        incident.setLon(BigDecimal.valueOf(-77.9837161));
        incident.setMedicalNeeded(true);
        incident.setNumberOfPeople(3);
        incident.setVictimName("John Doe");
        incident.setVictimPhoneNumber("(123) 456-7890");
        incident.setStatus(IncidentStatus.REPORTED.toString());

        service.createIncident(incident);

        Incident returning = service.findByIncidentId(incidentId);
        Assert.assertNotNull(returning);
        Assert.assertEquals(incidentId, returning.getId());
    }

}
