var app = app || {};

app.CourseController = (function () {
    var courses = {
        courses: [
            {
                id: 1,
                title: 'Java EE',
                description: 'Some quick example text to build on the card title and make up the bulk of the card\'s content.',
                lectures: [{id: 1, title: 'Introduction to course'}, {id: 2, title: 'JPA'}]
            },
            {
                id: 2,
                title: 'AngularJS',
                description: 'Some quick example text to build on the card title and make up the bulk of the card\'s content.',
                lectures: [{id: 1, title: 'Introduction to course'}, {id: 2, title: 'Angular Seed'}]
            },
            {
                id: 3,
                title: 'JavaScript',
                description: 'Some quick example text to build on the card title and make up the bulk of the card\'s content.',
                lectures: [{id: 1, title: 'Introduction to course'}, {id: 2, title: 'Basics'}]
            }
        ]
    };
    
    
    function getCourse(context, selector) {
        var courseId = context.params['courseId'];
        
        app.CourseView.renderCourse(selector, courses.courses[courseId]);
        /*
        app.CourseDao.getCourse(courseId)
                .done(function (course) {
                    app.CourseView.renderCourse(selector, course);
                })
                .error(function (error) {
                    console.log(error);
                });
        */
    }
    
    function getCourses(selector) {
        app.CourseView.renderCourses(selector, courses);
        /*
        app.CourseDao.getCourses()
                .done(function (courses) {
                    app.CourseView.renderCourses(selector, courses);
                })
                .error(function (error) {
                    console.log(error);
                });
        */
    }
    
    function getAddCourse(selector) {
        app.CourseView.renderAddCourse(selector);
    }
    
    function postAddCourse(context) {
        var title = context.params['title'],
            description = context.params['description'];
        
        courses.push({title: title, description: description, lectures: []});
        /*
        app.CourseDao.addCourse(title, description)
            .done(function (course) {
                context.redirect('#/courses/');

                app.NotificationManager.notifySuccess('Course "' + course.title + '" successfully added!');
            })
            .error(function (error) {
                console.log(error);
            });
        */
    }
    
    return {
        getCourse: getCourse,
        getCourses: getCourses,
        getAddCourse: getAddCourse,
        postAddCourse: postAddCourse
    };
}());