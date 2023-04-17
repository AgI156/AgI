package com.hotel.servlet;

import com.hotel.global.Global;
import com.hotel.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute(Global.USER_LOGIN_KEY);
//        System.out.println("........." + user);
        if (user != null) {
//            System.out.println("user不是空的！！！" + user);
            req.setAttribute("user", user);
        }


        req.getRequestDispatcher("/WEB-INF/jsp/index/index.jsp").forward(req, resp);
    }
}
