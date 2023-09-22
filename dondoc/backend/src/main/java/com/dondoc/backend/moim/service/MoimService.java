package com.dondoc.backend.moim.service;

import com.dondoc.backend.moim.dto.MoimDetailDto;
import com.dondoc.backend.moim.dto.MoimListDto;
import com.dondoc.backend.moim.entity.Moim;

import java.util.List;
import java.util.Map;

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
}
