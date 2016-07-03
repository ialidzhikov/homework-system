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
    var user = {
        role: 'trainee'
    };
    
    function getSubmissions(selector) {
        if (user.role === 'trainee') {
            app.SubmissionView.renderTraineeSubmissions(selector, submissions);
            /*
            app.SubmissionDao.getSubmissionsByTraineeId()
                    .done(function (submissions) {
                        app.SubmissionView.renderSubmissions(selector, submissions);
                    })
                    .error(function (error) {
                        console.log(error);
                    });
            */
        } else if (user.role === 'trainer') {
            app.SubmissionView.renderTrainerSubmissions(selector, submissions);
        }
        
    }
    
    function getSubmission(context, selector) {
        var id = context.params['submissionId'];
        
        if (user.role === 'trainee') {
            
        } else if (user.role === 'trainer') {
            app.SubmissionView.renderTrainerSubmission(selector, submissions[id]);
        }
    }
    
    return {
        getSubmissions: getSubmissions,
        getSubmission: getSubmission
    };
}());