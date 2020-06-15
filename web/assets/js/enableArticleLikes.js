function enableArticleLike(id) {
    $(`#likeArticleButton_${id}`).click(function() {
        $.ajax({
            type: "POST",
            url: "./likeArticle",
            data: $(`#likeArticleForm_${id}`).serialize(),
            success: function() {
                $(`#likeArticleForm_${id}`).toggleClass("d-none");
                $(`#unlikeArticleForm_${id}`).toggleClass("d-none");
            }
        });
        return false;
    });
}

function enableArticleUnlike(id) {
    $(`#unlikeArticleButton_${id}`).click(function() {
        $.ajax({
            type: "POST",
            url: "./unlikeArticle",
            data: $(`#unlikeArticleForm_${id}`).serialize(),
            success: function() {
                $(`#likeArticleForm_${id}`).toggleClass("d-none");
                $(`#unlikeArticleForm_${id}`).toggleClass("d-none");
            }
        });
        return false;
    });
}

function checkArticleLike(user, article) {
    $.ajax({
       type: "GET",
       url: `./checkLikeArticleStatus?user=${user}&&article=${article}`,
        success: (res) => {
            if (res.status === "success") {
                $(`#unlikeArticleForm_${article}`).toggleClass("d-none");
            } else {
                $(`#likeArticleForm_${article}`).toggleClass("d-none");
            }
        }
    });
}


