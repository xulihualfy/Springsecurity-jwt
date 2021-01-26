package com.xlh.security.core;

import com.google.gson.Gson;
import com.xlh.security.config.JWTConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author xulihua
 * @date 2021/1/22 16:01
 * jwt请求接口拦截器
 * BasicAuthenticationFilter:检测和处理http basic认证
 */
@Slf4j
public class JWTAuthticationFilter extends BasicAuthenticationFilter {

    public JWTAuthticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }
    /**
     * jwt-token检验
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 获取请求的token
        String tokenHeader = request.getHeader(JWTConfig.tokenHeader);
        if (Objects.nonNull(tokenHeader) && tokenHeader.startsWith(JWTConfig.tokenPrefix)) {
            // 截取token
            try {
                String token = tokenHeader.replace(JWTConfig.tokenPrefix, "");
                // 解析jwt
                Claims claims = Jwts.parser().setSigningKey(JWTConfig.secret).parseClaimsJws(token).getBody();
                // 获取用户名和用户id
                String userName = claims.getSubject();
                String id = claims.getId();
                if (Objects.nonNull(userName) && Objects.nonNull(id)) {
                    List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
                    // 获取用户的角色权限
                    String authorities = claims.get("authorities").toString();
                    if (Objects.nonNull(authorities)) {
                        List<Map<String, String>> list = new Gson().fromJson(authorities, List.class);
                        if (Optional.ofNullable(list).isPresent()){
                            list.stream().forEach(map -> {
                                Optional<Map<String, String>> optionalMap = Optional.ofNullable(map);
                                if (optionalMap.isPresent()) {
                                    String authority = optionalMap.get().get("role");
                                    grantedAuthorityList.add(new SimpleGrantedAuthority(authority));
                                }
                            });
                        }
                    }
                    // 组装参数
                    SecurityUserDetail userDetail = new SecurityUserDetail();
                    userDetail.setUsername(userName);
                    userDetail.setUserId(Long.parseLong(id));
                    userDetail.setAuthorities(grantedAuthorityList);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetail, id, grantedAuthorityList);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (ExpiredJwtException e) {
                log.info("token过期");
            } catch (Exception e) {
                log.info("token失效:" + e.getMessage());
            }
        }
        chain.doFilter(request, response);
        return;
    }
}
