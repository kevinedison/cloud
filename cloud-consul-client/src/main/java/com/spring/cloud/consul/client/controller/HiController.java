package com.spring.cloud.consul.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @version 1.0
 * @description：
 * @author: Yang.Chang
 * @project: cloud
 * @package: com.spring.cloud.consul.client.controller
 * @email: cy880708@163.com
 * @date: 2018/10/25 下午7:00
 * @mofified By:
 */
@RestController
public class HiController {

    @Autowired
    private LoadBalancerClient loadBalancer;

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/helloConsul")
    public String HelloConsul() {
        ServiceInstance serviceInstance = loadBalancer.choose("consul-server-1");
        URI uri = serviceInstance.getUri();
        String callServiceResult = new RestTemplate().getForObject(uri.toString()+"/hi",String.class);
        System.out.println(callServiceResult);
        return "callServiceResult : " + callServiceResult;
    }

    /**
     * 从所有服务中选择一个服务（轮询）
     */
    @RequestMapping("/discover")
    public Object discover() {
        return loadBalancer.choose("consul-server-1").getUri().toString();
    }

    /**
     * 获取所有服务
     */
    @RequestMapping("/services")
    public Object services() {
        return discoveryClient.getInstances("consul-server-1");
    }

}
