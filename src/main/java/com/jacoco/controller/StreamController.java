package com.jacoco.controller;

import com.jacoco.service.StreamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "stream技术", tags = {"stream技术"})
@RestController
@RequiredArgsConstructor
public class StreamController {

    private final StreamService streamService;

    @ApiOperation(value = "查询流的极值（最大值和最小值）")
    @GetMapping("/api/v1/stream/get_max_min")
    public ResponseEntity<?> getMaxAndMinFromSortedGroupOfSteam(@RequestParam String numbers) {
        final List<Integer> numberList = Arrays.stream(numbers.split(",")).map(Integer::parseInt).collect(Collectors.toList());

        return ResponseEntity.ok(streamService.getMaxAndMinFromSortedGroupOfSteam(numberList));
    }

    @ApiOperation(value = "查询极值（最大值和最小值）")
    @GetMapping("/api/v1/get_max_min")
    public ResponseEntity<?> getMaxAndMinFromSortedGroup(@RequestParam String numbers) {
        final List<Integer> numberList = Arrays.stream(numbers.split(",")).map(Integer::parseInt).collect(Collectors.toList());

        return ResponseEntity.ok(streamService.getMaxAndMinFromSortedGroup(numberList));
    }
}
