package org.cajun.navy.model.responder;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.*;
import org.cajun.navy.model.mission.MissionEntity;
import org.cajun.navy.service.MissionService;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

@Startup
@Singleton
public class ResponderSimulatorTask {

    private static final Logger logger = Logger.getLogger(ResponderSimulatorTask.class.getName());

    @Resource
    TimerService timerService;

    @Inject
    MissionService missionService;

    @Inject
    @ConfigProperty(name = "responder.simulator.task.active", defaultValue = "true")
    private boolean active;

    @PostConstruct
    void init() {
        if (active) {
            ScheduleExpression expression = new ScheduleExpression().second("*/5").minute("*").hour("*");
            TimerConfig timerConfig = new TimerConfig();
            timerConfig.setPersistent(true);
            timerService.createCalendarTimer(expression, timerConfig);
        } else {
            logger.info("Disable ResponderSimulatorTask");
        }
    }

    @Timeout
    public void doMoveResponderLocation(){
        List<MissionEntity> missions = missionService.getAllCreatedOrUpdated();
        logger.info("Are there any missions for next move? "+missions.size());
        missions.forEach(mission -> {
            missionService.doResponderNextMove(mission);
        });
    }
}
