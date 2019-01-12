create table public.Servidor
(
    ser_id serial primary key,
    ser_tipo integer,
	ser_disponible boolean,
    ser_principal boolean
	);

	
	create Table public.Archivo(
	arc_id serial primary key,
	arc_nombre varchar(100) unique
	);

	
	create Table public.Version(
	ver_id serial primary key,
	ver_fecha timestamp,
	ver_archivo integer,
	CONSTRAINT fk_ver_archivo FOREIGN KEY (ver_archivo)
	REFERENCES Archivo(arc_id)
	);
	
	create Table public.Ser_Ver(
	ser_ver_id serial primary key,
	ser_ver_version integer,
	ser_ver_servidor integer,
	CONSTRAINT fk_ser_ver_version FOREIGN KEY (ser_ver_version)
	REFERENCES Version(ver_id),
	CONSTRAINT fk_ser_ver_servidor FOREIGN KEY (ser_ver_servidor)
	REFERENCES Servidor(ser_id)
	);