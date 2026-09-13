<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <aside class="sidebar">
        <p class="sidebar__title">Donor Menu</p>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/donor/dashboard">Dashboard</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/donor/browse-ngos">Browse NGOs</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/donor/my-donations">My Donations</a>
        <a class="sidebar__link sidebar__link--active" href="${pageContext.request.contextPath}/donor/profile">My Profile</a>
    </aside>

    <div class="dashboard-content">
        <h1>My Profile</h1>
        <p class="subtitle">View and update your personal details</p>

        <c:if test="${not empty successMsg}">
            <div class="success-msg">${successMsg}</div>
        </c:if>

        <div class="form-wrapper" style="margin: 0;">
            <form action="${pageContext.request.contextPath}/donor/profile" method="post">
                <div class="form-group">
                    <label for="name">Full Name</label>
                    <input type="text" id="name" name="name" value="${donorProfile.name}" required>
                </div>

                <div class="form-group">
                    <label for="email">Email (cannot be changed)</label>
                    <input type="email" id="email" value="${donorProfile.email}" disabled
                           style="background-color:#f1f5f9; cursor:not-allowed;">
                </div>

                <div class="form-group">
                    <label for="phone">Phone</label>
                    <input type="text" id="phone" name="phone" value="${donorProfile.phone}" required>
                </div>

                <div class="form-group">
                    <label for="address">Address</label>
                    <input type="text" id="address" name="address" value="${donorProfile.address}">
                </div>

                <button type="submit" class="btn btn-primary">Save Changes</button>
            </form>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />