package by.piskunou.solvdlaba.service;

import reactor.core.publisher.Mono;

import java.util.Map;

public interface EmailService {

    Mono<Void> sendMessage(String email, Map<String, Object> templateModel);

}
