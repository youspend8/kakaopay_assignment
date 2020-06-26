package com.kakaopay.api.service;

import com.kakaopay.api.helper.exception.MalformedRequestException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public abstract class BaseService {
    @Autowired
    protected HttpServletRequest request;

    /**
     * 요청한 사용자의 식별값(X-USER-ID)을 Request HTTP Request Header 로부터 가져오는 함수
     * @return HTTP Header : X-USER-ID
     * @throws MalformedRequestException - X-USER-ID 값이 존재하지 않을경우 발생
     */
    protected String getRequestUserId() throws MalformedRequestException {
        return Optional.ofNullable(request.getHeader("X-USER-ID"))
                .orElseThrow(MalformedRequestException::new);
    }

    /**
     * 요청한 사용자가 속한 대화방의 식별값(X-ROOM-ID)을 HTTP Request Header 로부터 가져오는 함수
     * @return HTTP Header : X-ROOM-ID
     * @throws MalformedRequestException - X-USER-ID 값이 존재하지 않을경우 발생
     */
    protected String getRequestRoomId() throws MalformedRequestException {
        return Optional.ofNullable(request.getHeader("X-ROOM-ID"))
                .orElseThrow(MalformedRequestException::new);

    }
}
