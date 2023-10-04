package com.dondoc.backend.notify.service;

import com.dondoc.backend.common.exception.NotFoundException;
import com.dondoc.backend.notify.entity.Notify;
import com.dondoc.backend.notify.repository.NotifyRepository;
import com.dondoc.backend.user.entity.User;
import com.dondoc.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotifyServiceImpl implements NotifyService {

    private final NotifyRepository notifyRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public Notify createNotify(String type, String title, String content, Long target) {
        Notify notify = new Notify(title,content,Integer.parseInt(type));
        User user = userRepository.findById(target).orElseThrow(()->new NotFoundException("userId = " + target + "인 User가 존재하지 않습니다"));
        notify.setUser(user);
        notifyRepository.save(notify);

        return notify;
    }

    @Override
    public List<Notify> getNotifyList(Long userId) {
        return notifyRepository.findByUserId(userId);
    }
}
