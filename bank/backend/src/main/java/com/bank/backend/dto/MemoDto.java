package com.bank.backend.dto;

import com.bank.backend.entity.Account;
import com.bank.backend.entity.History;
import com.bank.backend.entity.Memo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class MemoDto {

    @ApiModel(value = "메모 작성", description = "메모 작성에 필요한 요청값 DTO")
    @Data
    public static class Request {

        @ApiModelProperty(value = "식별번호")
        @NotBlank(message = "식별번호를 입력해주세요.")
        private String identificationNumber;

        @ApiModelProperty(value = "계좌번호")
        @NotBlank(message = "계좌번호를 입력해주세요.")
        private String accountNumber;

        @ApiModelProperty(value = "거래내역ID")
        @NotNull(message = "거래내역ID를 입력해주세요.")
        private Long historyId;

        @ApiModelProperty(value = "작성할 내용")
        private String content;

        @Builder
        public static Memo saveMemoDto(History history, String content){
            return Memo.builder()
                    .historyId(history)
                    .content(content)
                    .build();
        }
    }

    @Builder
    @Data
    public static class Response {

        private Long id;
        private History historyId;
        private String content;

        public static MemoDto.Response toDTO(Memo entity) {
            return Response.builder()
                    .id(entity.getId())
                    .historyId(entity.getHistoryId())
                    .content(entity.getContent())
                    .build();
        }
    }

}
