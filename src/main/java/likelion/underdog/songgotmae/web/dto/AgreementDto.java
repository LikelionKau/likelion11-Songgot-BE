package likelion.underdog.songgotmae.web.dto;


import likelion.underdog.songgotmae.domain.post.Post;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AgreementDto {

    @Data
    public static class Create {
        @NotNull
        private Post post;
        @NotNull
        private Boolean isAgree;
    }

    @Data
    public static class Response {
        private Long agreementId;
        private String message;

        @Builder
        public Response(Long agreementId, String message) {
            this.agreementId = agreementId;
            this.message = message;
        }
    }
}
