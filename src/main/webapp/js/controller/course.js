var app = app || {};

app.CourseController = (function () {
    
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
			        .success(function (courses) {
			        	app.CourseDao.getMyCourses()
			        		.success(function (myCourses) {			        			
			        			if (role === 'TRAINEE') {
			        				markEnrolledCourses(courses, myCourses);
			        				
					                app.CourseView.renderTraineeCourses(selector, courses);
					            } else if (role === 'TRAINER') {
					                app.CourseView.renderTrainerCourses(selector, myCourses);
					            }
			        		})
			        		.error(function (error) {
			        			console.log(error);
			        		});
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
    
    function postEnroll(context) {
    	var id = context.params['courseId'];
    	
    	app.CourseDao.enroll(id)
    		.success(function (response) {
    			context.redirect('#/courses/');
    			
    			app.NotificationManager.notifySuccess('You have successfully enrolled course!');
    		})
    		.error(function (error) {
    			console.log(error);
    		});
    }
    
    function markEnrolledCourses(courses, myCourses) {
    	for (var i = 0; i < courses.length; i++) {
    		for (var j = 0; j < myCourses.length; j++) {
    			if (courses[i].id === myCourses[j].id) {
    				courses[i].hasEnrolled = true;
    				break;
    			}
    		}
    	}
    }
    
    function postMarkAsFavourite(context) {
    	var id = context.params['courseId'];
    	
    	app.CourseDao.markAsFavourite(id, true)
			.success(function (response) {
				context.redirect('#/courses/');
				
				app.NotificationManager.notifySuccess('You have successfully marked course as favourite!');
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
        postAddLecture: postAddLecture,
        postEnroll: postEnroll,
        postMarkAsFavourite: postMarkAsFavourite
    };
}());