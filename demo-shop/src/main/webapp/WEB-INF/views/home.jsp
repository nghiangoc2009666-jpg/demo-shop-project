<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/views/common/header.jspf" %>
<div class="hero">
    <h1>Xin chào, <c:out value="${pageContext.request.userPrincipal.name}"/> 👋</h1>
    <p>Bạn đã đăng nhập thành công.</p>
</div>
<c:choose>
<c:when test="${pageContext.request.isUserInRole('ADMIN')}">
    <div class="tiles">
        <a class="tile" href="<c:url value='/admin/categories'/>"><div class="ic">📂</div><h3>Quản lý Category</h3><span>Thêm, sửa, xóa, tìm kiếm danh mục</span></a>
        <a class="tile" href="<c:url value='/admin/users'/>"><div class="ic">👥</div><h3>Quản lý User</h3><span>Quản lý tài khoản và phân quyền</span></a>
    </div>
</c:when>
<c:otherwise>
    <div class="card">Tài khoản của bạn có quyền <b>USER</b>. Khu vực quản trị chỉ dành cho ADMIN.</div>
</c:otherwise>
</c:choose>
<%@ include file="/WEB-INF/views/common/footer.jspf" %>
