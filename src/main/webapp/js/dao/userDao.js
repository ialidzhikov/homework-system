var app = app || {};

app.UserDao = (function () {
    var HOSTNAME = '';
    
    
    function login(email, password) {
        return $.ajax({
            method: 'POST',
            url: HOSTNAME + 'user/login',
            dataType: 'json',
            data: {
                email: email,
                password: password
            }
        });
    }
    
    return {
        login: login
    };
}());