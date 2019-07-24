package com.org.project.theWeatherMan.model;

/**
 * Response implements an application to have custom response.
 *
 * @author abhishek.sisodiya
 * @since 1/07/2019.
 */
public class Response {

    private int code;

    private Object data;

    private String message;

    /**
     * To initialize.
     *
     * @param code {@link String}
     * @param data {@link Object}
     */
    public Response(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * Gets code.
     *
     * @return value of code {@link String}
     */
    public Integer getCode() {
        return code;
    }

    /**
     * Sets code.
     *
     * @param code {@link String}
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * Gets data.
     *
     * @return value of data {@link Object}
     */
    public Object getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data {@link Object}
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * Gets message.
     *
     * @return value of message {@link String}
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message {@link String}
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
