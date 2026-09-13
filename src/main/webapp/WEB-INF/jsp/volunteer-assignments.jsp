<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <aside class="sidebar">
        <p class="sidebar__title">Volunteer Menu</p>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/volunteer/dashboard">Dashboard</a>
        <a class="sidebar__link sidebar__link--active" href="${pageContext.request.contextPath}/volunteer/assignments">My Assignments</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/volunteer/profile">Profile</a>
    </aside>

    <div class="dashboard-content">
        <h1>My Assignments</h1>
        <p class="subtitle">Tasks assigned to you</p>

        <c:forEach var="assignment" items="${assignments}">
            <div class="item-card">
                <div class="item-card__info">
                    <h3>${assignment.requestTitle}</h3>
                    <p>Assigned on ${assignment.assignedDate}</p>
                </div>
                <span class="pill pill-medium">${assignment.status}</span>
            </div>
        </c:forEach>

        <c:if test="${empty assignments}">
            <div class="empty-state">No assignments yet.</div>
        </c:if>
    </div>
</div>

<jsp:include page="footer.jsp" />