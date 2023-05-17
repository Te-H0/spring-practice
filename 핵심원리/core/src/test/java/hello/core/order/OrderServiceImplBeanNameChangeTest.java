package hello.core.order;

import hello.core.AutoAppConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class OrderServiceImplBeanNameChangeTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

    @Test
    void componentNameChangeTest() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println("beanDefinitionName = " + beanDefinitionName);
        }
    }
}