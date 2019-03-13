/*
 * Created at 11:38 on 2019-03-13
 */
package com.example.respmix;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.actuate.metrics.web.servlet.DefaultWebMvcTagsProvider;
import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTags;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerMapping;

/**
 * @author zzhao
 */
@Component
public class ZoneWebMvcTagsProvider extends DefaultWebMvcTagsProvider {

  public static final String PATH_VAR_NAME_ZONE = "zone";

  private static final Set<String> ZONES_ALLOWED = new HashSet<>(Arrays.asList(
      "foo",
      "bar"
  ));

  @Override
  public Iterable<Tag> getTags(HttpServletRequest request, HttpServletResponse response,
      Object handler, Throwable exception) {
    final String zoneName = getZoneName(request).orElse("");

    return ZONES_ALLOWED.contains(zoneName)
        ? Tags.of(WebMvcTags.method(request), WebMvcTags.uri(request, response),
        Tag.of(PATH_VAR_NAME_ZONE, zoneName))
        : Tags.of(WebMvcTags.method(request), WebMvcTags.uri(request, response));
  }

  @SuppressWarnings("unchecked")
  private Optional<String> getZoneName(HttpServletRequest req) {
    final Map<String, Object> pathVarByName =
        (Map<String, Object>) req.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    if (!CollectionUtils.isEmpty(pathVarByName) && pathVarByName.containsKey(PATH_VAR_NAME_ZONE)) {
      return Optional.ofNullable(String.valueOf(pathVarByName.get(PATH_VAR_NAME_ZONE)));
    }

    return Optional.empty();
  }
}