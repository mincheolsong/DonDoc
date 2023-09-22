package com.dondoc.backend.notify.service;

import com.dondoc.backend.notify.entity.Notify;

public interface NotifyService {

    Notify createNotify(String type,String title,String content,Long target);
}
