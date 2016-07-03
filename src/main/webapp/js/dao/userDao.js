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
    
    function addTrainee(facultyNumber, name, email, fieldOfStudy) {
		$.ajax({
			method: 'POST',
			url: HOSTNAME + 'trainees/add',
			dataType: 'json',
			contentType: 'application/json',
			data: {
				facultyNumber: facultyNumber,
				name: name,
				email: email,
				fieldOfStudy: fieldOfStudy
			}
		});
	}
    
    function addTrainer(username, name, email, degree) {
    	return $.ajax({
    		method: 'POST',
    		url: HOSTNAME + 'trainers/add',
    		dataType: 'json',
    		contentType: 'application/json',
    		data: {
    			username: username,
    			name: name,
    			email: email,
    			degree: degree
    		}
    	});
    }
    
    return {
        login: login,
        addTrainee: addTrainee,
    };
}());