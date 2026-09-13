<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="auth-wrapper">
    <div class="auth-wrapper__header">
        <h2>Welcome back</h2>
        <p>Sign in to continue making an impact.</p>
    </div>

    <div class="auth-card">
        <c:if test="${not empty errorMsg}">
            <div class="error-msg">${errorMsg}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" placeholder="you@example.com" required>
           <div class="form-group">
    <label for="password">Password</label>
    <input type="password" id="password" name="password" placeholder="Enter password" required>
</div>

<p style="text-align:right; margin-bottom:1rem; font-size:0.83rem;">
    <a href="${pageContext.request.contextPath}/forgot-password" style="color:#16a34a; font-weight:600;">Forgot password?</a>
</p>

<button type="submit" class="btn btn-primary">Sign In</button>

        <p class="auth-footer-link">Don't have an account? <a href="${pageContext.request.contextPath}/register">Register</a></p>
    </div>
</div>

<jsp:include page="footer.jsp" />