/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cajun.navy.service.incident;

import java.math.BigDecimal;
import java.util.UUID;
import jakarta.inject.Inject;

import org.cajun.navy.model.incident.IncidentEntity;
import org.cajun.navy.model.incident.IncidentDao;
import org.cajun.navy.model.incident.IncidentStatus;
import org.cajun.navy.service.AbstractTestBase;
import org.junit.Assert;
import org.junit.Test;

public class IncidentDaoIT extends AbstractTestBase {
    @Inject
    private IncidentDao dao;

    @Test
    public void sanity() {
        Assert.assertNotNull(dao);
    }

    @Test
    public void testReadOnlyQueries() {
        dao.findAll();
        dao.findByName("Test");
        dao.findByIncidentId(UUID.randomUUID().toString());
        dao.findByStatus(IncidentStatus.REPORTED.name());
    }

    @Test
    public void createIncident() {
        IncidentEntity incident = new IncidentEntity();
        incident.setLat(BigDecimal.valueOf(34.214745));
        incident.setLon(BigDecimal.valueOf(-77.9837161));
        incident.setMedicalNeeded(true);
        incident.setNumberOfPeople(3);
        incident.setVictimName("John Doe");
        incident.setVictimPhoneNumber("(123) 456-7890");

        IncidentEntity created = dao.create(incident);
        Assert.assertNotNull(created.getId());
        Assert.assertNotNull(created.getIncidentId());
        Assert.assertNotNull(created.getReportedTime());
        Assert.assertNotNull(created.getStatus());
    }

    @Test
    public void deleteAll() {
        dao.deleteAll();
    }
}
