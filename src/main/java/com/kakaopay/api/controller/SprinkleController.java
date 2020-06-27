package com.kakaopay.api.controller;

import com.kakaopay.api.entity.SprinkleDetailVO;
import com.kakaopay.api.entity.SprinkleVO;
import com.kakaopay.api.helper.exception.MalformedRequestException;
import com.kakaopay.api.helper.exception.SprinkleException;
import com.kakaopay.api.helper.response.ResponseCode;
import com.kakaopay.api.helper.response.ResponseMessage;
import com.kakaopay.api.service.SprinkleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sprinkle")
@RequiredArgsConstructor
@Slf4j
public class SprinkleController extends BaseController {
    private final SprinkleService sprinkleService;

    /**
     *  URI Mapping : 뿌리기
     * @param money - 뿌릴 금액
     * @param division - 뿌릴 인원
     * @return ResponseMessage : data - 뿌리기 요청건에 대한 고유 token
     */
    @GetMapping(value = "/generate", produces = "application/json; charset=UTF-8")
    public ResponseEntity<ResponseMessage<String>> generate(
            @RequestParam(value = "money") int money,
            @RequestParam(value = "division") int division) {

        ResponseMessage.ResponseMessageBuilder<String> builder = ResponseMessage.builder();
        try {
            SprinkleVO sprinkleVO = sprinkleService.generate(money, division);
            builder.data(sprinkleVO.getToken());
            builder.code(ResponseCode.CODE_0.getCode());
            builder.message(ResponseCode.CODE_0.getMessage());
        } catch (MalformedRequestException e) {
            failure(builder, ResponseCode.CODE_11);
        } catch (SprinkleException e) {
            failure(builder, e.getResponseCode());
        }
        return ResponseEntity.ok(builder.build());
    }

    /**
     *  URI Mapping : 받기
     * @param token - 뿌리기 시 발급된 token
     * @return ResponseMessage : data - 주운 금액
     */
    @GetMapping(value = "/acquire", produces = "application/json; charset=UTF-8")
    public ResponseEntity<ResponseMessage<Integer>> acquire(
            @RequestParam("token") String token) {

        ResponseMessage.ResponseMessageBuilder<Integer> builder = ResponseMessage.builder();
        try {
            SprinkleDetailVO sprinkleDetailVO = sprinkleService.acquire(token);
            builder.code(ResponseCode.CODE_0.getCode());
            builder.message(ResponseCode.CODE_0.getMessage());
            builder.data(sprinkleDetailVO.getDivMoney());
        } catch (MalformedRequestException e) {
            failure(builder, ResponseCode.CODE_11);
        } catch (SprinkleException e) {
            failure(builder, e.getResponseCode());
        }
        return ResponseEntity.ok(builder.build());
    }

    /**
     *  URI Mapping : 조회
     * @param token - 뿌리기 시 발급된 token
     * @return SprinkleDetailVO 또는 실패 응답
     */
    @GetMapping(value = "/info", produces = "application/json; charset=UTF-8")
    public ResponseEntity<ResponseMessage<SprinkleVO>> info(
            @RequestParam("token") String token) {

        ResponseMessage.ResponseMessageBuilder<SprinkleVO> builder = ResponseMessage.builder();
        try {
            SprinkleVO sprinkleVO = sprinkleService.fetchInfo(token);
            if (sprinkleVO != null) {
                builder.code(ResponseCode.CODE_0.getCode());
                builder.message(ResponseCode.CODE_0.getMessage());
                builder.data(sprinkleVO);
            } else {
                failure(builder, ResponseCode.CODE_10);
            }
        } catch (MalformedRequestException e) {
            failure(builder, ResponseCode.CODE_11);
        } catch (SprinkleException e) {
            failure(builder, e.getResponseCode());
        }
        return ResponseEntity.ok(builder.build());
    }
}
