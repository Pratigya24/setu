<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="auth-wrapper">
    <div class="auth-wrapper__header">
        <h2>Forgot Password</h2>
        <p>Enter your registered email to reset your password.</p>
    </div>

    <div class="auth-card">
        <c:if test="${not empty errorMsg}">
            <div class="error-msg">${errorMsg}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/forgot-password" method="post">
            <div class="form-group">
                <label for="email">Registered Email</label>
                <input type="email" id="email" name="email" placeholder="you@example.com" required>
            </div>
            <button type="submit" class="btn btn-primary">Continue</button>
        </form>

        <p class="auth-footer-link">Remember your password? <a href="${pageContext.request.contextPath}/login">Sign in</a></p>
    </div>
</div>

<jsp:include page="footer.jsp" />