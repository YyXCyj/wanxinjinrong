package cn.itcast.wanxinp2p.gateway.config;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yy
 * @version 1.0
 */
public class SwaggerConfig implements SwaggerResourcesProvider {
    @Override
    public List<SwaggerResource> get() {
        List resources = new ArrayList<>();
        resources.add(swaggerResource("consumer-service", "/consume/v2/api-docs", "2.0"));
        resources.add(swaggerResource("account-service", "/account/v2/api-docs", "2.0"));
        resources.add(swaggerResource("uaa-service", "/uaa/v2/api-docs", "2.0"));

        return resources;
    }
    private Object swaggerResource(String name, String location, String version) {

        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }

}
