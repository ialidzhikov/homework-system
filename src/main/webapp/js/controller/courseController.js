var app = app || {};

app.CourseController = (function () {
    var courses = {
        courses: [
            {
                id: 1,
                title: 'Java EE',
                description: 'Some quick example text to build on the card title and make up the bulk of the card\'s content.',
                lectures: [{id: 1, title: 'Introduction to course', deadline: new Date()}, {id: 2, title: 'JPA', deadline: new Date(2016, 7, 1)}]
            },
            {
                id: 2,
                title: 'AngularJS',
                description: 'Some quick example text to build on the card title and make up the bulk of the card\'s content.',
                lectures: [{id: 1, title: 'Introduction to course', deadline: new Date(2016, 7, 26)}, {id: 2, title: 'Angular Seed', deadline: new Date(2016, 10, 1)}]
            },
            {
                id: 3,
                title: 'JavaScript',
                description: 'Some quick example text to build on the card title and make up the bulk of the card\'s content.',
                lectures: [{id: 1, title: 'Introduction to course', deadline: new Date()}, {id: 2, title: 'Basics', deadline: new Date()}]
            }
        ]
    };
    var user = {
        role: 'trainee'
    };
    
    function getCourse(context, selector) {
        var courseId = context.params['courseId'];
        
        if (user.role === 'trainee') {
            app.CourseView.renderTraineeCourse(selector, courses.courses[courseId]);
        } else if (user.role === 'trainer') {
            app.CourseView.renderTrainerCourse(selector, courses.courses[courseId]);
        }
        
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
    	/*
    	if (user.role === 'trainee') {
            app.CourseView.renderTraineeCourses(selector, courses);
        } else if (user.role === 'trainer') {
            app.CourseView.renderTrainerCourses(selector, courses);
        }*/
    	
    	app.CourseDao.getAllCourses()
	        .done(function (courses) {
	        	console.log(courses);
	        	if (user.role === 'trainee') {
	                app.CourseView.renderTraineeCourses(selector, courses);
	            } else if (user.role === 'trainer') {
	                app.CourseView.renderTrainerCourses(selector, courses);
	            }
	        })
	        .error(function (error) {
	            console.log(error);
	        });
    }
    
    function getAddCourse(selector) {
        app.CourseView.renderAddCourse(selector);
    }
    
    function postAddCourse(context) {
        var title = context.params['title'],
            description = context.params['description'];
        
        app.CourseDao.addCourse(title, description)
            .done(function (course) {
                context.redirect('#/courses/');

                app.NotificationManager.notifySuccess('Course "' + course.title + '" successfully added!');
            })
            .error(function (error) {
                console.log(error);
            });
    }
    
    function getAddLecture(context, selector) {
        var id = context.params['courseId'];
        
        app.CourseView.renderAddLecture(selector, id);
    }
    
    function postAddLecture(context, selector) {
        var id = context.params['courseId'],
            title = context.params['title'],
            deadline = context.params['deadline'],
            homework = context.params['homework'];
    
        console.log(deadline);
        console.log(homework);
    }
    
    return {
        getCourse: getCourse,
        getCourses: getCourses,
        getAddCourse: getAddCourse,
        postAddCourse: postAddCourse,
        getAddLecture: getAddLecture,
        postAddLecture: postAddLecture
    };
}());