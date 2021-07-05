package com.example.cardmanager.config;

import com.example.cardmanager.exceptions.domain.ErrorMap;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Configuration of the error mapping between
 * External errors and Application Messages/Codes
 */
@Getter
@Setter
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "error-config")
public class ErrorConfig {

    private List<ErrorMap> errorMapping;
}
