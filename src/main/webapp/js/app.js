var app = app || {};

(function () {
    var sammy = Sammy(function () {
        var sidebarSelector = '#sidebar-container',
            containerSelector = '#main-container';
        
        this.before({ except: { path: '#(\/login\/|\/register\/)?' } }, function () {
            app.HomeController.getSidebar(sidebarSelector);
        });
        
        this.get('#/', function () {
            app.HomeController.getRoot(containerSelector, sidebarSelector);
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
        
        this.get('#/courses/add/', function () {
            app.CourseController.getAddCourse(containerSelector);
        });
        
        this.post('#/courses/add/', function () {
            app.CourseController.postAddCourse(this);
        });
        
        this.get('#/submissions/', function () {
            app.SubmissionController.getSubmissions(containerSelector);
        });
        
        this.get('#/courses/:courseId', function () {
            app.CourseController.getCourse(this, containerSelector);
        });
    });
    
    sammy.run('#/');
}());