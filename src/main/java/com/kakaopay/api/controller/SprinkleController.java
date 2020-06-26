package com.kakaopay.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kakaopay.api.entity.SprinkleVO;
import com.kakaopay.api.helper.exception.MalformedRequest;
import com.kakaopay.api.service.SprinkleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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
     * @return token - 뿌리기 요청건에 대한 고유 token
     */
    @GetMapping("")
    public ResponseEntity<Map<String, String>> index(
            @RequestParam(value = "money") int money,
            @RequestParam(value = "division") int division) throws JsonProcessingException {
        Map<String, String> resultMap = new HashMap<>();

        try {
            SprinkleVO sprinkleVO = sprinkleService.generateSprinkle(money, division);
            resultMap.put("token", sprinkleVO.getToken());

            return ResponseEntity.ok(resultMap);

        } catch (MalformedRequest e) {
            e.printStackTrace();

            resultMap.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(resultMap);
        }
    }

    /**
     *  URI Mapping : 받기
     *  2. 받기 API
     * ● 다음 조건을 만족하는 받기 API를 만들어 주세요.
     * ○ 뿌리기 시 발급된 token을 요청값으로 받습니다.
     * ○ token에 해당하는 뿌리기 건 중 아직 누구에게도 할당되지 않은 분배건 하나를 API를 호출한 사용자에게 할당하고, 그 금액을 응답값으로 내려줍니다.
     * ○ 뿌리기 당 한 사용자는 한번만 받을 수 있습니다.
     * ○ 자신이 뿌리기한 건은 자신이 받을 수 없습니다.
     * ○ 뿌린기가 호출된 대화방과 동일한 대화방에 속한 사용자만이 받을 수 있습니다.
     * ○ 뿌린 건은 10분간만 유효합니다. 뿌린지 10분이 지난 요청에 대해서는 받기 실패 응답이 내려가야 합니다.
     * @param token - 뿌리기 시 발급된 token
     * @return response - 응답 (주운 금액 또는 받기 실패 응답)
     */
    @GetMapping(value="/acquire")
    public ResponseEntity<String> acquire(
            @RequestParam("token") String token) {

        return ResponseEntity.ok("acquire");
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
    @GetMapping(value="/info")
    public ResponseEntity<String> info(
            @RequestParam("token") String token) {

        return ResponseEntity.ok("info");
    }
}
