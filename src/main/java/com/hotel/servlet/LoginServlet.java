package com.hotel.servlet;

import com.hotel.global.Global;
import com.hotel.model.User;
import com.hotel.service.UserService;
import com.hotel.service.impl.UserServiceImpl;
import com.hotel.util.BeanFactory;
import com.hotel.util.Md5Utils;
import com.hotel.util.Servlet;
import com.wf.captcha.utils.CaptchaUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/user/*")
public class LoginServlet extends HttpServlet {
    private final UserService service = BeanFactory.getBean(UserServiceImpl.class);
    private HttpServletRequest req;
    private HttpServletResponse resp;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String ctx = req.getContextPath();//上下文路径
        String action = uri.replace(ctx, "");

        if (action.equals("/user/login")) {//登录
            String error = (String) req.getSession().getAttribute("error");
            if (error != null) {
                req.setAttribute("error", error);
                req.getSession().removeAttribute("error");
            }

            req.getRequestDispatcher("/WEB-INF/jsp/newlogin.jsp").forward(req, resp);

        } else if (action.equals("/user/logout")) {//退出
            req.getSession().removeAttribute(Global.USER_LOGIN_KEY);
            resp.sendRedirect(req.getContextPath() + "/index");

        } else if (action.equals("/user/captcha")) {//验证码
            CaptchaUtil.out(100, 40, 4, req, resp);

        } else if (action.equals("/user/edit")) {
            String username = req.getParameter("username");
//            System.out.println(username);
            User user = service.findByUsername(username);
//            System.out.println(user.getUsername());

            if (user == null) {
                req.setAttribute("error", "要修改密码的用户不存在");
            } else {
                req.setAttribute("user", user);
            }
            req.getRequestDispatcher("/WEB-INF/jsp/user/edit.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.req = req;
        this.resp = resp;

        String uri = req.getRequestURI();
        String ctx = req.getContextPath();//上下文路径
        String action = uri.replace(ctx, "");

        if (action.equals("/user/login")) {
            login();
        } else if (action.equals("/user/edit")) {
            edit();
        }
    }

    //判断账号密码验证码登录
    private void login() throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String captcha = req.getParameter("captcha");


        //正则表达式验证密码长度
        String regex1 = "^.{8,16}$";

        if (!password.matches(regex1)) {
            req.getSession().setAttribute("error", "密码长度应在8-16之间");
            resp.sendRedirect(req.getContextPath() + "/user/login");
            return;
        }


        //判断
        boolean b = CaptchaUtil.ver(captcha, req);
        if (!b) {
            req.getSession().setAttribute("error", "验证码错误");
            resp.sendRedirect(req.getContextPath() + "/user/login");
            return;
        }

        User user = service.findByUsername(username);
        if (user == null) {
            req.getSession().setAttribute("error", "用户名不存在");
            resp.sendRedirect(req.getContextPath() + "/user/login");
            return;
        }

        boolean passed = service.checkLogin(user, password);
//        System.out.println("检查" + user);
        if (passed) {
            req.getSession().setAttribute(Global.USER_LOGIN_KEY, user);
//            System.out.println("成功里面的" + user);
            resp.sendRedirect(req.getContextPath() + "/index");

        } else {
            req.getSession().setAttribute("error", "密码不正确");
            resp.sendRedirect(req.getContextPath() + "/user/login");
        }
    }

    private void edit() throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        String againPassword = req.getParameter("againPassword");
//        System.out.println(newPassword + againPassword);

        //后端验证用户是否存在
        User user = service.findByUsername(username);
        if (user == null) {
            req.getSession().setAttribute("error", "用户不存在");
            resp.sendRedirect(req.getContextPath() + "/index");
            return;
        }
        //验证是用户名密码是否匹配
        boolean passed = service.checkLogin(user, password);
        if (passed) {

            user.setUsername(username);
            //正则表达式验证密码格式
            String regex1 = "^.{8,16}$";
            String regex2 = ".*\\d+.*";
            String regex3 = ".*[A-Z]+.*";
            String regex4 = ".*[a-z]+.*";
            if (!newPassword.matches(regex1)) {
                Servlet.renderJson(Map.of("success", false, "error", "新密码长度应在8-16之间"), resp);
                return;
            }
            if (!newPassword.matches(regex2)) {
                Servlet.renderJson(Map.of("success", false, "error", "新密码至少有一位数字"), resp);
                return;
            }
            if (!newPassword.matches(regex3)) {
                Servlet.renderJson(Map.of("success", false, "error", "新密码至少有一位大写字母"), resp);
                return;
            }
            if (!newPassword.matches(regex4)) {
                Servlet.renderJson(Map.of("success", false, "error", "新密码至少有一位小写字母"), resp);
                return;
            }

            //验证两次新密码是否相同，新旧密码是否相同
            if (!againPassword.equals(newPassword)) {
                Servlet.renderJson(Map.of("success", false, "error", "两次输入新密码不相同"), resp);
                return;
            }
            if (password.equals(newPassword)) {
                Servlet.renderJson(Map.of("success", false, "error", "新旧密码相同"), resp);
                return;
            }


            //对newPassword进行MD5加密
            String encrypt = Md5Utils.encrypt(newPassword + "{" + user.getUsername() + "}");//密码加密之后的密文
            user.setPassword(encrypt);
            boolean b = service.update(user);

            if (b) {
                Servlet.renderJson(Map.of("success", true), resp);
                req.getSession().removeAttribute(Global.USER_LOGIN_KEY);
            } else {
                Servlet.renderJson(Map.of("success", false, "error", "保存学生信息失败"), resp);
            }
        } else {
            Servlet.renderJson(Map.of("success", false, "error", "旧密码不正确"), resp);
//            req.getSession().setAttribute("error", "旧密码不正确");
//            resp.sendRedirect(req.getContextPath() + "/user/edit");
        }


    }
}

