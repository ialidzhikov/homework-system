var app = app || {};

app.SubmissionController = (function () {
    
    function getSubmissions(selector) {
    	app.UserDao.getAuthenticated()
    		.success(function (authenticated) {
    			var role = authenticated.role;
    			
    			app.SubmissionDao.getSubmissions()
	        		.success(function (submissions) {
	        			if (role === 'TRAINEE') {
	        				app.SubmissionView.renderTraineeSubmissions(selector, submissions);
	        			} else if (role === 'TRAINER') {
	        				app.SubmissionView.renderTrainerSubmissions(selector, submissions);
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
    
    function getSubmission(context, selector) {
        var id = context.params['submissionId'];
        
        app.UserDao.getAuthenticated()
        	.success(function (authenticated) {
        		var role = authenticated.role;
        		
        		app.SubmissionDao.getSubmission(id)
        			.success(function (submission) {
        				if (role === 'TRAINEE') {
        					
        				} else if (role === 'TRAINER') {
        					app.SubmissionView.renderTrainerSubmission(selector, submission);        					
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
    
    function postSubmissionMark(context) {
    	var id = context.params['submissionId'],
    		mark = context.params['mark'];
    	
    	app.SubmissionDao.addSubmissionMark(id, mark)
    		.success(function (response) {
    			console.log(response);
    			
    			context.redirect('#/submissions/');
    		})
    		.error(function (error) {
    			console.log(error);
    		});
    }
    
    return {
        getSubmissions: getSubmissions,
        getSubmission: getSubmission,
        postSubmissionMark: postSubmissionMark
    };
}());