package app;

import facade.HospitalFacade;
import medicos.MedicoConcreto;
import pacientes.Paciente;
import signos.SignosVitales;

import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final HospitalFacade hospital = new HospitalFacade();
    private static final Map<String, Paciente> pacientesRegistrados = new LinkedHashMap<>();
    private static Paciente pacienteActual = null;

    public static void main(String[] args) {
        inicializarHospital();

        while (true) {
            mostrarMenu();
            int opcion = leerOpcion();
            switch (opcion) {
                case 1:
                    registrarPaciente();
                    break;
                case 2:
                    seleccionarPaciente();
                    break;
                case 3:
                    registrarSignosVitales();
                    break;
                case 4:
                    crearOrdenMedica();
                    break;
                case 5:
                    consultarHistoriaClinica();
                    break;
                case 6:
                    verCamillas();
                    break;
                case 7:
                    verEquiposMedicos();
                    break;
                case 8:
                    actualizarEstadoCamilla();
                    break;
                case 9:
                    actualizarEstadoEquipo();
                    break;
                case 0:
                    salir();
                    break;
                default:
                    System.out.println("⚠️ Opción inválida. Intente de nuevo.");
                    break;
            }
        }
    }

    private static void inicializarHospital() {
        hospital.registrarMedico(new MedicoConcreto("Dra. Marina Ruiz"));
        hospital.registrarMedico(new MedicoConcreto("Dr. Juan Pérez"));
        hospital.registrarMedico(new MedicoConcreto("Dr. Camilo García"));
    }

    private static void mostrarMenu() {
        System.out.println("\n══════════════════════════════════════");
        System.out.println("     🏥 Sistema de Emergencias");
        System.out.println("══════════════════════════════════════");
        System.out.println("1️⃣  Registrar nuevo paciente");
        System.out.println("2️⃣  Seleccionar paciente");
        System.out.println("3️⃣  Registrar signos vitales");
        System.out.println("4️⃣  Crear orden médica");
        System.out.println("5️⃣  Consultar historia clínica");
        System.out.println("6️⃣  Ver estado de camillas");
        System.out.println("7️⃣  Ver estado de equipos médicos");
        System.out.println("8️⃣  Actualizar estado de camilla");
        System.out.println("9️⃣  Actualizar estado de equipo médico");
        System.out.println("0️⃣  Salir");
        if (pacienteActual != null) {
            System.out.println("👤 Paciente seleccionado: " + pacienteActual.getNombre());
        } else {
            System.out.println("👤 No hay paciente seleccionado.");
        }
        System.out.print("\nSeleccione una opción ➤ ");
    }

    private static int leerOpcion() {
        while (!scanner.hasNextInt()) {
            System.out.println("⚠️ Ingrese un número válido.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void registrarPaciente() {
        scanner.nextLine(); // limpiar buffer
        System.out.println("\n--- Registro de Paciente ---");
        System.out.print("👤 Nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.print("🆔 Identificación: ");
        String id = scanner.nextLine();
        System.out.print("🎂 Edad: ");
        int edad = leerOpcion();
        scanner.nextLine();
        System.out.print("🏠 Dirección: ");
        String direccion = scanner.nextLine();
        System.out.print("📞 Contacto de emergencia: ");
        String contacto = scanner.nextLine();
        System.out.print("💊 Alergias conocidas: ");
        String alergias = scanner.nextLine();
        System.out.print("🩺 Medicamentos actuales: ");
        String medicamentos = scanner.nextLine();
        System.out.print("🏥 Seguro médico: ");
        String seguro = scanner.nextLine();
        System.out.print("📝 Historial de vacunación: ");
        String vacunas = scanner.nextLine();

        Paciente paciente = hospital.registrarPaciente(
                nombre, id, edad, direccion, contacto,
                alergias, medicamentos, seguro, vacunas
        );

        pacientesRegistrados.put(id, paciente);
        System.out.println("✅ Paciente registrado exitosamente.");
    }

    private static void seleccionarPaciente() {
        scanner.nextLine(); // limpiar buffer
        System.out.println("\n--- Selección de Paciente ---");
        if (pacientesRegistrados.isEmpty()) {
            System.out.println("⚠️ No hay pacientes registrados.");
            return;
        }
        for (Paciente p : pacientesRegistrados.values()) {
            System.out.println("🆔 " + p.getIdentificacion() + " - " + p.getNombre());
        }
        System.out.print("Ingrese la identificación del paciente ➤ ");
        String id = scanner.nextLine();

        pacienteActual = pacientesRegistrados.get(id);
        if (pacienteActual == null) {
            System.out.println("⚠️ Paciente no encontrado.");
        } else {
            System.out.println("✅ Paciente seleccionado: " + pacienteActual.getNombre());
        }
    }

    private static void registrarSignosVitales() {
        if (!verificarPaciente()) return;
        System.out.println("\n--- Registro de Signos Vitales ---");
        System.out.print("🌡️  Temperatura (°C): ");
        double temp = scanner.nextDouble();
        System.out.print("❤️ Pulso (bpm): ");
        int pulso = scanner.nextInt();
        System.out.print("🩸 Presión Sistólica (mmHg): ");
        int sistolica = scanner.nextInt();
        System.out.print("🩸 Presión Diastólica (mmHg): ");
        int diastolica = scanner.nextInt();
        System.out.print("🫁 Saturación de oxígeno (%): ");
        double oxigeno = scanner.nextDouble();
        scanner.nextLine();

        SignosVitales signos = new SignosVitales(temp, pulso, sistolica, diastolica, oxigeno);
        hospital.registrarSignosVitales(pacienteActual, signos);

        // Automáticamente asignamos al médico
        hospital.asignarPacienteAMedico(pacienteActual);
    }

    private static void crearOrdenMedica() {
        if (!verificarPaciente()) return;
        scanner.nextLine();
        System.out.println("\n--- Nueva Orden Médica ---");
        System.out.print("📋 Tipo de orden (Medicamento, Examen, Procedimiento, Incapacidad): ");
        String tipo = scanner.nextLine();
        System.out.print("📝 Descripción detallada: ");
        String descripcion = scanner.nextLine();
        hospital.generarOrdenMedica(pacienteActual, tipo, descripcion);
    }

    private static void consultarHistoriaClinica() {
        if (!verificarPaciente()) return;
        System.out.println("\n--- Historia Clínica ---");
        hospital.consultarHistoriaClinica(pacienteActual);
    }

    private static void verCamillas() {
        System.out.println("\n--- Estado de Camillas ---");
        hospital.mostrarCamillas();
    }

    private static void verEquiposMedicos() {
        System.out.println("\n--- Estado de Equipos Médicos ---");
        hospital.mostrarEquipos();
    }

    private static void actualizarEstadoCamilla() {
        System.out.print("\nIngrese ID de camilla a actualizar: ");
        int id = leerOpcion();
        scanner.nextLine();
        System.out.print("Nuevo estado (Libre, Ocupada, Fuera de servicio): ");
        String estado = scanner.nextLine();
        hospital.actualizarEstadoCamilla(id, estado);
    }

    private static void actualizarEstadoEquipo() {
        System.out.print("\nIngrese ID de equipo médico a actualizar: ");
        int id = leerOpcion();
        scanner.nextLine();
        System.out.print("Nuevo estado (Operativo, Mantenimiento, Fallando): ");
        String estado = scanner.nextLine();
        hospital.actualizarEstadoEquipo(id, estado);
    }

    private static void salir() {
        System.out.println("\n👋 Gracias por usar el Sistema de Emergencias. ¡Hasta pronto!");
        System.exit(0);
    }

    private static boolean verificarPaciente() {
        if (pacienteActual == null) {
            System.out.println("⚠️ No hay paciente seleccionado. Seleccione uno primero.");
            return false;
        }
        return true;
    }
}
