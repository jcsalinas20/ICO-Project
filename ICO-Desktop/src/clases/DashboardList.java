package clases;

public class DashboardList {

    private Consulta datosConsulta;
    private Paciente paciente;

    public DashboardList(Consulta datosConsulta, Paciente paciente) {
        this.datosConsulta = datosConsulta;
        this.paciente = paciente;
    }

    public Consulta getDatosConsulta() {
        return datosConsulta;
    }

    public Paciente getPaciente() {
        return paciente;
    }
}
