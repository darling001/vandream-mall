package com.vandream;

import com.vandream.mall.business.dto.publish.Advertisement;
import com.vandream.mall.business.execption.PublishException;
import com.vandream.mall.business.service.PublishService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dingjie
 * @date 2018/10/10
 * @time 14:22
 * Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PublishCodeTest {

    @Resource
    private PublishService publishService;
    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void publishCodeListTest(){
        String pubCode="publishCode1";
        List<String> pubCodeList=new ArrayList<>();
        pubCodeList.add(pubCode);
        try {
            List<Advertisement> advertisementList = publishService.previewAdvContent(pubCodeList);
            System.out.println("========================advertisementList.toString() = " + advertisementList.toString());
        } catch (PublishException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void publishRidesTest(){
        String jsonStr="{\n" +
                " \"type\": \"\",\n" +
                " \"list\": [{\n" +
                "  \"id\": \"werwerwtweriue1232445343432433\",\n" +
                "  \"desc\": \"万郡绿建商城上线啦\",\n" +
                "  \"url\": \"http://www.vandream.com/html/information.html?id=10\",\n" +
                "  \"imageUrl\": \"group1/M00/00/01/rBCwJFriCSqAOgD2AAJavz26CqM814.jpg\",\n" +
                "  \"sort\": 1,\n" +
                "  \"publishStatus\": 1\n" +
                " }, {\n" +
                "  \"id\": \"dsrwrewtwetiue1232445343432473\",\n" +
                "  \"desc\": \"供需方入住计划全面开启\",\n" +
                "  \"url\": \"http://mall.vandream.com/#/bulletin/bulletinDetail?id=62&type=5\",\n" +
                "  \"imageUrl\": \"group1/M00/00/16/rBCwI1syAPiAEQRiAAAmywe30l8214.jpg\",\n" +
                "  \"sort\": 2,\n" +
                "  \"publishStatus\": 1\n" +
                " }]\n" +
                "}";
        String publishKey="cms_publish_publishCode1";
        redisTemplate.opsForValue().set(publishKey,jsonStr);

        Object object = redisTemplate.opsForValue().get(publishKey);
        System.out.println(" = ======redis取值:" + object.toString());
    }
}
