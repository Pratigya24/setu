<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <aside class="sidebar">
        <p class="sidebar__title">Admin Menu</p>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/admin/manage-users">Manage Users</a>
        <a class="sidebar__link sidebar__link--active" href="${pageContext.request.contextPath}/admin/manage-ngos">Manage Charitable Homes</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/admin/manage-volunteers">Manage Volunteers</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/admin/reports">Reports</a>
    </aside>

    <div class="dashboard-content">
        <h1>Manage Charitable Homes</h1>
        <p class="subtitle">All registered charitable homes and their verification status</p>

        
        <c:forEach var="ngo" items="${ngoList}">
    <div class="ngo-card">
        <div class="ngo-card__logo">${ngo.name.substring(0,1)}</div>
        <div class="ngo-card__info">
            <h3>${ngo.name}</h3>
            <p>${ngo.address} &bull; Reg No: ${ngo.registrationNumber}</p>
            <c:choose>
                <c:when test="${ngo.approved}">
                    <span class="verified-tag yes">&#10003; Verified</span>
                </c:when>
                <c:otherwise>
                    <span class="verified-tag no">&#9679; Pending Verification</span>
                </c:otherwise>
            </c:choose>
        </div>
        <div style="display:flex; gap:0.5rem; flex-wrap:wrap;">
            <c:if test="${!ngo.approved}">
                <a href="${pageContext.request.contextPath}/admin/approve-ngo?id=${ngo.id}" class="btn btn-primary">Approve</a>
            </c:if>
            <c:if test="${ngo.approved}">
                <span class="pill pill-low">Active</span>
            </c:if>
            <c:if test="${not empty ngo.verificationDocumentPath}">
                <a href="${pageContext.request.contextPath}/admin/view-document?path=${ngo.verificationDocumentPath}" target="_blank" class="btn btn-outline">View Document</a>
            </c:if>
            <c:if test="${not empty ngo.homePhotoPath}">
                <a href="${pageContext.request.contextPath}/admin/view-document?path=${ngo.homePhotoPath}" target="_blank" class="btn btn-outline">View Photo</a>
            </c:if>
            <a href="${pageContext.request.contextPath}/admin/delete-ngo?id=${ngo.id}"
               class="btn btn-outline"
               style="color:#b91c1c; border-color:#fecaca;"
               onclick="return confirm('Delete this Charitable Home and its account permanently?');">
                Delete
            </a>
        </div>
    </div>
</c:forEach>

        <c:if test="${empty ngoList}">
            <div class="empty-state">No charitable homes registered yet.</div>
        </c:if>
    </div>
</div>

<jsp:include page="footer.jsp" />