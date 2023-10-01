package com.dondoc.backend.moim.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MoimHistoryApiDto {

    @Data
    public static class ListResponse{
        private String success;
        private List<ListResponseResponse> response;
        private ErrorDto error;

    }
    @Data
    public static class ListResponseResponse{
        private History historyId;
        private BankCodeDto toCode;
        private String memo;
    }

    @Data
    public static class History{
        private Long id;

        private String toAccount;

        private int type;

        private int transferAmount;

        private int afterBalance;

        private String sign;

        private String toSign;

        private LocalDateTime createdAt;
    }
    @Data
    public static class BankCodeDto{
        private Long bankCodeId;
        private String bankName;
    }

    @Data
    public static class ErrorDto{
        private String message;
        private int status;
    }

}
