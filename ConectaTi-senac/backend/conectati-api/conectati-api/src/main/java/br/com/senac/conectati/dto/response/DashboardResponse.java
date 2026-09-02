package br.com.senac.conectati.dto.response;

import br.com.senac.conectati.enums.StatusChamado;
import br.com.senac.conectati.enums.TipoUsuario;
import lombok.Getter;
import lombok.Setter;

import java.util.EnumMap;
import java.util.Map;

@Getter
@Setter
public class DashboardResponse {

    private TipoUsuario cargo;
    private long totalChamados;
    private Map<StatusChamado, Long> chamadosPorStatus = new EnumMap<>(StatusChamado.class);
    private Map<TipoUsuario, Long> usuariosPorCargo = new EnumMap<>(TipoUsuario.class);
    private long totalEquipamentos;
}
