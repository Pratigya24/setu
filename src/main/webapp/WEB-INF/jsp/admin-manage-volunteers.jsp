<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <aside class="sidebar">
        <p class="sidebar__title">Admin Menu</p>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/admin/manage-users">Manage Users</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/admin/manage-ngos">Manage NGOs</a>
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
                    <th>Skills</th>
                    <th>Availability</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="v" items="${volunteerList}">
                    <tr>
                        <td>
                            <div class="user-cell">
                                <span class="avatar">${v.user.name.substring(0,1)}</span>
                                <div>
                                    <div class="user-cell__name">${v.user.name}</div>
                                    <div class="user-cell__email">${v.user.email}</div>
                                </div>
                            </div>
                        </td>
                        <td>
                            <c:forEach var="skill" items="${v.skills.split(',')}">
                                <span class="chip">${skill}</span>
                            </c:forEach>
                        </td>
                        <td>${v.availability}</td>
                        <td>
                            <c:choose>
                                <c:when test="${v.status == 'APPROVED'}">
                                    <span class="pill pill-low">Approved</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="pill pill-medium">${v.status}</span>
                                </c:otherwise>
                            </c:choose>
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