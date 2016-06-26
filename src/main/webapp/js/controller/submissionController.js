var app = app || {};

app.SubmissionController = (function () {
    var submissions = [
        {
            id: 1,
            courseName: 'Java EE',
            lectureName: 'Introduction to course',
            submittedOn: '6/25/2016 15:33',
            mark: 8
        },
        {
            id: 1,
            courseName: 'Java EE',
            lectureName: 'Introduction to course',
            submittedOn: '6/25/2016 15:33',
            mark: 8
        },
        {
            id: 1,
            courseName: 'Java EE',
            lectureName: 'Introduction to course',
            submittedOn: '6/25/2016 15:33',
            mark: 8
        },
        {
            id: 1,
            courseName: 'Java EE',
            lectureName: 'Introduction to course',
            submittedOn: '6/25/2016 15:33',
            mark: 8
        },
        {
            id: 1,
            courseName: 'Java EE',
            lectureName: 'Introduction to course',
            submittedOn: '6/25/2016 15:33',
            mark: 8
        },
        {
            id: 1,
            courseName: 'Java EE',
            lectureName: 'Introduction to course',
            submittedOn: '6/25/2016 15:33',
            mark: 8
        }
    ];
    
    
    function getSubmissions(selector) {
        app.SubmissionView.renderSubmissions(selector, submissions);
        
        app.SubmissionDao.getSubmissionsByUserId()
                .done(function (submissions) {
                    app.SubmissionView.renderSubmissions(selector, submissions);
                })
                .error(function (error) {
                    console.log(error);
                });
    }
    
    return {
        getSubmissions: getSubmissions
    };
}());