<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/views/common/header.jspf" %>
<div class="page-head">
    <h1>User <span class="count">(${totalItems})</span></h1>
    <a class="btn" href="<c:url value='/admin/users/new'/>">+ Thêm mới</a>
</div>
<form class="searchbar" method="get" action="<c:url value='/admin/users'/>">
    <input class="input" name="keyword" placeholder="🔍 Username, họ tên hoặc email..." value="<c:out value='${keyword}'/>">
    <button class="btn">Tìm kiếm</button>
    <a class="btn btn-light" href="<c:url value='/admin/users'/>">Xóa lọc</a>
</form>
<div class="table-wrap">
<table>
    <thead><tr><th style="width:60px">ID</th><th>Username</th><th>Họ tên</th><th>Email</th><th>Role</th><th>Trạng thái</th><th style="width:150px">Thao tác</th></tr></thead>
    <tbody>
    <c:forEach var="u" items="${items}">
        <tr>
            <td>${u.id}</td>
            <td><b><c:out value="${u.username}"/></b></td>
            <td><c:out value="${u.fullName}"/></td>
            <td><c:out value="${u.email}"/></td>
            <td><span class="badge ${u.role == 'ADMIN' ? 'b-admin' : 'b-user'}">${u.role}</span></td>
            <td><span class="badge ${u.enabled ? 'b-on' : 'b-off'}">${u.enabled ? 'Hoạt động' : 'Khóa'}</span></td>
            <td class="act">
                <a class="btn btn-sm btn-warn" href="<c:url value='/admin/users/${u.id}/edit'/>">Sửa</a>
                <form method="post" action="<c:url value='/admin/users/${u.id}/delete'/>" onsubmit="return confirm('Xóa người dùng này?');">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <button class="btn btn-sm btn-danger">Xóa</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${empty items}"><tr><td colspan="7" class="empty">Không có dữ liệu</td></tr></c:if>
    </tbody>
</table>
</div>
<c:set var="basePath" value="/admin/users"/>
<%@ include file="/WEB-INF/views/common/pagination.jspf" %>
<%@ include file="/WEB-INF/views/common/footer.jspf" %>
