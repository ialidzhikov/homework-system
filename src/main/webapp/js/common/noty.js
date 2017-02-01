var app = app || {};

app.NotificationManager = (function () {
    
    function notifySuccess(msg) {
        noty({
            text: msg,
            type: 'success',
            timeout: 2000,
            closeWith: ['click'],
            theme: 'relax',
            layout: 'bottom'
        });
    }
    
    function notifyError(msg) {
        noty({
            text: msg,
            type: 'error',
            timeout: 2000,
            closeWith: ['button', 'timeout'],
            theme: 'relax'
        });
    }
    
    return {
        notifySuccess: notifySuccess,
        notifyError: notifyError
    };
    
}());