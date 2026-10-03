<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <aside class="sidebar">
        <p class="sidebar__title">Volunteer Menu</p>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/volunteer/dashboard">Dashboard</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/volunteer/assignments">My Assignments</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/volunteer/available-pickups">Available Pickups</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/volunteer/profile">Profile</a>
    </aside>

    <div class="dashboard-content">
        <h1>Available Pickups</h1>
        <p class="subtitle">Donations accepted by charitable homes. First come, first served &mdash; accept one to take it up.</p>

        <c:if test="${not empty successMsg}"><div class="success-msg">${successMsg}</div></c:if>
        <c:if test="${not empty errorMsg}"><div class="error-msg">${errorMsg}</div></c:if>

        <c:forEach var="d" items="${pickupList}">
            <div class="item-card" style="align-items:flex-start;">
                <div class="item-card__info">
                    <h3>${d.title} &mdash; Qty ${d.quantity}</h3>
                    <p><strong>Pickup from:</strong> ${d.pickupAddress}</p>
                    <p><strong>Deliver to:</strong> ${d.ngo.name}, ${d.ngo.fullAddress}</p>
                    <p><strong>NGO phone:</strong> ${d.ngo.phone}</p>
                </div>
                <form action="${pageContext.request.contextPath}/volunteer/claim-pickup" method="post">
                    <input type="hidden" name="donationId" value="${d.id}">
                    <button type="submit" class="btn btn-primary">Accept Pickup</button>
                </form>
            </div>
        </c:forEach>

        <c:if test="${empty pickupList}">
            <div class="empty-state">No pickups available right now. Check back soon!</div>
        </c:if>
    </div>
</div>

<jsp:include page="footer.jsp" />
