package by.piskunou.solvdlaba.domain.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendEmailEvent {

    private UUID uuid;
    private String email;
    private String username;
    private String token;

}
