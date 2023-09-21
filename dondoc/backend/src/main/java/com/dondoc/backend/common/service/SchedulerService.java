package com.dondoc.backend.common.service;

import com.dondoc.backend.moim.entity.Mission;
import com.dondoc.backend.moim.entity.Moim;
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

        List<Mission> missionList = missionRepository.findByStatusOrStatus(0, 1);

        LocalDate now = LocalDate.now();

        for(Mission m : missionList) {
            if(now.isAfter(m.getEndDate())){ 
                if(m.getStatus()==0){ // 승인 대기 -> 승인 거절
                    m.setStatus(2);
                } else if(m.getStatus()==1) { // 미션 진행 중 -> 미션 실패
                    m.setStatus(3);
                    
                    // limited 계산
                    Moim moim = m.getMoimMember().getMoim();
                    moim.setLimited(moim.getLimited() - m.getAmount());
                }
            }
        }
    }
}
