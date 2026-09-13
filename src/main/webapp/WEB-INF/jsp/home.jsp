<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<section class="hero-v2">
    <div class="hero-v2__badge">🤝 Connecting Donors, NGOs & Volunteers</div>

    <h1>Give hope, one <span>donation</span> at a time</h1>
    <p>SETU bridges surplus resources with the people who need them most &mdash; through a transparent, verified, and efficient platform.</p>

    <div class="hero-v2__actions">
        <c:choose>
            <c:when test="${empty sessionScope.role}">
                <a href="${pageContext.request.contextPath}/register" class="btn btn-primary">Get Started &rarr;</a>
                <a href="${pageContext.request.contextPath}/login" class="btn btn-outline">Login</a>
            </c:when>
            <c:when test="${sessionScope.role == 'DONOR'}">
                <a href="${pageContext.request.contextPath}/donor/browse-ngos" class="btn btn-primary">Browse NGOs &amp; Donate</a>
            </c:when>
            <c:when test="${sessionScope.role == 'NGO'}">
                <a href="${pageContext.request.contextPath}/ngo/post-requirement" class="btn btn-primary">Post a Requirement</a>
            </c:when>
            <c:when test="${sessionScope.role == 'VOLUNTEER'}">
                <a href="${pageContext.request.contextPath}/volunteer/dashboard" class="btn btn-primary">Go to Dashboard</a>
            </c:when>
            <c:when test="${sessionScope.role == 'ADMIN'}">
                <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-primary">Go to Dashboard</a>
            </c:when>
        </c:choose>
    </div>

    <div class="stats-strip">
        <div class="stats-strip__item">
            <div class="stats-strip__number">${totalDonors != null ? totalDonors : '0'}+</div>
            <div class="stats-strip__label">Active Donors</div>
        </div>
        <div class="stats-strip__item">
            <div class="stats-strip__number">${totalNgos != null ? totalNgos : '0'}+</div>
            <div class="stats-strip__label">Verified NGOs</div>
        </div>
        <div class="stats-strip__item">
            <div class="stats-strip__number">${totalDonations != null ? totalDonations : '0'}+</div>
            <div class="stats-strip__label">Donations Made</div>
        </div>
    </div>
</section>

<section class="section-v2">
    <p class="section-v2__eyebrow">How It Works</p>
    <h2>One platform, three roles, endless impact</h2>

    <div class="role-grid">
        <div class="role-card">
            <div class="role-card__icon">🎁</div>
            <h3>Donors</h3>
            <p>Browse NGO requirements and donate food, clothes, books, or medical items directly against a real, verified need.</p>
        </div>
        <div class="role-card">
            <div class="role-card__icon">🏢</div>
            <h3>NGOs</h3>
            <p>Post requirements, receive verified donations, and manage everything through a single, simple dashboard.</p>
        </div>
        <div class="role-card">
            <div class="role-card__icon">💚</div>
            <h3>Volunteers</h3>
            <p>Get assigned to donation pickups and help NGOs coordinate resources efficiently on the ground.</p>
        </div>
    </div>
</section>

<c:if test="${empty sessionScope.role}">
    <div class="cta-banner">
        <h2>Ready to make a difference?</h2>
        <p>Join SETU today and become part of a growing community of changemakers.</p>
        <a href="${pageContext.request.contextPath}/register" class="btn">Join SETU Now</a>
    </div>
</c:if>

<jsp:include page="footer.jsp" />