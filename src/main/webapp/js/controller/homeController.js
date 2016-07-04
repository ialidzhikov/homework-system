var app = app || {};

app.HomeController = (function () {
	
    function getLogin(selector) {
        app.HomeView.renderLogin(selector);
    }
    
    function postLogin(context) {
        var username = context.params['username'],
            password = context.params['password'];
        
        app.UserDao.login(username, password)
            .success(function (user) {
                context.redirect('#/home/');
        
                app.NotificationManager.notifySuccess("You have successfully logged in!");
            })
            .error(function (error) {
                console.log(error);
                
                app.NotificationManager.notifyError("Invalid username or password!");
            });
    }
    
    function getSidebar(context, selector) {
    	app.UserDao.getAuthenticated()
    		.success(function (authenticated) {
    			app.HomeView.renderSidebar(selector, authenticated);
    		})
    		.error(function (error) {
    			context.redirect('#/login/');
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
									app.HomeView.renderAdminHome(selector, trainees, trainers);
								})
								.error(function (error) {
									console.log(error);
								});
						})
						.error(function (error) {
							console.log(error);
						});
				} else {
					app.SubmissionDao.getSubmissions()
						.success(function (submissions) {
							app.CourseDao.getMyCourses()
								.success(function (courses) {
									if (role === 'TRAINEE') {
										app.HomeView.renderTraineeHome(selector, submissions, courses);
									} else if (role === 'TRAINER') {
										app.HomeView.renderTrainerHome(selector, submissions, courses);
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
			})
			.error(function (error) {
				context.redirect('#/login/');
			});
    }
    
    function getAddTrainee(selector) {
    	app.HomeView.renderAddTrainee(selector);
    }
    
    function postAddTrainee(context) {
    	var facultyNumber = context.params['facultyNumber'],
    		password = context.params['password'],
    		name = context.params['name'],
    		email = context.params['email'],
    		fieldOfStudy = context.params['fieldOfStudy'];
    	
    	app.UserDao.addTrainee(facultyNumber, password, name, email, fieldOfStudy)
    		.success(function () {
    			context.redirect('#/home/');
    			
    			app.NotificationManager.notifySuccess("You have successfully added new trainee!");
    		})
    		.error(function (error) {
    			console.log(error);
    		});
    }
    
    function getDeleteTrainee(context, selector) {
    	var id = context.params['traineeId'];
    	
    	app.UserDao.getTraineeById(id)
    		.success(function (trainee) {
    			app.HomeView.renderDeleteTrainee(selector, trainee);
    		})
    		.error(function (error) {
    			console.log(error);
    		});
    }
    
    function postDeleteTrainee(context) {
    	var id = context.params['traineeId'];
    	
    	app.UserDao.deleteTrainee(id)
    		.success(function () {
    			context.redirect('#/home/');
    			
    			app.NotificationManager.notifySuccess("You have successfully deleted trainee!");
    		})
    		.error(function (error) {
    			console.log(error);
    		});
    }
    
    function getAddTrainer(selector) {
    	app.HomeView.renderAddTrainer(selector);
    }
    
    function postAddTrainer(context) {
    	var username = context.params['username'],
    		password = context.params['password'],
    		name = context.params['name'],
    		email = context.params['email'],
    		degree = context.params['degree'];
    	
    	app.UserDao.addTrainer(username, password, name, email, degree)
    		.success(function () {
    			context.redirect('#/home/');
    			
    			app.NotificationManager.notifySuccess("You have successfully added new trainer!");
    		})
    		.error(function (error) {
    			console.log(error);
    		});
    }
    
    function getDeleteTrainer(context, selector) {
    	var id = context.params['trainerId'];
    	
    	app.UserDao.getTrainerById(id)
			.success(function (trainer) {
				app.HomeView.renderDeleteTrainer(selector, trainer);
			})
			.error(function (error) {
				console.log(error);
			});
    }
    
    function postDeleteTrainer(context) {
    	var id = context.params['trainerId'];
    	
    	app.UserDao.deleteTrainer(id)
    		.success(function () {
    			context.redirect('#/home/');
    			
    			app.NotificationManager.notifySuccess("You have successfully deleted trainer!");
    		})
    		.error(function (error) {
    			console.log(error);
    		});
    }
    
    function getLogout(context) {
    	app.UserDao.logout()
    		.success(function () {
    			context.redirect('#/login/');
    			
    			app.NotificationManager.notifySuccess("You have successfully logged out!");
    		})
    		.error(function (error) {
    			console.log(error);
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