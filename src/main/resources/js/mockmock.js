$(function() {

    $('.deleteLink a.delete').click(function() {
        $.ajax({
            type: "POST",
            url: "/mail/delete/all"
        }).done(function( msg ) {
            window.location = '/';
        });
    });

});