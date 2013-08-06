$(function() {

    $('.deleteLink a.delete').click(function(event) {
        $.ajax({
            type: "POST",
            url: "/mail/delete/all"
        }).done(function( msg ) {
            window.location = '/';
        });

        event.preventDefault();
    });

});