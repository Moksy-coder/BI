package com.moksy.springbootinit.config;

import lombok.Data;
import org.apache.ibatis.annotations.Delete;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.data.redis")
public class RedissonConfig {
    private Integer database;
    private String host;
    private String port;
    //创建一个配置类
    @Bean
    public RedissonClient getRedissonConfig(){
       Config config = new Config();
       config.useSingleServer()
               .setDatabase(database)
               .setAddress("redis://"+host+":"+port);
        //创建Redission实例
       RedissonClient redisson = Redisson.create(config);
       return redisson;
   }

}
