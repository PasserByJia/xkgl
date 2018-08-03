<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/4/15 0015
  Time: 下午 12:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>课程补选</h1>
<c:url var="find" value="/findCourseController" />
<form action="${find}" method="post">
    学生学号：
    <input type="text" name="studentNo"/>
    <br>
    课程名称：
    <select name="courseId">
        <c:forEach var="course" items="${course}">
            <option value="${course.id}">
                    ${course.title}
            </option>
        </c:forEach>
    </select>
    <input type="submit">
</form>

</body>
</html>
