var app = app || {};

app.SubmissionView = (function () {
    
    function renderTraineeSubmissions(selector, submissions) {
        $.get('templates/trainee-submissions.html', function (template) {
            var html = Mustache.render(template, {submissions: submissions});
            $(selector).html(html);
        });
    }
    
    function renderTrainerSubmissions(selector, submissions) {
        $.get('templates/trainer-submissions.html', function (template) {
            var html = Mustache.render(template, {submissions: submissions});
            $(selector).html(html);
        });
    }
    
    function renderTrainerSubmission(selector, submission) {
        $.get('templates/trainer-submission.html', function (template) {
            var html = Mustache.render(template, submission);
            $(selector).html(html);
        });
    }
    
    return {
        renderTraineeSubmissions: renderTraineeSubmissions,
        renderTrainerSubmissions: renderTrainerSubmissions,
        renderTrainerSubmission: renderTrainerSubmission
    };
}());