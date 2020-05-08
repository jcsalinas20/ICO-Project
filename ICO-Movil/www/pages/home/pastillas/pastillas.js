function getDiaActual() {
    var date = new Date()
    var dia = getNombreDia(date.getDay())
    var diaNum = date.getDate()
    if (diaNum < 10) {
        diaNum = "0" + diaNum
    }
    var mes = date.getMonth() + 1
    if (mes < 10) {
        mes = "0" + mes
    }
    console.log()

    if (leng === "cat") {
        document.getElementById(
            "dia-actual"
        ).innerHTML = `Dia: <b id="dia">${dia}</b> - ${diaNum}/${mes}/${date.getFullYear()}`
    } else {
        document.getElementById(
            "dia-actual"
        ).innerHTML = `Día: <b id="dia">${dia}</b> - ${diaNum}/${mes}/${date.getFullYear()}`
    }
}

function getNombreDia(dia) {
    if (dia == 0) {
        if (leng === "cat") {
            return "Diumenge"
        } else {
            return "Domingo"
        }
    } else if (dia == 1) {
        if (leng === "cat") {
            return "Dilluns"
        } else {
            return "Lunes"
        }
    } else if (dia == 2) {
        if (leng === "cat") {
            return "Dimarts"
        } else {
            return "Martes"
        }
    } else if (dia == 3) {
        if (leng === "cat") {
            return "Dimecres"
        } else {
            return "Miercoles"
        }
    } else if (dia == 4) {
        if (leng === "cat") {
            return "Dijous"
        } else {
            return "Jueves"
        }
    } else if (dia == 5) {
        if (leng == "cat") {
            return "Divendres"
        } else {
            return "Viernes"
        }
    } else if (dia == 6) {
        if (leng === "cat") {
            return "Dissabte"
        } else {
            return "Sabado"
        }
    }
    return null
}

function getItems(token) {
    queryMedicamentos(token)
}

var queryMedicamentos = async function callQuery(token) {
    var URL
    if (token == "") {
        URL = `https://api-ico.herokuapp.com/api/null/medicamentos`
    } else {
        URL = `https://api-ico.herokuapp.com/api/${token}/medicamentos`
    }

    $.ajax({
        type: "GET",
        url: URL,
        crossDomain: true,
        dataType: "json"
    })
        .done(async function(res) {
            if (res) {
                var dia = document.getElementById("dia").innerHTML.toLowerCase()
                if (leng == "cat") {
                    dia = traducirCatToCas(dia)
                }
                var parent = document.getElementById("medicamentos")
                parent.innerHTML = ""
                for (let j = 0; j < res.medicamentos.length; j++) {
                    const med = res.medicamentos[j]
                    if (med.dias[dia] == 1) {
                        const li = document.createElement("li")
                        var div1 = document.createElement("div")
                        var img = document.createElement("img")
                        var div2 = document.createElement("div")
                        var p1 = document.createElement("p")
                        var p2 = document.createElement("p")
                        var div3 = document.createElement("div")
                        var div4 = document.createElement("div")
                        var label = document.createElement("label")
                        const input = document.createElement("input")
                        var span1 = document.createElement("span")
                        var span2 = document.createElement("span")

                        img.className = "imagen-med"
                        img.width = "50"
                        img.height = "50"
                        img.src = med.imagen
                        img.alt = med.nombre
                        div1.appendChild(img)
                        div1.className = "container-img"

                        span1.className = "text"
                        if (leng === "cat") {
                            span1.innerHTML = "Ja me l'he pres:"
                        } else {
                            span1.innerHTML = "Ya me la he tomado:"
                        }
                        input.type = "checkbox"
                        input.defaultChecked = med.pastillaTomada
                        if (comprobarHora(med.hora)) {
                            if (!input.checked) {
                                var minutos = med.hora.split(":")
                                minutos[0]++
                                if (minutos[0] < 0) {
                                    minutos[0] = 23
                                }
                                if (minutos[0] < 10) {
                                    minutos[0] = "0" + minutos[0]
                                }
                                if (
                                    getHora(new Date()) >
                                    `${minutos[0]}:${minutos[1]}`
                                ) {
                                    li.style.backgroundColor = "#FF8989"
                                } else {
                                    li.style.backgroundColor = "#FFCC6E"
                                }
                            } else {
                                li.style.backgroundColor = "#AFA"
                            }
                        } else {
                            input.disabled = true
                        }
                        span2.className = "lever"
                        label.appendChild(span1)
                        label.innerHTML += " &nbsp;&nbsp;&nbsp;&nbsp;No"
                        label.appendChild(input)
                        label.appendChild(span2)
                        label.innerHTML += "Sí"
                        label.className = "lbl-med"
                        div4.appendChild(label)
                        label.addEventListener("click", function() {
                            cambioEstado(
                                med.pastillaTomada,
                                med.id,
                                med.hora,
                                li,
                                input,
                                token
                            )
                        })
                        div4.className = "switch"
                        div3.appendChild(div4)
                        div3.className = "container-medicamento-tomado"
                        p1.className = "nombre-med"
                        p1.innerHTML = med.nombre
                        p2.className = "hora-med"
                        p2.innerHTML = "Hora: " + med.hora
                        div2.appendChild(p1)
                        div2.appendChild(p2)
                        div2.appendChild(div3)
                        div2.className = "container-datos"

                        li.appendChild(div1)
                        li.appendChild(div2)
                        li.id = med.id
                        li.className = "collection-item container-medicamento"
                        parent.appendChild(li)
                    }
                }
                await sleep(10000) // 10 segundos
                queryMedicamentos(token)
            }
        })
        .fail(function() {
            // LLAMAR AL METODO DEL PADRE
            window.parent.activateToast(
                "No se pudo establecer conexión con el servidor"
            )
        })
}

function sleep(ms) {
    return new Promise(resolve => {
        setTimeout(resolve, ms)
    })
}

function comprobarHora(hora) {
    var horaActual = getHora(new Date())

    var minutos = hora.split(":")
    minutos[1] = parseInt(minutos[1]) - 10
    if (minutos[1] < 0) {
        var cont = Math.abs(minutos[1])
        minutos[1] = 60 - cont
        minutos[0]--
        if (minutos[0] < 0) {
            minutos[0] = 23
        }
        if (minutos[0] < 10) {
            minutos[0] = "0" + minutos[0]
        }
        if (minutos[1] < 10) {
            minutos[1] = "0" + minutos[1]
        }
    }

    if (horaActual > `${minutos[0]}:${minutos[1]}`) {
        return true
    }
    return false
}

function getHora(date) {
    var h = date.getHours()
    if (h < 10) {
        h = "0" + h
    }
    var m = date.getMinutes()
    if (m < 10) {
        m = "0" + m
    }
    return `${h}:${m}`
}

async function cambioEstado(checked, id, hora, li, input, token) {
    if (!input.disabled) {
        if (checked) {
            estado = false
            li.style.backgroundColor = "#FF8989"
        } else {
            estado = true
            li.style.backgroundColor = "#AFA"
        }
        var URL
        if (token == "") {
            URL = `https://api-ico.herokuapp.com/api/null/cambioEstadoPastilla/null/null/null`
        } else {
            URL = `https://api-ico.herokuapp.com/api/${token}/cambioEstadoPastilla/${id}/${hora}/${estado}`
        }

        // input.disabled = true
        await $.ajax({
            type: "GET",
            url: URL,
            crossDomain: true,
            dataType: "json"
        })
            .done(function(res) {})
            .fail(function() {
                // LLAMAR AL METODO DEL PADRE
                window.parent.activateToast(
                    "No se pudo establecer conexión con el servidor"
                )
            })
        await sleep2(2000)
    }
}

function sleep2(ms) {
    return new Promise(resolve => {
        setTimeout(resolve, ms)
    })
}

function traducirCatToCas(dia) {
    if (dia == "diumenge") {
        return "domingo"
    } else if (dia == "dilluns") {
        return "lunes"
    } else if (dia == "dimarts") {
        return "martes"
    } else if (dia == "dimecres") {
        return "miercoles"
    } else if (dia == "dijous") {
        return "jueves"
    } else if (dia == "divendres") {
        return "viernes"
    } else if (dia == "dissabte") {
        return "sabado"
    }
}
