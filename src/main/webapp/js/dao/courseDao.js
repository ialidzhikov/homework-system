var app = app || {};

app.CourseDao = (function () {
    var HOSTNAME = '';
    
    function getCourse(courseId) {
        return $.ajax({
            method: 'GET',
            url: HOSTNAME + 'courses/' + courseId,
            dataType: 'json'
        });
    }
    
    function getCourses() {
        return $.ajax({
            method: 'GET',
            url: HOSTNAME + 'courses/',
            dataType: 'json'
        });
    }
    
    function getCoursesByTraineeId(traineeId) {
        return $.ajax({
            method: 'GET',
            url: HOSTNAME + 'courses?query={"traineeId": "' + traineeId + '"}',
            dataType: 'json'
        });
    }
    
    function getCoursesByTrainerId(trainerId) {
        return $.ajax({
            method: 'GET',
            url: HOSTNAME + 'courses?query={"trainerId": "' + trainerId + '"}',
            dataType: 'json'
        });
    }
    
    function addCourse(title, description) {
        return $.ajax({
            method: 'POST',
            url: HOSTNAME + 'courses/add',
            dataType: 'json',
            data: {
                title: title,
                description: description
            }
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
        getCourses: getCourses,
        getCoursesByTraineeId: getCoursesByTraineeId,
        getCoursesByTrainerId: getCoursesByTrainerId,
        addCourse: addCourse,
        addHomework: addHomework
        
    };
}());