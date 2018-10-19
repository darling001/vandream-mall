
package com.vandream;

import com.alibaba.fastjson.JSON;
import com.vandream.mall.business.dto.homepage.CategoryDTO;
import com.vandream.mall.business.execption.CategoryException;
import com.vandream.mall.business.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/8
 * @time : 10:06
 * Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CategoryServiceTests {

    @Autowired
    private CategoryService categoryServiceImpl;

    @Test
    public void getCategoryList() throws CategoryException {
        String categoryId="";
        List<CategoryDTO> categoryList = categoryServiceImpl.getCategoryList(categoryId);
        System.out.println(JSON.toJSONString(categoryList));
    }

    @Test
    public void queryCategoryList() throws CategoryException {

        List<CategoryDTO> categoryList = categoryServiceImpl.queryCategoryList();
        System.out.println(JSON.toJSONString(categoryList));
    }
}
