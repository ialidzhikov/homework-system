'use strict';

var app = app || {};

app.UserDao = (function () {
    var HOSTNAME = 'webapi/hmwsrest/v1/';
    
    function login(user) {
        return $.ajax({
            method: 'POST',
            url: HOSTNAME + 'user/login',
            contentType: 'application/json',
            data: JSON.stringify(user)
        });
    }

    function getTraineeById(id) {
    	return $.ajax({
    		method: 'GET',
    		url: HOSTNAME + 'trainees/' + id,
    		dataType: 'json'
    	});
    }
    
    function addTrainee(trainee) {
		return $.ajax({
			method: 'POST',
			url: HOSTNAME + 'trainees',
			contentType: 'application/json',
			data: JSON.stringify(trainee)
		});
	}
    
    function getAllTrainees() {
    	return $.ajax({
    		method: 'GET',
    		url: HOSTNAME + 'trainees',
    		dataType: 'json'
    	});
    }
    
    function deleteTrainee(id) {
    	return $.ajax({
    		method: 'DELETE',
    		url: HOSTNAME + 'trainees/' + id
    	});
    }
    
    function addTrainer(trainer) {
    	return $.ajax({
    		method: 'POST',
    		url: HOSTNAME + 'trainers',
    		contentType: 'application/json',
    		data: JSON.stringify(trainer)
    	});
    }
    
    function getTrainerById(id) {
    	return $.ajax({
    		method: 'GET',
    		url: HOSTNAME + 'trainers/' + id,
    		dataType: 'json'
    	});
    }
    
    function getAllTrainers() {
    	return $.ajax({
    		method: 'GET',
    		url: HOSTNAME + 'trainers',
    		dataType: 'json'
    	});
    }
    
    function deleteTrainer(id) {
    	return $.ajax({
    		method: 'DELETE',
    		url: HOSTNAME + 'trainers/' + id
    	});
    }
    
    function getAuthenticated() {
    	return $.ajax({
    		method: 'GET',
    		url: HOSTNAME + 'user/authenticated',
    		dataType: 'json'
    	});
    }
    
    function logout() {
    	return $.ajax({
    		method: 'POST',
    		url: HOSTNAME + 'user/logout'
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