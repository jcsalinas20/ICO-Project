function cambiarIdioma(leng) {
    window.parent.document.getElementById(
        "container-frame-idioma"
    ).style.display = "none"
    localStorage.setItem("leng", leng)
    $.ajax({
        type: "GET",
        url: `https://api-ico.herokuapp.com/api/${localStorage.getItem('jwt')}/idioma/${leng}`,
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
    window.parent.location.href = "./../../"
}
