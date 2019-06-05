/*
 * Created at 11:33 on 2019-06-05
 */
package com.example.respmix;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author zzhao
 */
@ControllerAdvice
@Slf4j
public class MyExceptionHandler {

  @ExceptionHandler(value = IllegalArgumentException.class)
  @ResponseStatus(value = HttpStatus.CONFLICT)
  @ResponseBody
  Map<String, String> handleIllegalArgumentException(Exception ex, HttpServletResponse response) {
    log.error("<handleIllegalArgumentException> {}", HttpStatus.CONFLICT, ex);
    response.setStatus(HttpStatus.CONFLICT.value());
    final HashMap<String, String> map = new HashMap<>();
    map.put("code", HttpStatus.CONFLICT.getReasonPhrase());
    map.put("reason", ex.getMessage());

    return map;
  }
}
