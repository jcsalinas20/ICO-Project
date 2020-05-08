function ocultarError() {
    document.getElementById("error").style.display = "none"
}

function mostrarError(text) {
    document.getElementById("error").style.display = "block"
    document.getElementById("error").innerHTML = text
}

function quitarEspacios(pass, pass2) {
    pass.value = pass.value.replace(" ", "")
    pass2.value = pass2.value.replace(" ", "")
}

function comprobarPassword() {
    var pass = document.getElementById("pass")
    var passRepetida = document.getElementById("passRepetida")

    quitarEspacios(pass, passRepetida)

    if (pass.value.length > 5) {
        if (pass.value === passRepetida.value) {
            return true
        } else {
            mostrarError("Las contraseñas no coinciden")
            passRepetida.value = ""
        }
    } else {
        mostrarError("Longitud mínima de 6 digitos")
        pass.value = ""
        passRepetida.value = ""
    }
    return false
}

function cambioPasswod(user) {
    if (comprobarPassword()) {
        var pass = document.getElementById("pass").value
        $.ajax({
                url: `https://api-ico.herokuapp.com/api/${user}/cambio-password/${pass}`,
                crossDomain: true,
                dataType: "json"
            })
            .done(function (res) {
                if (res.mensaje === 'Se ha actualizar la contraseña correctamente.') {
                    ocultarPopUp()
                }
            })

            .fail(function () {
                alert("No se pudo establecer conexión con el servidor")
            })
    }
}

function ocultarPopUp() {
    console.log('gihpghpi')
    window.parent.document.getElementById('container-frame-cambio-password').style.display = 'none'
    window.parent.activateToast('Contraseña actualizada correctamente.')
}