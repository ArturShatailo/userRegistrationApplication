
$(document).ready(function() {

    getCurrentUserData();


    $("#change-info").click(function(){
        $(".edit-user-pop-up").css("display", "flex");
    });

    $("#editUser").click(function() {
        $(".edit-user-pop-up").css("display", "none");
        //editUser();
    });



});


function getCurrentUserData() {
    $.ajax('/registration/get-user', {
        type: 'GET',  // http method
        data: {},  // data to submit
        success: function (data, status, xhr) {
            $('#user-name').text(data["name"]);
            $('#user-surname').text(data["surname"]);
            $('#user-email').text(data["email"]);
            $('#user-country').text(data["country"]);
        },
        error: function (jqXhr, textStatus, errorMessage) {
            //error message
        }
    });
}

/*
function editUser(){
    $.ajax('/registration/editUserServlet', {
        type: 'PUT',  // http method
        data: {
            name: $("#name").val(),
            surname: $("#surname").val(),
            country: $("#country").val()
        },  // data to submit
        success: function (data, status, xhr) {
            getCurrentUserData();
        },
        error: function (jqXhr, textStatus, errorMessage) {
            //error message
        }
    });
}*/