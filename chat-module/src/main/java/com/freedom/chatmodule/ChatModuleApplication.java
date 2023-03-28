package com.freedom.chatmodule;

import com.freedom.chatmodule.service.BclientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication注解包含了@EnableAutoConfiguration注解，它会自动扫描classpath下的各种jar包，并根据其中的配置自动配置数据源等组件
@SpringBootApplication
public class ChatModuleApplication implements CommandLineRunner {

    @Autowired
    private BclientService bclientService;

    public static void main(String[] args) {
        SpringApplication.run(ChatModuleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        bclientService.requestDanmaku();
    }

}

//在Spring Boot项目中，主配置类一般是指一个带有@SpringBootApplication注解的Java类。这个注解包含了多个注解的元注解，用于启用Spring Boot自动配置、组件扫描和其他一些基本功能。
//
//具体来说，主配置类需要考虑以下几个方面：
//
//自动配置：Spring Boot自动配置大大简化了我们的开发过程，避免了大量繁琐的配置。因此，在主配置类中需要启用自动配置，可以通过@EnableAutoConfiguration注解实现。
//
//组件扫描：在主配置类中，需要启用组件扫描，以便Spring能够自动扫描到我们所编写的所有组件，并加入到容器管理中。可以通过@ComponentScan注解实现。
//
//Bean定义：除了自动扫描外，我们还可以通过@Bean注解手动定义Bean对象，添加到容器中进行管理。
//
//配置属性：在应用程序中，我们会有一些需要配置的属性，例如数据库连接信息、缓存配置等。这些属性可以通过@ConfigurationProperties注解读取，并在主配置类中进行配置。
//
//主配置类的注入过程如下：
//
//Spring Boot启动时，自动扫描主配置类上的各种注解，获取到其中的配置信息。
//
//根据配置信息，创建并初始化ApplicationContext对象。
//
//ApplicationContext对象根据配置信息，扫描并实例化所有带有注解的类，将其加入到容器中进行管理。
//
//如果需要使用某个组件或Bean对象，我们可以通过@Autowired或者ApplicationContext的getBean()方法来获取它们的实例。这个过程中，Spring会自动根据类型或者名称匹配到相应的Bean对象，并注入到我们的代码中。