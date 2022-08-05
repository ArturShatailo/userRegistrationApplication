$(document).ready(function() {

    //Calls function that fills html with received from database values
    getUsersData();

    $(document).on('click', function (e){
        e.preventDefault();
        let _this = e.target;
        if(_this.matches("#deleteUser")){
            deleteUser($( _this ).attr("userid"));
        }
    })
    $("#search-button").click(function(){
        showSearch($("#search-value").val().trim());
    });


});

function showSearch(val) {
    $.ajax('/registration/get-user-byEmail', {
        type: 'GET',  // http method
        data: {
            email: val
        },
        dataType: "json", // data to submit
        success: function (data) {
            $(".display-scroll-wrapper").children().remove();
            createUserRecord(data);
        },
        error: function () {
            setCookie("errorMessage", "Unable to find record", 5);
            printCookies();
        }
    });
}


function deleteUser(id) {
    $.ajax('/registration/deleteServlet', {
        type: 'GET',  // http method
        data: {
            id: id // data to submit
        },
        dataType: "html",
        success: function () {
            $(".user-record[userid='"+id+"']").remove();
            printCookies();
        },
        error: function () {
            setCookie("errorMessage", "Unable to delete user", 5);
            printCookies();
        }
    });
}

function getUsersData() {
    $.ajax('/registration/get-users', {
        type: 'GET',  // http method
        data: {},
        dataType: "json", // data to submit
        success: function (data) {
            console.log(data);
            let keys = Object.keys(data).length;
            for (let i = 0; i < keys; i++) {
                createUserRecord(data[i]);
            }
        },
        error: function () {
            setCookie("errorMessage", "Unable to upload personal data", 5);
            printCookies();
        }
    });
}

function createUserRecord(datum) {
    let user = document.createElement("div");
    user.className = "user-record";
    user.setAttribute("userid", datum["id"]);
    let userName = document.createElement("p");
    userName.textContent = datum["name"];
    userName.setAttribute("data-target", "name");
    let userSurname = document.createElement("p");
    userSurname.textContent = datum["surname"];
    userSurname.setAttribute("data-target", "surname");
    let userCountry = document.createElement("p");
    userCountry.textContent = datum["country"];
    userCountry.setAttribute("data-target", "country");
    let userEmail = document.createElement("p");
    userEmail.textContent = datum["email"];
    userEmail.setAttribute("data-target", "email");
    let userPassword = document.createElement("p");
    userPassword.textContent = datum["password"];
    userPassword.setAttribute("data-target", "password");
    let deleteButton = document.createElement('button');
    deleteButton.className = "button";
    deleteButton.id = "deleteUser";
    deleteButton.setAttribute("userid", datum["id"]);
    deleteButton.textContent = "Delete";

    user.append(userName);
    user.append(userSurname);
    user.append(userCountry);
    user.append(userEmail);
    user.append(userPassword);
    user.append(deleteButton);

    $(".display-scroll-wrapper").append(user);
}