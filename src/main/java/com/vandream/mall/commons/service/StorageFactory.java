package com.vandream.mall.commons.service;

import com.vandream.mall.commons.service.impl.FastdfsStorageService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

/**
 * 存储设置的工厂类
 * 
 * @author Li Jie
 *
 */
public class StorageFactory implements FactoryBean<StorageService> {

	@Autowired
	private AutowireCapableBeanFactory autowireCapableBeanFactory;

	/** 存储服务的类型，目前仅支持fastdfs */
	@Value("${storage.type}")
	private String type;

	private Map<String, Class<? extends StorageService>> classMap;

	public StorageFactory() {
		classMap = new HashMap<>();
		classMap.put("fastdfs", FastdfsStorageService.class);
	}

	@Override
	public StorageService getObject() throws Exception {
		Class<? extends StorageService> clazz = classMap.get(type);
		if (clazz == null) {
			throw new RuntimeException("Unsupported storage type [" + type + "], valid are " + classMap.keySet());
		}

		StorageService bean = clazz.newInstance();
		autowireCapableBeanFactory.autowireBean(bean);
		autowireCapableBeanFactory.initializeBean(bean, bean.getClass().getSimpleName());
		return bean;
	}

	@Override
	public Class<?> getObjectType() {
		return StorageService.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
