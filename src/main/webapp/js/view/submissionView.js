var app = app || {};

app.SubmissionView = (function () {
    
    function renderSubmissions(selector, submissions) {
        $.get('templates/submissions.html', function (template) {
            var html = Mustache.render(template, {submissions: submissions});
            $(selector).html(html);
        });
    }
    
    return {
        renderSubmissions: renderSubmissions
    };
}());