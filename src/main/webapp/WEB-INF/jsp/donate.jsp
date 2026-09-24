<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="header.jsp" />

<div class="form-wrapper">
    <h2>Make a Donation</h2>

    <c:if test="${not empty successMsg}">
        <div class="success-msg">${successMsg}</div>
    </c:if>

    <c:if test="${not empty selectedRequest}">
        <div style="background:#f9fafb; border:1px solid #eaeaea; border-radius:0.6rem; padding:1rem; margin-bottom:1.5rem;">
            <strong>${selectedRequest.title}</strong>
            <p style="font-size:0.85rem; color:#6b7280; margin-top:0.3rem;">${selectedRequest.description}</p>
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/donor/donate" method="post">
        <input type="hidden" name="requestId" value="${selectedRequest.id}">

        <div class="form-group">
            <label for="title">Item Name</label>
            <input type="text" id="title" name="title" placeholder="e.g. Rice, Blankets, Books" required>
        </div>
        <div class="form-group">
            <label for="category">Category</label>
            <select id="category" name="categoryId" required>
                <c:forEach var="cat" items="${categoryList}">
                    <option value="${cat.id}">${cat.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="quantity">Quantity</label>
            <input type="number" id="quantity" name="quantity" placeholder="10" min="1" required>
        </div>
        <div class="form-group">
            <label for="pickupAddress">Pickup Address</label>
            <textarea id="pickupAddress" name="pickupAddress" rows="3" placeholder="Where should the item be picked up from?" required></textarea>
        </div>
        <button type="submit" class="btn btn-primary">Submit Donation</button>
    </form>
</div>

<jsp:include page="footer.jsp" />