package com.finale.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    public ApiResponse main() {
        return ApiResponse.successResponse("피날레 백엔드 서버입니다!");
    }
}
