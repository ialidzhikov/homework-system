var app = app || {};

app.HomeView = (function () {
    
    function renderLogin(selector) {
        $.get('templates/login.html', function (template) {
            $(selector).html(template);
        });
    }
    
    function renderSidebar(selector) {
        $.get('templates/sidebar.html', function (template) {
            $(selector).html(template);
        });
    }
    
    function renderAdminSidebar(selector) {
    	$.get('templates/admin-sidebar.html', function (template) {
            $(selector).html(template);
        });
    }
    
    function renderTraineeHome(selector, submissions, courses) {
        $.get('templates/trainee-home.html', function (template) {
            var data = {
                submissions: submissions,
                courses: courses
            };
            
            var html = Mustache.render(template, data);
            $(selector).html(html);
        });
    }
    
    function renderTrainerHome(selector, submissions, courses) {
        $.get('templates/trainer-home.html', function (template) {
            var data = {
                submissions: submissions,
                courses: courses
            };
            
            var html = Mustache.render(template, data);
            $(selector).html(html);
        });
    }
    
    function renderAdminHome(selector) {
    	$.get('templates/admin-home.html', function (template, trainee) {
            $(selector).html(template);
        });
    }
    
    return {
        renderLogin: renderLogin,
        renderSidebar: renderSidebar,
        renderAdminSidebar: renderAdminSidebar,
        renderTraineeHome: renderTraineeHome,
        renderTrainerHome: renderTrainerHome,
        renderAdminHome: renderAdminHome
    };
}());