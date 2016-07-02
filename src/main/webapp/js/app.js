var app = app || {};

(function () {
    moment().format();
    
    var sammy = Sammy(function () {
        var sidebarSelector = '#sidebar-container',
            containerSelector = '#main-container';
        
        this.before({ except: { path: '#(\/login\/|\/register\/)?' } }, function () {
            app.HomeController.getSidebar(sidebarSelector);
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
            app.HomeController.getHome(containerSelector);
        });
        
        this.get('#/courses/', function () {
            app.CourseController.getCourses(containerSelector);
        });
        
        this.get('#/courses/:courseId/', function () {
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
        
        this.get('#/submissions/', function () {
            app.SubmissionController.getSubmissions(containerSelector);
        });
        
        this.get('#/submissions/:submissionId/', function () {
            app.SubmissionController.getSubmission(this, containerSelector);
        });
        
    });
    
    sammy.run('#/');
}());