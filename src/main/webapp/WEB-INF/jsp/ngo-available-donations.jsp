<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <aside class="sidebar">
        <p class="sidebar__title">NGO Menu</p>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/ngo/dashboard">Dashboard</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/ngo/post-requirement">Post Requirement</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/ngo/requests">My Requests</a>
        <a class="sidebar__link sidebar__link--active" href="${pageContext.request.contextPath}/ngo/available-donations">Available Items</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/ngo/donations-received">Donations Received</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/ngo/profile">Profile</a>
    </aside>

    <div class="dashboard-content">
        <h1>Available Items</h1>
        <p class="subtitle">Items donors have offered. First come, first served &mdash; claim before another NGO does.</p>

        <c:if test="${not empty successMsg}">
            <div class="success-msg">${successMsg}</div>
        </c:if>
        <c:if test="${not empty errorMsg}">
            <div class="error-msg">${errorMsg}</div>
        </c:if>

        <c:forEach var="item" items="${availableList}">
            <div class="item-card">
                <div class="item-card__info">
                    <h3>${item.title}</h3>
                    <p>${item.description}</p>
                    <div class="item-card__meta">
                        <span class="pill pill-low">Qty: ${item.quantity}</span>
                        <span class="pill pill-open">Offered by: ${item.donor.name}</span>
                    </div>
                </div>
                <form action="${pageContext.request.contextPath}/ngo/accept-donation" method="post">
                    <input type="hidden" name="id" value="${item.id}">
                    <button type="submit" class="btn btn-primary">Accept</button>
                </form>
            </div>
        </c:forEach>

        <c:if test="${empty availableList}">
            <div class="empty-state">No items available right now. Check back soon!</div>
        </c:if>
    </div>
</div>

<jsp:include page="footer.jsp" />