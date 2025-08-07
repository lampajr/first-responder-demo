package org.cajun.navy.model.responder;

import jakarta.ejb.Singleton;
import jakarta.ejb.Timeout;
import jakarta.inject.Inject;
import org.cajun.navy.model.mission.MissionEntity;
import org.cajun.navy.service.MissionService;

import java.util.List;
import java.util.logging.Logger;

@Singleton
public class ResponderSimulatorTask {

    private static final Logger logger = Logger.getLogger(ResponderSimulatorTask.class.getName());

//    @Resource
//    TimerService timerService;

    @Inject
    MissionService missionService;

//    @Inject
//    @ConfigProperty(name = "responder.simulator.task.active", defaultValue = "true")
//    private boolean active;


    @Timeout
    public void doMoveResponderLocation(){
        List<MissionEntity> missions = missionService.getAllCreatedOrUpdated();
        logger.info("Are there any missions for next move? "+missions.size());
        missions.forEach(mission -> {
            missionService.doResponderNextMove(mission);
        });
    }
}
