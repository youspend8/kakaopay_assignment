package com.kakaopay.api.controller;

import com.kakaopay.api.entity.ResponseMessage;
import com.kakaopay.api.entity.SprinkleDetailVO;
import com.kakaopay.api.entity.SprinkleVO;
import com.kakaopay.api.helper.exception.SprinkleException;
import com.kakaopay.api.helper.exception.MalformedRequestException;
import com.kakaopay.api.helper.response.ResponseCode;
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
     *  1. 뿌리기 API
     * ● 다음 조건을 만족하는 뿌리기 API를 만들어 주세요.
     * ○ 뿌릴 금액, 뿌릴 인원을 요청값으로 받습니다.
     * ○ 뿌리기 요청건에 대한 고유 token을 발급하고 응답값으로 내려줍니다.
     * ○ 뿌릴 금액을 인원수에 맞게 분배하여 저장합니다. (분배 로직은 자유롭게 구현해 주세요.)
     * ○ token은 3자리 문자열로 구성되며 예측이 불가능해야 합니다.
     * @param money - 뿌릴 금액
     * @param division - 뿌릴 인원
     * @return token ( type JSON ) - 뿌리기 요청건에 대한 고유 token
     */
    @GetMapping(value = "/generate", produces = "application/json; charset=UTF-8")
    public ResponseEntity<ResponseMessage<String>> generate(
            @RequestParam(value = "money") int money,
            @RequestParam(value = "division") int division) throws Exception {

        ResponseMessage.ResponseMessageBuilder<String> builder = ResponseMessage.builder();
        try {
            SprinkleVO sprinkleVO = sprinkleService.generate(money, division);
            builder.data(sprinkleVO.getToken());
            builder.code(ResponseCode.CODE_0.getCode());
            builder.message(ResponseCode.CODE_0.getMessage());
        } catch (MalformedRequestException e) {
            failure(builder, ResponseCode.CODE_11);
        }
        return ResponseEntity.ok(builder.build());
    }

    /**
     *  URI Mapping : 받기
     *  2. 받기 API
     * ● 다음 조건을 만족하는 받기 API를 만들어 주세요.
     * ○ 뿌리기 시 발급된 token을 요청값으로 받습니다.
     * ○ token에 해당하는 뿌리기 건 중 아직 누구에게도 할당되지 않은 분배건 하나를 API를 호출한 사용자에게 할당하고, 그 금액을 응답값으로 내려줍니다.
     * ○ 뿌리기 당 한 사용자는 한번만 받을 수 있습니다.
     * ○ 자신이 뿌리기한 건은 자신이 받을 수 없습니다.
     * ○ 뿌리기가 호출된 대화방과 동일한 대화방에 속한 사용자만이 받을 수 있습니다.
     * ○ 뿌린 건은 10분간만 유효합니다. 뿌린지 10분이 지난 요청에 대해서는 받기 실패 응답이 내려가야 합니다.
     * @param token - 뿌리기 시 발급된 token
     * @return response - 응답 (주운 금액 또는 받기 실패 응답)
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
        System.out.println("builder :: "+ builder.build());
        return ResponseEntity.ok(builder.build());
    }

    /**
     *  URI Mapping : 조회
     *  3. 조회 API
     * ● 다음 조건을 만족하는 조회 API를 만들어 주세요.
     * ○ 뿌리기 시 발급된 token을 요청값으로 받습니다.
     * ○ token에 해당하는 뿌리기 건의 현재 상태를 응답값으로 내려줍니다. 현재 상태는 다음의 정보를 포함합니다.
     * ○ 뿌린 시각, 뿌린 금액, 받기 완료된 금액, 받기 완료된 정보 ([받은 금액, 받은 사용자 아이디] 리스트)
     * ○ 뿌린 사람 자신만 조회를 할 수 있습니다. 다른사람의 뿌리기건이나 유효하지 않은 token에 대해서는 조회 실패 응답이 내려가야 합니다.
     * ○ 뿌린 건에 대한 조회는 7일 동안 할 수 있습니다.
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
