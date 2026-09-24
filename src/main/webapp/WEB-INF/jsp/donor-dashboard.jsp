<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <aside class="sidebar">
        <p class="sidebar__title">Donor Menu</p>
        <a class="sidebar__link sidebar__link--active" href="${pageContext.request.contextPath}/donor/dashboard">Dashboard</a>
        
<a class="sidebar__link" href="${pageContext.request.contextPath}/donor/browse-ngos">Browse Charitable Homes</a>        
        <a class="sidebar__link" href="${pageContext.request.contextPath}/donor/my-donations">My Donations</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/donor/profile">My Profile</a>
    </aside>

    <div class="dashboard-content">

        <div class="dashboard-hero-banner">
            <div>
                <h2>Welcome back, ${sessionScope.userName}</h2>
                <p>Every donation you make brings a smile to someone who needs it most.</p>
            </div>
        </div>

        <p class="subtitle">Here's a summary of your donation activity</p>

        <div class="stat-grid">
            <div class="stat-card">
                <div class="stat-card__label">Total Donations</div>
                <div class="stat-card__value">${totalDonations != null ? totalDonations : 0}</div>
            </div>
            <div class="stat-card">
                <div class="stat-card__label">NGOs Helped</div>
                <div class="stat-card__value">${ngosHelped != null ? ngosHelped : 0}</div>
            </div>
            <div class="stat-card">
                <div class="stat-card__label">Pending Pickups</div>
                <div class="stat-card__value">${pendingPickups != null ? pendingPickups : 0}</div>
            </div>
        </div>

        <h2 style="font-size:1.1rem; margin-bottom:1rem; color:#0f172a;">Recent Donations</h2>

        <c:forEach var="donation" items="${recentDonations}">
            <div class="item-card">
                <div class="item-card__info">
                    <h3>${donation.title}</h3>
                    <p>Quantity: ${donation.quantity} &bull; Donated on ${donation.donationDate}</p>
                </div>
                <c:choose>
                    <c:when test="${donation.status == 'PENDING'}">
                        <span class="pill pill-medium">${donation.status}</span>
                    </c:when>
                    <c:when test="${donation.status == 'COMPLETED'}">
                        <span class="pill pill-low">${donation.status}</span>
                    </c:when>
                    <c:otherwise>
                        <span class="pill pill-open">${donation.status}</span>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:forEach>

        <c:if test="${empty recentDonations}">
            <div class="empty-state">
                No donations yet. <a href="${pageContext.request.contextPath}/donor/browse-ngos">Browse NGOs</a> to get started.
            </div>
        </c:if>
    </div>
</div>

<jsp:include page="footer.jsp" />