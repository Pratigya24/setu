<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <aside class="sidebar">
        <p class="sidebar__title">Admin Menu</p>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/admin/manage-users">Manage Users</a>
		<a class="sidebar__link" href="${pageContext.request.contextPath}/admin/manage-ngos">Manage Charitable Homes</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/admin/manage-volunteers">Manage Volunteers</a>
        <a class="sidebar__link sidebar__link--active" href="${pageContext.request.contextPath}/admin/reports">Reports</a>
    </aside>

    <div class="dashboard-content">
        <h1>System Reports</h1>
        <p class="subtitle">Overall donation and NGO performance</p>

        <div class="stat-grid">
            <div class="stat-card">
                <div class="stat-card__label">Total Donations (All Time)</div>
                <div class="stat-card__value">${monthlyDonations}</div>
            </div>
            <div class="stat-card">
                <div class="stat-card__label">Requests Fulfilled</div>
                <div class="stat-card__value">${requestsFulfilled}</div>
            </div>
        </div>

        <div class="panel">
            <p class="panel__title">Category-wise Donation Breakdown</p>
            <div class="report-bars">
                <div class="report-bar__row">
                    <div class="report-bar__label">Food</div>
                    <div class="report-bar__track"><div class="report-bar__fill" style="width: 70%;"></div></div>
                    <div class="report-bar__value">70%</div>
                </div>
                <div class="report-bar__row">
                    <div class="report-bar__label">Clothes</div>
                    <div class="report-bar__track"><div class="report-bar__fill" style="width: 45%;"></div></div>
                    <div class="report-bar__value">45%</div>
                </div>
                <div class="report-bar__row">
                    <div class="report-bar__label">Books</div>
                    <div class="report-bar__track"><div class="report-bar__fill" style="width: 30%;"></div></div>
                    <div class="report-bar__value">30%</div>
                </div>
                <div class="report-bar__row">
                    <div class="report-bar__label">Medical</div>
                    <div class="report-bar__track"><div class="report-bar__fill" style="width: 20%;"></div></div>
                    <div class="report-bar__value">20%</div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />