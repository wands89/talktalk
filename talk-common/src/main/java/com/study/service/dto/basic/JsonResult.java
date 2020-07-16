package com.study.service.dto.basic;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.study.service.dto.enums.HttpStatusMicro;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
@JsonInclude(JsonInclude.Include.ALWAYS)
public class JsonResult {
    private boolean flag = true;
    private String errMessage = null;
    private int code = HttpStatus.OK.value();
    private Object result = null;

    public static JsonResult create() {
        return new JsonResult();
    }

    public static JsonResult success() {
        return create();
    }

    public static JsonResult success(Object result) {
        return create().setResult(result);
    }

    public static JsonResult success(HttpStatusMicro result) {
        return create().setResult(result.getMessage()).setCode(result.getCode());
    }

    public static JsonResult success(int code, Object result) {
        return create().setCode(code).setResult(result);
    }

    public static JsonResult fail(int code, String errMessage) {
        return create().setFlag(false).setCode(code).setErrMessage(errMessage);
    }

    public static JsonResult fail(HttpStatusMicro response) {
        return create().setFlag(false).setCode(response.getCode()).setErrMessage(response.getMessage());
    }

}
