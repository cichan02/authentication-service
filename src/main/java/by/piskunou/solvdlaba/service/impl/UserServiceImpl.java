package by.piskunou.solvdlaba.service.impl;

import by.piskunou.solvdlaba.domain.User;
import by.piskunou.solvdlaba.domain.event.UpdatePasswordEvent;
import by.piskunou.solvdlaba.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("AIRPORT_CORE_HOST")
    private String airportCoreHost;

    private final WebClient.Builder webClientBuilder;
    private final KafkaSender<String, UpdatePasswordEvent> sender;

    @Override
    public Mono<User> findByUsername(String username) {
        return webClientBuilder.baseUrl("http://" + airportCoreHost + "/users")
                .build()
                .get()
                .uri("/username?username=" + username)
                .retrieve()
                .bodyToMono(User.class);
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return webClientBuilder.baseUrl("http://" + airportCoreHost + "/users")
                .build()
                .get()
                .uri("/email?email=" + email)
                .retrieve()
                .bodyToMono(User.class);
    }

    @Override
    public Mono<Void> updatePasswordByUsername(UpdatePasswordEvent updatePasswordEvent) {
        sender.send( Mono.just(
                    SenderRecord.create(
                            "updatePassword",
                            0,
                            System.currentTimeMillis(),
                            updatePasswordEvent.getUuid().toString(),
                            updatePasswordEvent,
                            null
                    )
                ) )
                .subscribe();
        return Mono.empty();
    }

}
