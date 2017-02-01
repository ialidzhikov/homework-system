'use strict';

var app = app || {};

app.CourseController = (function () {
    
    function getCourse(context, selector) {
        var id = context.params['courseId'];
        
        app.UserDao.getAuthenticated()
        	.success(function (authenticated) {
        		app.CourseDao.getCourse(id)
	                .done(function (lectures) {
	                	var role = authenticated.role;
	            		
	                	lectures.forEach(function (lecture) {
	                        lecture.isUploadActive = new Date(lecture.endDate) > new Date();
	                        lecture.endDate = moment(lecture.endDate).format('DD-MMM-YYYY HH:mm');
	                    });
	                	
	            		if (role === 'TRAINEE' || role === 'ADMIN') {
	            			app.Renderer.render(selector, 'templates/trainee-course.html', { lectures: lectures })
	            				.success(function () {
	            					addUploadEventHandler();	            					
	            				});
	            		} else if (role === 'TRAINER') {
	            			var data = {
	            				courseId: id,
	            				lectures: lectures
	            			};
	            			
	            			app.Renderer.render(selector, 'templates/trainer-course.html', data);
	            		}
	                });
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
			        				
					                app.Renderer.render(selector, 'templates/trainee-courses.html', { courses: courses });
					            } else if (role === 'TRAINER') {
					                app.Renderer.render(selector, 'templates/trainer-courses.html', { courses: myCourses});
					            }
			        		});
			        });
	    	});
    }
    
    function getAddCourse(selector) {
        app.Renderer.render(selector, 'templates/add-course.html');
    }
    
    function postAddCourse(context) {
        app.CourseDao.addCourse(context.params)
            .done(function (course) {
                context.redirect('#/courses');

                app.NotificationManager.notifySuccess('Course "' + course.name + '" successfully added!');
            });
    }
    
    function getAddLecture(context, selector) {
        app.Renderer.render(selector, 'templates/add-lecture.html', { id: context.params['courseId'] });
    }
    
    function postAddLecture(context, selector) {
        app.CourseDao.addLecture(context.params)
        	.success(function () {
        		context.redirect('#/courses/' +  context.params['courseId']);
        		
        		app.NotificationManager.notifySuccess('You have successfully added new lecture!');
        	});
    }
    
    function postEnroll(context) {
    	app.CourseDao.enroll(context.params['courseId'])
    		.success(function () {
    			context.redirect('#/courses');
    			
    			app.NotificationManager.notifySuccess('You have successfully enrolled course!');
    		});
    }
    
    function postMarkAsFavourite(context) {
    	var id = context.params['courseId'];
    	
    	app.CourseDao.markAsFavourite(id, true)
			.success(function (response) {
				context.redirect('#/courses');
				
				app.NotificationManager.notifySuccess('You have successfully marked course as favourite!');
			})
			.error(function (error) {
				console.log(error);
			});
    }
    
    function addUploadEventHandler() {
    	$('input:file').on('change', function() {
            var lectureId = $(this).attr('data-lecture-id');
            var file = this.files[0];
            
            if (file.type === 'application/x-zip-compressed' || file.name.endsWith('.zip')) {
            	app.SubmissionDao.addHomework(lectureId, file)
                	.success(function (success) {
                		console.log(success);
                		
                		app.NotificationManager.notifySuccess('You have successfully uploaded a homework!');
                	});
            } else {
            	app.NotificationManager.notifyError('You have to upload a zip archive!');
            }
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