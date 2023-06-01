package cn.itcast.wanxinp2p.consumer.config;

import cn.itcast.wanxinp2p.consumer.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvc Config
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/**");
	}
}
