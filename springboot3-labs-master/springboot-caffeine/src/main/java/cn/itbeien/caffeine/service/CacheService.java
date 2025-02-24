package cn.itbeien.caffeine.service;

import cn.itbeien.caffeine.dao.SysUserMapper;
import cn.itbeien.caffeine.entity.SysUser;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI学习社群
 * Copyright© 2025 itbeien
 */
@Component
@Slf4j
public class CacheService {
    @Resource
    private SysUserMapper sysUserMapper;
    // 使用@Cacheable注解，表示该方法的结果会被缓存
    @Cacheable(value = "user", key = "#p0",unless = "#result == null")
    public SysUser getUserById(Long id) {
        // 从数据库获取数据
        log.info("Fetching data from database for id:{} " , id);
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(id);
        return sysUser;
    }

    // 使用@CachePut注解，表示该方法的结果会被更新到缓存中
    @CachePut(value = "user", key = "#p0")
    public SysUser updateUserById(SysUser sysUser) {
        // 更新数据库中的数据
        log.info("Updating data in database for id: {}" , sysUser.getId());
        this.sysUserMapper.updateByPrimaryKey(sysUser);
        return sysUser;
    }

    // 使用@CacheEvict注解，表示该方法会从缓存中删除对应的数据
    @CacheEvict(value = "user", key = "#p0")
    public void deleteUserById(Long id) {
        // 从数据库中删除数据
        log.info("Deleting data from database for id: {}" , id);
        this.sysUserMapper.deleteByPrimaryKey(id);
    }
}
