package com.dondoc.backend.moim.dto;

import com.dondoc.backend.moim.entity.Moim;
import com.dondoc.backend.moim.entity.MoimMember;
import com.dondoc.backend.moim.entity.WithdrawRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class MoimDetailDto {

    public static class Response{
        /**
         * - 모임 id
         * - 모임 이름
         * - 모임 설명
         * - 회원 리스트 (회원ID, 이름, 계좌번호)
         * - 모임 계좌번호
         * - 모임 식별번호
         */
        private Long moimId;
        private String moimName;
        private String introduce;
        private List<MoimMemberDto> moimMembers;
        private String moimAccountNumber;
        private String identificationNumber;
        public Response(){}
        public Response(Moim moim) {
            this.moimId = moim.getId();
            this.moimName = moim.getMoimName();
            this.introduce = moim.getIntroduce();
            this.moimMembers = moim.getMoimMemberList().stream()
                    .map(moimMember -> new MoimMemberDto(moimMember))
                    .collect(toList());
            this.moimAccountNumber = moim.getMoimAccountNumber();
            this.identificationNumber = moim.getIdentificationNumber();
        }
    }
    @NoArgsConstructor
    @Data
    public static class MemberResponse extends Response{
        /**
         * - 모임 id
         * - 모임 이름
         * - 모임 설명
         * - 회원 리스트 (회원ID, 이름, 계좌번호)
         * - 모임 계좌번호
         * - 모임 식별번호
         */
        public MemberResponse(Moim moim){
            super(moim);
        }

    }
    @NoArgsConstructor
    @Data
    public static class ManagerResponse extends Response{
        /**
         * - 모임 id
         * - 모임 이름
         * - 모임 설명
         * - 회원 리스트 (회원ID, 이름, 계좌번호)
         * - 모임 계좌번호
         * - 모임 식별번호
         * - 잔액
         * - 처리되지 않은 요청 정보 (요청한 사람 id)
         */
        private int balance;
        private List<WithDrawRequestDto> request;
        public ManagerResponse(Moim moim,int balance){
            super(moim);
            this.balance = balance;
        }
    }

    /**
     * Response에서 사용하는 클래스
     */
    private static class MoimMemberDto{
        private Long userId;
        private Long moimMemberId;
        private String nickname;
        private String accountNumber;

        public MoimMemberDto(MoimMember moimMember){
            userId = moimMember.getUser().getId();
            moimMemberId = moimMember.getId();
            nickname = moimMember.getUser().getNickName();
            accountNumber = moimMember.getAccount().getAccountNumber();
        }
    }

    /**
     * Manager Response에서 사용하는 클래스
     */
    public static class WithDrawRequestDto{
        Long userId;
        Long moimMemberId;
        String nickname;

        public WithDrawRequestDto(WithdrawRequest withdrawRequest,MoimMember moimMember){
            this.userId = moimMember.getUser().getId();
            this.moimMemberId = moimMember.getId();
            this.nickname = moimMember.getUser().getNickName();
        }
    }
}
