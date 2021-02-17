package trustnet.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import trustnet.auth.interceptor.GeneralInterceptor;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
	public GeneralInterceptor generalInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(generalInterceptor)
                .addPathPatterns("/main")
                .addPathPatterns("/user/*")
                .addPathPatterns("/manager/*")
                .addPathPatterns("/managerZone/*")
                .addPathPatterns("/company/*")
                .addPathPatterns("/zone/*")
                .addPathPatterns("/zoneLicense/*")
                .excludePathPatterns("/user/enrollPage").excludePathPatterns("/js/**").excludePathPatterns("/css/**");
    }

    
}