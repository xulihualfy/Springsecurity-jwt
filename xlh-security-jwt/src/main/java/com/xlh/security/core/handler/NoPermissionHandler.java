package com.xlh.security.core.handler;

import com.xlh.security.common.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xulihua
 * @date 2021/1/22 10:58
 * 暂无权限处理类
 */
@Slf4j
@Component
public class NoPermissionHandler implements AccessDeniedHandler {
    /**
     * 无权限处理方法
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        log.info("暂无权限:"+e.getMessage());
        ResultUtil resultUtil = ResultUtil.failed(ResultUtil.FAILED_NO_PERMISSION_CODE, "暂无权限");
        ResultUtil.Response(resultUtil,httpServletResponse);
    }
}
