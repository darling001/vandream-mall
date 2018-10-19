package com.vandream;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vandream.mall.business.dao.homepage.CategoryDAO;
import com.vandream.mall.business.dto.homepage.CategoryDTO;
import com.vandream.mall.commons.utils.ObjectUtil;
import com.vandream.mall.commons.utils.StringUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/3/5
 * @time : 14:51
 * Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CategoryDAOTests {

    @Autowired
    private CategoryDAO categoryDAO;

    @Test
    public void getCategoryList() {

        String categoryId = "7";
        List<CategoryDTO> categoryList = categoryDAO.getCategoryList(categoryId);

        String s = JSON.toJSONString(categoryList);
        System.out.println(s);
    }

    @Test
    public void queryCategoryList() {
        List<CategoryDTO> categoryDTOList = categoryDAO.queryCategoryList();
        List<CategoryDTO> levelOneList = new ArrayList<>();

        testDigui(categoryDTOList, 0, Integer.valueOf(categoryDTOList.get(0).getCategoryLevel()), levelOneList);
        levelOneList.sort((o1, o2) -> {
            return o1.getSort() - o2.getSort();
        });

        System.out.println(JSON.toJSONString(levelOneList));
//        List<CategoryDTO> levelOneList = new ArrayList<>();
//        for (CategoryDTO categoryDTO : categoryDTOList)
//        {
//            if ("1".equals(categoryDTO.getCategoryLevel())){
//                levelOneList.add(categoryDTO);
//            }
//
//            if ("2".equals(categoryDTO.getCategoryLevel())){
//                CategoryDTO categoryLevelOne = levelOneList.get(levelOneList.size() - 1);
//                categoryLevelOne.getCategoryList().add(categoryDTO);
//            }
//
//            if ("3".equals(categoryDTO.getCategoryLevel())){
//                //获取二级类目列表
//                List<CategoryDTO> levelTwoList = levelOneList.get(levelOneList.size() - 1).getCategoryList();
//                //获取二级类目对象
//                CategoryDTO categoryLevelTwo = levelTwoList.get(levelTwoList.size() - 1);
//                //将二级类目对象中的列表赋值
//                categoryLevelTwo.getCategoryList().add(categoryDTO);
//            }
//        }
//
//        System.out.println(JSON.toJSONString(levelOneList));
    }

    public int testDigui(List<CategoryDTO> categoryList, int index, int level, List<CategoryDTO> fList) {
        for (; index < categoryList.size(); index++) {
            CategoryDTO categoryDTO = categoryList.get(index);
            if (Integer.valueOf(categoryDTO.getCategoryLevel()) > level) {
                index = testDigui(categoryList, index, level + 1, categoryList.get(index - 1).getCategoryList());
            } else if (Integer.valueOf(categoryDTO.getCategoryLevel()) == level) {
                fList.add(categoryDTO);
            } else if (Integer.valueOf(categoryDTO.getCategoryLevel()) < level) {
                break;
            }
        }
        return index - 1;

//        for( CategoryDTO categoryDTO : categoryList ) {
//            if( Integer.valueOf(categoryDTO.getCategoryLevel()) > lev ) {
//                testDigui( categoryList, lev+1, categoryDTO.getCategoryList() );
//            } else if( Integer.valueOf(categoryDTO.getCategoryLevel()) == lev ) {
//                fList.add(categoryDTO);
//            }
//        }
    }


    @Test
    public void testSet() {
        String tags = "[\"结构材料\", \"金属矿\"]";
        List list = JSON.parseObject(tags, List.class);
        ArrayList arrayList = JSON.parseObject(tags, new ArrayList<Map<String, Object>>().getClass());
        for (Object o : arrayList) {
            System.out.println(o);
        }
        System.out.println(JSON.toJSONString(arrayList));
    }

    @Test
    public void test1() {
        String tags = "[{\"categroyId\":1, \"level\" :1, \"fullcode\":\"1\",\"sort\":2},{\"categroyId\":2, " +
                "\"level\":2," +
                "\"fullcode\":\"1/2\"},{\"categroyId\":4, \"level\" :3, \"fullcode\":\"1/2/4\"},{\"categroyId\":3, " +
                "\"level\":1," +
                "\"fullcode\":\"3\", \"sort\":1}]";
        //全表数据 cmc_category_info
//        List<CategoryDTO> categoryFullList = new ArrayList<>();
//        if (ObjectUtil.isEmpty(categoryFullList)) {
//
//        }
//        Map<String, CategoryDTO> categoryMap = new HashMap<>();
//        Map<String, CategoryDTO> level1Map = new HashMap<>();
//        Map<String, List<CategoryDTO>> level2Map = new HashMap<>();
//        Map<String, List<CategoryDTO>> level3Map = new HashMap<>();


        JSONArray jsonArr = JSONObject.parseArray(tags);

        List<JSONObject> cateList = new ArrayList<>();

        int currentLeve1Id = 0;
        int currentLeve2Id = 0;
        for (Object obj : jsonArr) {
            JSONObject jsonObj = (JSONObject) obj;

            if (jsonObj.getInteger("level") == 1) {
                cateList.add(jsonObj);
            }

            if (jsonObj.getInteger("level") == 2) {
                JSONArray jsonArray = cateList.get(cateList.size() - 1).getJSONArray("level2");
                if (jsonArray == null) jsonArray = new JSONArray();
                jsonArray.add(jsonObj);
                cateList.get(cateList.size() - 1).put("level2", jsonArray);
            }
            if (jsonObj.getInteger("level") == 3) {
                JSONArray level2Arr = cateList.get(cateList.size() - 1).getJSONArray("level2");
                JSONArray level3Arr = level2Arr.getJSONObject(level2Arr.size() - 1).getJSONArray("level3");
                if (level3Arr == null) level3Arr = new JSONArray();
                level3Arr.add(jsonObj);
                level2Arr.getJSONObject(level2Arr.size() - 1).put("level3", level3Arr);
            }
        }

        //if(level3.size()>0) level2.get(level2.size()-1).put("level3", level3);
        //if(level2.size()>0) cateList.get(cateList.size() - 1).put("level2", level2);

        cateList.sort((o1, o2) -> {
            return o1.getInteger("sort") - o2.getInteger("sort");
        });

        System.out.println(cateList.toString());

//        List<CategoryDTO> returnList = new ArrayList<>();
//        for (CategoryDTO categoryDTO : categoryFullList) {
//            categoryMap.put(categoryDTO.getCategoryCode(), categoryDTO);
//            String[] splitCode = categoryDTO.getCategoryFullCode().split("/");
//            if ("1".equals(categoryDTO.getCategoryLevel())) {
//                level1Map.put(categoryDTO.getCategoryCode(), categoryDTO);
//            } else if ("2".equals(categoryDTO.getCategoryLevel())) {
//                List<CategoryDTO> categoryDTOList = level2Map.get(splitCode[0]);
//                if (ObjectUtil.isEmpty(categoryDTOList)) {
//                    categoryDTOList = new ArrayList<>();
//                }
//                categoryDTOList.add(categoryDTO);
//                if (StringUtil.isNotBlank(splitCode[0])) {
//                    level2Map.put(splitCode[0], categoryDTOList);
//                }
//
//            } else if ("3".equals(categoryDTO.getCategoryLevel())) {
//                List<CategoryDTO> categoryDTOList = level3Map.get(splitCode[1]);
//                if (ObjectUtil.isEmpty(categoryDTOList)) {
//                    categoryDTOList = new ArrayList<>();
//                }
//                categoryDTOList.add(categoryDTO);
//                if (StringUtil.isNotBlank(splitCode[1])) {
//                    level3Map.put(splitCode[1], categoryDTOList);
//                }
//
//            }
//
//        }
//
//        for (Map.Entry<String, List<CategoryDTO>> level2Entry : level2Map.entrySet()) {
//            String level1Code = level2Entry.getKey();
//            CategoryDTO categoryDTO = level1Map.get(level1Code);
//            List<CategoryDTO> level2List = level2Entry.getValue();
//            categoryDTO.setCategoryList(level2List);
//            for (CategoryDTO level2DTO : level2List) {
//                String level2Code = level2DTO.getCategoryCode();
//                List<CategoryDTO> level3List = level3Map.get(level2Code);
//                level2DTO.setCategoryList(level3List);
//
//            }
//        }
//        returnList = (List<CategoryDTO>) level1Map.values();
//
//        System.out.println(JSON.toJSONString(returnList));

    }


}
