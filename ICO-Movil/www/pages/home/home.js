var authToken = function comprobarToken() {
    if (localStorage.getItem("jwt")) {
        const token = localStorage.getItem("jwt")
        var URL
        if (token == "") {
            URL = `https://api-ico.herokuapp.com/api/null/perfil`
        } else {
            URL = `https://api-ico.herokuapp.com/api/${token}/perfil`
        }

        $.ajax({
            type: "GET",
            url: URL,
            crossDomain: true,
            dataType: "json"
        })
            .done(async function(res) {
                if (res.mensaje === "ERROR, no se encontró el Paciente.") {
                    window.location.href = "./../../"
                    return
                } else {
                    await sleep(2000)
                    authToken()
                }
            })
            .fail(function() {
                return false
            })
    } else {
        window.location.href = "./../../"
        return
    }
}

function sleep(ms) {
    return new Promise(resolve => {
        setTimeout(resolve, ms)
    })
}

function primerInicioSesion(user) {
    var API
    if (user === "") {
        API = `https://api-ico.herokuapp.com/api/null/primer-inicio-sesion`
    } else {
        API = `https://api-ico.herokuapp.com/api/${user}/primer-inicio-sesion`
    }
    $.ajax({
        type: "GET",
        url: API,
        crossDomain: true,
        dataType: "json"
    })
        .done(function(res) {
            if (res) {
                delayPopUp()
            }
        })
        .fail(function() {
            activateToast("No se pudo establecer conexión con el servidor")
        })
}

function delayPopUp() {
    // Comprobar si es el primer inicio sesion
    window.onload = function() {
        setTimeout(loadPopUp, 3000) // Esperar 3 segundos
    }
}

function loadPopUp() {
    document.getElementById("frame-password").style.animation =
        "1s ease-out 0s 1 popup"
    document.getElementById("container-frame-cambio-password").style.display =
        "flex"
}

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]")
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search)
    return results === null
        ? ""
        : decodeURIComponent(results[1].replace(/\+/g, " "))
}

function activateToast(mensaje) {
    M.toast({
        html: mensaje
    })
}

function ocultarVentana() {
    window.parent.document.getElementById("frameNoticia").style.display = "none"
    window.parent.document.getElementById("menuNoticia").style.display = "none"
}
