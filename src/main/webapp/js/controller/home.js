'use strict';

var app = app || {};

app.HomeController = (function () {
	
    function getLogin(selector) {
        app.Renderer.render(selector, 'templates/login.html');
    }
    
    function postLogin(context) {
        app.UserDao.login(context.params)
            .success(function () {
                context.redirect('#/home');
        
                app.NotificationManager.notifySuccess('You have successfully logged in!');
            })
            .error(function () {
                app.NotificationManager.notifyError('Invalid username or password!');
            });
    }
    
    function getSidebar(selector) {
    	app.UserDao.getAuthenticated()
    		.success(function (authenticated) {
    			app.Renderer.render(selector, 'templates/sidebar.html', authenticated);
    		});
    }
    
    function getHome(context, selector) {
    	app.UserDao.getAuthenticated()
			.success(function (authenticated) {
				var role = authenticated.role;
				
				if (role === 'ADMIN') {
					app.UserDao.getAllTrainers()
						.success(function (trainers) {
							app.UserDao.getAllTrainees()
								.success(function (trainees) {
									var data = {
										trainers: trainers,
										trainees: trainees
									};
									
									app.Renderer.render(selector, 'templates/admin-home.html', data);
								});
						});
				} else {
					app.SubmissionDao.getSubmissions()
						.success(function (submissions) {
							app.CourseDao.getMyCourses()
								.success(function (courses) {
									if (role === 'TRAINEE') {
										var data = {
											submissions: submissions,
											courses: courses
										};
										
										app.Renderer.render(selector, 'templates/trainee-home.html', data);
									} else if (role === 'TRAINER') {
										var data = {
											submissions: submissions,
											courses: courses
										};
										
										app.Renderer.render(selector, 'templates/trainer-home.html', data);
									}
								});
						});
				}
			})
			.error(function (error) {
				context.redirect('#/login');
			});
    }
    
    function getAddTrainee(selector) {
    	app.Renderer.render(selector, 'templates/add-trainee.html');
    }
    
    function postAddTrainee(context) {
    	app.UserDao.addTrainee(context.params)
    		.success(function () {
    			context.redirect('#/home');
    			
    			app.NotificationManager.notifySuccess('You have successfully added new trainee!');
    		});
    }
    
    function getDeleteTrainee(context, selector) {
    	app.UserDao.getTraineeById(context.params['traineeId'])
    		.success(function (trainee) {
    			app.Renderer.render(selector, 'templates/delete-trainee.html', trainee);
    		});
    }
    
    function postDeleteTrainee(context) {
    	app.UserDao.deleteTrainee(context.params['traineeId'])
    		.success(function () {
    			context.redirect('#/home');
    			
    			app.NotificationManager.notifySuccess('You have successfully deleted trainee!');
    		});
    }
    
    function getAddTrainer(selector) {
    	app.Renderer.render(selector, 'templates/add-trainer.html');
    }
    
    function postAddTrainer(context) {
    	app.UserDao.addTrainer(context.params)
    		.success(function () {
    			context.redirect('#/home');
    			
    			app.NotificationManager.notifySuccess('You have successfully added new trainer!');
    		});
    }
    
    function getDeleteTrainer(context, selector) {
    	app.UserDao.getTrainerById(context.params['trainerId'])
			.success(function (trainer) {
				app.Renderer.render(selector, 'templates/delete-trainer.html', trainer);
			});
    }
    
    function postDeleteTrainer(context) {
    	app.UserDao.deleteTrainer(context.params['trainerId'])
    		.success(function () {
    			context.redirect('#/home');
    			
    			app.NotificationManager.notifySuccess('You have successfully deleted trainer!');
    		});
    }
    
    function getLogout(context) {
    	app.UserDao.logout()
    		.success(function () {
    			context.redirect('#/login');
    			
    			app.NotificationManager.notifySuccess('You have successfully logged out!');
    		});
    }
    
    return {
        getLogin: getLogin,
        postLogin: postLogin,
        getSidebar: getSidebar,
        getHome: getHome,
        getAddTrainee: getAddTrainee,
        postAddTrainee: postAddTrainee,
        getDeleteTrainee: getDeleteTrainee,
        postDeleteTrainee: postDeleteTrainee,
        getAddTrainer: getAddTrainer,
        postAddTrainer: postAddTrainer,
        getDeleteTrainer: getDeleteTrainer,
        postDeleteTrainer: postDeleteTrainer,
        getLogout: getLogout
    };
}());