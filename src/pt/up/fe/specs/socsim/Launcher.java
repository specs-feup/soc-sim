package pt.up.fe.specs.socsim;

import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.model.dpi.DPI;
import pt.up.fe.specs.socsim.model.register.Register;
import pt.up.fe.specs.socsim.parser.ModuleParser;

import java.io.IOException;
import java.util.List;

public class Launcher {
    public static void main(String[] args) throws IOException {

        String resource = "/config.json";

        Module module = ModuleParser.parse(resource);

        System.out.println("Module name: " + module.name());
        System.out.println("Module REG: " + module.interfaces().reg());
        System.out.println("Module OBI Slave: " + module.interfaces().obiSlave());
        System.out.println("Module OBI Master: " + module.interfaces().obiMaster());

        List<Register> registers = module.registers();
        registers.forEach(reg -> System.out.println(
            "Register name: " + reg.name() + ", type: " + reg.type().toString() + ", width: " + reg.width() + ", initial: " + reg.initial())
        );

        DPI dpi = module.dpi();
        System.out.println("DPI send: ");
        dpi.send().forEach(System.out::println);

        System.out.println("DPI recv: ");
        dpi.recv().forEach(System.out::println);
    }
}
