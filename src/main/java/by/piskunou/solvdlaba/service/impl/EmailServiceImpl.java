package by.piskunou.solvdlaba.service.impl;

import by.piskunou.solvdlaba.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Override
    public void sendMessage(String email, Map<String, Object> templateModel) {

    }

}
