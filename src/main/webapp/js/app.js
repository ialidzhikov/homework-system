var app = app || {};

(function () {
    moment().format();
    
    var sammy = Sammy(function () {
        var sidebarSelector = '#sidebar-container',
            containerSelector = '#main-container';
        
        this.before({ except: { path: '#(\/login\/)?' } }, function () {
            app.HomeController.getSidebar(this, sidebarSelector);
        });
        
        this.get('#/', function () {
            this.redirect('#/home/');
        });
        
        this.get('#/login/', function () {
            app.HomeController.getLogin(containerSelector);
        });
        
        this.post('#/login/', function () {
            app.HomeController.postLogin(this);
        });
        
        this.get('#/home/', function () {
            app.HomeController.getHome(this, containerSelector);
        });
        
        this.get('#/courses/', function () {
            app.CourseController.getCourses(containerSelector);
        });
        
        this.get('#/courses/:courseId', function () {
            app.CourseController.getCourse(this, containerSelector);
        });
        
        this.get('#/courses/add/', function () {
            app.CourseController.getAddCourse(containerSelector);
        });
        
        this.post('#/courses/add/', function () {
            app.CourseController.postAddCourse(this);
        });
        
        this.get('#/courses/:courseId/lectures/add/', function () {
            app.CourseController.getAddLecture(this, containerSelector);
        });
        
        this.post('#/courses/:courseId/lectures/add/', function () {
            app.CourseController.postAddLecture(this, containerSelector);
        });
        
        this.get('#/courses/:courseId/enroll/', function () {
        	app.CourseController.postEnroll(this);
        });
        
        this.get('#/courses/:courseId/favourite/', function () {
        	app.CourseController.postMarkAsFavourite(this);
        });
        
        this.get('#/submissions/', function () {
            app.SubmissionController.getSubmissions(containerSelector);
        });
        
        this.get('#/submissions/:submissionId/', function () {
            app.SubmissionController.getSubmission(this, containerSelector);
        });
        
        this.post('#/submissions/:submissionId/', function () {
        	app.SubmissionController.postSubmissionMark(this);
        });
        
        this.get('#/trainees/add/', function () {
        	app.HomeController.getAddTrainee(containerSelector);
        });
        
        this.post('#/trainees/add/', function () {
        	app.HomeController.postAddTrainee(this);
        });
        
        this.get('#/trainees/:traineeId/delete/', function () {
        	app.HomeController.getDeleteTrainee(this, containerSelector);
        });
        
        this.post('#/trainees/:traineeId/delete/', function () {
        	app.HomeController.postDeleteTrainee(this);
        });
        
        this.get('#/trainers/add/', function () {
        	app.HomeController.getAddTrainer(containerSelector);
        });
        
        this.post('#/trainers/add/', function () {
        	app.HomeController.postAddTrainer(this);
        });
        
        this.get('#/trainers/:trainerId/delete/', function () {
        	app.HomeController.getDeleteTrainer(this, containerSelector);
        });
        
        this.post('#/trainers/:trainerId/delete/', function () {
        	app.HomeController.postDeleteTrainer(this, containerSelector 	);
        });
        
        this.get('#/logout/', function () {
        	app.HomeController.getLogout(this);
        });
    });
    
    sammy.run('#/');
}());