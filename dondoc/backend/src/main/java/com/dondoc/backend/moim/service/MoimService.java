package com.dondoc.backend.moim.service;

import com.dondoc.backend.moim.dto.MissionRequestDto;
import com.dondoc.backend.moim.dto.WithdrawRequestDto;
import com.dondoc.backend.moim.entity.Moim;

public interface MoimService {

    boolean createOnwerAPI(String identificationNumber, String moimName);

    String createAccountAPI(String moimName, int bankCode, String identificationNumber, String password);

    Moim createMoim(String identificationNumber, String moimName, String introduce, String moimAccount, int limited, int moimType);

    WithdrawRequestDto.Response withdrawReq(WithdrawRequestDto.Request req) throws Exception;

    MissionRequestDto.Response missionReq(MissionRequestDto.Request req) throws Exception;
}
