$("#auth-btn").click(function () {
    var clientId = $("#email-inpt").val();
    var clientSecret = $("#password-inpt").val();
    var authorizationBasic = window.btoa(clientId + ':' + clientSecret);
    var xhr = new XMLHttpRequest();

    xhr.addEventListener("readystatechange", function () {
        if (this.readyState === 4) {
            $.getJSON('../status/authentication/' + clientId, function (response) {
//				console.log(response);
            }).always(function (response) {
                console.log(response);

                if (response.message !== undefined && response.message === "Success") {
                    window.location.replace("../admin/index.html");
                } else {
                    alert("Check login or password.");
                }
            });
        }
    });
    xhr.open("POST", "../oauth_login");
    xhr.setRequestHeader("Authorization", "Basic " + authorizationBasic);
    xhr.setRequestHeader("Allow-Control-Allow-Origin", "*");
    xhr.withCredentials = false;

    xhr.send("POST", "../oauth_login", false);
});
