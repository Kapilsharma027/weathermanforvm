package com.org.project.theWeatherMan.util;

import org.springframework.http.HttpStatus;

import com.org.project.theWeatherMan.model.Response;

/**
 * CustomResponseEntity class to implements an application to handle response.
 *
 * @author abhishek.sisodiya
 * @since 1/07/2019.
 */
public class CustomResponseEntity {

    /**
     * Create response object to send data on client side.
     *
     * @param data {@link Object}
     * @return response {@link Response}
     */
    public Response getResponseObject(Object data) {
        return new Response(HttpStatus.OK.value(),"Message" ,data);
    }
}
