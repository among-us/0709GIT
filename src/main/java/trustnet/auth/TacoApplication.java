package trustnet.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import trustnet.auth.interceptor.GeneralInterceptor;

@SpringBootApplication
public class TacoApplication extends SpringBootServletInitializer{
	
	public static void main(String[] args) {
		SpringApplication.run(TacoApplication.class, args);
	}
	
}
