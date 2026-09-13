<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <aside class="sidebar">
        <p class="sidebar__title">Volunteer Menu</p>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/volunteer/dashboard">Dashboard</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/volunteer/assignments">My Assignments</a>
        <a class="sidebar__link sidebar__link--active" href="${pageContext.request.contextPath}/volunteer/profile">Profile</a>
    </aside>

    <div class="dashboard-content">
        <h1>My Profile</h1>
        <p class="subtitle">View and update your volunteer details</p>

        <c:if test="${not empty successMsg}">
            <div class="success-msg">${successMsg}</div>
        </c:if>

        <div class="form-wrapper" style="margin: 0;">
            <form action="${pageContext.request.contextPath}/volunteer/profile" method="post">
                <div class="form-group">
                    <label for="name">Full Name</label>
                    <input type="text" id="name" name="name" value="${volunteerProfile.name}" required>
                </div>

                <div class="form-group">
                    <label for="email">Email (cannot be changed)</label>
                    <input type="email" id="email" value="${volunteerProfile.email}" disabled
                           style="background-color:#f1f5f9; cursor:not-allowed;">
                </div>

                <div class="form-group">
                    <label for="phone">Phone</label>
                    <input type="text" id="phone" name="phone" value="${volunteerProfile.phone}" required>
                </div>

                <div class="form-group">
                    <label for="address">Address</label>
                    <input type="text" id="address" name="address" value="${volunteerProfile.address}">
                </div>

                <div class="form-group">
                    <label for="availability">Availability</label>
                    <input type="text" id="availability" name="availability" value="${volunteerProfile.availability}" placeholder="e.g. Weekends">
                </div>

                <button type="submit" class="btn btn-primary">Save Changes</button>
            </form>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />