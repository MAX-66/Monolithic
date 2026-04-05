package com.brenden.cloud.config;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.brenden.cloud.injector.LogicSqlInjector;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

/**
 * 做点什么
 *
 * @author lxq
 * @date 2026-03-24 08:32
 */

@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页插件
        interceptor.addInnerInterceptor(paginationInnerInterceptor());
        // 乐观锁
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

    @Bean
    @SneakyThrows
    public IdentifierGenerator idGenerator() {
        return new DefaultIdentifierGenerator(InetAddress.getLocalHost());
    }

    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }


    /**
     * 解除每页500条限制, 在超出页数之后mybatis会自动断掉
     */
    private PaginationInnerInterceptor paginationInnerInterceptor() {
        PaginationInnerInterceptor interceptor = new PaginationInnerInterceptor();
        // 单页分页条数限制
        interceptor.setMaxLimit(-1L);
        interceptor.setOverflow(true);
        return interceptor;
    }
}
