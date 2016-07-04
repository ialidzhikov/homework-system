var app = app || {};

app.CourseDao = (function () {
    var HOSTNAME = 'webapi/hmwsrest/v1/';
    
    function getCourse(courseId) {
        return $.ajax({
            method: 'GET',
            url: HOSTNAME + 'course/' + courseId + '/lecture',
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
    
    function addCourse(name, description) {
        return $.ajax({
            method: 'POST',
            url: HOSTNAME + 'courses/add',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify({
                name: name,
                description: description
            })
        });
    }
    
    function addLecture(courseId, title, deadline, task) {
        return $.ajax({
            method: 'POST',
            url: HOSTNAME + 'course/lecture',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify({
                courseId: courseId,
                name: title,
                endDate: deadline,
                task: task
            })
        });
    }
    
    return {
        getCourse: getCourse,
        getAllCourses: getAllCourses,
        getMyCourses: getMyCourses,
        addCourse: addCourse,
        addLecture: addLecture
    };
}());