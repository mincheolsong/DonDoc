package com.dondoc.backend.user.dto.account;

import com.dondoc.backend.user.entity.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

public class HistoryDto {
    @Data
    @Builder
    public static class Response{
        private String msg;
        private Map historyList;
        @JsonIgnore
        private boolean success;
    }
}
