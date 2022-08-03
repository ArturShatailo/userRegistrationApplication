

$(document).ready(function(){

    printCookies();

    //$("#sing-up-button").click(function(){

        /*
        $.ajax('registerUser', {
            type: 'POST',  // http method
            data: {
                name: $("#name").val(),
                surname: $("#surname").val(),
                country: $("#country").val(),
                email: $("#email").val(),
                password: $("#password").val(),
                passwordRepeat: $("#passwordRepeat").val()
            },  // data to submit
            success: function (data, status, xhr) {

                //$('p').append('status: ' + status + ', data: ' + data);
            },
            error: function (jqXhr, textStatus, errorMessage) {
                //$('p').append('Error' + errorMessage);
            }
        });
         */

        /*
        $.post("registerUser",
            {
                name: $("#name").val(),
                surname: $("#surname").val(),
                country: $("#country").val(),
                email: $("#email").val(),
                password: $("#password").val(),
                passwordRepeat: $("#passwordRepeat").val()
            },
            function(){
                //window.location.href = "login.jsp"

            });


    });
*/



});


function printCookies() {

    newCookiePrint("errorMessage");
    newCookiePrint("successfulMessage");

    $(".cookies").show(400);

    setTimeout(function () {
        $(".cookies").hide(400);
    }, 5000)
}

function newCookiePrint(cookie) {
    if(getCookie(cookie).length > 0){
        const newCookie = document.createElement("p");
        newCookie.textContent = getCookie(cookie);
        newCookie.className = cookie;
        $(".cookies").append(newCookie);
    }
}


function getCookie(cname) {
    let name = cname + "=";
    let ca = document.cookie.split(';');
    for(let i = 0; i < ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) === ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) === 0) {
            return c.substring(name.length, c.length).replaceAll('"', '');
        }
    }
    return "";
}