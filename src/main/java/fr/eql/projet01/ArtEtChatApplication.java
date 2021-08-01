package fr.eql.projet01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ArtEtChatApplication extends SpringBootServletInitializer{
	  @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(ArtEtChatApplication.class);
	    }

	    public static void main(String[] args) {
	        SpringApplication.run(ArtEtChatApplication.class, args);
	    }

}
//@EnableFeignClients
//@SpringBootApplication
//public class ArtEtChatApplication extends SpringBootServletInitializer{
//	public static void main(String[] args) {
//		SpringApplication app = new SpringApplication(ArtEtChatApplication.class);
//		app.setAdditionalProfiles("initData");
//		app.run(args);
//	}
//}
