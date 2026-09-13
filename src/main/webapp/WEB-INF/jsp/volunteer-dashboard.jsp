<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <aside class="sidebar">
        <p class="sidebar__title">Volunteer Menu</p>
        <a class="sidebar__link sidebar__link--active" href="${pageContext.request.contextPath}/volunteer/dashboard">Dashboard</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/volunteer/assignments">My Assignments</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/volunteer/profile">Profile</a>
    </aside>

    <div class="dashboard-content">
        <h1>Welcome, ${sessionScope.userName}</h1>
        <p class="subtitle">Your volunteering activity</p>

        <div class="stat-grid">
            <div class="stat-card">
                <div class="stat-card__label">Active Assignments</div>
                <div class="stat-card__value">${activeAssignments != null ? activeAssignments : 0}</div>
            </div>
            <div class="stat-card">
                <div class="stat-card__label">Completed Tasks</div>
                <div class="stat-card__value">${completedTasks != null ? completedTasks : 0}</div>
            </div>
        </div>

        <h2 style="font-size:1.1rem; margin-bottom:1rem;">Assigned Tasks</h2>
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