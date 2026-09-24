<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <aside class="sidebar">
        <p class="sidebar__title">Donor Menu</p>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/donor/dashboard">Dashboard</a>
	<a class="sidebar__link sidebar__link--active" href="${pageContext.request.contextPath}/donor/browse-ngos">Browse Charitable Homes</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/donor/my-donations">My Donations</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/donor/profile">My Profile</a>
    </aside>

    <div class="dashboard-content">
	<h1>Browse Charitable Homes &amp; Requirements</h1>
        <p class="subtitle">Find a request that matches what you can give</p>

        <c:forEach var="request" items="${requestList}">
            <div class="item-card">
                <div class="item-card__info">
                    <h3>${request.title}</h3>
                    <p>${request.description}</p>
                    <div class="item-card__meta">
                        <span class="pill pill-open">${request.status}</span>
                        <c:choose>
                            <c:when test="${request.urgency == 'High'}">
                                <span class="pill pill-urgent">High Urgency</span>
                            </c:when>
                            <c:when test="${request.urgency == 'Medium'}">
                                <span class="pill pill-medium">Medium Urgency</span>
                            </c:when>
                            <c:otherwise>
                                <span class="pill pill-low">Low Urgency</span>
                            </c:otherwise>
                        </c:choose>
                        <span class="pill pill-low">Needed: ${request.quantity}</span>
                    </div>
                </div>
                <a href="${pageContext.request.contextPath}/donor/donate?requestId=${request.id}" class="btn btn-primary">Donate</a>
            </div>
        </c:forEach>

        <c:if test="${empty requestList}">
            <div class="empty-state">No open requirements right now. Check back soon!</div>
        </c:if>
    </div>
</div>

<jsp:include page="footer.jsp" />