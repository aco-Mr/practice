package com.aco.practice.demo1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HaoJianXu
 * @Date: 2020/5/31 14:07
 *
 * @Profile 注解 标识加载在dev,local和test文件使用
 */
@Configuration
@EnableSwagger2
//@Profile(value = {"local","dev","test"})
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("spring-test-interface")
                //加载配置信息
                .apiInfo(apiInfo())
                //设置全局参数
                .globalOperationParameters(globalParamBuilder())
                //设置全局响应参数
                .globalResponseMessage(RequestMethod.GET,responseBuilder())
                .globalResponseMessage(RequestMethod.POST,responseBuilder())
                .globalResponseMessage(RequestMethod.PUT,responseBuilder())
                .globalResponseMessage(RequestMethod.DELETE,responseBuilder())
                .select()
                //扫描接口的所在包的位置
                .apis(RequestHandlerSelectors.basePackage("com.aco.practice.demo1"))
                .paths(PathSelectors.any()).build()
                //设置安全认证
                .securitySchemes(security());

    }

    /**
     * 获取swagger创建初始化信息
     * @return
     */
    private ApiInfo apiInfo() {
        Contact contact = new Contact("Aco", "", "aco@xxx.com");
        return new ApiInfoBuilder()
                // 页面标题
                .title("swagger Api文档")
                // 描述
                .description("demo aco")
                // 创建人
                .contact(contact)
                // 版本
                .version("1.0.0")
                .build();
    }

    /**
     * 安全认证参数
     * @return
     */
    private List<ApiKey> security() {
        List<ApiKey> list=new ArrayList<>();
        list.add(new ApiKey("token", "token", "header"));
        return list;
    }

    /**
     * 构建全局参数列表
     * @return
     */
    private List<Parameter> globalParamBuilder(){
        List<Parameter> pars = new ArrayList<>();
        pars.add(parameterBuilder("token","令牌","string","header",false).build());
        return pars;
    }

    /**
     * 创建参数
     * @return
     */
    private ParameterBuilder parameterBuilder(String name, String desc, String type , String parameterType, boolean required) {
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name(name).description(desc).modelRef(new ModelRef(type)).parameterType(parameterType).required(required).build();
        return tokenPar;
    }

    /**
     * 创建全局响应值
     * @return
     */
    private List<ResponseMessage> responseBuilder() {
        List<ResponseMessage> responseMessageList = new ArrayList<>();
        responseMessageList.add(new ResponseMessageBuilder().code(200).message("响应成功").build());
        responseMessageList.add(new ResponseMessageBuilder().code(500).message("服务器内部错误").build());
        return responseMessageList;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("**/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars*")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
