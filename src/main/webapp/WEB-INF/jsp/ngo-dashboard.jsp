<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <aside class="sidebar">
        <p class="sidebar__title">NGO Menu</p>
        <a class="sidebar__link sidebar__link--active" href="${pageContext.request.contextPath}/ngo/dashboard">Dashboard</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/ngo/post-requirement">Post Requirement</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/ngo/requests">My Requests</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/ngo/donations-received">Donations Received</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/ngo/profile">Profile</a>
    </aside>

    <div class="dashboard-content">
        <h1>${sessionScope.userName} Dashboard</h1>
        <p class="subtitle">Manage your requirements and incoming donations</p>

        <div class="stat-grid">
            <div class="stat-card">
                <div class="stat-card__label">Open Requests</div>
                <div class="stat-card__value">${openRequests != null ? openRequests : 0}</div>
            </div>
            <div class="stat-card">
                <div class="stat-card__label">Donations Received</div>
                <div class="stat-card__value">${donationsReceived != null ? donationsReceived : 0}</div>
            </div>
            <div class="stat-card">
                <div class="stat-card__label">Verification Status</div>
                <div class="stat-card__value" style="font-size:1rem;">
                    <c:choose>
                        <c:when test="${ngoApproved}">
                            <span class="pill pill-low">Verified</span>
                        </c:when>
                        <c:otherwise>
                            <span class="pill pill-medium">Pending</span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <h2 style="font-size:1.1rem; margin-bottom:1rem;">Recent Requests</h2>

        <c:choose>
            <c:when test="${not empty myRequests}">
                <c:forEach var="request" items="${myRequests}">
                    <div class="item-card">
                        <div class="item-card__info">
                            <h3>${request.title}</h3>
                            <p>Needed: ${request.quantity} &bull; Posted: ${request.requestDate}</p>
                        </div>
                        <span class="pill pill-open">${request.status}</span>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="empty-state">No requirements posted yet. <a href="${pageContext.request.contextPath}/ngo/post-requirement">Post one now</a>.</div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<jsp:include page="footer.jsp" />