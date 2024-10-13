package com.finale.sms;

import com.finale.common.ApiResponse;
import com.finale.entity.SmsType;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sms")
public class SmsController {

    private final SmsService smsService;

    private final String DEPOSIT = "deposit";
    private final String REMIND = "remind";

    /**
     * 입금 독촉 문자 발송
     */
    @PostMapping("/remind")
    public ApiResponse remindSend(@RequestBody RemindDTO dto) throws CoolsmsException {
        return smsService.remindSend(dto);
    }

    /**
     * 문자 생성 및 수정 (입금확인, 독촉)
     * deposit / remind
     */
    @Operation(summary = "[코치용] 타입 별 문자 생성 및 수정 API", description = "type 값은 현재 deposit, remind 만 가능합니다!")
    @PostMapping("/create/{type}")
    public ApiResponse createDeposit(@RequestBody TemplateDTO dto, @PathVariable("type") String type) {
        if (DEPOSIT.equals(type)) {
            return smsService.createByType(SmsType.DEPOSIT, dto.text());
        } else if (REMIND.equals(type)) {
            return smsService.createByType(SmsType.REMIND, dto.text());
        } else {
            return ApiResponse.badRequestResponse("type이 잘못되었습니다.");
        }

    }

    /**
     * 입금확인 문자 가져오기
     */
    @Operation(summary = "[코치용] 타입 별 문자 디테일 조회 API", description = "type 값은 현재 deposit, remind 만 가능합니다!")
    @GetMapping("/detail/{type}")
    public ApiResponse depositDetail(@PathVariable("type") String type) {
        if (DEPOSIT.equals(type)) {
            return smsService.getTypeDetail(SmsType.DEPOSIT);
        } else if (REMIND.equals(type)) {
            return smsService.getTypeDetail(SmsType.REMIND);
        } else {
            return ApiResponse.badRequestResponse("type이 잘못되었습니다.");
        }
    }
}
