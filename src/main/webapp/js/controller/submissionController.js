var app = app || {};

app.SubmissionController = (function () {
    var submissions = [
        {
            id: 0,
            courseName: 'Java EE',
            lectureName: 'Introduction to course',
            submittedBy: 'Pesho',
            submittedOn: '6/25/2016 15:33',
            mark: 8
        },
        {
            id: 1,
            courseName: 'Java EE',
            lectureName: 'Introduction to course',
            submittedBy: 'Gosho',
            submittedOn: '6/25/2016 15:33',
            mark: 8
        },
        {
            id: 2,
            courseName: 'Java EE',
            submittedBy: 'Gosho',
            lectureName: 'Introduction to course',
            submittedOn: '6/25/2016 15:33',
            mark: null
        },
        {
            id: 3,
            courseName: 'Java EE',
            lectureName: 'Introduction to course',
            submittedBy: 'Pesho',
            submittedOn: '6/25/2016 15:33',
            mark: null
        }
    ];
    
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