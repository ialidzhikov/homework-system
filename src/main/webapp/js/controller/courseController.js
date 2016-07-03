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
    
    function getCourse(context, selector) {
        var id = context.params['courseId'];
        
        app.UserDao.getAuthenticated()
        	.success(function (authenticated) {
        		app.CourseDao.getCourse(id)
	                .done(function (lectures) {
	                	var role = authenticated.role;
	            		
	            		if (role === 'TRAINEE' || role === 'ADMIN') {
	            			app.CourseView.renderTraineeCourse(selector, lectures, id);
	            		} else if (role === 'TRAINER') {
	            			app.CourseView.renderTrainerCourse(selector, lectures, id);
	            		}
	                })
	                .error(function (error) {
	                    console.log(error);
	                });
        	})
        	.error(function (error) {
        		console.log(error);
        	});
    }
    
    function getCourses(selector) {
    	app.UserDao.getAuthenticated()
	    	.success(function (authenticated) {
	    		var role = authenticated.role;
	    		
	    		app.CourseDao.getAllCourses()
			        .done(function (courses) {
			        	if (role === 'TRAINEE') {
			                app.CourseView.renderTraineeCourses(selector, courses);
			            } else if (role === 'TRAINER') {
			                app.CourseView.renderTrainerCourses(selector, courses);
			            }
			        })
			        .error(function (error) {
			            console.log(error);
			        });
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
            task = context.params['task'];
    
        app.CourseDao.addLecture(id, title, deadline, task)
        	.success(function () {
        		context.redirect('#/courses/' +  id);
        		
        		app.NotificationManager.notifySuccess('You have successfully add new lecture!');
        	})
        	.error(function (error) {
        		console.log(error);
        	});
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