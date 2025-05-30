package pacientes;

import historia.HistoriaClinica;

public class Paciente {
    private String nombre;
    private String identificacion;
    private int edad;
    private String direccion;
    private String contactoEmergencia;
    private String alergias;
    private String medicamentos;
    private String seguroMedico;
    private String historialVacunacion;

    private HistoriaClinica historiaClinica;

    protected Paciente(PacienteBuilder builder) {
        this.nombre = builder.nombre;
        this.identificacion = builder.identificacion;
        this.edad = builder.edad;
        this.direccion = builder.direccion;
        this.contactoEmergencia = builder.contactoEmergencia;
        this.alergias = builder.alergias;
        this.medicamentos = builder.medicamentos;
        this.seguroMedico = builder.seguroMedico;
        this.historialVacunacion = builder.historialVacunacion;
        this.historiaClinica = new HistoriaClinica(alergias, medicamentos);
    }

    public HistoriaClinica getHistoriaClinica() {
        return historiaClinica;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public int getEdad() {
        return edad;
    }

    @Override
    public String toString() {
        return "Paciente: " + nombre + ", ID: " + identificacion + ", Edad: " + edad;
    }
}
