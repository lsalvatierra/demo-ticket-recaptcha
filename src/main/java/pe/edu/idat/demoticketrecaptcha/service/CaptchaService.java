package pe.edu.idat.demoticketrecaptcha.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
@Service
public class CaptchaService {

    @Value("${recaptcha.secret.key}")
    private String secretKey;

    private static final String VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public boolean isCaptchaValid(String response) {
        if (response == null || response.isEmpty()) {
            return false;
        }

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("secret", secretKey);
        body.add("response", response);

        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> googleResponse = restTemplate.postForObject(VERIFY_URL, body, Map.class);
            return (Boolean) googleResponse.get("success");
        } catch (Exception e) {
            return false;
        }
    }
}
