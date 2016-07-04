var app = app || {};

app.CourseView = (function () {
    
    function renderTraineeCourse(selector, lectures) {
        $.get('templates/trainee-course.html', function (template) {
            lectures.forEach(function (lecture) {
                lecture.isUploadActive = new Date(lecture.endDate) > new Date();
                lecture.endDate = moment(lecture.endDate).format('DD-MMM-YYYY HH:mm');
            });
            var html  = Mustache.render(template, { lectures: lectures});
            $(selector).html(html);
            
            $('input:file').on('change', function() {
                var lectureId = $(this).attr('data-lecture-id');
                var file = this.files[0];
                
                app.SubmissionDao.addHomework(lectureId, file)
                	.success(function (success) {
                		console.log(success);
                		
                		app.NotificationManager.notifySuccess('You have successfully uploaded a homework!');
                	})
                	.error(function (error) {
                		console.log(error);
                	});
            });
        });
    }
    
    function renderTrainerCourse(selector, lectures, courseId) {
        $.get('templates/trainer-course.html', function (template) {
            lectures.forEach(function (lecture) {
                lecture.isUploadActive = lecture.endDate > new Date();
                lecture.endDate = moment(lecture.endDate).format('DD-MMM-YYYY HH:mm');
            });
            var html  = Mustache.render(template, { courseId: courseId, lectures: lectures });
            $(selector).html(html);
        });
    }
    
    function renderTraineeCourses(selector, courses) {
        $.get('templates/trainee-courses.html', function (template) {
            var html = Mustache.render(template, { courses: courses });
            $(selector).html(html);
        });
    }
    
    function renderTrainerCourses(selector, courses) {
        $.get('templates/trainer-courses.html', function (template) {
            var html = Mustache.render(template, { courses: courses });
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