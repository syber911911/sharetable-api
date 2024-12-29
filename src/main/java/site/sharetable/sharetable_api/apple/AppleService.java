package site.sharetable.sharetable_api.apple;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.*;
import java.net.URL;
import java.time.Instant;
import java.util.Date;
import org.bouncycastle.util.io.pem.*;

@Service
public class AppleService {

    @Value("${apple.team.id}")
    private String APPLE_TEAM_ID;

    @Value("${apple.login.key}")
    private String APPLE_LOGIN_KEY;

    @Value("${apple.client.id}")
    private String APPLE_CLIENT_ID;

    @Value("${apple.redirect.url}")
    private String APPLE_REDIRECT_URL;

    @Value("${apple.key.path}")
    private String APPLE_KEY_PATH;

    private final static String APPLE_AUTH_URL = "https://appleid.apple.com";

    public String getAppleLogin() {
        return APPLE_AUTH_URL + "/auth/authorize"
                + "?client_id=" + APPLE_CLIENT_ID
                + "&redirect_uri=" + APPLE_REDIRECT_URL
                + "&response_type=code%20id_token&scope=name%20email&response_mode=form_post";
    }

    public AppleDTO getAppleInfo(String code) throws Exception {
        if (code == null) throw new Exception("Failed get authorization code");

        String clientSecret = createClientSecret();
        String userId = "";
        String email  = "";
        String accessToken = "";

        WebClient webClient = WebClient.builder()
                .baseUrl(APPLE_AUTH_URL + "/auth/token")
                .defaultHeader("Content-type", "application/x-www-form-urlencoded")
                .build();

        String response = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", APPLE_CLIENT_ID)
                        .queryParam("client_secret", clientSecret)
                        .queryParam("code", code)
                        .queryParam("redirect_uri", APPLE_REDIRECT_URL)
                        .build()
                )
                .retrieve()
                .bodyToMono(String.class)
                .retry(2)
                .block();

        assert response != null;
        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();

        accessToken = jsonObject.get("access_token").getAsString();
        String idToken = jsonObject.get("id_token").getAsString();

        String subject = Jwts.parser()
                .parseClaimsJws(idToken)
                .getBody()
                .getSubject();

        JsonObject payload = JsonParser.parseString(subject).getAsJsonObject();
        userId = payload.get("sub").getAsString();
        email = payload.get("email").getAsString();

        return AppleDTO.builder()
                .id(userId)
                .token(accessToken)
                .email(email).build();
    }

    private String createClientSecret() throws Exception {
        Date now = Date.from(Instant.now());
        Date expirationTime = Date.from(Instant.now().plusSeconds(3600 * 24));

        return Jwts.builder()
                .setHeaderParam("kid", APPLE_LOGIN_KEY)
                .setHeaderParam("alg", "ES256")
                .setIssuer(APPLE_TEAM_ID)
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .setAudience(APPLE_AUTH_URL)
                .setSubject(APPLE_CLIENT_ID)
                .signWith(SignatureAlgorithm.ES256, getPrivateKey())
                .compact();
    }

    private byte[] getPrivateKey() throws Exception {
        byte[] content = null;
        File file = null;

        URL res = getClass().getResource(APPLE_KEY_PATH);

        if ("jar".equals(res.getProtocol())) {
            try {
                InputStream input = getClass().getResourceAsStream(APPLE_KEY_PATH);
                file = File.createTempFile("tempfile", ".tmp");
                OutputStream out = new FileOutputStream(file);

                int read;
                byte[] bytes = new byte[1024];

                while ((read = input.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }

                out.close();
                file.deleteOnExit();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            file = new File(res.getFile());
        }

        if (file.exists()) {
            try (FileReader keyReader = new FileReader(file);
                 PemReader pemReader = new PemReader(keyReader))
            {
                PemObject pemObject = pemReader.readPemObject();
                content = pemObject.getContent();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new Exception("File " + file + " not found");
        }

        return content;
    }


}