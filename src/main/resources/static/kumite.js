
window.onbeforeunload = function () {
    $.ajax(
        {
            url: '/exit/' + document.getElementById('hiddenID').value,
            type: 'GET',
            contentType: 'application/json',
            async: false
        });
};