package com.dondoc.backend.moim.service;

import com.dondoc.backend.moim.entity.Moim;
import java.util.List;
import java.util.Map;
import com.dondoc.backend.moim.dto.*;


public interface MoimService {

    boolean createOnwerAPI(String identificationNumber, String moimName);

    Map<String,Object> createAccountAPI(String moimName, int bankCode, String identificationNumber, String password);

    Moim createMoim(String identificationNumber, String moimName, String introduce, Long moimAccountId, String moimAccountNumber, int limited, int moimType, int managerSize);

    boolean checkIdenNumDuplicate(String identificationNumber);

    String makeIdentificationNumber() throws Exception;

    List<Moim> getMoimList(String userId);

    MoimDetailDto.Response getMoimDetail(String userId, Long moimId) throws Exception;

    List<Moim> findMoimWithMember(Long moimId);

    int searchBalance(String identificationNumber);

    Moim findById(Long id) throws Exception;

    WithdrawRequestDto.Response withdrawReq(Long userId, WithdrawRequestDto.Request req) throws Exception;

    MissionRequestDto.Response missionReq(Long userId, MissionRequestDto.Request req) throws Exception;

    AllRequestDto.Response getRequestList(Long userId, AllRequestDto.Request req) throws Exception;

    DetailRequestDto.Response getRequestDetail(Long userId, DetailRequestDto.Request req) throws Exception;

    AllowRequestDto.Response allowRequest(Long userId, AllowRequestDto.Request req) throws Exception;

    String rejectRequest(Long userId, RejectRequestDto.Request req) throws Exception;

    AllowRequestDto.Response allowMissionRequest(Long userId, AllowRequestDto.Request req) throws Exception;

    String rejectMissionRequest(Long userId, RejectRequestDto.Request req) throws Exception;

    List<MissionInfoDto.Response> getMyMission(Long userId) throws Exception;

    SuccessOrNotMissionDto.Response successMission(Long userId, SuccessOrNotMissionDto.Request req) throws Exception;

    SuccessOrNotMissionDto.Response failMission(Long userId, SuccessOrNotMissionDto.Request req) throws Exception;

    SuccessOrNotMissionDto.Response quitMission(Long userId, SuccessOrNotMissionDto.Request req) throws Exception;

    CancelRequestDto.Response cancelReq(Long userId, CancelRequestDto.Request req) throws Exception;

}
