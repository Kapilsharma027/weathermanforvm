package com.org.project.theWeatherMan.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is annotation to pass url in rest controller for every controller.
 *
 * @author abhishek.sisodiya
 * @since 1/07/2019.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@RestController
@RequestMapping
public @interface RestApiController {

    /**
     * Alias.
     *
     * @return String[]
     */
    @AliasFor(annotation = RequestMapping.class, attribute = "value")
    String[] value();
}
