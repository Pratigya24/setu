<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <aside class="sidebar">
        <p class="sidebar__title">Admin Menu</p>
        <a class="sidebar__link sidebar__link--active" href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/admin/manage-users">Manage Users</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/admin/manage-ngos">Manage Charitable Homes</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/admin/manage-volunteers">Manage Volunteers</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/admin/reports">Reports</a>
    </aside>

    <div class="dashboard-content">
        <h1>Admin Overview</h1>
        <p class="subtitle">System-wide statistics</p>

        <div class="stat-grid">
            <div class="stat-card">
                <div class="stat-card__label">Total Donors</div>
                <div class="stat-card__value">${totalDonors != null ? totalDonors : 0}</div>
            </div>
            <div class="stat-card">
                <div class="stat-card__label">Total Charitable Homes</div>
                <div class="stat-card__value">${totalNgos != null ? totalNgos : 0}</div>
            </div>
            <div class="stat-card">
                <div class="stat-card__label">Pending Approvals</div>
                <div class="stat-card__value">${pendingApprovals != null ? pendingApprovals : 0}</div>
            </div>
            <div class="stat-card">
                <div class="stat-card__label">Total Donations</div>
                <div class="stat-card__value">${totalDonations != null ? totalDonations : 0}</div>
            </div>
        </div>

        <h2 style="font-size:1.1rem; margin-bottom:1rem; color:#0f172a;">Charitable Homes Awaiting Verification</h2>

        <c:forEach var="ngo" items="${pendingNgoList}">
            <div class="item-card">
                <div class="item-card__info">
                    <h3>${ngo.name}</h3>
                    <p>${ngo.address} &bull; Reg No: ${ngo.registrationNumber}</p>
                </div>
                <div style="display:flex; gap:0.5rem;">
                    <a href="${pageContext.request.contextPath}/admin/approve-ngo?id=${ngo.id}" class="btn btn-primary">Approve</a>
                    <a href="${pageContext.request.contextPath}/admin/reject-ngo?id=${ngo.id}" class="btn btn-outline">Reject</a>
                </div>
            </div>
        </c:forEach>

        <c:if test="${empty pendingNgoList}">
            <div class="empty-state">No charitable homes pending verification.</div>
        </c:if>
    </div>
</div>

<jsp:include page="footer.jsp" />