<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <aside class="sidebar">
        <p class="sidebar__title">Admin Menu</p>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/admin/manage-users">Manage Users</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/admin/manage-ngos">Manage Charitable Homes</a>
        <a class="sidebar__link sidebar__link--active" href="${pageContext.request.contextPath}/admin/manage-volunteers">Manage Volunteers</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/admin/reports">Reports</a>
    </aside>

    <div class="dashboard-content">
        <h1>Manage Volunteers</h1>
        <p class="subtitle">All registered volunteers and their availability</p>

        <div class="data-table-wrapper">
            <table>
                <thead>
                <tr>
                    <th>Volunteer</th>
                    <th>Availability</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="v" items="${volunteerList}">
                    <tr>
                        <td>
                            <div class="user-cell">
                                <span class="avatar">${v.name.substring(0,1)}</span>
                                <div>
                                    <div class="user-cell__name">${v.name}</div>
                                    <div class="user-cell__email">${v.email}</div>
                                </div>
                            </div>
                        </td>
                        <td>${v.availability}</td>
                        <td>
                            <c:choose>
                                <c:when test="${v.approved}">
                                    <span class="pill pill-low">Approved</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="pill pill-medium">Pending</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:if test="${!v.approved}">
                                <a href="${pageContext.request.contextPath}/admin/approve-volunteer?id=${v.id}" class="btn btn-primary">Approve</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <c:if test="${empty volunteerList}">
            <div class="empty-state">No volunteers registered yet.</div>
        </c:if>
    </div>
</div>

<jsp:include page="footer.jsp" />