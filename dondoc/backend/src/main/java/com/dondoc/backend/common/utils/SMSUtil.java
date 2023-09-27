package com.dondoc.backend.common.utils;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SMSUtil {

    @Value("${sms.key}")
    private String API_KEY;

    @Value("${sms.secret}")
    private String API_SECRET;

    private DefaultMessageService defaultMessageService;

    @PostConstruct
    private void init(){
        this.defaultMessageService = NurigoApp.INSTANCE.initialize(API_KEY,API_SECRET,"https://api.coolsms.co.kr");
    }

    // 단일 메시지 발송 예제
    public SingleMessageSentResponse sendOne(String to, String verificationCode) {
        Message message = new Message();
        System.out.println(to);
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        message.setFrom("01026807453");
        message.setTo(to);
        message.setText("[돈독] 인증번호 [" + verificationCode + "]를 입력해주세요.");

        SingleMessageSentResponse response = this.defaultMessageService.sendOne(new SingleMessageSendingRequest(message));
        return response;
    }
}
