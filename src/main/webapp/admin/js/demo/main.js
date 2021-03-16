$.getJSON('../status/user-info', function (response) {
//				console.log(response);
}).always(function (response) {
    console.log(response);

    if (response.lastName != undefined && response.firstName != undefined) {
        $("#user-fname-lname").text(response.lastName + " " + response.firstName);
    } else {

    }
});