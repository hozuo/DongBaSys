package top.ericson.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import top.ericson.common.anno.RequestLog;
import top.ericson.common.exception.ServiceException;
import top.ericson.common.util.ShiroUtil;
import top.ericson.common.vo.PageObject;
import top.ericson.common.vo.SysUserDeptVo;
import top.ericson.sys.dao.SysUserDao;
import top.ericson.sys.dao.SysUserRoleDao;
import top.ericson.sys.entity.SysUser;
import top.ericson.sys.service.SysUserService;

@Slf4j
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    /**
     * @author Ericson
     * @date 2020/02/08 18:05
     * @param username
     * @param pageCurrent
     * @return PageObject<SysUserDeptVo>
     * @see top.ericson.sys.service.SysUserService#findPageObjects(java.lang.String, java.lang.Integer)
     * @description 分页查询
     */
    // @Cacheable(value = "userCache")
    @Transactional(readOnly = true)
    @RequestLog("分页查询")
    @Override
    public PageObject<SysUserDeptVo> findPageObjects(String username,
        Integer pageCurrent) {
        System.out.println("findPageObjects:" + Thread.currentThread());
        // 1.对参数进行校验
        if (pageCurrent == null || pageCurrent < 1) {
            throw new IllegalArgumentException("当前页码值无效");
        }
        // 2.查询总记录数并进行校验
        int rowCount = sysUserDao.getRowCount(username);
        if (rowCount == 0) {
            throw new ServiceException("没有找到对应记录");
        }
        // 3.查询当前页记录
        int pageSize = 5;
        int startIndex = (pageCurrent - 1) * pageSize;
        List<SysUserDeptVo> records = sysUserDao.findPageObjects(username, startIndex, pageSize);
        int pageCount = (rowCount - 1) / pageSize + 1;
        // 4.对查询结果进行封装并返回
        return new PageObject<SysUserDeptVo>(pageCurrent, pageSize, rowCount, pageCount, records);
    }

    /**
     * @author Ericson
     * @date 2020/02/08 16:30
     * @see top.ericson.sys.service.SysUserService#validById(java.lang.Integer, java.lang.Integer, java.lang.String)
     * @description 禁用用户
     */
    @Override
    public int validById(Integer id,
        Integer valid,
        String modifiedUser) {
        // 1.合法性验证
        if (id == null || id <= 0) {
            throw new ServiceException("参数不合法,id = " + id);
        }
        if (valid != 1 && valid != 0) {
            throw new ServiceException("参数不合法,valie = " + valid);
        }
        if (StringUtils.isEmpty(modifiedUser)) {
            throw new ServiceException("修改用户不能为空");
        }
        // 2.执行禁用或启用操作
        int rows = sysUserDao.validById(id, valid, modifiedUser);
        // 3.判定结果,并返回
        if (rows == 0) {
            throw new ServiceException("此记录可能已经不存在");
        }
        return rows;
    }

    /**
     * @author Ericson
     * @date 2020/02/08 18:06
     * @param entity
     * @param roleIds
     * @see top.ericson.sys.service.SysUserService#saveObject(top.ericson.sys.entity.SysUser, java.lang.Integer[])
     * @description 存储用户
     */
    @Override
    public int saveObject(SysUser entity,
        Integer[] roleIds) {
        long start = System.currentTimeMillis();
        log.info("start:" + start);
        // 1.参数校验
        if (entity == null) {
            throw new ServiceException("保存对象不能为空");
        }
        if (StringUtils.isEmpty(entity.getUsername())) {
            throw new ServiceException("用户名不能为空");
        }
        if (StringUtils.isEmpty(entity.getPassword())) {
            throw new ServiceException("密码不能为空");
        }
        if (roleIds == null || roleIds.length == 0) {
            throw new ServiceException("至少要为用户分配角色");
        }
        // 2.保存用户自身信息
        // 2.1对密码进行加密
        String source = entity.getPassword();
        String salt = UUID.randomUUID().toString();
        // Shiro框架
        SimpleHash sh = new SimpleHash(
            // algorithmName 算法
            "MD5",
            // 原密码
            source,
            // 盐值
            salt,
            // hashIterations表示加密次数
            1);
        entity.setSalt(salt);
        entity.setPassword(sh.toHex());
        String createdUser = ShiroUtil.getLoginUserName();
        entity.setCreatedUser(createdUser);
        int rows = sysUserDao.insertObject(entity);
        // 3.保存用户角色关系数据
        sysUserRoleDao.insertObjects(entity.getId(), roleIds);
        long end = System.currentTimeMillis();
        log.info("end:" + end);
        log.info("total time :" + (end - start));
        // 4.返回结果
        return rows;
    }

    /**
     * @author Ericson
     * @date 2020/02/08 18:06
     * @see top.ericson.sys.service.SysUserService#findObjectById(java.lang.Integer)
     * @description 用于修改用户时显示用户的相关信息
     */
    @Override
    public Map<String, Object> findObjectById(Integer userId) {
        // 1.合法性验证
        if (userId == null || userId <= 0) {
            throw new ServiceException("参数数据不合法,userId=" + userId);
        }
        // 2.业务查询
        SysUserDeptVo user = sysUserDao.findObjectById(userId);
        if (user == null) {
            throw new ServiceException("此用户已经不存在");
        }
        List<Integer> roleIds = sysUserRoleDao.findRoleIdsByUserId(userId);
        // 3.数据封装
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("roleIds", roleIds);
        return map;
    }

    @Override
    @CacheEvict(value = "userCache", key = "#entity.id")
    public int updateObject(SysUser entity,
        Integer[] roleIds) {
        // 1.参数有效性验证
        if (entity == null) {
            throw new IllegalArgumentException("保存对象不能为空");
        }
        if (StringUtils.isEmpty(entity.getUsername())) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        if (roleIds == null || roleIds.length == 0) {
            throw new IllegalArgumentException("必须为其指定角色");
        }
        // 其它验证自己实现，例如用户名已经存在，密码长度，...
        // 2.更新用户自身信息
        int rows = sysUserDao.updateObject(entity);
        // 3.保存用户与角色关系数据
        sysUserRoleDao.deleteObjectsByUserId(entity.getId());
        sysUserRoleDao.insertObjects(entity.getId(), roleIds);
        // 4.返回结果
        return rows;
    }

    @Override
    public int updatePassword(String password,
        String newPassword,
        String cfgPassword) {
        // 1.判定新密码与密码确认是否相同
        if (StringUtils.isEmpty(newPassword)) {
            throw new IllegalArgumentException("新密码不能为空");
        }
        if (StringUtils.isEmpty(cfgPassword)) {
            throw new IllegalArgumentException("确认密码不能为空");
        }
        if (!newPassword.equals(cfgPassword)) {
            throw new IllegalArgumentException("两次输入的密码不相等");
        }
        // 2.判定原密码是否正确
        if (StringUtils.isEmpty(password)) {
            throw new IllegalArgumentException("原密码不能为空");
        }
        // 获取登陆用户
        SysUser user = (SysUser)SecurityUtils.getSubject().getPrincipal();
        SimpleHash sh = new SimpleHash("MD5", password, user.getSalt(), 1);
        if (!user.getPassword().equals(sh.toHex())) {
            throw new IllegalArgumentException("原密码不正确");
        }
        // 3.对新密码进行加密
        String salt = UUID.randomUUID().toString();
        sh = new SimpleHash("MD5", newPassword, salt, 1);
        // 4.将新密码加密以后的结果更新到数据库
        int rows = sysUserDao.updatePassword(sh.toHex(), salt, user.getId());
        if (rows == 0) {
            throw new ServiceException("修改失败");
        }
        return rows;
    }

}
