<%@ taglib prefix="c" uri="jakarta.tags.core" %>
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
                    <p>Assigned on ${assignment.assignedDate} &bull; Pickup: ${assignment.donation.pickupAddress}</p>
                    <c:if test="${not empty assignment.pickedUpDate}">
                        <p>Picked up on ${assignment.pickedUpDate}</p>
                    </c:if>
                    <c:if test="${not empty assignment.deliveredDate}">
                        <p>Delivered on ${assignment.deliveredDate}</p>
                    </c:if>
                </div>
                <span class="pill pill-medium">${assignment.status}</span>
                <div>
                    <c:if test="${assignment.status == 'ASSIGNED'}">
                        <form action="${pageContext.request.contextPath}/volunteer/assignments/status" method="post">
                            <input type="hidden" name="assignmentId" value="${assignment.id}">
                            <input type="hidden" name="status" value="PICKED_UP">
                            <button type="submit" class="btn btn-primary">Mark Picked Up</button>
                        </form>
                    </c:if>
                    <c:if test="${assignment.status == 'PICKED_UP'}">
                        <form action="${pageContext.request.contextPath}/volunteer/assignments/status" method="post">
                            <input type="hidden" name="assignmentId" value="${assignment.id}">
                            <input type="hidden" name="status" value="DELIVERED">
                            <button type="submit" class="btn btn-primary">Mark Delivered</button>
                        </form>
                        <c:if test="${not assignment.trackingEnabled}">
                            <form action="${pageContext.request.contextPath}/volunteer/assignments/tracking/start" method="post">
                                <input type="hidden" name="assignmentId" value="${assignment.id}">
                                <button type="submit" class="btn btn-outline">Start Live Tracking</button>
                            </form>
                        </c:if>
                        <c:if test="${assignment.trackingEnabled}">
                            <p data-location-status="${assignment.id}">Location sharing is active.</p>
                            <script>
                                (function () {
                                    const assignmentId = '${assignment.id}';
                                    if (!navigator.geolocation) return;
                                    navigator.geolocation.watchPosition(function (position) {
                                        const body = new URLSearchParams({
                                            assignmentId: assignmentId,
                                            latitude: position.coords.latitude,
                                            longitude: position.coords.longitude
                                        });
                                        fetch('${pageContext.request.contextPath}/volunteer/assignments/tracking/location', {
                                            method: 'POST',
                                            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                                            body: body
                                        });
                                    }, function () {
                                        const status = document.querySelector('[data-location-status="' + assignmentId + '"]');
                                        if (status) status.textContent = 'Location permission is required for live tracking.';
                                    }, {enableHighAccuracy: true, maximumAge: 10000, timeout: 15000});
                                }());
                            </script>
                        </c:if>
                    </c:if>
                </div>
            </div>
        </c:forEach>

        <c:if test="${empty assignments}">
            <div class="empty-state">No assignments yet.</div>
        </c:if>
    </div>
</div>

<jsp:include page="footer.jsp" />