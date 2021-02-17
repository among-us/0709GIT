package trustnet.auth.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.ModelAndView;

import trustnet.auth.interceptor.obj.PermissionObj;

@Configuration
public class GeneralConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public ModelAndView modelAndView() {
		return new ModelAndView();
	}
    
	@Bean
	public PermissionObj permissionObj() {
		return new PermissionObj();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
