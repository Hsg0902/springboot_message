package com.hwua.web.servlet;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hwua.pojo.Message;
import com.hwua.pojo.User;
import com.hwua.service.IMessageService;
import com.hwua.util.StringUtil;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/msg.do")
public class MessageServlet extends HttpServlet {
    IMessageService msgService = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());//从ServletContext中取出IOC容器
        msgService = ctx.getBean(IMessageService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String param = req.getParameter("param");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        if (param.equals("queryAllMsgs")) {
            //从session中取出当前登录的User对象
            User user = (User) req.getSession().getAttribute("user");
            Integer pageNo = Integer.parseInt(req.getParameter("pageNo"));//当前页
            Integer pageSize = Integer.parseInt(req.getParameter("pageSize"));//每页显示的记录数
            try {
                /**
                 * 把每页要用到的数据都封装在PageEntity中
                 */
                PageHelper.startPage(pageNo,pageSize);
                List<Message> list = msgService.queryMessageByLoginUser(user.getId());
                PageInfo<Message> pageInfo = new PageInfo<>(list);
//                String jsonStr = JSON.toJSONString(pageInfo, SerializerFeature.PrettyFormat);
//                System.out.println(jsonStr);
//                out.write(jsonStr);
                //req.setAttribute("pageEntity", pageEntity);
                //req.getRequestDispatcher("/main.jsp").forward(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (param.equals("showMsgById")) {
            String id = req.getParameter("id");
            try {
                Message msg = msgService.queryMessageById(id);
                req.setAttribute("msg", msg);
                req.getRequestDispatcher("/readMsg.jsp").forward(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (param.equals("delMsg")) {
            String id = req.getParameter("id");
            try {
                int res = msgService.deleteMsgById(Integer.parseInt(id));
                if (res > 0) {
                    out.write("success");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (param.equals("sendMsg")) {
            Long sendid = ((User) req.getSession().getAttribute("user")).getId();
            Integer receiveid = Integer.parseInt(req.getParameter("toUser"));
            String title = StringUtil.replaceStr(req.getParameter("title"));
            String msgContent = StringUtil.replaceStr(req.getParameter("content"));
            String msg_create_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            int state = 1;
            try {
                Message msg = new Message(sendid, title, msgContent, state, receiveid, msg_create_date);
                int res = msgService.sendMessage(msg);
                if (res > 0) {
                    out.write("success");
                    //req.getRequestDispatcher("/user.do?param=queryAllUsers").forward(req, resp);
                }
            } catch (Exception e) {
                out.write("failure");
                e.printStackTrace();
            }
        }
    }
}
