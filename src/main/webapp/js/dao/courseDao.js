var app = app || {};

app.CourseDao = (function () {
    var HOSTNAME = 'webapi/hmwsrest/v1/';
    
    function getCourse(courseId) {
        return $.ajax({
            method: 'GET',
            url: HOSTNAME + 'courses/' + courseId,
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
    
    function addHomework(lectureId, file) {
        return $.ajax({
            method: 'POST',
            url: HOSTNAME + 'courses/homeworks',
            dataType: 'json',
            data: {
                lectureId: lectureId,
                file: file
            }
        });
    }
    
    return {
        getCourse: getCourse,
        getAllCourses: getAllCourses,
        addCourse: addCourse,
        addHomework: addHomework
    };
}());