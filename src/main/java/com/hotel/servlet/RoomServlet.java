package com.hotel.servlet;

import com.alibaba.fastjson2.JSON;
import com.hotel.model.Room;
import com.hotel.model.RoomSearchBean;
import com.hotel.service.RoomService;
import com.hotel.service.impl.RoomServiceImpl;
import com.hotel.util.BeanFactory;
import com.hotel.util.PaginateInfo;
import com.hotel.util.Servlet;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@WebServlet("/room/*")
public class RoomServlet extends HttpServlet {

    private final RoomService service = BeanFactory.getBean(RoomServiceImpl.class);
    private HttpServletRequest req;
    private HttpServletResponse resp;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String ctx = req.getContextPath();//上下文路径
        String action = uri.replace(ctx, "");

        if (action.equals("/room/list")) {//房间列表
            req.getRequestDispatcher("/WEB-INF/jsp/room/list.jsp").forward(req, resp);

        } else if (action.equals("/room/add")) {//添加房间
            HttpSession session = req.getSession();
            Room rm = (Room) session.getAttribute("room");
            String error = (String) session.getAttribute("error");

            if (rm != null) {
                req.setAttribute("room", rm);
                session.removeAttribute("room");
                req.setAttribute("error", error);
                session.removeAttribute("error");
            }

            req.getRequestDispatcher("/WEB-INF/jsp/room/add.jsp").forward(req, resp);

        } else if (action.equals("/room/edit")) {
            String id = req.getParameter("id");
            try {
                Integer iId = Integer.valueOf(id);
                Room rm = service.findById(iId);
                if (rm == null) {
                    req.setAttribute("error", "要修改的房间不存在");
                } else {
                    req.setAttribute("room", rm);
                }
            } catch (NumberFormatException e) {
                req.setAttribute("error", "请指定要修改的房间的id");
            }

            req.getRequestDispatcher("/WEB-INF/jsp/room/edit.jsp").forward(req, resp);

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.req = req;
        this.resp = resp;

        String uri = req.getRequestURI();
        String ctx = req.getContextPath();
        String action = uri.replace(ctx, "");

        if (action.equals("/room/combolist")) {
            combolist();
        } else if (action.equals("/room/list")) {//列表查询
            search();
        } else if (action.equals("/room/delete")) {//删除
            delete();
        } else if (action.equals("/room/add")) {
            //System.out.println("触发跳转");
            save();
        } else if (action.equals("/room/edit")) {
            edit();
        }

    }

    private void combolist() {
        List<Room> rooms = service.findAll();

        Servlet.renderJson(Map.of("success", true, "data", rooms), resp);
    }

    //列表查询
    private void search() {
        String pageNo = req.getParameter("pageNo");
        String pageSize = req.getParameter("pageSize");

        String name = req.getParameter("name");
        String kind = req.getParameter("kind");
//        System.out.println(kind);
        String state = req.getParameter("state");
        String priceFrom = req.getParameter("priceFrom");
        String priceTo = req.getParameter("priceTo");
//        System.out.println("接收到jsp" + priceTo);

        RoomSearchBean rsb = new RoomSearchBean();//查询条件
        if (StringUtils.hasText(name)) {
            rsb.setName(name);
        }
        if (StringUtils.hasText(kind)) {
            rsb.setKind(kind);
//            System.out.println(rsb.getKind());
        }
        if (StringUtils.hasText(state)) {
            rsb.setState(state);
        }
        if (StringUtils.hasText(priceFrom)) {
            try {
                Double db = Double.valueOf(priceFrom);
                rsb.setPriceFrom(db);
            } catch (Exception e) {
                e.printStackTrace();
                Servlet.renderJson(Map.of("error", "价格格式不正确，请输入数字!"), resp);
                return;
            }
        }
        if (StringUtils.hasText(priceTo)) {
            try {
                Double db = Double.valueOf(priceTo);
                rsb.setPriceTo(db);
//                System.out.println("赋值给room对象" + rsb.getPriceTo());
            } catch (Exception e) {
                e.printStackTrace();
                Servlet.renderJson(Map.of("error", "价格格式不正确，请输入数字"), resp);
                return;
            }
        }

        int iPageNo = 1;
        if (StringUtils.hasText(pageNo)) {
            iPageNo = Integer.parseInt(pageNo);
        }
        int iPageSize = 10;
        if (StringUtils.hasText(pageSize)) {
            iPageSize = Integer.parseInt(pageSize);
        }

        PaginateInfo pi = new PaginateInfo(iPageNo, iPageSize);//分页对象
        List<Room> rooms = service.findAll(rsb, pi);

//        rooms.remove(0);
        //ajax响应类型
        String json = JSON.toJSONString(Map.of("rooms", rooms, "pi", pi));
        Servlet.renderJson(json, resp);

    }

    //删除选中记录
    private void delete() {
        String[] rmIds = req.getParameterValues("ids");
        Integer[] ids = Stream.of(rmIds).map(Integer::valueOf).toArray(Integer[]::new);
        //调用业务方法删除记录
        try {
            int rows = service.deleteByIds(ids);
            Servlet.renderJson(Map.of("success", true, "msg", "删除成功", "rows", rows), resp);
        } catch (Exception e) {
            e.printStackTrace();
            Servlet.renderJson(Map.of("success", false, "msg", "删除失败"), resp);
        }
    }

    private void save() throws IOException {
        String name = req.getParameter("name");
        String kind = req.getParameter("kind");
        String state = req.getParameter("state");
        String price = req.getParameter("price");
        String description = req.getParameter("description");


        HttpSession session = req.getSession();
        boolean passed = true;//是否通过校验
        List<String> errors = new ArrayList<>();
        Room rsb = new Room();
        //校验房间号（姓名）是否为空
        if (!StringUtils.hasText(name)) {
            passed = false;
            errors.add("房间姓名（房间号）不可为空");
        }
        //校验房间类型是否符合
        if (!"小床房".equals(kind) && !"中床房".equals(kind) && !"大床房".equals(kind)) {
            passed = false;
            errors.add("房间类型输入不正确");
        }
        //校验房间状态是否符合
        if (!"空闲".equals(state) && !"有客".equals(state) && !"维修".equals(state)) {
            passed = false;
            errors.add("房间状态输入不正确");
        }

        rsb.setName(name);
        rsb.setKind(kind);
        rsb.setPrice(Double.valueOf(price));
        rsb.setState(state);
        rsb.setDescription(description);


        if (!passed) {
            session.setAttribute("room", rsb);
            String error = errors.stream().collect(Collectors.joining("\n"));
            session.setAttribute("error", error);
            resp.sendRedirect(req.getContextPath() + "/room/add");
            return;
        }

        boolean b = service.save(rsb);

        if (b) {
            resp.sendRedirect(req.getContextPath() + "/room/list");
        } else {
            session.setAttribute("room", rsb);
            session.setAttribute("error", "保存学生异常");
            resp.sendRedirect(req.getContextPath() + "/room/add");
        }
    }

    private void edit() throws IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String kind = req.getParameter("kind");
        String state = req.getParameter("state");
        String price = req.getParameter("price");
        String description = req.getParameter("description");


        HttpSession session = req.getSession();
        Room rsb = new Room();
        //校验id
        if (id == null) {
            Servlet.renderJson(Map.of("error", "要修改的房间编号不可为空"), resp);
            return;
        }
        try {
            rsb.setId(Integer.valueOf(id));
        } catch (NumberFormatException e) {
            Servlet.renderJson(Map.of("error", "要修改的房间编号不正确"), resp);
            return;
        }
        //校验房间号（姓名）是否为空
        if (!StringUtils.hasText(name)) {
            Servlet.renderJson(Map.of("error", "房间号不可为空"), resp);
            return;
        }
        //校验房间类型是否符合
        if (!"小床房".equals(kind) && !"中床房".equals(kind) && !"大床房".equals(kind)) {
            Servlet.renderJson(Map.of("error", "房间类型不可为空"), resp);
            return;
        }
        //校验房间状态是否符合
        if (!"空闲".equals(state) && !"有客".equals(state) && !"维修".equals(state)) {
            Servlet.renderJson(Map.of("error", "房间状态不可为空"), resp);
            return;
        }
        //校验价格
        if (price == null) {
            Servlet.renderJson(Map.of("error", "要修改的房间价格不可为空"), resp);
            return;
        }
        try {
            rsb.setPrice(Double.valueOf(price));
        } catch (NumberFormatException e) {
            Servlet.renderJson(Map.of("error", "要修改的房间价格不正确"), resp);
            return;
        }

        rsb.setName(name);
        rsb.setKind(kind);
        rsb.setState(state);
        rsb.setDescription(description);

        boolean b = service.update(rsb);

        if (b) {
            Servlet.renderJson(Map.of("success", true), resp);
        } else {
            Servlet.renderJson(Map.of("success", false, "error", "保存房间信息错误"), resp);
        }
    }


}
