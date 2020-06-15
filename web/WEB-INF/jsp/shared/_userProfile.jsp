<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="profile" class="card my-3 border-0">
    <div class="row">
        <div id="nameAndSignature" class="col-sm-5">
            <p class="text-center"><img src="./images/avatar/${author.avatar}" style="max-width: 150px; object-fit:cover; border-radius: 50%"></p>
            <p class="text-center my-0" style="font-size: small"><a href="./articlesPage?userName=${author.userName}" data-toggle="tooltip" title="${author.signature}">
                <span id="nickname" class="font-weight-light">${author.nickname}</span>
            </a></p>
            <p class="text-center my-0" style="font-size: small">
                <span id="signature"  class="font-weight-light">${author.signature}</span>
            </p>
        </div>

        <div id="otherInfo" class="col-sm-7">
            <dl class="row">
                    <dt class="col-md-3">date of birth:</dt>
                    <dd class="col-md-9">${author.dateOfBirth.toString()}</dd>
                    <dt class="col-md-3">email:</dt>
                    <dd class="col-md-9">${author.email}</dd>
                    <dt class="col-md-3">description:</dt>
                    <dd class="col-md-9">${author.description}</dd>
            </dl>
            <p class="text-right">
            <c:if test="${author.userName.equals(user.userName)}">
                <a href="./editProfilePage?userName=${author.userName}" role="button">Edit</a>
            </c:if></p>
            <c:if test="${isUserLoggedIn && !author.userName.equals(user.userName)}">
                <form id="followForm_${author.id}" action="./follow" method="post" class="d-none">
                    <input type="hidden" name="followee" value="${author.id}">
                    <input type="hidden" name="follower" value="${user.id}">
                    <button id="followButton_${author.id}" type="submit">Follow</button>
                </form>
                <form id="unfollowForm_${author.id}" action="./unfollow" method="post" class="d-none">
                    <input type="hidden" name="followee" value="${author.id}">
                    <input type="hidden" name="follower" value="${user.id}">
                    <button id="unfollowButton_${author.id}" type="submit">Unfollow</button>
                </form>
                <script>
                    enableFollow(${author.id});
                    enableUnfollow(${author.id});
                    checkFollow(${author.id}, ${user.id});
                </script>
            </c:if>
        </div>
    </div>
</div>
