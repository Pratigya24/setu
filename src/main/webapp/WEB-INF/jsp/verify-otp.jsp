<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="auth-wrapper">
    <div class="auth-wrapper__header">
        <h2>Verify OTP</h2>
        <p>Enter the OTP sent to ${email} and set a new password.</p>
    </div>

    <div class="auth-card">
        <c:if test="${not empty errorMsg}">
            <div class="error-msg">${errorMsg}</div>
        </c:if>
        <c:if test="${not empty successMsg}">
            <div class="success-msg">${successMsg}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/verify-otp" method="post">
            <input type="hidden" name="email" value="${email}">

            <div class="form-group">
                <label for="otp">OTP</label>
                <input type="text" id="otp" name="otp" placeholder="Enter 6-digit OTP" required maxlength="6">
            </div>
            <div class="form-group">
                <label for="newPassword">New Password</label>
                <input type="password" id="newPassword" name="newPassword" required minlength="6">
            </div>
            <div class="form-group">
                <label for="confirmPassword">Confirm Password</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required minlength="6">
            </div>
            <button type="submit" class="btn btn-primary">Reset Password</button>
        </form>
    </div>
</div>

<jsp:include page="footer.jsp" /></parameter>