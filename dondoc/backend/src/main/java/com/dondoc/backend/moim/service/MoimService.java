package com.dondoc.backend.moim.service;

import com.dondoc.backend.moim.entity.Moim;

public interface MoimService {
    boolean createOnwerAPI(String identificationNumber, String moimName);

    String createAccountAPI(String moimName, int bankCode, String identificationNumber, String password);

    Moim createMoim(String identificationNumber, String moimName, String introduce, String moimAccount, int limited, int moimType);
}
