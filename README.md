# ICO-Project

TPV Hospital Project with collaborators @mmoralesl.

* ## MongoDB

### Documents Consultas
```js
{
  id_consulta: Integer,
  id_doctor: Integer,
  id_paciente: Integer,
  id_direccion: Integer,
  consultas: Array [
    Object {
      hora: String,
      dia: String,
      asistido: Boolean,
      notas: String,
      notas_doc: String,
      num_consulta: Integer,
    }
  ]
}
```

### Documents Doctores
```js
{
  id_doctor: Integer,
  nombre: String,
  apellidos: String,
  username: String,
  password: md5(String),
  id_hospital: Integer,
  planta: Integer,
  sala: Integer,
  horarios: String(startHour-endHour),
  dias: Array [
    0: Integer(0-1),
    1: Integer(0-1),
    2: Integer(0-1),
    3: Integer(0-1),
    4: Integer(0-1),
    5: Integer(0-1),
    6: Integer(0-1),
  ]
}
```

### Documents Hospitales
```js
{
  id_hospital: Integer,
  nombre: String,
  direccion: String
}
```

### Documents Medicamentos
```js
{
  id: Integer,
  nombre: String,
  imagen: String(url)
}
```

### Documents Pacientes
```js
{
  id_paciente: Integer,
  nombre: String,
  apellidos: String,
  dni: String,
  password: md5(String),
  token: String(autogeneration),
  expireToken: String(autogeneration),
  foto: String(url),
  primerInicioSesion: Boolean,
  fecha_nacimiento: String,
  genero: String(H-M),
  leng: String(cas-cat),
  medicamentos: Array [
    Object {
      id: Integer,
      dias: Object {
        lunes: Integer(0-1),
        martes: Integer(0-1),
        miercoles: Integer(0-1),
        jueves: Integer(0-1),
        viernes: Integer(0-1),
        sabado: Integer(0-1),
        domingo: Integer(0-1),
      },
      hora: Array [
        String
      ],
      pastillaTomada: Array [
        Boolean
      ]
    }
  ]
}
```

* ## [ICO-API](https://github.com/jcsalinas20/ICO-API)

* ## [ICO-Desktop](https://github.com/jcsalinas20/ICO-Desktop)

### Login

![login](http://imgfz.com/i/v2tmdIE.png)

### Dashboard

![dashboard](http://imgfz.com/i/WyPxmCO.png)

### Search

![dashboard](http://imgfz.com/i/cOylRfa.png)

### Consultas

![dashboard](http://imgfz.com/i/ZzK9YAo.png)

### Medicamentos

![dashboard](http://imgfz.com/i/sBfLuhl.png)

* ## [ICO-Mobile](https://github.com/jcsalinas20/ICO-Movil)
