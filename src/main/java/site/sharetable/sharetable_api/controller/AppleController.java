package site.sharetable.sharetable_api.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.sharetable.sharetable_api.apple.AppleService;
import site.sharetable.sharetable_api.dto.ApiCommonResponse;
import site.sharetable.sharetable_api.dto.AppleDTO;


@RestController
@RequiredArgsConstructor
@RequestMapping("apple")
public class AppleController {

    private final AppleService appleService;

    @GetMapping("/test")
    public ResponseEntity<ApiCommonResponse<String>> test() {
        String text = appleService.testAppleId();

        ApiCommonResponse<String> response = new ApiCommonResponse<>(true, text);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/callback")
    public ResponseEntity<AppleDTO> callback(HttpServletRequest request) throws Exception {
        AppleDTO appleInfo = appleService.getAppleInfo(request.getParameter("code"));

        return ResponseEntity.ok()
                .body(appleInfo);
    }


}