'use strict';

var app = app || {};

app.CourseDao = (function () {
    var HOSTNAME = 'webapi/hmwsrest/v1/';
    
    function getLectures(courseId) {
        return $.ajax({
            method: 'GET',
            url: HOSTNAME + 'courses/' + courseId + '/lectures',
            dataType: 'json'
        });
    }
    
    function getAllCourses() {
        return $.ajax({
            method: 'GET',
            url: HOSTNAME + 'courses/',
            dataType: 'json'
        });
    }
    
    function getMyCourses() {
    	return $.ajax({
            method: 'GET',
            url: HOSTNAME + 'courses/my',
            dataType: 'json'
        });
    }
    
    function addCourse(course) {
        return $.ajax({
            method: 'POST',
            url: HOSTNAME + 'courses',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(course)
        });
    }
    
    function addLecture(courseId, lecture) {
        return $.ajax({
            method: 'POST',
            url: HOSTNAME + 'courses/' + courseId + '/lectures',
            contentType: 'application/json',
            data: JSON.stringify(lecture)
        });
    }
    
    function enroll(id) {
    	return $.ajax({
    		method: 'POST',
    		url: HOSTNAME + 'courses/' + id + '/enroll'
    	});
    }
    
    function markAsFavourite(id, isFavourite) {
    	return $.ajax({
    		method: 'POST',
    		url: HOSTNAME + 'courses/' + id + '/favourite',
    		contentType: 'application/json',
    		data: JSON.stringify({
    			isFavourite: isFavourite
    		})
    	});
    }
    
    return {
        getLectures: getLectures,
        getAllCourses: getAllCourses,
        getMyCourses: getMyCourses,
        addCourse: addCourse,
        addLecture: addLecture,
        enroll: enroll,
        markAsFavourite: markAsFavourite
    };
}());