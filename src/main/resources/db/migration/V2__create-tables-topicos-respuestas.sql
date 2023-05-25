CREATE TABLE topicos(
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(125) NOT NULL ,
    mensaje VARCHAR(255) NOT NULL,
    fechaCreacion DATETIME NOT NULL ,
    estado VARCHAR(50) NOT NULL ,
    id_autor BIGINT NOT NULL ,
    id_curso BIGINT NOT NULL ,
    PRIMARY KEY (id),
    CONSTRAINT fk_topico_autor FOREIGN KEY (id_autor) REFERENCES usuarios(id),
    CONSTRAINT fk_topico_curso FOREIGN KEY (id_curso) REFERENCES cursos(id)
);

CREATE TABLE respuestas(
    id BIGINT NOT NULL AUTO_INCREMENT,
    mensaje VARCHAR(255) NOT NULL ,
    fechaCreacion DATETIME NOT NULL ,
    id_topico BIGINT NOT NULL ,
    id_autor BIGINT NOT NULL ,
    solucion TINYINT(1) NOT NULL ,
    PRIMARY KEY (id),
    CONSTRAINT fk_respuesta_topico FOREIGN KEY (id_topico) REFERENCES topicos(id),
    CONSTRAINT fk_respuesta_autor FOREIGN KEY (id_autor) REFERENCES usuarios(id)

);