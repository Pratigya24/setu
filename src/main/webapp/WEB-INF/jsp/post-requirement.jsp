<jsp:include page="header.jsp" />

<div class="form-wrapper">
    <h2>Post a Requirement</h2>

    <c:if test="${not empty successMsg}">
        <div class="success-msg">${successMsg}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/ngo/post-requirement" method="post">
        <div class="form-group">
            <label for="title">Title</label>
            <input type="text" id="title" name="title" placeholder="e.g. Winter Blankets Needed" required>
        </div>
        <div class="form-group">
            <label for="category">Category</label>
            <select id="category" name="categoryId" required>
                <c:forEach var="cat" items="${categoryList}">
                    <option value="${cat.categoryId}">${cat.categoryName}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="description">Description</label>
            <textarea id="description" name="description" rows="3" placeholder="Describe what's needed and why" required></textarea>
        </div>
        <div class="form-group">
            <label for="quantityNeeded">Quantity Needed</label>
            <input type="number" id="quantityNeeded" name="quantityNeeded" placeholder="50" min="1" required>
        </div>
        <div class="form-group">
            <label for="urgency">Urgency</label>
            <select id="urgency" name="urgency" required>
                <option value="Low">Low</option>
                <option value="Medium">Medium</option>
                <option value="High">High</option>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Post Requirement</button>
    </form>
</div>

<jsp:include page="footer.jsp" />