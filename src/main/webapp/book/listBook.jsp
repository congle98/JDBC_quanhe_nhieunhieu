<%--
  Created by IntelliJ IDEA.
  User: CONG
  Date: 5/29/2021
  Time: 9:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Quản lý BooksList</title>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
</head>
<body>
<nav class="navbar navbar-dark bg-dark fixed-top">
    <div class="container-fluid">
        <a class="navbar-brand btn btn-outline-info" href="/Books?action=create">Thêm sản phẩm mới</a>
    </div>
</nav>

<div style="margin-top: 70px">
    <table class="table table-hover">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Mã sách</th>
            <th scope="col">Tên sách</th>
            <th scope="col">Tên tác giả</th>
            <th scope="col">Mô tả sách</th>
            <th scope="col">Thuộc loại</th>
            <th colspan="2" scope="col">Thao tác</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${bookList}" var="b">
            <tr>
                <td scope="row">${b.id}</td>
                <td>${b.name}</td>
                <td>${b.author}</td>
                <td>${b.description}</td>
                <td><c:forEach items="${b.categories}" var="category" varStatus="loop">
                    ${category.name} &nbsp &nbsp
                </c:forEach></td>
                <td>
                    <a class="btn btn-outline-warning" href="/Books?action=edit&id=${b.id}">Sửa</a>
                </td>

                <td>
                    <a class="btn btn-outline-danger" href="/Books?action=delete&id=${b.id}">Xoá</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
</html>
