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
    
    function renderAdminHome(selector, trainees, trainers) {
    	$.get('templates/admin-home.html', function (template, trainee) {
    		var html = Mustache.render(template, { trainees: trainees, trainers: trainers });
            $(selector).html(html);
        });
    }
    
    function renderAddTrainee(selector) {
    	$.get('templates/add-trainee.html', function (template) {
            $(selector).html(template);
        });
    }
    
    function renderDeleteTrainee(selector, trainee) {
    	$.get('templates/delete-trainee.html', function (template) {
            var html = Mustache.render(template, trainee);
    		$(selector).html(html);
        });
    }
    
    function renderAddTrainer(selector) {
    	$.get('templates/add-trainer.html', function (template) {
    		$(selector).html(template);
    	});
    }
    
    function renderDeleteTrainer(selector, trainer) {
    	$.get('templates/delete-trainer.html', function (template) {
    		var html = Mustache.render(template, trainer);
    		$(selector).html(html);
    	});
    }
    
    return {
        renderLogin: renderLogin,
        renderSidebar: renderSidebar,
        renderTraineeHome: renderTraineeHome,
        renderTrainerHome: renderTrainerHome,
        renderAdminHome: renderAdminHome,
        renderAddTrainee: renderAddTrainee,
        renderDeleteTrainee: renderDeleteTrainee,
        renderAddTrainer: renderAddTrainer,
        renderDeleteTrainer: renderDeleteTrainer
    };
}());