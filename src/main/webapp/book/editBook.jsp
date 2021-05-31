<%--
  Created by IntelliJ IDEA.
  User: CONG
  Date: 5/29/2021
  Time: 10:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="a" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Sửa sách</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    <style>
        .form-product-1{
            margin: 75px auto;
            height: 570px;
        }
        .form-product{
            width: 90%;
            height: 90%;
            margin: auto;
        }

    </style>
</head>
<body>
<nav class="navbar navbar-dark bg-dark fixed-top">
    <div class="container-fluid">
        <a class="navbar-brand btn btn-outline-info" href="/Books">Thoát ra ngoài</a>
    </div>
</nav>

<div class="col-sm-5  border border-secondary border-5 rounded  form-product-1">
    <div class="form-product">
        <form method="post" >
            <div class="form-group">
                <label class="form-label" for="id">Id sách</label>
                <input class="form-control" name="id" id="id" value="${book.id}" disabled>
            </div>
            <div class="form-group">
                <label class="form-label" for="name">Tên sách</label>
                <input class="form-control" name="name" id="name" value="${book.name}"></br>
            </div>
            <div class="form-group">
                <label class="form-label" for="author">Tên tác giả</label>
                <input class="form-control" name="author" id="author" value="${book.author}"></br>
            </div>
            <div class="form-group">
                <label class="form-label" for="description">Mô tả sách</label>
                <input class="form-control" name="description" id="description" value="${book.description}"></br>
            </div>
            <div class="form-group">
                <label class="form-label" for="category_id">Loại sách</label>
                <select class="form-select" name="category_id"  id="category_id" multiple >
                    <c:forEach items="${categories}" var="category" >
                        <option value="${category.id}"
                                <c:forEach items="${categoriesOfBook}" var="cOB">
                                         <c:if test="${category.id == cOB.id}">selected="true"</c:if>
                                </c:forEach>>
                             ${category.name}
                        </option>
                    </c:forEach>
                </select></br>
<%--                <c:forEach items="${categoriesOfBook}" var="categoriesOfBook">--%>
<%--                    <c:if test="${category.id = categoriesOfBook.id}">selected="true"</c:if>--%>
<%--                </c:forEach>--%>
            </div>
            <button class="btn btn-danger" type="submit">Xác Nhận</button>
        </form>
    </div>
</div>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
</html>
