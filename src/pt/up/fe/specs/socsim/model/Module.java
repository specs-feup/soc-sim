package pt.up.fe.specs.socsim.model;

import pt.up.fe.specs.socsim.model.interfaces.Interface;

import java.util.List;

public record Module(String name, String description, List<Interface> interfaces) { }
