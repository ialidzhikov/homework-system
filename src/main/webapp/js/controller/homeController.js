var app = app || {};

app.HomeController = (function () {
    var submissions = [
        {
            id: 1,
            courseName: 'Java EE',
            lectureName: 'Introduction to course',
            submittedBy: 'Pesho',
            submittedOn: '6/25/2016 15:33',
            mark: 8
        },
        {
            id: 2,
            courseName: 'Java EE',
            lectureName: 'Introduction to course',
            submittedBy: 'Gosho',
            submittedOn: '6/26/2016 15:33',
            mark: null
        }
    ];
    var courses = [
        {
            id: 1,
            title: 'Java EE',
            description: 'Some quick example text to build on the card title and make up the bulk of the card\'s content.'
        }
    ];
    var trainees = [
        {
        	id: 0,
        	name: 'Gosho',
        	email: 'pesho@gmail.com',
        	facultyNumber: 71570,
        	fieldOfStudy: 'Computer Science'
        },
        {
        	id: 1,
        	name: 'Pesho',
        	email: 'pesho@gmail.com',
        	facultyNumber: 71570,
        	fieldOfStudy: 'Computer Science'
        },
        {
        	id: 2,
        	username: 'mariyka',
        	name: 'Mariyka',
        	email: 'pesho@gmail.com',
        	facultyNumber: 71570,
        	fieldOfStudy: 'Computer Science'
        }
    ];
    
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
					app.CourseDao.getAllCourses()
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
    	
    	app.HomeView.renderDeleteTrainer(selector, trainers[id]);
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
        getLogout: getLogout
    };
}());