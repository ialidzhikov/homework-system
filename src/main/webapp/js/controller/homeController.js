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
    var user = {
    	fullName: 'Gosho Petrov',
        role: 'trainer'
    };
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
    var trainers = [
        {
        	id: 0,
        	username: 'trainer',
        	name: 'Gosho Trainer',
        	email: 'trainer@sap.com',
        	degree: 'PhD'
        },
        {
        	id: 1,
        	username: 'trainer',
        	name: 'Gosho Trainer',
        	email: 'trainer@sap.com',
        	degree: 'PhD'
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
    
    function getSidebar(selector) {
		app.HomeView.renderSidebar(selector);
    }
    
    function getHome(selector) {
        if (user.role === 'trainee') {
            app.HomeView.renderTraineeHome(selector, submissions, courses);
            /*
            app.SubmissionDao.getSubmissionsByTraineeId()
                    .done(function (submissions) {
                        app.CourseDao.getCoursesByTraineeId()
                                .done(function (courses) {
                                    app.HomeView.renderHome(selector, submissions, courses);
                                })
                                .error(function (error) {
                                    console.log(error);
                                });
                    })
                    .error(function (error) {
                        console.log(error);
                    });
            */
        } else if (user.role === 'trainer') {
            app.HomeView.renderTrainerHome(selector, submissions, courses);
            
            /*
            app.SubmissionDao.getSubmissionsByTrainerId()
                .done(function (submissions) {
                    app.CourseDao.getCoursesByTrainerId()
                        .done(function (courses) {
                            app.HomeView.renderTrainerHome(selector, submissions, courses);
                        })
                        .error(function (error) {
                            console.log(error);
                        })
                .error(function (error) {
                    console.log(error);
                });
            */ 
        } else if (user.role === 'admin') {
        	app.HomeView.renderAdminHome(selector, trainees, trainers);
        }
    }
    
    function getAddTrainee(selector) {
    	app.HomeView.renderAddTrainee(selector);
    }
    
    function postAddTrainee(context) {
    	var facultyNumber = context.params['facultyNumber'],
    	name = context.params['name'],
    	email = context.params['email'],
    	fieldOfStudy = context.params['fieldOfStudy'];
    	
    	
    }
    
    function getDeleteTrainee(context, selector) {
    	var id = context.params['traineeId'];
    	
    	app.HomeView.renderDeleteTrainee(selector, trainees[id]);
    }
    
    function getAddTrainer(selector) {
    	app.HomeView.renderAddTrainer(selector);
    }
    
    function postAddTrainer(context) {
    	var username = context.params['username'],
    		name = context.params['name'],
    		email = context.params['email'],
    		degree = context.params['degree'];
    	
    	
    }
    
    function getDeleteTrainer(context, selector) {
    	var id = context.params['trainerId'];
    	
    	app.HomeView.renderDeleteTrainer(selector, trainers[id]);
    }
    
    return {
        getLogin: getLogin,
        postLogin: postLogin,
        getSidebar: getSidebar,
        getHome: getHome,
        getAddTrainee: getAddTrainee,
        postAddTrainee: postAddTrainee,
        getDeleteTrainee: getDeleteTrainee,
        getAddTrainer: getAddTrainer,
        getDeleteTrainer: getDeleteTrainer
    };
}());