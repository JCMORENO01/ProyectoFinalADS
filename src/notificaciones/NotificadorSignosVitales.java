package notificaciones;

import medicos.Medico;

import java.util.ArrayList;
import java.util.List;

public class NotificadorSignosVitales {
    private List<Medico> medicos;

    public NotificadorSignosVitales() {
        this.medicos = new ArrayList<>();
    }

    public void registrar(Medico medico) {
        medicos.add(medico);
    }

    public void notificar(String mensaje) {
        for (Medico medico : medicos) {
            medico.actualizar(mensaje);
        }
    }
}
