var app = app || {};

app.CourseView = (function () {
    
    function renderCourses(selector, courses) {
        $.get('templates/courses.html', function (template) {
            var html = Mustache.render(template, courses);
            $(selector).html(html);
        });
    }
    
    function renderCourse(selector, course) {
        $.get('templates/course.html', function (template) {
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
    
    function renderAddCourse(selector) {
        $.get('templates/add-course.html', function (template) {
            $(selector).html(template);
        });
    }
    
    return {
        renderCourse: renderCourse,
        renderCourses: renderCourses,
        renderAddCourse: renderAddCourse
    };
}());