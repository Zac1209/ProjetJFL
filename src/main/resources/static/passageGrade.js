function passerExamen(username,evaluateur)
{
    $.ajax(
        {
            url: '/Exam/'+ username +'/'+evaluateur+'/true',
            type: 'GET',
            contentType: 'application/json',
            async: false,
            success : function(id) {

            },
            error: function (xhr, status, error)
            {
                alert("Erreur!");
            }
        });
}

function echecExamen(username,evaluateur)
{
    $.ajax(
        {
            url: '/Exam/'+ username +'/'+evaluateur+'/false',
            type: 'GET',
            contentType: 'application/json',
            async: false,
            success : function(id) {

            },
            error: function (xhr, status, error)
            {
                alert("Erreur!");
            }
        });
}

function mettreAncien(username)
{
    $.ajax(
        {
            url: '/mettreAncien/'+ username ,
            type: 'GET',
            contentType: 'application/json',
            async: false,
            success : function(id) {

            },
            error: function (xhr, status, error)
            {
                alert("Erreur!");
            }
        });
}

function mettreSensei(username)
{
    $.ajax(
        {
            url: '/mettreSensei/'+ username ,
            type: 'GET',
            contentType: 'application/json',
            async: false,
            success : function(id) {

            },
            error: function (xhr, status, error)
            {
                alert("Erreur!");
            }
        });
}

function enleverSensei(username)
{
    mettreAncien(username);
}