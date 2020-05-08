function ocultarVentana() {
    window.parent.document.getElementById('framePerfil').style.display = 'none'
    window.parent.document.body.style.animation = '0.7s ease-out 0s 1 bottomToTop'
}

function cambiarContra() {
    window.parent.document.getElementById('framePerfil').style.display = 'none'
    window.parent.document.getElementById('container-frame-cambio-password').style.display = 'flex'
}

function cerrarSesion() {
    window.parent.document.getElementById("frame-preload").contentWindow.document.getElementById('mensaje').innerHTML = 'Cerrando sesión.'
    window.parent.document.getElementById('frame-preload').style.display = 'flex'
    var dni = document.getElementById('dni').innerHTML.split(':</b> ')
    $.ajax({
        type: "GET",
        url: `https://api-ico.herokuapp.com/api/${dni[1]}/logout`,
        crossDomain: true,
        dataType: "json"
    })
        .done(async function(res) {
            if (res) {
                localStorage.removeItem('jwt')
                window.parent.document.getElementById('frame-preload').style.display = 'none'
                window.parent.location.href = './../../'
            }
        })
        .fail(function() {
            // LLAMAR AL METODO DEL PADRE
            activateToast("No se pudo establecer conexión con el servidor")
        })
}

function cambiarIdioma() {
    window.parent.document.getElementById('container-frame-idioma').style.display = 'flex'
}