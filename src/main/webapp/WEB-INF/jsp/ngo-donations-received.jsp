<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <aside class="sidebar">
        <p class="sidebar__title">NGO Menu</p>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/ngo/dashboard">Dashboard</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/ngo/post-requirement">Post Requirement</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/ngo/requests">My Requests</a>
        <a class="sidebar__link sidebar__link--active" href="${pageContext.request.contextPath}/ngo/donations-received">Donations Received</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/ngo/profile">Profile</a>
    </aside>

    <div class="dashboard-content">
        <h1>Donations Received</h1>
        <p class="subtitle">Donations donors have made against your requests</p>

        <c:forEach var="donation" items="${receivedDonations}">
            <div class="item-card">
                <div class="item-card__info">
                    <h3>${donation.title} &mdash; Qty ${donation.quantity}</h3>
                    <p>From: ${donation.donor.name} &bull; ${donation.donationDate}</p>
                </div>
                <span class="pill pill-open">${donation.status}</span>
            </div>
        </c:forEach>

        <c:if test="${empty receivedDonations}">
            <div class="empty-state">No donations received yet.</div>
        </c:if>
    </div>
</div>

<jsp:include page="footer.jsp" />