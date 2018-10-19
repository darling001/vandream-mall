package com.vandream;

import com.vandream.mall.business.dto.BaseDTO;
import com.vandream.mall.business.execption.SmsMsgException;
import com.vandream.mall.business.service.AreaService;
import com.vandream.mall.business.service.RedisService;
import com.vandream.mall.business.service.SmsMsgService;
import com.vandream.mall.business.vo.AreaVO;
import com.vandream.mall.commons.service.ApiExecutorBxService;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author dingjie
 * @date 2018/3/20
 * @time 17:59
 * Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class publicClassTest  {
    @Autowired
    private ApiExecutorBxService apiExecutorBxService;
    @Autowired
    private SmsMsgService smsMsgService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private AreaService areaService;
    @Test
    public void testApiAspect(){
        BaseDTO baseDTO =new BaseDTO();
        baseDTO.setOperatorUserId("1");
        baseDTO.setOperatorUserName("test");
        String str= null;
        try {
            str = apiExecutorBxService.register(baseDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("测试 str = " + str);
    }
    public long getRandomId(){
        long num=0;
        try {
            long l=(int) Math.round(Math.random() * (99999-10000) +10000);
            num=l;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }
    @Test
    public  void testRandomMath(){

        for (int i=0;i<1000;i++) {
            long random=getRandomId();
            System.out.println("random = " + random);
        }
    }
    @Test
    public void  testRedisSmsCode(){
        String phoneNo="18668001520";
        try {
//            redisService.del("15257174832");
            smsMsgService.sendSmsCode(phoneNo,"SMS_126700020",null);
           String code= redisService.get(phoneNo);
            System.out.println("code = " + code);
        } catch (SmsMsgException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testAreaList(){
        redisService.del("provinces");
        List<AreaVO> areaVOList= areaService.getArea();
        System.out.println("areaVOList.toString() = " + redisService.get("provinces"));
    }
    @Test
    public void getCurrentTime(){
       long curTime=System.currentTimeMillis();
        System.out.println("curTime = " + curTime);
    }
}
