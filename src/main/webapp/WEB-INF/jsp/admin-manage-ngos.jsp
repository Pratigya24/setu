<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <aside class="sidebar">
        <p class="sidebar__title">Admin Menu</p>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/admin/manage-users">Manage Users</a>
        <a class="sidebar__link sidebar__link--active" href="${pageContext.request.contextPath}/admin/manage-ngos">Manage NGOs</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/admin/manage-volunteers">Manage Volunteers</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/admin/reports">Reports</a>
    </aside>

    <div class="dashboard-content">
        <h1>Manage NGOs</h1>
        <p class="subtitle">All registered NGOs and their verification status</p>

        <c:forEach var="ngo" items="${ngoList}">
            <div class="ngo-card">
                <div class="ngo-card__logo">${ngo.orgName.substring(0,1)}</div>
                <div class="ngo-card__info">
                    <h3>${ngo.orgName}</h3>
                    <p>${ngo.address} &bull; Reg No: ${ngo.registrationNo}</p>
                    <c:choose>
                        <c:when test="${ngo.verified}">
                            <span class="verified-tag yes">&#10003; Verified</span>
                        </c:when>
                        <c:otherwise>
                            <span class="verified-tag no">&#9679; Pending Verification</span>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div>
                    <c:if test="${!ngo.verified}">
                        <a href="${pageContext.request.contextPath}/admin/approve-ngo?id=${ngo.ngoId}" class="btn btn-primary">Approve</a>
                    </c:if>
                    <c:if test="${ngo.verified}">
                        <span class="pill pill-low">Active</span>
                    </c:if>
                </div>
            </div>
        </c:forEach>

        <c:if test="${empty ngoList}">
            <div class="empty-state">No NGOs registered yet.</div>
        </c:if>
    </div>
</div>

<jsp:include page="footer.jsp" />