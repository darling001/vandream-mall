package com.vandream;
import com.alibaba.fastjson.JSON;
import com.vandream.mall.business.dao.CompanyDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/19
 * @time : 20:10
 * Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CompanyDAOTest {

    @Autowired
    private CompanyDAO companyDAO;

    @Test
    public void getCompanyTypeList(){
        String customerType ="A44";
        System.out.println(JSON.toJSONString(companyDAO.getCompanyTypeList(customerType)));
    }
    @Test
    public void test(){
        String image = "fsfdsafdfdfasdf.jpg";
        System.out.println(image.substring(image.lastIndexOf(".")+1,image.length()));
    }
}
