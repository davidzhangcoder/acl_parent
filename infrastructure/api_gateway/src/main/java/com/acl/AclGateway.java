package com.acl;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringCloudApplication
public class AclGateway {
    public static void main(String[] args) {
        SpringApplication.run(AclGateway.class);
    }
}
