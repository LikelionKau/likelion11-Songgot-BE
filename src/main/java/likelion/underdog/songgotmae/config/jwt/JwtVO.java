package likelion.underdog.songgotmae.config.jwt;

public interface JwtVO {
    public static final String SECRET_KEY = "임시언더독"; // 임시키

    // TODO : refreshToken, OIDC 구현 보류

    public static final int EXPIRATION_TIME = 1000 * 60 * 60 * 2; // 엑세스 토큰 유효시간 : 2시간
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";
}
