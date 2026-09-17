<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="auth-split">
    <div class="auth-split__image">
        <div class="auth-split__image-text">
            <h3>Join the SETU community</h3>
            <p>Whether you're giving, receiving, or volunteering &mdash; every role makes a real difference.</p>
        </div>
    </div>

    <div class="auth-split__form">
        <div class="auth-wrapper">
            <div class="auth-wrapper__header">
                <h2>Join SETU</h2>
                <p>Become a donor, charitable home, or volunteer and start making an impact today.</p>
            </div>

            <div class="auth-card">
                <c:if test="${not empty errorMsg}">
                    <div class="error-msg">${errorMsg}</div>
                </c:if>

                <form action="${pageContext.request.contextPath}/register" method="post" enctype="multipart/form-data">

                    <p class="auth-divider">I want to join as</p>

                    <div class="role-select">
                        <input type="radio" id="roleDonor" name="role" value="DONOR" checked onclick="toggleDocUpload()">
                        <label for="roleDonor">
                            <span class="role-select__icon">🎁</span>
                            Donor
                        </label>

                        <input type="radio" id="roleNgo" name="role" value="NGO" onclick="toggleDocUpload()">
                        <label for="roleNgo">
                            <span class="role-select__icon">🏢</span>
                            Charitable Home
                        </label>

                        <input type="radio" id="roleVolunteer" name="role" value="VOLUNTEER" onclick="toggleDocUpload()">
                        <label for="roleVolunteer">
                            <span class="role-select__icon">💚</span>
                            Volunteer
                        </label>
                    </div>

                    <div class="form-group">
                        <label for="name">Full Name / Organization Name</label>
                        <input type="text" id="name" name="name" placeholder="Your name" required>
                    </div>

                    <div class="field-row">
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email" id="email" name="email" placeholder="you@example.com" required>
                        </div>
                        <div class="form-group">
                            <label for="phone">Phone</label>
                            <input type="text" id="phone" name="phone" placeholder="9876543210" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="address">Address</label>
                        <input type="text" id="address" name="address" placeholder="City, State">
                    </div>

                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" placeholder="Create a password" required>
                    </div>

                    <!-- Charitable Home proof fields -->
                    <div id="docUploadGroup" style="display:none;">

                        <div class="field-row">
                            <div class="form-group">
                                <label for="registrationNumber">Registration / License Number</label>
                                <input type="text" id="registrationNumber" name="registrationNumber" placeholder="e.g. NGO-REG-2024-1234">
                            </div>
                            <div class="form-group">
                                <label for="capacity">Capacity (No. of people housed)</label>
                                <input type="number" id="capacity" name="capacity" placeholder="e.g. 50" min="1">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="verificationDocument">Verification Document (Registration Certificate / Proof)</label>
                            <input type="file" id="verificationDocument" name="verificationDocument" accept=".pdf,.jpg,.jpeg,.png">
                            <p style="font-size:0.78rem; color:#78716c; margin-top:0.35rem;">
                                Upload your registration certificate or a valid government proof of this home/shelter.
                            </p>
                        </div>

                        <div class="form-group">
                            <label for="homePhoto">Photo of the Home / Shelter</label>
                            <input type="file" id="homePhoto" name="homePhoto" accept=".jpg,.jpeg,.png">
                            <p style="font-size:0.78rem; color:#78716c; margin-top:0.35rem;">
                                A clear photo of the premises helps donors and admins verify authenticity.
                            </p>
                        </div>

                        <p style="font-size:0.78rem; color:#c2410c; background:#fff7ed; border:1px solid #fed7aa; padding:0.6rem 0.8rem; border-radius:0.9rem; margin-bottom:1.2rem;">
                            Your account will stay <strong>pending</strong> until an admin reviews these documents and approves it.
                        </p>
                    </div>

                    <button type="submit" class="btn btn-primary">Create Account</button>
                </form>

                <p class="auth-footer-link">Already have an account? <a href="${pageContext.request.contextPath}/login">Sign in</a></p>
            </div>
        </div>
    </div>
</div>

<script>
    function toggleDocUpload() {
        var isNgo = document.getElementById('roleNgo').checked;
        var group = document.getElementById('docUploadGroup');
        var docInput = document.getElementById('verificationDocument');
        group.style.display = isNgo ? 'block' : 'none';
        docInput.required = isNgo;
    }
    toggleDocUpload();
</script>

<jsp:include page="footer.jsp" />