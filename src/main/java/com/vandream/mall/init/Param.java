package com.vandream.mall.init;

/**
 * @Author Oakley(fangchao)
 * @Date 2018-01-17 17:08
 */
public class Param {
    /**
     * 参数名
     */
    private String name;
    /**
     * 参数类型
     */
    private Class paramTypeClass;
    /**
     * 默认值
     */
    private String defaultValue;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setParamTypeClass(Class<?> c) {
        this.paramTypeClass = c;
    }

    public Class<?> getParamTypeClass() {
        return this.paramTypeClass;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }
}
