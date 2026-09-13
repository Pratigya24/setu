<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="auth-wrapper">
    <div class="auth-wrapper__header">
        <h2>Reset Password</h2>
        <p>Set a new password for ${email}</p>
    </div>

    <div class="auth-card">
        <c:if test="${not empty errorMsg}">
            <div class="error-msg">${errorMsg}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/reset-password" method="post">
            <input type="hidden" name="email" value="${email}">

            <div class="form-group">
                <label for="newPassword">New Password</label>
                <input type="password" id="newPassword" name="newPassword" placeholder="Enter new password" required minlength="6">
            </div>
            <div class="form-group">
                <label for="confirmPassword">Confirm Password</label>
                <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Re-enter new password" required minlength="6">
            </div>
            <button type="submit" class="btn btn-primary">Reset Password</button>
        </form>
    </div>
</div>

<jsp:include page="footer.jsp" />