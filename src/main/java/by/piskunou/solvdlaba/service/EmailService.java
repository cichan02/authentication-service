package by.piskunou.solvdlaba.service;

import java.util.Map;

public interface EmailService {

    void sendMessage(String email, Map<String, Object> templateModel);

}
