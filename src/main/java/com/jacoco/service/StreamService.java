package com.jacoco.service;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * java stream流操作.
 *
 * @author xxx
 */
@Slf4j
@Service
public class StreamService {

    public Map<Boolean, List<Integer>> getMaxAndMinFromSortedGroupOfSteam(List<Integer> numbers) {
        //分组、排序并提取最小和最大值
        Map<Boolean, List<Integer>> result = numbers.stream()
                .collect(Collectors.groupingBy(n -> n % 2 == 0,
                        //分组：奇数和偶数
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                //收集到列表
                                list -> {
                                    //对列表进行排序
                                    Collections.sort(list);
                                    //提取并返回最小和最大值
                                    return Lists.newArrayList(list.get(0), list.get(list.size() - 1));
                                }
                        )));

        return result;
    }

    public Map<Boolean, List<Integer>> getMaxAndMinFromSortedGroup(List<Integer> numbers) {
        Map<Boolean, List<Integer>> result = new HashMap<>();
        //偶数分组
        result.put(true, new ArrayList<>());
        //奇数分组
        result.put(false, new ArrayList<>());

        for (Integer number : numbers) {
            if (number % 2 == 0) {
                result.get(true).add(number);
            } else {
                result.get(false).add(number);
            }
        }

        //对分组后的列表进行排序
        Collections.sort(result.get(true));
        Collections.sort(result.get(false));

        //提取并设置最小和最大值（注意这里需要使用get(list.size()-1)来获取最大值）
        List<Integer> evenResult = result.get(true);
        if (evenResult.size() > 1) {
            result.put(true, Arrays.asList(evenResult.get(0), evenResult.get(evenResult.size() - 1)));
        } else if (evenResult.size() == 1) {
            //如果只有一个元素，则最小值和最大值相同
            result.put(true, Arrays.asList(evenResult.get(0), evenResult.get(0)));
        } else {
            //如果没有元素，则为空列表
            result.put(true, Collections.emptyList());
        }

        List<Integer> oddResult = result.get(false);
        if (oddResult.size() > 1) {
            result.put(false, Arrays.asList(oddResult.get(0), oddResult.get(oddResult.size() - 1)));
        } else if (oddResult.size() == 1) {
            //如果只有一个元素，则最小值和最大值相同
            result.put(false, Arrays.asList(oddResult.get(0), oddResult.get(0)));
        } else {
            //如果没有元素，则为空列表
            result.put(false, Collections.emptyList());
        }

        for (Map.Entry<Boolean, List<Integer>> entry : result.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + StringUtils.join(entry.getValue()));
        }

        return result;
    }

}
