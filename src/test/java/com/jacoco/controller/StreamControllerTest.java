package com.jacoco.controller;

import com.alibaba.fastjson.JSON;
import com.jacoco.JacocoTestApplication;
import com.jacoco.utils.AbstractRestfulApiTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JacocoTestApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("utest")
public class StreamControllerTest extends AbstractRestfulApiTest {
    @Before
    public void before() throws Exception {

    }

    @Test
    public void getMaxAndMinFromSortedGroupOfSteam_ValidInput_ReturnsExpectedResponse() throws Exception {
        String url = "/api/v1/stream/get_max_min";
        MockHttpServletRequestBuilder requestBuilder = get(url).contentType(JSON_TYPE)
                .param("numbers", "1,2,3,4,5,6,7,8,9,10");

        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        // 解析结果
        Map<Boolean, List<Integer>> contentAsMap = JSON.parseObject(result.getResponse().getContentAsString(), HashMap.class);

        List<Integer> evenNumbers = contentAsMap.get("true");
        assertEquals(Arrays.asList(2, 10), evenNumbers, "Even numbers group should contain the min and max even numbers");

        List<Integer> oddNumbers = contentAsMap.get("false");
        assertEquals(Arrays.asList(1, 9), oddNumbers, "Even numbers group should contain the min and max even numbers");
    }


    @Test
    public void getMaxAndMinFromSortedGroup_ValidInput_ReturnsExpectedResponse() throws Exception {
        String url = "/api/v1/get_max_min";
        MockHttpServletRequestBuilder requestBuilder = get(url).contentType(JSON_TYPE)
                .param("numbers", "1,2,3,4,5,6,7,8,9,10");

        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        // 解析结果
        Map<Boolean, List<Integer>> contentAsMap = JSON.parseObject(result.getResponse().getContentAsString(), HashMap.class);

        List<Integer> evenNumbers = contentAsMap.get("true");
        assertEquals(Arrays.asList(2, 10), evenNumbers, "Even numbers group should contain the min and max even numbers");

        List<Integer> oddNumbers = contentAsMap.get("false");
        assertEquals(Arrays.asList(1, 9), oddNumbers, "Even numbers group should contain the min and max even numbers");
    }
}
