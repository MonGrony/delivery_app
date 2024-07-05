package com.sparta.delivery_app.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JPAConfiguration {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() { //jpaQueryFactory 를 Bean 으로 등록 -> Spring 에서 관리하게 함
        return new JPAQueryFactory(entityManager);

    }
}
