package com.dondoc.backend.notify.service;

import com.dondoc.backend.notify.entity.Notify;

import java.util.List;

public interface NotifyService {

    Notify createNotify(String type,String title,String content,Long target);

    List<Notify> getNotifyList(Long userId);
}
