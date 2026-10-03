<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="dashboard-shell">
        <jsp:include page="donor-sidebar.jsp">
        <jsp:param name="activePage" value="profile" />
    </jsp:include>

    <div class="dashboard-content">
        <h1>My Profile</h1>
        <p class="subtitle">View and update your personal details</p>

        <c:if test="${not empty successMsg}">
            <div class="success-msg">${successMsg}</div>
        </c:if>

        <div class="form-wrapper" style="margin: 0;">
            <form action="${pageContext.request.contextPath}/donor/profile" method="post">
                <div class="form-group">
                    <label for="name">Full Name</label>
                    <input type="text" id="name" name="name" value="${donorProfile.name}" required>
                </div>

                <div class="form-group">
                    <label for="email">Email (cannot be changed)</label>
                    <input type="email" id="email" value="${donorProfile.email}" disabled
                           style="background-color:#f1f5f9; cursor:not-allowed;">
                </div>

                <div class="form-group">
                    <label for="phone">Phone</label>
                    <input type="text" id="phone" name="phone" value="${donorProfile.phone}" required>
                </div>

                <p class="auth-divider">Detailed Address</p>
                <div class="form-group">
                    <label for="addressLine1">Address Line 1</label>
                    <input type="text" id="addressLine1" name="addressLine1" value="${donorProfile.addressLine1}" placeholder="House / building number, street">
                </div>
                <div class="form-group">
                    <label for="addressLine2">Address Line 2</label>
                    <input type="text" id="addressLine2" name="addressLine2" value="${donorProfile.addressLine2}" placeholder="Apartment, area, locality">
                </div>
                <div class="form-group">
                    <label for="landmark">Landmark</label>
                    <input type="text" id="landmark" name="landmark" value="${donorProfile.landmark}">
                </div>
                <div class="field-row">
                    <div class="form-group">
                        <label for="city">City / Town</label>
                        <input type="text" id="city" name="city" value="${donorProfile.city}">
                    </div>
                    <div class="form-group">
                        <label for="state">State / Province</label>
                        <input type="text" id="state" name="state" value="${donorProfile.state}">
                    </div>
                </div>
                <div class="field-row">
                    <div class="form-group">
                        <label for="postalCode">Postal / PIN Code</label>
                        <input type="text" id="postalCode" name="postalCode" value="${donorProfile.postalCode}">
                    </div>
                    <div class="form-group">
                        <label for="country">Country</label>
                        <input type="text" id="country" name="country" value="${donorProfile.country}">
                    </div>
                </div>

                <button type="submit" class="btn btn-primary">Save Changes</button>
            </form>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />