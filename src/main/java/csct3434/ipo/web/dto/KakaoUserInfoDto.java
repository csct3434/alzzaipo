package csct3434.ipo.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class KakaoUserInfoDto {
    private final Long id;
    private final String email;
    private final String nickname;

    @Builder
    public KakaoUserInfoDto(Long id, String email, String nickname) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }
}
