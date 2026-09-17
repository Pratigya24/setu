<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<section class="hero-v2">
    <div class="hero-v2__decor hero-v2__decor--a"></div>
    <div class="hero-v2__decor hero-v2__decor--b"></div>

    <div class="hero-v2__grid">
        <div class="hero-v2__content">
            <div class="hero-v2__badge">🤝 Connecting Donors, Charitable Homes &amp; Volunteers</div>

            <h1>Give hope, one <span>donation</span> at a time</h1>
            <p>SETU bridges surplus resources with the people who need them most &mdash; through a transparent, verified, and efficient platform.</p>

            <div class="hero-v2__actions">
                <c:choose>
                    <c:when test="${empty sessionScope.role}">
                        <a href="${pageContext.request.contextPath}/register" class="btn btn-primary">Get Started &rarr;</a>
                        <a href="${pageContext.request.contextPath}/login" class="btn btn-outline">I already have an account</a>
                    </c:when>
                    <c:when test="${sessionScope.role == 'DONOR'}">
                        <a href="${pageContext.request.contextPath}/donor/browse-ngos" class="btn btn-primary">Browse Charitable Homes &amp; Donate</a>
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

            <div class="stats-strip stats-strip--left">
                <div class="stats-strip__item">
                    <div class="stats-strip__number">${totalDonors != null ? totalDonors : '0'}+</div>
                    <div class="stats-strip__label">Active Donors</div>
                </div>
                <div class="stats-strip__item">
                    <div class="stats-strip__number">${totalNgos != null ? totalNgos : '0'}+</div>
                    <div class="stats-strip__label">Verified Charitable Homes</div>
                </div>
                <div class="stats-strip__item">
                    <div class="stats-strip__number">${totalDonations != null ? totalDonations : '0'}+</div>
                    <div class="stats-strip__label">Donations Made</div>
                </div>
            </div>
        </div>

        <div class="hero-v2__visual">
            <div class="hero-v2__image-stack">
                <img class="hero-v2__img-main" src="https://images.unsplash.com/photo-1569003376670-596a186b90f8?q=80&w=900&auto=format&fit=crop" alt="School children smiling at a charitable home">
                <img class="hero-v2__img-small" src="https://images.unsplash.com/photo-1669532673647-b1185ea1a594?q=80&w=500&auto=format&fit=crop" alt="Donor packing clothes for donation">

                <div class="floating-card floating-card--top">
                    <span class="floating-card__icon">✅</span>
                    <div>
                        <strong>Verified Homes</strong>
                        <p>Every listing admin-approved</p>
                    </div>
                </div>

                <div class="floating-card floating-card--bottom">
                    <span class="floating-card__icon">📦</span>
                    <div>
                        <strong>New Donation</strong>
                        <p>Winter blankets &bull; just now</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<section class="section-v2">
    <p class="section-v2__eyebrow">How It Works</p>
    <h2>One platform, three roles, endless impact</h2>

    <div class="role-grid">
        <div class="role-card">
            <img class="role-card-img" src="https://images.unsplash.com/photo-1514792368985-f80e9d482a02?q=80&w=800&auto=format&fit=crop" alt="Pile of donation boxes ready to give">
            <div class="role-card__icon">🎁</div>
            <h3>Donors</h3>
            <p>Browse charitable home requirements and donate food, clothes, books, or medical items directly against a real, verified need.</p>
        </div>
        <div class="role-card">
            <img class="role-card-img" src="https://images.unsplash.com/photo-1574722772633-e401c33eb317?q=80&w=800&auto=format&fit=crop" alt="Community members supporting each other">
            <div class="role-card__icon">🏢</div>
            <h3>Charitable Homes</h3>
            <p>Post requirements, receive verified donations, and manage everything through a single, simple dashboard.</p>
        </div>
        <div class="role-card">
            <img class="role-card-img" src="https://images.unsplash.com/photo-1758599668408-dfb4a7ed22c0?q=80&w=800&auto=format&fit=crop" alt="Volunteers doing community service outdoors">
            <div class="role-card__icon">💚</div>
            <h3>Volunteers</h3>
            <p>Get assigned to donation pickups and help charitable homes coordinate resources efficiently on the ground.</p>
        </div>
    </div>
</section>

<section class="impact-strip">
    <h2>Real people. Real impact.</h2>
    <p>Every item donated through SETU reaches an orphanage, old age home, or family that truly needs it.</p>

    <div class="stats-strip">
        <div class="stats-strip__item">
            <div class="stats-strip__number">${totalDonations != null ? totalDonations : '0'}+</div>
            <div class="stats-strip__label">Items Donated</div>
        </div>
        <div class="stats-strip__item">
            <div class="stats-strip__number">${totalNgos != null ? totalNgos : '0'}+</div>
            <div class="stats-strip__label">Charitable Homes Supported</div>
        </div>
        <div class="stats-strip__item">
            <div class="stats-strip__number">${totalDonors != null ? totalDonors : '0'}+</div>
            <div class="stats-strip__label">People Who Cared</div>
        </div>
    </div>
</section>

<section class="gallery-section">
    <p class="section-v2__eyebrow">Moments of Giving</p>
    <h2>Every donation tells a story</h2>

    <div class="gallery-grid">
        <img class="gallery-item-tall" src="https://images.unsplash.com/photo-1593113646773-028c64a8f1b8?q=80&w=800&auto=format&fit=crop" alt="People with donation boxes at a food bank">
        <img src="https://images.unsplash.com/photo-1488521787991-ed7bbaae773c?q=80&w=800&auto=format&fit=crop" alt="Children receiving care at a charitable home">
        <img src="https://images.unsplash.com/photo-1669532673647-b1185ea1a594?q=80&w=800&auto=format&fit=crop" alt="Clothes being packed for donation">
        <img src="https://images.unsplash.com/photo-1574722772633-e401c33eb317?q=80&w=800&auto=format&fit=crop" alt="Community distributing groceries">
        <img src="https://images.unsplash.com/photo-1758272133786-ee98adcc6837?q=80&w=800&auto=format&fit=crop" alt="A community coming together">
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