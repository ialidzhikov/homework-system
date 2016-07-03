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
    
    function getTraineeById(id) {
    	return $.ajax({
    		method: 'GET',
    		url: HOSTNAME + 'trainees/' + id,
    		dataType: 'json',
    		contentType: 'application/json',
    	});
    }
    
    function addTrainee(facultyNumber, password, name, email, fieldOfStudy) {
		return $.ajax({
			method: 'POST',
			url: HOSTNAME + 'trainees',
			dataType: 'json',
			contentType: 'application/json',
			data: JSON.stringify({
				facultyNumber: facultyNumber,
				password: password,
				name: name,
				email: email,
				fieldOfStudy: fieldOfStudy
			})
		});
	}
    
    function getAllTrainees() {
    	return $.ajax({
    		method: 'GET',
    		url: HOSTNAME + 'trainees',
    		dataType: 'json',
    		contentType: 'application/json'
    	});
    }
    
    function deleteTrainee(id) {
    	return $.ajax({
    		method: 'DELETE',
    		url: HOSTNAME + 'trainees',
    		dataType: 'json',
    		contentType: 'application/json',
    		data: JSON.stringify({
    			id: id
    		})
    	});
    }
    
    function addTrainer(username, password, name, email, degree) {
    	return $.ajax({
    		method: 'POST',
    		url: HOSTNAME + 'trainers',
    		dataType: 'json',
    		contentType: 'application/json',
    		data: JSON.stringify({
    			username: username,
    			password: password,
    			name: name,
    			email: email,
    			degree: degree
    		})
    	});
    }
    
    function getTrainerById(id) {
    	return $.ajax({
    		method: 'GET',
    		url: HOSTNAME + 'trainers/' + id,
    		dataType: 'json',
    		contentType: 'application/json',
    	});
    }
    
    function getAllTrainers() {
    	return $.ajax({
    		method: 'GET',
    		url: HOSTNAME + 'trainers',
    		dataType: 'json',
    		contentType: 'application/json'
    	});
    }
    
    function deleteTrainer(id) {
    	return $.ajax({
    		method: 'DELETE',
    		url: HOSTNAME + 'trainers',
    		dataType: 'json',
    		contentType: 'application/json',
    		data: JSON.stringify({
    			id: id
    		})
    	});
    }
    
    function getAuthenticated() {
    	return $.ajax({
    		method: 'GET',
    		url: HOSTNAME + 'user/authenticated',
    		dataType: 'json',
    		contentType: 'application/json'
    	});
    }
    
    function logout() {
    	return $.ajax({
    		method: 'POST',
    		url: HOSTNAME + 'user/logout',
    		dataType: 'json',
    		contentType: 'application/json'
    	});
    }
    
    return {
        login: login,
        getAuthenticated: getAuthenticated,
        addTrainee: addTrainee,
        getTraineeById: getTraineeById,
        getAllTrainees: getAllTrainees,
        deleteTrainee: deleteTrainee,
        addTrainer: addTrainer,
        getTrainerById: getTrainerById,
        getAllTrainers: getAllTrainers,
        deleteTrainer: deleteTrainer,
        logout: logout
    };
}());