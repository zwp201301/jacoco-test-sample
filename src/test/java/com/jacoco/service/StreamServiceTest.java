package com.jacoco.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamServiceTest {
    private StreamService streamService;
    private List<Integer> numbers;

    @BeforeEach
    void setUp() {
        streamService = new StreamService();
        numbers = Arrays.asList(1, 5, 3, 8, 10, 2, 6, 7, 4, 9);
    }

    @Test
    void getMaxAndMinFromSortedGroup_ShouldReturnExpectedResult() {
        Map<Boolean, List<Integer>> result = streamService.getMaxAndMinFromSortedGroup(numbers);

        // Validate the even numbers group
        List<Integer> evenNumbers = result.get(true);
        assertEquals(Arrays.asList(2, 10), evenNumbers, "Even numbers group should contain the min and max even numbers");

        // Validate the odd numbers group
        List<Integer> oddNumbers = result.get(false);
        assertEquals(Arrays.asList(1, 9), oddNumbers, "Odd numbers group should contain the min and max odd numbers");
    }

    @Test
    void getMaxAndMinFromSortedGroup_justOneEvenElement() {
        Map<Boolean, List<Integer>> result = streamService.getMaxAndMinFromSortedGroup(Arrays.asList(1, 5, 3, 7, 4, 9));

        // Validate the even numbers group
        List<Integer> evenNumbers = result.get(true);
        assertEquals(Arrays.asList(4, 4), evenNumbers, "Even numbers group should contain the min and max even numbers");
    }

    @Test
    void getMaxAndMinFromSortedGroup_notExistEvenElement() {
        Map<Boolean, List<Integer>> result = streamService.getMaxAndMinFromSortedGroup(Arrays.asList(1, 5, 3, 7, 9));

        // Validate the even numbers group
        List<Integer> evenNumbers = result.get(true);
        assertEquals(0, evenNumbers.size(), "There are not exist Even numbers");
    }

    @Test
    void getMaxAndMinFromSortedGroup_justOneOddElement() {
        Map<Boolean, List<Integer>> result = streamService.getMaxAndMinFromSortedGroup(Arrays.asList(2, 6, 8, 10, 4, 9));

        // Validate the odd numbers group
        List<Integer> oddNumbers = result.get(false);
        assertEquals(Arrays.asList(9, 9), oddNumbers, "Odd numbers group should contain the min and max odd numbers");
    }

    @Test
    void getMaxAndMinFromSortedGroup_notExistOddElement() {
        Map<Boolean, List<Integer>> result = streamService.getMaxAndMinFromSortedGroup(Arrays.asList(2, 6, 8, 10, 4));

        // Validate the odd numbers group
        List<Integer> oddNumbers = result.get(false);
        assertEquals(0, oddNumbers.size(), "There are not exist Odd numbers");
    }

    @Test
    void getMaxAndMinFromSortedGroupOfStream_ShouldReturnExpectedResult() {
        Map<Boolean, List<Integer>> result = streamService.getMaxAndMinFromSortedGroupOfSteam(numbers);

        // Validate the even numbers group
        List<Integer> evenNumbers = result.get(true);
        assertEquals(Arrays.asList(2, 10), evenNumbers, "Even numbers group should contain the min and max even numbers");

        // Validate the odd numbers group
        List<Integer> oddNumbers = result.get(false);
        assertEquals(Arrays.asList(1, 9), oddNumbers, "Odd numbers group should contain the min and max odd numbers");
    }
}
