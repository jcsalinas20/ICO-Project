function getItems(token) {
    queryConsultas(token)
    queryHistorialConsultas(token)
}

let consultasPendientes = []
let historialConsultas = []

var queryHistorialConsultas = function callQuery(token) {
    var URL
    if (token == "") {
        URL = `https://api-ico.herokuapp.com/api/null/historial-consultas`
    } else {
        URL = `https://api-ico.herokuapp.com/api/${token}/historial-consultas`
    }

    $.ajax({
        type: "GET",
        url: URL,
        crossDomain: true,
        dataType: "json"
    })
        .done(async function(res) {
            if (res) {
                var parent = document.getElementById("historial-consultas")
                parent.innerHTML = ""
                historialConsultas = res.historial_consultas
                for (let j = 0; j < res.historial_consultas.length; j++) {
                    var con = res.historial_consultas[j]
                    var li = document.createElement("li")
                    var div1 = document.createElement("div")
                    var div2 = document.createElement("div")
                    var div3 = document.createElement("div")
                    var a = document.createElement("a")
                    var i1 = document.createElement("i")
                    var i2 = document.createElement("i")

                    i1.appendChild(document.createTextNode("access_time"))
                    i1.className = "material-icons"
                    i1.style.verticalAlign = "middle"
                    div1.appendChild(i1)
                    div1.className = "icons-consultas"

                    div2.className = "text-consultas"
                    if (leng == "cat") {
                        div2.appendChild(
                            document.createTextNode(
                                "Dia: " + con.dia + " - Hora: " + con.hora
                            )
                        )
                    } else {
                        div2.appendChild(
                            document.createTextNode(
                                "Día: " + con.dia + " - Hora: " + con.hora
                            )
                        )
                    }

                    i2.appendChild(document.createTextNode("send"))
                    i2.style.verticalAlign = "middle"
                    i2.className = "material-icons"
                    i2.addEventListener("click", function() {
                        mostrarConsulta(j, "historial")
                    })
                    a.appendChild(i2)
                    a.className = "secondary-content"
                    div3.appendChild(a)
                    div3.className = "icons-consultas"

                    if (con.asistido) {
                        li.style.backgroundColor = "#AFA"
                    } else {
                        li.style.backgroundColor = "#FF8989"
                    }
                    li.appendChild(div1)
                    li.appendChild(div2)
                    li.appendChild(div3)
                    li.id = con.id_consulta
                    li.className = "collection-item container-lista"
                    parent.appendChild(li)
                }
                await sleep(60000) // 1 minuto
                queryHistorialConsultas(token)
            }
        })
        .fail(function() {
            // LLAMAR AL METODO DEL PADRE
            activateToast("No se pudo establecer conexión con el servidor")
        })
}

var queryConsultas = function callQuery(token) {
    var URL
    if (token == "") {
        URL = `https://api-ico.herokuapp.com/api/null/consultas`
    } else {
        URL = `https://api-ico.herokuapp.com/api/${token}/consultas`
    }

    $.ajax({
        type: "GET",
        url: URL,
        crossDomain: true,
        dataType: "json"
    })
        .done(async function(res) {
            if (res) {
                var parent = document.getElementById("consultas")
                parent.innerHTML = ""
                consultasPendientes = res.consultas
                for (let j = 0; j < res.consultas.length; j++) {
                    var con = res.consultas[j]
                    var li = document.createElement("li")
                    var div1 = document.createElement("div")
                    var div2 = document.createElement("div")
                    var div3 = document.createElement("div")
                    var a = document.createElement("a")
                    var i1 = document.createElement("i")
                    var i2 = document.createElement("i")

                    i1.appendChild(document.createTextNode("access_time"))
                    i1.className = "material-icons"
                    i1.style.verticalAlign = "middle"
                    div1.appendChild(i1)
                    div1.className = "icons-consultas"

                    div2.className = "text-consultas"
                    if (leng == "cat") {
                        div2.appendChild(
                            document.createTextNode(
                                "Dia: " + con.dia + " - Hora: " + con.hora
                            )
                        )
                    } else {
                        div2.appendChild(
                            document.createTextNode(
                                "Día: " + con.dia + " - Hora: " + con.hora
                            )
                        )
                    }

                    i2.appendChild(document.createTextNode("send"))
                    i2.style.verticalAlign = "middle"
                    i2.className = "material-icons"
                    i2.addEventListener("click", function() {
                        mostrarConsulta(j, "pendientes")
                    })
                    a.appendChild(i2)
                    a.className = "secondary-content"
                    div3.appendChild(a)
                    div3.className = "icons-consultas"

                    if (comprobarHora(con.hora, con.dia)) {
                        li.style.backgroundColor = "#FFCC6E"
                    }

                    li.appendChild(div1)
                    li.appendChild(div2)
                    li.appendChild(div3)
                    li.id = con.id_consulta
                    li.className = "collection-item container-lista"
                    parent.appendChild(li)
                }
                await sleep(6000) // 1 minuto
                queryConsultas(token)
            }
        })
        .fail(function() {
            // LLAMAR AL METODO DEL PADRE
            activateToast("No se pudo establecer conexión con el servidor")
        })
}

function sleep(ms) {
    return new Promise(resolve => {
        setTimeout(resolve, ms)
    })
}

function mostrarConsulta(index, mostrar) {
    var consulta = {}
    if (mostrar === "pendientes") {
        consulta = consultasPendientes[index]
    } else {
        consulta = historialConsultas[index]
    }
    if (leng === "cat") {
        window.parent.document
            .getElementById("frameConsultaPendiente")
            .contentWindow.document.getElementById(
                "dia-hora"
            ).innerHTML = `<b>Dia:</b> ${consulta.dia} - <b>Hora:</b> ${consulta.hora}`
        window.parent.document
            .getElementById("frameConsultaPendiente")
            .contentWindow.document.getElementById(
                "nombre_doc"
            ).innerHTML = `<b>Nom:</b> ${consulta.doctor}`
        window.parent.document
            .getElementById("frameConsultaPendiente")
            .contentWindow.document.getElementById(
                "planta"
            ).innerHTML = `<b>Planta:</b> ${consulta.planta}`
        window.parent.document
            .getElementById("frameConsultaPendiente")
            .contentWindow.document.getElementById(
                "sala"
            ).innerHTML = `<b>Sala:</b> ${consulta.sala}`
        window.parent.document
            .getElementById("frameConsultaPendiente")
            .contentWindow.document.getElementById("nota").innerHTML =
            consulta.notas
        window.parent.document
            .getElementById("frameConsultaPendiente")
            .contentWindow.document.getElementById(
                "nombre_hospital"
            ).innerHTML = `<b>Nom:</b> ${consulta.nombre_hospital}`
        window.parent.document
            .getElementById("frameConsultaPendiente")
            .contentWindow.document.getElementById(
                "direccion"
            ).innerHTML = `<b>Direcció:</b> ${consulta.direccion}`
    } else {
        window.parent.document
            .getElementById("frameConsultaPendiente")
            .contentWindow.document.getElementById(
                "dia-hora"
            ).innerHTML = `<b>Día:</b> ${consulta.dia} - <b>Hora:</b> ${consulta.hora}`
        window.parent.document
            .getElementById("frameConsultaPendiente")
            .contentWindow.document.getElementById(
                "nombre_doc"
            ).innerHTML = `<b>Nombre:</b> ${consulta.doctor}`
        window.parent.document
            .getElementById("frameConsultaPendiente")
            .contentWindow.document.getElementById(
                "planta"
            ).innerHTML = `<b>Planta:</b> ${consulta.planta}`
        window.parent.document
            .getElementById("frameConsultaPendiente")
            .contentWindow.document.getElementById(
                "sala"
            ).innerHTML = `<b>Sala:</b> ${consulta.sala}`
        window.parent.document
            .getElementById("frameConsultaPendiente")
            .contentWindow.document.getElementById("nota").innerHTML =
            consulta.notas
        window.parent.document
            .getElementById("frameConsultaPendiente")
            .contentWindow.document.getElementById(
                "nombre_hospital"
            ).innerHTML = `<b>Nombre:</b> ${consulta.nombre_hospital}`
        window.parent.document
            .getElementById("frameConsultaPendiente")
            .contentWindow.document.getElementById(
                "direccion"
            ).innerHTML = `<b>Dirección:</b> ${consulta.direccion}`
    }
    window.parent.document
        .getElementById("frameConsultaPendiente")
        .contentWindow.document.getElementById(
            "gmap_canvas"
        ).src = `https://maps.google.com/maps?q=${consulta.direccion}&t=&z=13&ie=UTF8&iwloc=&output=embed`
    window.parent.document.getElementById(
        "frameConsultaPendiente"
    ).style.display = "flex"
}

function comprobarHora(hora, dia) {
    var date = new Date()
    var day = date.getDate()
    if (day < 10) {
        day = "0" + day
    }
    var month = date.getMonth() + 1
    if (month < 10) {
        month = "0" + month
    }
    var fechaActual = `${date.getFullYear()}${month}${day}`
    var fechaConsulta = ""
    var fechaSeparada = dia.split("-")
    for (let i = fechaSeparada.length - 1; i >= 0; i--) {
        fechaConsulta += fechaSeparada[i]
    }

    var h = date.getHours()
    if (h < 10) {
        h = "0" + h
    }
    var d = date.getMinutes()
    if (d < 10) {
        d = "0" + d
    }
    var horaActual = `${h}:${d}`

    if (fechaActual >= fechaConsulta) {
        if (horaActual > hora) {
            return true
        }
        return true
    }
    return false
}

$(document).ready(function() {
    $(".collapsible").collapsible()
})
