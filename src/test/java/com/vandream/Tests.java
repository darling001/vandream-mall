package com.vandream;

import com.alibaba.fastjson.JSON;
import com.vandream.mall.business.dto.AddressDTO;
import com.vandream.mall.business.dto.DefaultAddressDTO;
import com.vandream.mall.business.dto.delivery.DeliveryDetailDTO;
import com.vandream.mall.business.vo.delivery.DeliveryDetailVO;
import com.vandream.mall.business.vo.demand.DemandHeadVO;
import com.vandream.mall.business.vo.search.SearchCategoryAggVO;
import com.vandream.mall.commons.constant.ComparatorInstance;
import com.vandream.mall.commons.utils.JSONUtil;
import com.vandream.mall.commons.utils.ObjectUtil;
import com.vandream.mall.commons.utils.StringUtil;
import java.lang.reflect.Field;
import java.text.Collator;
import java.util.*;
import org.junit.Test;

/**
 * @author Li Jie
 */
public class Tests {
    private static final String LIST_TYPE_NAME = "java.util.List";

    private static final Comparator<Object> COMPARATOR = Collator.getInstance(Locale
            .CHINA);
    private static final Comparator<Object> COMPARATOR2 = Collator.getInstance();

    @Test
    public void testSort() {
        List<String> list = new ArrayList<>();
        list.add("200");
        list.add("100");
        list.add("80");
        list.add("110");
        list.add("150");
        list.add("95");
        list.add("85");
        list.add("M170");
        list.add("M120");
        list.add("130");
        list.add("20KG/套2.4");
        list.add("20KG/套2.3");
        list.add("40KG/套");
        list.add("50KG/套");
        //130KG/套 20KG/套 40KG/套 50KG/套
        Collections.sort(list, ComparatorInstance.STRING_HASH_COMPARATOR);
        System.out.println(list);
    }

    @Test
    public void getFirstDigits() {
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("abc");
        list.add("1000$");
        list.add("1123~45");
        list.add("(541) 754-3010");
        list.add("\\u0967\\u0968\\u096");
        list.add("0.125mm");
        list.add("170");
        list.add("120");
        list.add("130");
        for (String str : list) {
            //System.out.println(str);
            System.out.println(StringUtil.getFirstDigit(str));
        }
    }

    @Test
    public void test() throws ClassNotFoundException, IllegalAccessException,
            InstantiationException {
        DemandHeadVO demandHeadVO = new DemandHeadVO();
        ArrayList<String> list = new ArrayList<>();
        list.add("1111");
        list.add("2222");
        demandHeadVO.setDemandDiscuss(list);
        Field[] declaredFields = demandHeadVO.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (ObjectUtil.isNotEmpty(declaredField)) {
                String typeName = declaredField.getType().getTypeName();
                Class<?> aClass = Class.forName(typeName);

                System.out.println(typeName);
                if (LIST_TYPE_NAME.equals(typeName)) {
                    System.out.println(declaredField.getType());
                }
                System.out.println(declaredField.toGenericString());
                //private java.lang.Object com.vandream.mall.business.vo.demand.DemandHeadVO
                // .demandDiscuss
                //System.out.println(declaredField.toString());

            }
        }

    }

    @Test
    public void test2() throws ClassNotFoundException, IllegalAccessException,
            InstantiationException {
        C c = new C();
        B b = new B();
        A a = new A();

        B bc = new C();
        A ac = new C();

        System.out.println(c instanceof C);
        System.out.println(c instanceof B);
        System.out.println(c instanceof A);

        System.out.println();

        System.out.println(c.getClass().isInstance(c));
        System.out.println(c.getClass().isInstance(b));
        System.out.println(c.getClass().isInstance(a));

        System.out.println();

        System.out.println(c.getClass().isInstance(bc));
        System.out.println(c.getClass().isInstance(ac));

        System.out.println();

        System.out.println(A.class.isInstance(a));
        System.out.println(A.class.isInstance(b));
        System.out.println(A.class.isInstance(c));
        System.out.println(A.class.isInstance(ac));
        System.out.println(A.class.isInstance(bc));

        System.out.println();

        System.out.println(B.class.isInstance(a));
        System.out.println(B.class.isInstance(b));
        System.out.println(B.class.isInstance(c));
        System.out.println(B.class.isInstance(ac));
        System.out.println(B.class.isInstance(bc));
    }

    @Test
    public void test3() throws Exception {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setOperatorUserId("11111");
        addressDTO.setOperatorUserName("22222");
        DefaultAddressDTO transfer = ObjectUtil.transfer(addressDTO, DefaultAddressDTO.class);
        System.out.println(JSON.toJSONString(transfer));
    }

    @Test
    public void test4() throws Exception {
        String srcJson = "{\"purchaseHeadId\":\"PU1804130003\"," +
                "\"deliveryNoticeId\":\"336f3728c368608f72f81e19e52431de\"," +
                "\"deliveryNoticeCode\":\"NO1804130002\",\"fromType\":\"SOMPLAN\"," +
                "\"status\":\"30\",\"address\":\"中国宁波(王先生收) 13124567898\"," +
                "\"itemList\":[{\"deliveryNoticeLineId\":\"02eff3ab89e9282afd6c2d8d33b254d2\"," +
                "\"deliveryNoticeLineCode\":\"NO18041300020001\",\"itemName\":\"方超专用-勿动\"," +
                "\"brandName\":\"品牌测试041001J\",\"parameters\":\"testbyCake为了测试合同\"," +
                "\"quantity\":10.000000,\"unit\":\"set\",\"noticeQuantity\":10.000000}]}";
        DeliveryDetailDTO deliveryDetailDTO = JSON.parseObject(srcJson, DeliveryDetailDTO.class);
        DeliveryDetailVO transfer = ObjectUtil.transfer(deliveryDetailDTO, DeliveryDetailVO.class);
        System.out.println(JSON.toJSONString(transfer));
    }

    @Test
    public void test5() throws Exception {
        String categoryListJson = "[{\"CATEGORY_ID\":\"a0ef6aa8aed9cbcede931ba9491fb6d5\"," +
                "\"CATEGORY_LEVEL\":\"1\",\"CATEGORY_NAME\":\"精装修\"}," +
                "{\"CATEGORY_ID\":\"389fc3598bb81d49a20e17ad55f4b489\",\"CATEGORY_LEVEL\":\"2\"," +
                "\"CATEGORY_NAME\":\"厨房电器\"}," +
                "{\"CATEGORY_ID\":\"7b1463edbde8acdea64ebd4fb1558bce\",\"CATEGORY_LEVEL\":\"3\"," +
                "\"CATEGORY_NAME\":\"油烟机\"}]";
        List<SearchCategoryAggVO> categoryAggList = JSON.parseArray(categoryListJson,
                SearchCategoryAggVO.class);
        System.out.println(JSONUtil.toJSON(categoryAggList, new ArrayList<SearchCategoryAggVO>()
                .getClass()));

    }


}

class A {

}

class B extends A {

}

class C extends B {

}
