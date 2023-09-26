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

    List<Moim> getMoimList(Long userId);

    MoimDetailDto.Response getMoimDetail(Long userId, Long moimId) throws Exception;

    List<Moim> findMoimWithMember(Long moimId);

    int searchBalance(String identificationNumber);

    Moim findById(Long id) throws Exception;

    List<Object> getHistoryList(String identificationNumber, String accountNumber);

    WithdrawRequestDto.Response withdrawReq(WithdrawRequestDto.Request req) throws Exception;

    MissionRequestDto.Response missionReq(MissionRequestDto.Request req) throws Exception;

    AllRequestDto.Response getRequestList(AllRequestDto.Request req) throws Exception;

    DetailRequestDto.Response getRequestDetail(DetailRequestDto.Request req) throws Exception;

    AllowRequestDto.Response allowRequest(AllowRequestDto.Request req) throws Exception;

    String rejectRequest(RejectRequestDto.Request req) throws Exception;

    AllowRequestDto.Response allowMissionRequest(AllowRequestDto.Request req) throws Exception;

    String rejectMissionRequest(RejectRequestDto.Request req) throws Exception;

    List<MissionInfoDto.Response> getMyMission(Long userId) throws Exception;

    SuccessOrNotMissionDto.Response successMission(SuccessOrNotMissionDto.Request req) throws Exception;

    SuccessOrNotMissionDto.Response failMission(SuccessOrNotMissionDto.Request req) throws Exception;

    SuccessOrNotMissionDto.Response quitMission(SuccessOrNotMissionDto.Request req) throws Exception;

    Object getHistoryDetail(String identificationNumber, String accountNumber, Long historyId);
}
