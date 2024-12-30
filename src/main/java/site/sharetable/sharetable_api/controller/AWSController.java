package site.sharetable.sharetable_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import site.sharetable.sharetable_api.dto.ApiCommonResponse;

@RestController
public class AWSController {

    @GetMapping("/health")
    public ResponseEntity<ApiCommonResponse<String>> healthCheck() {

        String result = "Share Table Server Healthy";
        ApiCommonResponse<String> apiResponse = new ApiCommonResponse<>(true, result);

        return ResponseEntity.ok(apiResponse);
    }
}
