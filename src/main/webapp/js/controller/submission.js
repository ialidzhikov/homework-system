'use strict';

var app = app || {};

app.SubmissionController = (function () {
    
    function getSubmissions(selector) {
    	app.UserDao.getAuthenticated()
    		.success(function (authenticated) {
    			var role = authenticated.role;
    			
    			app.SubmissionDao.getSubmissions()
	        		.success(function (submissions) {
	        			if (role === 'TRAINEE') {
	        				app.Renderer.render(selector, 'templates/trainee-submissions.html', { submissions: submissions });
	        			} else if (role === 'TRAINER') {
	        				app.Renderer.render(selector, 'templates/trainer-submissions.html', { submissions: submissions });
	        			}
	        		});
    		});
    }
    
    function getSubmission(context, selector) {
        var id = context.params['submissionId'];
        
        app.UserDao.getAuthenticated()
        	.success(function (authenticated) {
        		var role = authenticated.role;
        		
        		app.SubmissionDao.getSubmission(id)
        			.success(function (submission) {
        				if (role === 'TRAINER') {
        					app.Renderer.render(selector, 'templates/trainer-submission.html', submission);        					
        				}
        			});
        	});
    }
    
    function postSubmissionMark(context) {
    	var id = context.params['submissionId'],
    		mark = context.params['mark'];
    	
    	app.SubmissionDao.addSubmissionMark(id, mark)
    		.success(function () {
    			context.redirect('#/submissions/');
    		});
    }
    
    return {
        getSubmissions: getSubmissions,
        getSubmission: getSubmission,
        postSubmissionMark: postSubmissionMark
    };
}());