$(".login").show();
$(".mainpage").hide();


$(".loginSubmit").click(()=> {
    let uname = $(".username").val();
    let upass = $(".password").val();
    $.ajax({
        dataType: "json",
        url: "user/login",
        method: "POST",
        data: {"username":uname, "password":upass},
        success: function (data) {
            if (data["success"] && data["detail"]["userType"] === 1) {
                console.log("Admin Logged In");
                $(".login").hide();
                $(".mainpage").show();
            };
        },
        error: function (data) {
            alert("fail" + data);
        }
    });
});

$(".logoutSubmit").click(() => {
    $.ajax({
        url: "user/logout",
        method: "POST",
        success: function (data) {
            console.log("User Logged Out");
            $(".content").empty();
            $(".login").show();
            $(".mainpage").hide();
        },
        error: function (data) {
            alert("fail" + data);
        }
    });
});

