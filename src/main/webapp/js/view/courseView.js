var app = app || {};

app.CourseView = (function () {
    
    function renderTraineeCourse(selector, course) {
        $.get('templates/trainee-course.html', function (template) {
            course.lectures.forEach(function (lecture) {
                lecture.isUploadActive = lecture.deadline > new Date();
                lecture.deadline = moment(lecture.deadline).format('DD-MMM-YYYY HH:mm');
            });
            var html  = Mustache.render(template, course);
            $(selector).html(html);
            
            $('input:file').on('change', function() {
                var lectureId = $(this).attr('data-lecture-id');
                
                //var file = this.files[0];
                //console.log(file.type);
                
                //app.CourseDao.addHomework(lectureId, file);
            });
        });
    }
    
    function renderTrainerCourse(selector, course) {
        $.get('templates/trainer-course.html', function (template) {
            course.lectures.forEach(function (lecture) {
                lecture.isUploadActive = lecture.deadline > new Date();
                lecture.deadline = moment(lecture.deadline).format('DD-MMM-YYYY HH:mm');
            });
            var html  = Mustache.render(template, course);
            $(selector).html(html);
        });
    }
    
    function renderTraineeCourses(selector, courses) {
        $.get('templates/trainee-courses.html', function (template) {
            var html = Mustache.render(template, courses);
            $(selector).html(html);
        });
    }
    
    function renderTrainerCourses(selector, courses) {
        $.get('templates/trainer-courses.html', function (template) {
            var html = Mustache.render(template, courses);
            $(selector).html(html);
        });
    }
    
    function renderAddCourse(selector) {
        $.get('templates/add-course.html', function (template) {
            $(selector).html(template);
        });
    }
    
    function renderAddLecture(selector, id) {
        $.get('templates/add-lecture.html', function (template) {
            var html = Mustache.render(template, {id: id})
            $(selector).html(html);
        });
    }
    
    return {
        renderTraineeCourse: renderTraineeCourse,
        renderTrainerCourse: renderTrainerCourse,
        renderTraineeCourses: renderTraineeCourses,
        renderTrainerCourses: renderTrainerCourses,
        renderAddCourse: renderAddCourse,
        renderAddLecture: renderAddLecture
    };
}());