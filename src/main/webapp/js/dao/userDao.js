var app = app || {};

app.UserDao = (function () {
    var HOSTNAME = 'webapi/hmwsrest/v1/';
    
    
    function login(username, password) {
        return $.ajax({
            method: 'POST',
            url: HOSTNAME + 'user/login',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify({
                username: username,
                password: password
            })
        });
    }
    
    return {
        login: login
    };
}());