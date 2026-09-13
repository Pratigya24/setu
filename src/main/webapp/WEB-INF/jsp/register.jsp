<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="auth-wrapper">
    <div class="auth-wrapper__header">
        <h2>Join SETU</h2>
        <p>Become a donor, NGO, or volunteer and start making an impact today.</p>
    </div>

    <div class="auth-card">
        <c:if test="${not empty errorMsg}">
            <div class="error-msg">${errorMsg}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/register" method="post">

            <p class="auth-divider">I want to join as</p>

            <div class="role-select">
                <input type="radio" id="roleDonor" name="role" value="DONOR" checked>
                <label for="roleDonor">
                    <span class="role-select__icon">🎁</span>
                    Donor
                </label>

                <input type="radio" id="roleNgo" name="role" value="NGO">
                <label for="roleNgo">
                    <span class="role-select__icon">🏢</span>
                    NGO
                </label>

                <input type="radio" id="roleVolunteer" name="role" value="VOLUNTEER">
                <label for="roleVolunteer">
                    <span class="role-select__icon">💚</span>
                    Volunteer
                </label>
            </div>

            <div class="form-group">
                <label for="name">Full Name / Organization Name</label>
                <input type="text" id="name" name="name" placeholder="Your name" required>
            </div>

            <div class="field-row">
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" placeholder="you@example.com" required>
                </div>
                <div class="form-group">
                    <label for="phone">Phone</label>
                    <input type="text" id="phone" name="phone" placeholder="9876543210" required>
                </div>
            </div>

            <div class="form-group">
                <label for="address">Address</label>
                <input type="text" id="address" name="address" placeholder="City, State">
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="Create a password" required>
            </div>

            <button type="submit" class="btn btn-primary">Create Account</button>
        </form>

        <p class="auth-footer-link">Already have an account? <a href="${pageContext.request.contextPath}/login">Sign in</a></p>
    </div>
</div>

<jsp:include page="footer.jsp" />