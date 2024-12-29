package site.sharetable.sharetable_api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiCommonResponse<T> {
    private boolean success;
    private T result;

    public ApiCommonResponse(boolean success, T result) {
        this.success = success;
        this.result = result;
    }

    public static ApiCommonResponse<ErrorMessage> errorResponse(String message) {
        ErrorMessage errorMessage = new ErrorMessage(message);
        return new ApiCommonResponse<>(false, errorMessage);
    }


    @Getter
    @AllArgsConstructor
    public static class ErrorMessage {
        private String errorMessage;
    }
}
