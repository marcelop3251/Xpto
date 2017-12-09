package br.com.xpto.entidades.auxiliares;

public class EstadoAndQuantidadeDeCidades {

	
	private long quantidadeDeCidades;
	private String estado;

	public EstadoAndQuantidadeDeCidades(String estado, long quantidadeDeCidades){
		this.estado = estado;
		this.quantidadeDeCidades = quantidadeDeCidades;
	}

	public long getQuantidadeDeCidades() {
		return quantidadeDeCidades;
	}

	public void setQuantidadeDeCidades(long quantidadeDeCidades) {
		this.quantidadeDeCidades = quantidadeDeCidades;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
}
