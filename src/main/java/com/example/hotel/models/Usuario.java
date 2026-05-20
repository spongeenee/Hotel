package com.example.hotel.models;

import java.time.LocalDateTime;

public class Usuario {
    private final long ID;
    private final String usuario;
    private final String passwordHash;
    private final UsuarioRol rol;
    private final LocalDateTime ultimoLogin;

    public Usuario(Builder builder) {
        this.ID = builder.ID;
        this.usuario = builder.usuario;
        this.passwordHash = builder.passwordHash;
        this.rol = builder.rol;
        this.ultimoLogin = builder.ultimoLogin;
    }

    public long ID() {
        return ID;
    }

    public String usuario() {
        return usuario;
    }

    public String passwordHash() {
        return passwordHash;
    }

    public UsuarioRol rol() {
        return rol;
    }

    public LocalDateTime ultimoLogin() {
        return ultimoLogin;
    }

    public static class Builder {
        private long ID;
        private String usuario;
        private String passwordHash;
        private UsuarioRol rol;
        private final LocalDateTime ultimoLogin;

        public Builder() {
            this.ultimoLogin = LocalDateTime.now();
        }

        public Builder ID(long ID) {
            this.ID = ID;
            return self();
        }

        public Builder usuario(String usuario) {
            this.usuario = usuario;
            return self();
        }

        public Builder passwordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return self();
        }

        public Builder rol(UsuarioRol rol) {
            this.rol = rol;
            return self();
        }

        public Builder self() {
            return this;
        }

        public Usuario build() {
            return new Usuario(this);
        }
    }
}
