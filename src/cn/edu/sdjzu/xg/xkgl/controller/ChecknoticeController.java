package cn.edu.sdjzu.xg.xkgl.controller;

import cn.edu.sdjzu.xg.xkgl.domain.Notice;
import cn.edu.sdjzu.xg.xkgl.service.NoticeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.TreeSet;

@WebServlet("/checknoticeController")
public class ChecknoticeController extends HttpServlet{
    //查看通知详情
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException,IOException {
       int id = Integer.parseInt(req.getParameter("id"));
       System.out.println(id);
        try {
            //通过id查找通知
            Notice notice = NoticeService.getInstance().find(id);
            //将找到的notice存入请求
            req.setAttribute("notice",notice);
            //转发页面
            req.getRequestDispatcher("pages/detailedNotice.jsp").forward(req,resp);
        } catch (SQLException e) {
            req.setAttribute("message","查看通知详情失败");
            req.getRequestDispatcher("/pages/error.jsp").forward(req,resp);
        }
    }
}
