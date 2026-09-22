<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <aside class="sidebar">
        <p class="sidebar__title">Admin Menu</p>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
        <a class="sidebar__link sidebar__link--active" href="${pageContext.request.contextPath}/admin/manage-users">Manage Users</a>
		<a class="sidebar__link" href="${pageContext.request.contextPath}/admin/manage-ngos">Manage Charitable Homes</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/admin/manage-volunteers">Manage Volunteers</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/admin/reports">Reports</a>
    </aside>

    <div class="dashboard-content">
        <h1>Manage Users</h1>
        <p class="subtitle">All registered accounts across the platform</p>

        <div class="toolbar">
            <input type="text" placeholder="Search by name or email...">
        </div>

        <div class="data-table-wrapper">
            <table>
                <thead>
                <tr>
                    <th>User</th>
                    <th>Role</th>
                    <th>Phone</th>
                    <th>Address</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="u" items="${userList}">
                    <tr>
                        <td>
                            <div class="user-cell">
                                <span class="avatar">${u.name.substring(0,1)}</span>
                                <div>
                                    <div class="user-cell__name">${u.name}</div>
                                    <div class="user-cell__email">${u.email}</div>
                                </div>
                            </div>
                        </td>
                        <td><span class="pill pill-open">${u.role}</span></td>
                        <td>${u.phone}</td>
                        <td>${u.address}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/delete-user?id=${u.id}"
                               class="btn btn-outline"
                               style="padding:0.35rem 0.8rem; font-size:0.78rem; color:#b91c1c; border-color:#fecaca;"
                               onclick="return confirm('Delete this user permanently?');">
                                Delete
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <c:if test="${empty userList}">
            <div class="empty-state">No users found.</div>
        </c:if>
    </div>
</div>

<jsp:include page="footer.jsp" />