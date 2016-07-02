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
        role: 'admin'
    };
    
    function getLogin(selector) {
        app.HomeView.renderLogin(selector);
    }
    
    function postLogin(context) {
        var username = context.params['username'],
            password = context.params['password'];
        
        app.UserDao.login(username, password)
            .done(function (user) {
                
                context.redirect('#/');
        
                app.NotificationManager.notifySuccess("You have successfully logged in!");
            })
            .error(function (error) {
                console.log(error);
            });
    }
    
    function getSidebar(selector) {
    	if (user.role == 'trainer' || user.role == 'trainee') {
    		app.HomeView.renderSidebar(selector);
    	} else if (user.role == 'admin') {
    		app.HomeView.renderAdminSidebar(selector);
    	}
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
        	app.HomeView.renderAdminHome(selector);
        }
    }
    
    return {
        getLogin: getLogin,
        postLogin: postLogin,
        getSidebar: getSidebar,
        getHome: getHome
    };
}());