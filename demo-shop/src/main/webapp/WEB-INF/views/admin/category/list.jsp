<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/views/common/header.jspf" %>
<div class="page-head">
    <h1>Category <span class="count">(${totalItems})</span></h1>
    <a class="btn" href="<c:url value='/admin/categories/new'/>">+ Thêm mới</a>
</div>
<form class="searchbar" method="get" action="<c:url value='/admin/categories'/>">
    <input class="input" name="keyword" placeholder="🔍 Tìm theo tên danh mục..." value="<c:out value='${keyword}'/>">
    <button class="btn">Tìm kiếm</button>
    <a class="btn btn-light" href="<c:url value='/admin/categories'/>">Xóa lọc</a>
</form>
<div class="table-wrap">
<table>
    <thead><tr><th style="width:70px">ID</th><th>Tên</th><th>Mô tả</th><th style="width:150px">Thao tác</th></tr></thead>
    <tbody>
    <c:forEach var="item" items="${items}">
        <tr>
            <td>${item.id}</td>
            <td><b><c:out value="${item.name}"/></b></td>
            <td><c:out value="${item.description}"/></td>
            <td class="act">
                <a class="btn btn-sm btn-warn" href="<c:url value='/admin/categories/${item.id}/edit'/>">Sửa</a>
                <form method="post" action="<c:url value='/admin/categories/${item.id}/delete'/>" onsubmit="return confirm('Xóa danh mục này?');">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <button class="btn btn-sm btn-danger">Xóa</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${empty items}"><tr><td colspan="4" class="empty">Không có dữ liệu</td></tr></c:if>
    </tbody>
</table>
</div>
<c:set var="basePath" value="/admin/categories"/>
<%@ include file="/WEB-INF/views/common/pagination.jspf" %>
<%@ include file="/WEB-INF/views/common/footer.jspf" %>
