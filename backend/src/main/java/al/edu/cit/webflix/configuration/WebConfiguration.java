package al.edu.cit.webflix.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    // Suppressed view inspection due to index.html being copied from frontend by using the maven-resources-plugin
    @SuppressWarnings("SpringMVCViewInspection")
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
        registry.addViewController("/{x:[\\w\\-]+}").setViewName("forward:/index.html");
        registry.addViewController("/{x:^(?!api$).*$}/*/{y:[\\w\\-]+}").setViewName("forward:/index.html");
        registry.addViewController("/swagger-ui").setViewName("redirect:/swagger-ui/");
        registry.addViewController("/swagger-ui/").setViewName("forward:/swagger-ui/index.html");
    }
}
