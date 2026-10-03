<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <jsp:include page="ngo-sidebar.jsp">
    <jsp:param name="activePage" value="requests" />
</jsp:include>

    <div class="dashboard-content">
        <h1>My Requests</h1>
        <p class="subtitle">All requirements you've posted</p>

        <c:forEach var="request" items="${myRequests}">
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
                        <span class="pill pill-low">Qty: ${request.quantity}</span>
                    </div>
                </div>
            </div>
        </c:forEach>

        <c:if test="${empty myRequests}">
            <div class="empty-state">No requests posted yet.</div>
        </c:if>
    </div>
</div>

<jsp:include page="footer.jsp" />