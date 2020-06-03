package me.likeanowl.aitameetup.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"me.likeanowl.aitameetup.repository"})
public class MybatisConfig {
}
