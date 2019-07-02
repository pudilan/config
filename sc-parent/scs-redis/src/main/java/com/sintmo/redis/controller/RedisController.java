package com.sintmo.redis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sintmo.redis.controller.result.BaseResult;
import com.sintmo.redis.entity.ResultEntity;
import com.sintmo.redis.service.RedisService;

@Controller
@RequestMapping("/redis/")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @RequestMapping("create")
    @ResponseBody
    public BaseResult<String> create(@RequestParam("type") String type, @RequestParam("url") String url) {
        BaseResult<String> result = null;
        try {
            String id = redisService.create(type, url);
            result = BaseResult.success(id);
        } catch (Exception e) {
            result = BaseResult.fail("99", e.getMessage());
        }
        return result;
    }

    @RequestMapping("keys")
    public List<String> keys(@RequestParam String keyword) {
        List<String> keys = null;
        return keys;
    }

    @RequestMapping("getByKey")
    public List<ResultEntity<Object>> getByKey(@RequestParam String key) {
        List<ResultEntity<Object>> list = null;
        return list;
    }

    @RequestMapping("removeByKey")
    public String removeByKey(@RequestParam String key) {
        return "success";
    }

}
