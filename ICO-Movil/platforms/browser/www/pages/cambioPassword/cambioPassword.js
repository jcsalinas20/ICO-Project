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
            if (leng === "cat") {
                mostrarError("Les contrasenyes no coincideixen")
            } else {
                mostrarError("Las contraseñas no coinciden")
            }
            passRepetida.value = ""
        }
    } else {
        if (leng === "cat") {
            mostrarError("Longitud mínima de 6 dígits")
        } else {
            mostrarError("Longitud mínima de 6 digitos")
        }
        pass.value = ""
        passRepetida.value = ""
    }
    return false
}

function cambioPasswod(token) {
    if (comprobarPassword()) {
        window.parent.document
            .getElementById("frame-preload")
            .contentWindow.document.getElementById("mensaje").innerHTML =
            "Cambiando la contraseña."
        window.parent.document.getElementById("frame-preload").style.display =
            "block"
        var pass = document.getElementById("pass").value
        $.ajax({
            url: `https://api-ico.herokuapp.com/api/${token}/cambio-password/${pass}`,
            crossDomain: true,
            dataType: "json"
        })
            .done(function(res) {
                if (
                    res.mensaje ===
                    "Se ha actualizar la contraseña correctamente."
                ) {
                    ocultarPopUp()
                }
            })

            .fail(function() {
                window.parent.document.getElementById(
                    "frame-preload"
                ).style.display = "none"
                window.parent.activateToast(
                    "No se pudo establecer conexión con el servidor"
                )
            })
    }
}

function ocultarPopUp() {
    window.parent.document.getElementById(
        "container-frame-cambio-password"
    ).style.display = "none"
    window.parent.activateToast("Contrasenya actualitzada correctament.")
    window.parent.document.getElementById("frame-preload").style.display =
        "none"
    window.parent.document
        .getElementById("frame-guia")
        .contentWindow.document.getElementById("frameBody").src =
        "pasos/paso1.html"
    window.parent.document.getElementById(
        "container-frame-guia"
    ).style.display = "flex"
}
