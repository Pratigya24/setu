<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <jsp:include page="donor-sidebar.jsp">
        <jsp:param name="activePage" value="homes" />
    </jsp:include>

    <div class="dashboard-content">
        <h1>🎉 Book an Occasion</h1>
        <p class="subtitle">Celebrate a special day with the residents of ${ngo.name}</p>

        <c:if test="${not empty errorMsg}">
            <div class="error-msg">${errorMsg}</div>
        </c:if>

        <div class="panel" style="max-width:620px;">
            <p class="panel__title">${ngo.name}</p>
            <p><strong>Address:</strong> ${ngo.fullAddress}</p>
            <p><strong>Phone:</strong> ${ngo.phone}</p>
            <c:if test="${not empty ngo.capacity}"><p><strong>Capacity:</strong> ${ngo.capacity}</p></c:if>
        </div>

        <div class="form-wrapper" style="margin:0; max-width:620px;">
            <form action="${pageContext.request.contextPath}/donor/book-occasion" method="post">
                <input type="hidden" name="ngoId" value="${ngo.id}">

                <div class="form-group">
                    <label for="occasionType">Occasion</label>
                    <select id="occasionType" name="occasionType" required onchange="toggleOther()">
                        <option value="Birthday">🎂 Birthday</option>
                        <option value="Anniversary">💍 Anniversary</option>
                        <option value="Festival">🪔 Festival</option>
                        <option value="Memorial">🕯️ Memorial / Remembrance</option>
                        <option value="Graduation">🎓 Graduation / Achievement</option>
                        <option value="Other">🎉 Other</option>
                    </select>
                </div>

                <div class="form-group" id="otherGroup" style="display:none;">
                    <label for="occasionTitle">Occasion Name</label>
                    <input type="text" id="occasionTitle" name="occasionTitle" placeholder="e.g. Baby shower, Retirement party">
                </div>

                <div class="form-group">
                    <label for="eventDate">Date</label>
                    <input type="date" id="eventDate" name="eventDate" required>
                </div>

                <div class="field-row">
                    <div class="form-group">
                        <label for="startTime">Start Time</label>
                        <input type="time" id="startTime" name="startTime" required>
                    </div>
                    <div class="form-group">
                        <label for="endTime">End Time</label>
                        <input type="time" id="endTime" name="endTime" required>
                    </div>
                </div>

                <div class="field-row">
                    <div class="form-group">
                        <label for="guestCount">Number of Guests</label>
                        <input type="number" id="guestCount" name="guestCount" min="1" placeholder="e.g. 10" required>
                    </div>
                    <div class="form-group">
                        <label for="contactPhone">Your Contact Number</label>
                        <input type="text" id="contactPhone" name="contactPhone" value="${donorProfile.phone}" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="description">Plan / Description</label>
                    <textarea id="description" name="description" rows="4" required
                        placeholder="What you plan to bring or do (cake, meals, gifts, games, prayers), special requests, etc."></textarea>
                </div>

                <p class="notice-box">Your request goes to the charitable home. The slot is confirmed only after they approve it.</p>

                <button type="submit" class="btn btn-primary">Send Booking Request</button>
            </form>
        </div>
    </div>
</div>

<script>
    document.getElementById('eventDate').min = new Date().toISOString().split('T')[0];

    function toggleOther() {
        var isOther = document.getElementById('occasionType').value === 'Other';
        document.getElementById('otherGroup').style.display = isOther ? 'block' : 'none';
        document.getElementById('occasionTitle').required = isOther;
    }
    toggleOther();
</script>

<jsp:include page="footer.jsp" />