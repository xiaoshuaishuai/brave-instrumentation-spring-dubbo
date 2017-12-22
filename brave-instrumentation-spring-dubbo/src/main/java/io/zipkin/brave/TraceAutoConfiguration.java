package io.zipkin.brave;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 配置注解
@EnableConfigurationProperties(TraceConfigProperties.class) // 开启指定类的配置
@ConditionalOnClass(TraceConfig.class)
@ConditionalOnProperty(prefix = "dubbo.zipkin", value = "enabled", matchIfMissing = true) // 指定的属性是否有指定的值
public class TraceAutoConfiguration {
	@Autowired
	TraceConfigProperties configProperties;

	@Bean
	@ConditionalOnMissingBean(TraceConfig.class) 
	public TraceConfig traceConfig() {
		System.out.println("traceConfig*******************");
		TraceConfig traceConfig = new TraceConfig(configProperties);
		return traceConfig;
	}
}
