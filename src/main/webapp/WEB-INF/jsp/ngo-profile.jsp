<jsp:include page="header.jsp" />

<div class="dashboard-shell">
    <aside class="sidebar">
        <p class="sidebar__title">NGO Menu</p>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/ngo/dashboard">Dashboard</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/ngo/post-requirement">Post Requirement</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/ngo/requests">My Requests</a>
        <a class="sidebar__link" href="${pageContext.request.contextPath}/ngo/donations-received">Donations Received</a>
        <a class="sidebar__link sidebar__link--active" href="${pageContext.request.contextPath}/ngo/profile">Profile</a>
    </aside>

    <div class="dashboard-content">
        <h1>NGO Profile</h1>
        <p class="subtitle">Update your organization details</p>

        <div class="form-wrapper" style="margin: 0;">
            <form action="${pageContext.request.contextPath}/ngo/profile" method="post">
                <div class="form-group">
                    <label for="orgName">Organization Name</label>
                    <input type="text" id="orgName" name="orgName" value="${ngoProfile.orgName}" required>
                </div>
                <div class="form-group">
                    <label for="registrationNo">Registration Number</label>
                    <input type="text" id="registrationNo" name="registrationNo" value="${ngoProfile.registrationNo}">
                </div>
                <div class="form-group">
                    <label for="address">Address</label>
                    <input type="text" id="address" name="address" value="${ngoProfile.address}">
                </div>
                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea id="description" name="description" rows="3">${ngoProfile.description}</textarea>
                </div>
                <button type="submit" class="btn btn-primary">Save Changes</button>
            </form>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />