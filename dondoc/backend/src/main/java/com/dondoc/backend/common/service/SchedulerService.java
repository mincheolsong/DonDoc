package com.dondoc.backend.common.service;

import com.dondoc.backend.moim.entity.Mission;
import com.dondoc.backend.moim.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerService {

    @Autowired
    private MissionRepository missionRepository;

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void endMission() {

        log.info("---- Start SchedulerService ----");

        List<Mission> missionList = missionRepository.findByStatusNot(2);

        LocalDate now = LocalDate.now();

        for(Mission m : missionList) {
            if(now.isEqual(m.getEndDate())){ // 미션 종료일이 오늘이면
                if(m.getStatus()==0){
                    m.setStatus(2); // 미션 거절
                } else if(m.getStatus()==1){
                    m.setStatus(3); // 미션 실패
                }
            }
        }
    }
}
