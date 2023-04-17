package com.hotel.servlet;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSON;
import com.hotel.global.UploadDataListener;
import com.hotel.model.Client;
import com.hotel.model.ClientSearchBean;
import com.hotel.service.ClientService;
import com.hotel.service.impl.ClientServiceImpl;
import com.hotel.util.BeanFactory;
import com.hotel.util.PaginateInfo;
import com.hotel.util.Servlet;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebServlet("/client/*")
public class ClientServlet extends HttpServlet {

    private final ClientService service = BeanFactory.getBean(ClientServiceImpl.class);
    private HttpServletRequest req;
    private HttpServletResponse resp;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String ctx = req.getContextPath();//上下文路径
        String action = uri.replace(ctx, "");

        if (action.equals("/client/list")) {//客户列表
            req.getRequestDispatcher("/WEB-INF/jsp/client/list.jsp").forward(req, resp);

        } else if (action.equals("/client/add")) {//添加房间
            HttpSession session = req.getSession();
            Client clt = (Client) session.getAttribute("client");
            String error = (String) session.getAttribute("error");

            if (clt != null) {
                req.setAttribute("client", clt);
                session.removeAttribute("client");
                req.setAttribute("error", error);
                session.removeAttribute("error");
            }

            req.getRequestDispatcher("/WEB-INF/jsp/client/add.jsp").forward(req, resp);

        } else if (action.equals("/client/edit")) {
            String id = req.getParameter("id");
            try {
                Integer iId = Integer.valueOf(id);
                Client clt = service.findById(iId);
                if (clt == null) {
                    req.setAttribute("error", "要修改的客户不存在");
                } else {
                    req.setAttribute("client", clt);
                }
            } catch (NumberFormatException e) {
                req.setAttribute("error", "请指定要修改的客户的id");
            }

            req.getRequestDispatcher("/WEB-INF/jsp/client/edit.jsp").forward(req, resp);

        } else if (action.equals("/client/export")) {
//            System.out.println("收到请求");
            export();
        } else if (action.equals("/client/import")) {
            req.getRequestDispatcher("/WEB-INF/jsp/client/import.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.req = req;
        this.resp = resp;

        String uri = req.getRequestURI();
        String ctx = req.getContextPath();//上下文路径
        String action = uri.replace(ctx, "");

        if (action.equals("/client/list")) {//列表查询
            search();
        } else if (action.equals("/client/delete")) {//删除
            delete();
        } else if (action.equals("/client/add")) {
            //System.out.println("触发跳转");
            save();
        } else if (action.equals("/client/edit")) {
            edit();
        } else if (action.equals("/client/checkClientId")) {//检查是否有重复客户卡号
            String clientId = req.getParameter("clientId");

            boolean b = service.hasClientId(clientId);
            Servlet.renderJson(Map.of("exist", b), resp);

        } else if (action.equals("/client/import")) {

            importFromExcel();
        }
    }


    private void search() {//列表查询
        String pageNo = req.getParameter("pageNo");
        String pageSize = req.getParameter("pageSize");

        String clientId = req.getParameter("clientId");
        String name = req.getParameter("name");
        String sex = req.getParameter("sex");
        /*String ageFrom = req.getParameter("ageFrom");
        String ageTo = req.getParameter("ageTo");*/
        String birthdayFrom = req.getParameter("birthdayFrom");
        String birthdayTo = req.getParameter("birthdayTo");
        String phone = req.getParameter("phone");


        ClientSearchBean csb = new ClientSearchBean();//查询条件
        if (StringUtils.hasText(clientId)) {
            csb.setClientId(clientId);
        }
        if (StringUtils.hasText(name)) {
            csb.setName(name);
        }
        if (StringUtils.hasText(sex)) {
            csb.setSex(sex);
        }
        //按校验年龄
        /*if (StringUtils.hasText(ageFrom)) {
            try {
                Integer it = Integer.valueOf(ageFrom);
                csb.setAgeFrom(it);
            } catch (Exception e) {
                e.printStackTrace();
                Servlet.renderJson(Map.of("error", "年龄格式不正确，请输入数字!"), resp);
                return;
            }
        }
        if (StringUtils.hasText(ageTo)) {
            try {
                Integer it = Integer.valueOf(ageTo);
                csb.setAgeTo(it);
            } catch (Exception e) {
                e.printStackTrace();
                Servlet.renderJson(Map.of("error", "年龄格式不正确，请输入数字"), resp);
                return;
            }
        }*/
        if (StringUtils.hasText(birthdayFrom)) {
            LocalDate ld = null;
            try {
                ld = LocalDate.parse(birthdayFrom, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                csb.setBirthdayFrom(ld);
            } catch (Exception e) {
                e.printStackTrace();
                Servlet.renderJson(Map.of("error", "日期格式不正确"), resp);
                return;
            }
        }
        if (StringUtils.hasText(birthdayTo)) {
            LocalDate ld = null;
            try {
                ld = LocalDate.parse(birthdayTo, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                csb.setBirthdayTo(ld);
            } catch (Exception e) {
                e.printStackTrace();
                Servlet.renderJson(Map.of("error", "日期格式不正确"), resp);
                return;
            }
        }
        if (StringUtils.hasText(phone)) {
            csb.setPhone(phone);
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
        List<Client> clients = service.findAll(csb, pi);
//        System.out.println(pi.getNavFirst());
//        System.out.println(pi.getNavLast());
        //ajax响应类型
        String json = JSON.toJSONString(Map.of("clients", clients, "pi", pi));
//        System.out.println(pi.getNavLast());
//        System.out.println(pi.getNavFirst());
        Servlet.renderJson(json, resp);

    }

    private void delete() {
        String[] cltIds = req.getParameterValues("ids");
        Integer[] ids = Stream.of(cltIds).map(Integer::valueOf).toArray(Integer[]::new);
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
        String clientId = req.getParameter("clientId");
        String name = req.getParameter("name");
        String sex = req.getParameter("sex");
//        String age = req.getParameter("age");
        String birthday = req.getParameter("birthday");
        String phone = req.getParameter("phone");
        String roomId = req.getParameter("roomId");
        String description = req.getParameter("description");


        HttpSession session = req.getSession();
        boolean passed = true;//是否通过校验
        List<String> errors = new ArrayList<>();
        //校验客户卡号是否为空
        Client clt = new Client();
        if (!StringUtils.hasText(clientId)) {
            passed = false;
            errors.add("客户卡号不可为空");
        }
        //校验姓名是否为空
        if (!StringUtils.hasText(name)) {
            passed = false;
            errors.add("客户姓名不可为空");
        }
        //校验生日格式是否正确
        try {
            LocalDate ld = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            clt.setBirthday(ld);
        } catch (Exception e) {
            passed = false;
            errors.add("日期格式不正确");
        }
        //校验手机号是否为空
        if (!StringUtils.hasText(phone)) {
            passed = false;
            errors.add("手机号不可为空");
        }
        //校验性别是否为男或者女
        if (!"男".equals(sex) && !"女".equals(sex)) {
            passed = false;
            errors.add("性别输入不正确");
        }
        if (!StringUtils.hasText(roomId)) {
            passed = false;
            errors.add("房间编号不可为空");
        }
        try {
            clt.setRoomId(Integer.valueOf(roomId));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            errors.add("房间编号不正确");
        }

        clt.setClientId(clientId);
        clt.setName(name);
        clt.setSex(sex);
//        clt.setAge(Integer.valueOf(age));
        clt.setPhone(phone);
        clt.setDescription(description);


        if (!passed) {
            session.setAttribute("client", clt);
            String error = errors.stream().collect(Collectors.joining("\n"));
            session.setAttribute("error", error);
            resp.sendRedirect(req.getContextPath() + "/client/add");
            return;
        }

        boolean b = service.save(clt);
        if (b) {
            resp.sendRedirect(req.getContextPath() + "/client/list");
        } else {
            session.setAttribute("client", clt);
            session.setAttribute("error", "保存学生异常");
            resp.sendRedirect(req.getContextPath() + "/client/add");
        }
    }

    //提交修改
    private void edit() {
        String id = req.getParameter("id");
        String clientId = req.getParameter("clientId");
        String name = req.getParameter("name");
        String sex = req.getParameter("sex");
//        String age = req.getParameter("age");
        String birthday = req.getParameter("birthday");
        String phone = req.getParameter("phone");
        String description = req.getParameter("description");


        HttpSession session = req.getSession();

        Client clt = new Client();
        //校验
        if (id == null) {
            Servlet.renderJson(Map.of("error", "要修改的客户编号不可为空"), resp);
            return;
        }
        try {
            clt.setId(Integer.valueOf(id));
        } catch (NumberFormatException e) {
            Servlet.renderJson(Map.of("error", "要修改的客户编号不正确"), resp);
            return;
        }
        //校验客户卡号是否为空
        if (!StringUtils.hasText(clientId)) {
            Servlet.renderJson(Map.of("error", "客户卡号不可为空"), resp);
            return;
        }
        //校验姓名是否为空
        if (!StringUtils.hasText(name)) {
            Servlet.renderJson(Map.of("error", "客户姓名不可为空"), resp);
            return;
        }
        //校验手机号是否为空
        if (!StringUtils.hasText(phone)) {
            Servlet.renderJson(Map.of("error", "手机号不可为空"), resp);
            return;
        }
        //校验性别是否为男或者女
        if (!"男".equals(sex) && !"女".equals(sex)) {
            Servlet.renderJson(Map.of("error", "性别输入不正确"), resp);
            return;
        }
        //校验生日格式是否正确
        try {
            LocalDate ld = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            clt.setBirthday(ld);
        } catch (Exception e) {
            e.printStackTrace();
            Servlet.renderJson(Map.of("error", "日期格式不正确"), resp);
            return;
        }


        clt.setClientId(clientId);
        clt.setName(name);
        clt.setSex(sex);
//        clt.setAge(Integer.valueOf(age));
        clt.setPhone(phone);
        clt.setDescription(description);


        boolean b = service.update(clt);

        if (b) {
            Servlet.renderJson(Map.of("success", true), resp);
        } else {
            Servlet.renderJson(Map.of("success", false, "error", "保存学生信息失败"), resp);
        }
    }

    //导出到excel
    private void export() {
//        System.out.println("进入导出函数");
        String type = req.getParameter("type");
        String[] ids = req.getParameterValues("ids");
        Integer[] iIds = Stream.of(ids).map(Integer::valueOf).toArray(Integer[]::new);

        if ("chosen".equals(type)) {//导出选中行
            List<Client> clients = service.findAll(iIds);

            //导出
            //response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            resp.setContentType("application/vnd.ms-excel");

            resp.setCharacterEncoding("utf-8");
            try {
                LocalDateTime now = LocalDateTime.now();
                String format = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                String fileName = URLEncoder.encode("客户信息列表导出" + format, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
                resp.setHeader("Content-Disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

                //导出
                EasyExcel.write(resp.getOutputStream(), Client.class).sheet("模板").doWrite(clients);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //从Excel导入
    private void importFromExcel() throws ServletException, IOException {
//        System.out.println("导入函数");
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(factory);
        try {
            //jakartaEE需要手动实现参数类型
            List<FileItem> items = sfu.parseRequest(req);

            for (FileItem fi : items) {
                if (!fi.isFormField()) {
                    if (fi.getSize() > 0) {
                        InputStream is = fi.getInputStream();//上传文件的输入流
                        EasyExcel.read(is, Client.class, new UploadDataListener()).sheet().doRead();
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }

        resp.sendRedirect(req.getContextPath() + "/client/list");

    }

}


