<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <aside class="sidebar">
        <p class="sidebar__title">Donor Menu</p>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/donor/dashboard">Dashboard</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/donor/browse-ngos">Browse NGOs</a>
        <a class="sidebar__link sidebar__link--active" href="${pageContext.request.contextPath}/donor/my-donations">My Donations</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/donor/profile">My Profile</a>
    </aside>

    <div class="dashboard-content">
        <h1>My Donations</h1>
        <p class="subtitle">All the donations you've made so far</p>

        <c:forEach var="donation" items="${donationList}">
            <div class="item-card">
                <div class="item-card__info">
                    <h3>${donation.itemName} &mdash; Qty ${donation.quantity}</h3>
                    <p>Donated on ${donation.donationDate} &bull; Pickup: ${donation.pickupAddress}</p>
                </div>
                <span class="pill pill-open">${donation.status}</span>
            </div>
        </c:forEach>

        <c:if test="${empty donationList}">
            <div class="empty-state">
                You haven't made any donations yet.
                <a href="${pageContext.request.contextPath}/donor/browse-ngos">Browse NGOs</a> to get started.
            </div>
        </c:if>
    </div>
</div>

<jsp:include page="footer.jsp" />