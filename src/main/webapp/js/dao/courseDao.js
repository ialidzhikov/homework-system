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
    
    function addLecture(courseId, task, deadline) {
        return $.ajax({
            method: 'POST',
            url: HOSTNAME + 'course/lecture',
            dataType: 'json',
            data: {
                courseId: courseId,
                task: task,
                endDate: deadline
            }
        });
    }
    
    return {
        getCourse: getCourse,
        getAllCourses: getAllCourses,
        addCourse: addCourse,
        addLecture: addLecture
    };
}());