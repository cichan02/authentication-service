package by.piskunou.solvdlaba.service.impl;

import by.piskunou.solvdlaba.domain.UpdatePasswordEvent;
import by.piskunou.solvdlaba.domain.User;
import by.piskunou.solvdlaba.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://micro-airport-core/users";
    private final KafkaSender<String, UpdatePasswordEvent> sender;

    @Override
    public User findByUsername(String username) {
        return restTemplate.getForObject(baseUrl + "/username?" + username, User.class);
    }

    @Override
    public User findByEmail(String email) {
        return restTemplate.getForObject(baseUrl + "/email?" + email, User.class);
    }

    @Override
    public Mono<Void> updatePasswordByUsername(UpdatePasswordEvent updatePasswordEvent) {
        sender.send(
                        Mono.just(
                                SenderRecord.create(
                                        "updatePassword",
                                        0,
                                        System.currentTimeMillis(),
                                        "key",
                                        updatePasswordEvent,
                                        null
                                )))
                .subscribe();
        return Mono.empty();
    }

}
