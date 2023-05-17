package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
        //자동으로 scan 하는거 빼는건데 App config 파일 등록안하려고 붙인거
        //@Configuration 에 들어가보면 @Component 있어서 자동 등록 되거든
)
public class AutoAppConfig {
}
