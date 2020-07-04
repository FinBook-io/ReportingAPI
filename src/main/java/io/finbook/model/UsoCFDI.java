package io.finbook.model;

public enum UsoCFDI {
	D01("Honorarios Médicos, Dentales y Gastos Hospitalarios"),
	D02("Gastos Médicos por Incapacidad o Discapacidad"),
	D03("Gastos Funerales"),
	D04("Donativos"),
	D05("Intereses Reales Efectivamente Pagados por Créditos Hipotecarios (Casa Habitación)"),
	D06("Aportaciones Voluntarias al SAR"),
	D07("Primas por Seguros de Gastos Médicos"),
	D08("Gastos de Transportación Escolar Obligatoria"),
	D09("Depósitos en Cuentas para el Ahorro, Primas que tengan como Base Planes de Pensiones"),
	D10("Pagos por Servicios Educativos (Colegiaturas)"),
	G01("Adquisición de Mercancías"),
	G02("Devoluciones, Descuentos o Bonificaciones"),
	G03("Gastos en General"),
	I01("Construcciones"),
	I02("Mobiliario y Equipo de Oficina por Inversiones"),
	I03("Equipo de Transporte"),
	I04("Equipo de Cómputo y Accesorios"),
	I05("Dados, Troqueles, Moldes, Matrices y Herramental"),
	I06("Comunicaciones Telefónicas"),
	I07("Comunicaciones Satelitales"),
	I08("Otra Maquinaria y Equipo"),
	P01("Por Definir");

	private final String description;

	UsoCFDI(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
