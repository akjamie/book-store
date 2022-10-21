package org.akj.springboot.user.controller;

import org.akj.springboot.common.domain.Result;
import org.akj.springboot.user.exception.ErrorCodeMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/v1/errors")
public class ErrorCodeMappingController {

    @GetMapping
    public Result listErrorCodeMapping() {
        Map<String, String> map = new TreeMap<>();
        Arrays.stream(ErrorCodeMap.values()).sorted(Comparator.comparing(ErrorCodeMap::code))
                .forEach(mp -> map.putIfAbsent(mp.code(), mp.message()));

        return Result.success(map);
    }
}
