package com.dondoc.backend.moim.service;

import com.dondoc.backend.moim.entity.Moim;
import java.util.List;
import java.util.Map;
import com.dondoc.backend.moim.dto.*;
import com.dondoc.backend.user.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;


public interface MoimService {

    boolean createOnwerAPI(String identificationNumber, String moimName);

    void delete(Moim moim);
    boolean checkActivate(Long moimId) throws Exception;

    Map<String,Object> createAccountAPI(String moimName, int bankCode, String identificationNumber, String password);

    Moim createMoim(User user, String moimName, String introduce, String password, Long accountId, int moimType, List<MoimCreateDto.InviteDto> manager) throws Exception;

    boolean checkIdenNumDuplicate(String identificationNumber);

    String makeIdentificationNumber() throws Exception;

    List<Moim> getMoimList(Long userId);

    MoimDetailDto.Response getMoimDetail(Long userId, Long moimId) throws Exception;

    List<Moim> findMoimWithMember(Long moimId);

    int searchBalance(String identificationNumber) throws JsonProcessingException;

    Moim findById(Long id) throws Exception;

    WithdrawRequestDto.Response withdrawReq(Long userId, WithdrawRequestDto.Request req) throws Exception;

    List<Object> getHistoryList(String identificationNumber, String accountNumber);

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

    Object getHistoryDetail(String identificationNumber, String accountNumber, Long historyId);
    //List<MoimMyDataDto.TransferResponse> getTransferAmount(String identificationNumber,String moimAccountNumber,String memberAccountNumber,String month);
}
