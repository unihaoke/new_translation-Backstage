package com.trans.translation.service.impl;

import com.trans.translation.common.Result;
import com.trans.translation.common.StatusCode;
import com.trans.translation.dao.UserDao;
import com.trans.translation.pojo.User;
import com.trans.translation.service.UserService;
import com.trans.translation.utils.HttpClientUtil;
import com.trans.translation.utils.JsonUtils;
import com.trans.translation.utils.JwtTokenUtil;
import com.trans.translation.vo.UserVo;
import com.trans.translation.vo.WXSessionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 服务层
 *
 * @author Administrator
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${wx.appId}")
    private String appId;
    @Value("${wx.secret}")
    private String secret;
    @Value("${wx.grant_type}")
    private String grant_type;

    /**
     * 获取openId
     * @param code
     * @return
     */
    @Override
    public Result getOpenId(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, String> param = new HashMap<>();
        param.put("appid", "wxe0d156a5aac77e36");
        param.put("secret", "9a59467bbc96e5a99bdfedf1be63e087");
        param.put("js_code", code);
        param.put("grant_type", "authorization_code");
        String wxResult = HttpClientUtil.doGet(url, param);
        WXSessionVo wxSessionVo = JsonUtils.jsonToPojo(wxResult,WXSessionVo.class);
//        if(userDao.hasUser(wxSessionVo.getOpenid()) == 0){
//            User user = new User();
//            user.setId(wxSessionVo.getOpenid());
//            user.setCreatetime(new Date());
//            user.setUpdatetime(new Date());
//            userDao.save(user);
//        }
        System.out.println(wxSessionVo.getOpenid()+"===============");
        return new Result(true,StatusCode.OK,"登录成功",wxSessionVo.getOpenid());
    }

    @Override
    public Result add(User user) {
        if(user == null){
            return new Result(false,StatusCode.LOGINERROR,"登录失败");
        }
        System.out.println(user.getId());
        user.setCreatetime(new Date());
        user.setUpdatetime(new Date());
        userDao.save(user);
        return new Result(true,StatusCode.OK,"登录成功");
    }

    @Override
    public Result findAll() {
        return new Result(true,StatusCode.OK,"查询成功",userDao.findAll());
    }

    @Override
    public Result update(User user) {
        if(user == null){
            return new Result(false,StatusCode.ERROR,"修改失败");
        }
        userDao.update(user.getUsername(),user.getEmail(),user.getPhone(),new Date(),user.getId());
        return new Result(true,StatusCode.OK,"修改成功");
    }

    @Override
    public Result findById(String id) {
        if(StringUtils.isEmpty(id)){
            return new Result(false,StatusCode.ERROR,"查询失败");
        }
        return new Result(true,StatusCode.OK,"查询成功",userDao.findById(id));
    }

    @Override
    public UserDetails loadUserById(String id) {
        User user = getById(id);
        return new UserVo(user);
    }

    /**
     * 用户登录
     * @param code
     * @return
     */
    @Override
    public String login(String code) {
        String token = null;
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, String> param = new HashMap<>();
        param.put("appid", appId);
        param.put("secret", secret);
        param.put("js_code", code);
        param.put("grant_type", grant_type);
        String wxResult = HttpClientUtil.doGet(url, param);
        WXSessionVo wxSessionVo = JsonUtils.jsonToPojo(wxResult,WXSessionVo.class);
        System.out.println(wxSessionVo.getOpenid()+"===============");
        try {
            UserDetails userDetails = loadUserById(wxSessionVo.getOpenid());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public User getById(String id) {
        User user = userDao.findById(id).orElse(null);
        if (user==null){
            user = new User();
            user.setId(id);
            userDao.save(user);
        }
        return user;
    }
}
