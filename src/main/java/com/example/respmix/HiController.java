/*
 * Created at 11:35 on 2019-03-13
 */
package com.example.respmix;

import io.micrometer.core.annotation.Timed;
import java.util.Collections;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zzhao
 */
@RestController
@RequestMapping("/hi/{" + ZoneWebMvcTagsProvider.PATH_VAR_NAME_ZONE + "}")
public class HiController {

  @Timed("hi_there")
  @GetMapping("there")
  Map<String, String> hiThere() {
    return Collections.singletonMap("hi", "there");
  }
}
