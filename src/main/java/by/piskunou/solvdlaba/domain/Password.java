package by.piskunou.solvdlaba.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Password {

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

}
