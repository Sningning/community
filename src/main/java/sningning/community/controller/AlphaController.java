package sningning.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sningning.community.service.AlphaService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @ClassName: AlphaController
 * @Author: Song Ningning
 * @Date: 2020-04-20 23:05
 */
@Controller
@RequestMapping("/alpha")
public class AlphaController {

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "Hello SpringBoot!";
    }

    @Autowired
    private AlphaService alphaService;

    @RequestMapping("/data")
    @ResponseBody
    public String getData() {
        return alphaService.find();
    }

    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response) {
        // 获取请求数据
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + ": " + value);
        }
        System.out.println(request.getParameter("code"));

        // 返回响应数据
        response.setContentType("text/html;charset=utf-8");
        try (
                PrintWriter writer = response.getWriter()
        ) {
            writer.write("<h1>响应数据</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 演示 GET 请求
     */

    // /students?current=2&limit=20
    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current", required = false, defaultValue = "1") int current,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }

    // /student/123
    @RequestMapping(path = "/student/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id) {
        System.out.println(id);
        return "a student";
    }


    /**
     * 演示 POST 请求
     */
    @RequestMapping(path = "/student", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name, int age) {
        System.out.println(name);
        System.out.println(age);
        return "success";
    }


    /**
     * 响应 HTML 数据
     */
    // 方式一
    // ModelAndView: 处理方法返回值类型为 ModelAndView 时, 方法体即可通过该对象添加模型数据
    // 不加 @ResponseBody 默认返回 html
    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("name", "张三");
        mv.addObject("age", "30");
        mv.setViewName("/demo/view");
        return mv;
    }

    // 方式二
    // Model: 入参为 org.springframework.ui.Model，处理方法返回时，Model 中的数据会自动添加到模型中。
    // 不加 @ResponseBody 默认返回 html
    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool(Model model) {
        model.addAttribute("name", "北京大学");
        model.addAttribute("age", "100");
        // 返回的是 view 的路径
        return "/demo/view";
    }


    /**
     * 响应 JSON 数据（异步请求）
     * 编写写目标方法，使其返回 JSON 对应的对象或集合
     */
    @RequestMapping(path = "emp", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEmp() {
        HashMap<String, Object> emp = new HashMap<>();
        emp.put("name", "张三");
        emp.put("age", "23");
        emp.put("salary", "10000");
        return emp;
    }

    @RequestMapping(path = "emps", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getEmps() {
        List<Map<String, Object>> emps = new LinkedList<>();

        HashMap<String, Object> emp1 = new HashMap<>();
        emp1.put("name", "张三");
        emp1.put("age", "23");
        emp1.put("salary", "10000.0");
        emps.add(emp1);

        HashMap<String, Object> emp2 = new HashMap<>();
        emp2.put("name", "李四");
        emp2.put("age", "25");
        emp2.put("salary", "15000.0");
        emps.add(emp2);

        return emps;
    }

}
