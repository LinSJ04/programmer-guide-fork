package com.beien.enums;


/**
 * 限流类型
 * @author beien
 */
public enum LimitType {
    /**
     * 默认策略全局限流，没有限定具体某个IP
     */
    DEFAULT,
    /**
     * 根据请求者IP进行限流
     */
    IP
}
